import Client.Client;
import Server.Server;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server(8081, "/src/Server/Files/");
            server.Start();
            Client client = new Client("localhost", 8081, "/src/Client/Files/");
            client.Start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
