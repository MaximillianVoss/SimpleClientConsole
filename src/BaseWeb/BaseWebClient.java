package BaseWeb;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class BaseWebClient {
    public ServerSocket welcomeSocket;
    public ServerSocket serverSocket;
    public Socket socket;
    public ObjectInputStream inStream;
    public ObjectOutputStream outStream;
    public String folderName;
    public char delimeter;
    public String address;
    public int port;
    public boolean started;
    public int timeOut;

    public BaseWebClient() throws Exception {
        this( 8080, "/Folder/");
    }

    public BaseWebClient(int port, String folderName) throws Exception {
        this.welcomeSocket = null;
        this.serverSocket = null;
        this.socket = null;
        this.inStream = null;
        this.outStream = null;
        this.folderName = folderName;
        this.port = port;
        this.started = false;
        this.delimeter = ';';
        this.timeOut = 2000;
    }

    public void Connect(String address) throws Exception {
        InetAddress addr = InetAddress.getByName(address);
        SocketAddress sockaddr = new InetSocketAddress(addr, this.port);
        this.socket = new Socket();
        this.socket.connect(sockaddr, this.timeOut);
    }

    public void Send(Object object) throws Exception {
        this.outStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.outStream.writeObject(object);
        this.outStream.close();
    }

    public Object Get() throws Exception {
        this.inStream = new ObjectInputStream(this.socket.getInputStream());
        Object object = this.inStream.readObject();
        this.inStream.close();
        return object;
    }

    public void Start() {

    }
}
