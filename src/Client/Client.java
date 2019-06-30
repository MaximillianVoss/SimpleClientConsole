package Client;

import BaseWeb.BaseWebClient;

import java.io.File;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends BaseWebClient {

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
//            File[] serverFilesList = (File[])Get();
//            File folder = new File(System.getProperty("user.dir") + folderName);
//            File[] listOfFiles = folder.listFiles();
//            ArrayList<File> differece = GetDifference(serverFilesList,listOfFiles);
//            Send(differece);
            this.started = true;
            this.currentState = State.compareList;
            while (this.started) {
                sleep(1000);

                //Send(this.username);
                //File[] files = GetDirInfo(this.folderName);
                //Send(files);
                //ArrayList<File> differece = (ArrayList<File>) Get();
                //System.out.println(differece);
//                State answer = (State) Get();
                switch (this.currentState){
                    case compareList:
                        PrintMesage("comp list");
                        Send(State.compareList);
                        this.currentState=State.sendFiles;
                        break;
                    case sendFiles:
                        PrintMesage("send files");
                        ArrayList<File> differece = (ArrayList<File>) Get();
                        break;
                    case close:
                        PrintMesage("clse");
                        break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
