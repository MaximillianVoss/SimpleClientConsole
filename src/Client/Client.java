package Client;

import BaseWeb.BaseWebClient;

import java.io.File;//для создания, поиска, удаления файлов и каталогов и т.д
import java.io.FileInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends BaseWebClient {

    private File[] prevList;

    public Client() throws Exception {

    }

    public Client(String address, int port, String username, String folderName) throws Exception {
        super(port, folderName);
        this.address = address;
        this.username = username;
        start();
    }


    public void run() {
        try {
            this.socket = new Socket(this.address, this.port);
            this.started = true;
            this.currentState = State.compareList;
            while (this.started) {
                sleep(1000);
                switch (this.currentState) {
                    case compareList:
                        PrintMesage(this.username + " compare files list");
                        Send(State.compareList);
                        Send(this.username);
                        this.currentState = State.sendFiles;
                        break;
                    case sendFiles:
                        PrintMesage(this.username + " send files");
                        File[] serverFiles = (File[]) Get();
                        File[] files = GetDirInfo(this.folderName);
                        ArrayList<String> difference = GetDifference(serverFiles, files);
                        Send(State.deletingFiles);
                        Send(difference);
                        for (String fileName : difference) {
                            File file = new File(this.folderName + "/" + fileName);
                            if (file.exists()) {
                                byte[] bytesArray = new byte[(int) file.length()];
                                FileInputStream fis = new FileInputStream(file);
                                fis.read(bytesArray);
                                fis.close();
                                Send(State.createFile);
                                Send(file.getName());
                                Send(State.getFiles);
                                Send(bytesArray);
                                PrintMesage(this.username + " send " + file.getName());
                            }
                        }
                        Send(State.waiting);
                        this.currentState = State.checkFiles;
                        this.prevList = GetDirInfo(this.folderName);
                        break;
                    case checkFiles:
                        if (GetDifference(GetDirInfo(this.folderName), this.prevList).size() > 0) {
                            this.currentState = State.compareList;
                            PrintMesage("Files change detected on " + this.username);
                        } else
                            PrintMesage("No files changes on " + this.username);
                        break;
                    case close:
                        PrintMesage("close");
                        break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
