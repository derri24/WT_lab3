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

        while (true) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(server.getInputStream());
                var activity = dataInputStream.readUTF();
                if (activity.equals("READ_HEADERS")) {
                    var headers = ByteSerializer.serialize(baseRepository.readHeaders());
                    dataOutputStream.writeInt(headers.length);
                    dataOutputStream.write(headers);
                } else if (activity.equals("READ")) {
                    var id = dataInputStream.readInt();
                    var file = ByteSerializer.serialize(baseRepository.read(id));
                    dataOutputStream.writeInt(file.length);
                    dataOutputStream.write(file);
                } else if (activity.equals("CREATE")) {
                    int length = dataInputStream.readInt();
                    if (length > 0) {
                        byte[] result = new byte[length];
                        dataInputStream.readFully(result, 0, result.length);
                        var file = (File) ByteSerializer.deserialize(result);
                        baseRepository.create(file);
                    }
                } else if (activity.equals("UPDATE")) {
                    int length = dataInputStream.readInt();
                    if (length > 0) {
                        byte[] result = new byte[length];
                        dataInputStream.readFully(result, 0, result.length);
                        var file = (File) ByteSerializer.deserialize(result);
                        baseRepository.update(file);
                    }
                }

            } catch (SocketTimeoutException s) {
                System.out.println("Socket time expired!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        int port = 6666;
        try {
            Thread thread = new Server(port);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}