package providers;
import entities.File;
import serializer.ByteSerializer;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SocketProvider implements BaseProvider {
    private Socket client;

    private void connect() {
        try {
            client = new Socket("localhost", 6666);
        } catch (Exception ignored) {
        }
    }

    public SocketProvider() {
        connect();
    }

    public void update(File file) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            dataOutputStream.writeUTF("UPDATE");

            var fileBytes = ByteSerializer.serialize(file);
            dataOutputStream.writeInt(fileBytes.length);
            dataOutputStream.write(fileBytes);

        } catch (IOException ignored) {
        }
    }

    public void create(File file) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            dataOutputStream.writeUTF("CREATE");

            var fileBytes = ByteSerializer.serialize(file);
            dataOutputStream.writeInt(fileBytes.length);
            dataOutputStream.write(fileBytes);
        } catch (IOException ignored) {
        }

    }

    public File read(int id) {
        File file = null;
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            dataOutputStream.writeUTF("READ");
            dataOutputStream.writeInt(id);

            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            int length = dataInputStream.readInt();
            if (length > 0) {
                byte[] result = new byte[length];
                dataInputStream.readFully(result, 0, result.length);
                file = (File) ByteSerializer.deserialize(result);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }


    public ArrayList<String> readHeaders() {
        ArrayList<String> headers = new ArrayList<String>();
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            dataOutputStream.writeUTF("READ_HEADERS");

            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            int length = dataInputStream.readInt();                    // read length of incoming message
            if (length > 0) {
                byte[] result = new byte[length];
                dataInputStream.readFully(result, 0, result.length); // read the message
                headers = (ArrayList<String>) ByteSerializer.deserialize(result);
            }
        } catch (Exception ignored) {
        }
        return headers;
    }
}
