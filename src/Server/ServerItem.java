package Server;

import BaseWeb.BaseWebClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.Socket;

public class ServerItem extends BaseWebClient {
    public ServerItem(Socket socket, String path) {
        super(socket);
        this.folderName = path;
        start();
    }


    @Override
    public void run() {
        this.started = true;
        this.currentState = State.waiting;
        while (started) {
            try {
                sleep(1000);


                State answer = (State) Get();
                switch (answer) {
                    case waiting:
                        this.currentState = (State) Get();
                    case compareList:
                        PrintMesage("Server get files list!");
                        String username = (String) Get();
                        File folder = new File(this.folderName + username);
                        if (!folder.exists())
                            folder.mkdir();
                        Send(GetDirInfo(this.folderName + username));
                        this.currentState = State.getFiles;
                        break;
                    case getFiles:
                        PrintMesage("Server getting files!");
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
