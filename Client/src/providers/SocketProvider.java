package providers;

import entities.File;
import serializer.ByteSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketProvider implements BaseProvider {

    private Socket client;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    private void connect() {
        try {
            String serverName = "localhost";
            int port = 6665;
            client = new Socket(serverName, port);
            OutputStream outToServer = client.getOutputStream();
            dataOutputStream = new DataOutputStream(outToServer);

            InputStream inFromServer = client.getInputStream();
            dataInputStream = new DataInputStream(inFromServer);
        } catch (Exception ex) {

        }
    }

    public SocketProvider() {
        connect();
    }

//    public void finalize() {
//        try {
//            client.close();
//        } catch (Exception ex) {
//
//        }
//
////statements like the closure of database connection
//    }

    public void update() {

    }

    public void create() {

    }

    public File read(int id) {
        try{
            dataOutputStream.writeUTF("READ");
            dataOutputStream.writeUTF(Integer.toString(id));

        }
        catch (Exception ex){

        }
        return null;
    }

    public ArrayList<String> readHeaders() {
        ArrayList<String> headers = new ArrayList<String>();
        try {
            dataOutputStream.writeUTF("READ_HEADERS");
            headers = (ArrayList<String>)ByteSerializer.deserialize(dataInputStream.readAllBytes());
        } catch (Exception ex) {

        }
        return headers;
    }
}
