package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server  extends Thread{
    public ServerSocket socket= null;
    public  int port;
    public LinkedList<ServerItem> serverList = new LinkedList<ServerItem>();
    public boolean started;
    public String folderName;
    public Server() throws IOException {
        this(8081,System.getProperty("user.dir") + "/src/Server/");
    }

    public Server(int port,String path) throws IOException {
        this.port=port;
        this.socket = new ServerSocket(this.port);
        this.folderName =path;
        start();
    }

    public void run() {
        System.out.println("Сервер запущен!");
        this.started=true;
        try{
            while(this.started) {
                Socket socket = this.socket.accept();
                this.serverList.add(new ServerItem(socket,this.folderName));
            }
        }
        catch (Exception ex) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
