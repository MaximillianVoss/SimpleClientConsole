package Server;

import BaseWeb.BaseWebClient;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends BaseWebClient {

    public Server() throws  Exception{
        super();
    }

    public Server(int port,String folderName) throws  Exception {
        super(port,folderName);
        this.serverSocket= new ServerSocket(this.port);

    }

    public void Start() {
        Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Server started!");
                try {
                    socket = serverSocket.accept();

                    File folder = new File(System.getProperty("user.dir") + folderName);
                    File[] listOfFiles = folder.listFiles();
                    String filesList = "";
                    if (listOfFiles != null) {
                        Send(listOfFiles);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mainThread.start();

    }
}
