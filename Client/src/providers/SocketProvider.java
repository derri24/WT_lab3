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
            String serverName = "localhost";
            int port = 6666;
             client = new Socket(serverName, port);

        } catch (Exception ex) {

        }
    }

    public SocketProvider() {
        connect();
    }

    public void update(File file) {
        OutputStream outToServer = null;
        try {
            outToServer = client.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outToServer);
            dataOutputStream.writeUTF("UPDATE");

            var fileBytes= ByteSerializer.serialize(file);
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeInt(fileBytes.length);
            out.write(fileBytes);


        } catch (IOException e) {


        }
    }

    public void create(File file) {
        OutputStream outToServer = null;
        try {
            outToServer = client.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outToServer);
            dataOutputStream.writeUTF("CREATE");

            var fileBytes= ByteSerializer.serialize(file);
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeInt(fileBytes.length);
            out.write(fileBytes);


        } catch (IOException e) {


        }

    }

    public File read(int id) {
       File file = null;
        try {
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outToServer);
            dataOutputStream.writeUTF("READ");
            dataOutputStream.writeInt(id);

            InputStream inFromServer = client.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inFromServer);

            int length = dataInputStream.readInt();                    // read length of incoming message
            if(length>0) {
                byte[] result = new byte[length];
                dataInputStream.readFully(result, 0, result.length); // read the message
                file = (File)ByteSerializer.deserialize(result);
            }


        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return file;
    }



    public ArrayList<String> readHeaders() {
        ArrayList<String> headers = new ArrayList<String>();
        try{

            OutputStream outToServer = client.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outToServer);
            dataOutputStream.writeUTF("READ_HEADERS");

            InputStream inFromServer = client.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inFromServer);

            int length = dataInputStream.readInt();                    // read length of incoming message
            if(length>0) {
                byte[] result = new byte[length];
                dataInputStream.readFully(result, 0, result.length); // read the message

                headers = (ArrayList<String>)ByteSerializer.deserialize(result);
            }
        }
        catch (Exception ex){

        }

        return headers;
    }
}
