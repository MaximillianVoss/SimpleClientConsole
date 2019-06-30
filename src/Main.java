import Client.Client;
import Server.Server;

public class Main {
    public static void main(String[] args) {
        try {
            int port =8081;
            Server server = new Server(port,System.getProperty("user.dir") + "/src/Server/");
            Client client = new Client("localhost", port,"Client1", System.getProperty("user.dir") + "/src/Client/" + "ClientFiles1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
