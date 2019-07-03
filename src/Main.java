import Client.Client;
import Server.Server;

public class Main {
    public static void main(String[] args) {
        try {
            int port =8081;
            Server server = new Server(port,System.getProperty("user.dir") + "/src/Server/");
            Client client1 = new Client("localhost", port,"Client1",
                    System.getProperty("user.dir") + "/src/Client/" + "ClientFiles1");
            Client client2 = new Client("localhost", port,"Client2",
                    System.getProperty("user.dir") + "/src/Client/" + "ClientFiles2");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
