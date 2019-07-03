package Server;

import BaseWeb.BaseWebClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerItem extends BaseWebClient {
    private File lastCreatedFile = null;

    public ServerItem(Socket socket, String path) {
        super(socket);
        this.folderName = path;
        start();
    }


    @Override  //перезаписываем метод?
    public void run() {
        this.started = true;
        this.currentState = State.waiting;
        while (started) {
            try {
                sleep(1000); //приостановим поток на заданное время?
                State answer = (State) Get();
                switch (answer) {
                    case waiting:
                        PrintMesage("Server is waiting!");
                        if (this.username.length() > 0) {
                            PrintMesage("Directory for " + this.username + ":");
                            File folder = new File(this.folderName + this.username);
                            if (folder.exists()) {
                                File[] files = GetDirInfo(this.folderName + this.username);
                                for (File file : files)
                                    PrintMesage(file.getName());
                            }

                        }

                        break;
                    case compareList:
                        PrintMesage("Server get files list!");
                        this.username = (String) Get();
                        File folder = new File(this.folderName + this.username);
                        if (!folder.exists())
                            folder.mkdir();
                        Send(GetDirInfo(this.folderName + this.username));
                        break;
                    case deletingFiles:
                        ArrayList<String> diffence = (ArrayList<String>) Get();
                        for (String fileName : diffence) {
                            File file = new File(this.folderName + this.username + "/" + fileName);
                            if (file.exists()) {
                                PrintMesage("Server deleting file!");
                                file.delete();
                            }
                        }
                        break;
                    case createFile:
                        PrintMesage("Server create file!");
                        String filename = (String) Get();
                        File file = new File(this.folderName + this.username + "/" + filename);
                        file.createNewFile();
                        this.lastCreatedFile = file;
                        break;
                    case getFiles:
                        PrintMesage("Server getting files!");
                        byte[] bytes = (byte[]) Get();
                        FileOutputStream fo = new FileOutputStream(this.lastCreatedFile);
                        fo.write(bytes);
                        break;
                    case sendFiles:
                        PrintMesage("Server sending files!");
                        break;
                    case close:
                        PrintMesage("close");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
