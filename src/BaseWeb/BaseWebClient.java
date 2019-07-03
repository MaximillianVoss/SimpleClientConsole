package BaseWeb;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

public class BaseWebClient extends Thread {
    public ServerSocket welcomeSocket;// Сокет на порту this.port
    public ServerSocket serverSocket;//
    public Socket socket;
    public ObjectInputStream inStream;
    public ObjectOutputStream outStream;
    public String folderName;
    public String username;
    public char delimeter;
    public String address;
    public int port;
    public boolean started;
    public int timeOut;
    public State currentState;

    public enum State {
        waiting,
        compareList,
        deletingFiles,
        createFile,
        getFiles,
        sendFiles,
        checkFiles,
        close
    }

    public BaseWebClient(Socket socket) {
        this.socket = socket;
    }

    public BaseWebClient() throws Exception {
        this(8080, "/Folder/");
    } //?

    public BaseWebClient(int port, String folderName) throws Exception {
        //this.welcomeSocket = null;//
        //this.serverSocket = null;
        this.socket = null; // по умолчанию
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
        //this.outStream.close();
    }

    public Object Get() throws Exception {
        this.inStream = new ObjectInputStream(this.socket.getInputStream());
        Object object = this.inStream.readObject();
        //this.inStream.close();
        return object;
    }

    public void PrintMesage(String message) {
        System.out.println(message);
    }

    public ArrayList<String> GetDifference(File[] files1, File[] files2) {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> fileNames1 = new ArrayList<String>();
        ArrayList<String> fileNames2 = new ArrayList<String>();
        for (File file : files1)
            fileNames1.add(file.getName());
        for (File file : files2)
            fileNames2.add(file.getName());
        for (int i = 0; i < fileNames1.size(); i++) {
            if (fileNames2.remove(fileNames1.get(i)))
                fileNames1.remove(fileNames1.get(i));
        }
        for (int i = 0; i < fileNames2.size(); i++) {
            if (fileNames1.remove(fileNames2.get(i)))
                fileNames2.remove(fileNames2.get(i));
        }
        list.addAll(fileNames1);
        list.addAll(fileNames2);
        return list;
    }

    public File[] GetDirInfo(String path) {
        File folder = new File(path);
        return folder.listFiles();
    }

    public void run() {
        //Child implement here
    }
}
