import data.BaseRepository;
import data.FileRepository;
import serializers.ByteSerializer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server extends Thread {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
       // while (true);
    }

    public void run() {
        while(true) {
            try {
               // System.out.println("Ожидание клиента на порт " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                //System.out.println("Просто подключается к " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                BaseRepository baseRepository = new FileRepository();

                var activity = in.readUTF();

                if (activity.equals("READ_HEADERS")){
                    var headers= ByteSerializer.serialize(baseRepository.readHeaders());
                    out.write(headers);
                }
                else if (activity.equals("READ")){
                    var id = in.readUTF();
                }


                //System.out.println(in.readUTF());

                //out.writeUTF("Спасибо за подключение  z xthdth к " + server.getLocalSocketAddress()
                      //  + "\nПока!");

               // server.close();

            } catch (SocketTimeoutException s) {
                System.out.println("Время сокета истекло!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String [] args) {
        //int port = Integer.parseInt();
        int port = 6665;
        try {
            Thread thread = new Server(port);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}