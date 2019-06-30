package Client;

import BaseWeb.BaseWebClient;

import java.net.Socket;

public class Client extends BaseWebClient {

    public Client() throws Exception {

    }

    public Client(String address, int port, String folderName) throws Exception {
        super(port, folderName);
        this.address=address;
    }

    public void  Start() {
        try {
            this.socket = new Socket(this.address, this.port);
            Object list = Get();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
