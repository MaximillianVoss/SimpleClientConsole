package Server;

import java.io.IOException;
import java.net.ServerSocket;// для обмена данными между сервирами клиента?
import java.net.Socket;
import java.util.LinkedList; //представляет структуру данных в виде связанного списка?

public class Server  extends Thread{//расширяет класс Поток
    public ServerSocket socket= null;
    public  int port;
    public LinkedList<ServerItem> serverList = new LinkedList<ServerItem>();//?
    public boolean started;
    public String folderName;
    public Server() throws IOException {
        this(8081,System.getProperty("user.dir") + "/src/Server/");//возвращает список системных свойств? чтобы найти каталог
    }//конструктор, который инициализирует объект - задает начальное состояние (не то, которое задано по умолчанию)
    /*порт, это номер порта, который используется демон-сервером. Под порты выделено 16 бит. Обычно заранее оговаривается какой будет порт
    зачем инициализируем, если запускаем в main?
    */
    public Server(int port,String path) throws IOException {
        this.port=port;
        this.socket = new ServerSocket(this.port);
        this.folderName =path;
        start();// запускаем поток
    }
// где учитывается обработка исключений? (try -catch); в классе Server - поскольку этот метод может выкинуть ошибку, нужно проверить что он может ее выкинуть?
// Сокет. Оно обозначает точку, через которую происходит соединение.  Проще говоря, сокет соединяет в сети две программы. try-catch обрабатываем при создании сервера в main?
    public void run() {//Запускаем сервер и обертываем в try-catch поскольку в классе Server есть исключение
        System.out.println("Сервер запущен!");
        this.started=true;//?
        try{
            while(this.started) {
                Socket socket = this.socket.accept();// ожидание подключения клиента (на входе имеем что?)
                this.serverList.add(new ServerItem(socket,this.folderName));
            }
        }
        catch (Exception ex) {
            try {//определяет блок кода, в котором может произойти исключение
                this.socket.close();
            } catch (IOException e) {//определяет блок кода, в котором происходит обработка исключения
                e.printStackTrace();//помогает определить, какой метод вызывает ошибку.
            }
        }

    }
}
