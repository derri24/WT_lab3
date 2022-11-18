import data.BaseRepository;
import data.FileRepository;
import entities.File;
import serializers.ByteSerializer;
import java.net.*;
import java.io.*;

public class Server extends Thread {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000000);
    }

    public void run() {
        BaseRepository baseRepository = new FileRepository();
        Socket server;
        try {
            server = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(true) {
            try {
                DataInputStream in = new DataInputStream(server.getInputStream());
                var activity = in.readUTF();
                if (activity.equals("READ_HEADERS")){
                    var headers=ByteSerializer.serialize(baseRepository.readHeaders());
                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    out.writeInt(headers.length);
                    out.write(headers);
                }
                else if(activity.equals("READ")){
                    var id = in.readInt();
                    var file= ByteSerializer.serialize(baseRepository.read(id));
                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    out.writeInt(file.length);
                    out.write(file);
                }
                else if (activity.equals("CREATE")){
                    int length = in.readInt();
                    if(length>0) {
                        byte[] result = new byte[length];
                        in.readFully(result, 0, result.length);
                        var file = (File)ByteSerializer.deserialize(result);
                        baseRepository.create(file);
                    }
                }
                else if (activity.equals("UPDATE")){
                    int length = in.readInt();
                    if(length>0) {
                        byte[] result = new byte[length];
                        in.readFully(result, 0, result.length);
                        var file = (File)ByteSerializer.deserialize(result);
                        baseRepository.update(file);
                    }
                }

            } catch (SocketTimeoutException s) {
                System.out.println("Время сокета истекло!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String [] args) {
        int port = 6666;
        try {
            Thread thread = new Server(port);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}