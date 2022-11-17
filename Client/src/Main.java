import providers.SocketProvider;
import serializer.ByteSerializer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static int chooseAdministratorActivity() {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose activity: \n1.Read\n2.Create\n3.Update");
        int activity = in.nextInt();
        if (activity == 1 || activity == 2 || activity == 3)
            return activity;
        else {
            System.out.println("Error! You should enter '1' or '2' or '3'");
            return chooseAdministratorActivity();
        }
    }

    public static int chooseClientActivity() {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose activity: \n1.Read");
        int activity = in.nextInt();
        if (activity == 1)
            return activity;
        else {
            System.out.println("Error! You should enter '1'");
            return chooseClientActivity();
        }
    }

    private static int role;

    public static int chooseRole() {
        Scanner in = new Scanner(System.in);
        //write headers
        role = in.nextInt();
        if (role == 1)
            return chooseAdministratorActivity();
        else if (role == 2)
            return chooseClientActivity();
        else {
            System.out.println("Error! You should enter '1' or '2'");
            return chooseRole();
        }
    }

//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        System.out.println("Hello!This program allows you to work with students' personal files.\nChoose your role(write number): \n1.Administrator\n2.Client");
//        String serverName = "localhost";
//        int port = 6665;
//
//        Socket client = new Socket(serverName, port);
//        OutputStream outToServer = client.getOutputStream();
//        DataOutputStream out = new DataOutputStream(outToServer);
//        out.writeUTF("READ_HEADERS");
//
//            InputStream inFromServer = client.getInputStream();
//            DataInputStream in = new DataInputStream(inFromServer);
//
//           ByteArrayInputStream b = new ByteArrayInputStream(in.readAllBytes());
//           ObjectInputStream o = new ObjectInputStream(b);
//var a=o.readObject();
//        var activity = chooseRole();
//        if (activity == 1) {
//
//        } else if (activity == 2) {
//
//        } else if (activity == 3) {
//
//        }
//
//    }

    private static void printHeaders(ArrayList<String> headers) {
        for (var header : headers)
            System.out.println(header);
    }

    private static void repeatChooseActivity(){
        if (role == 1)
            chooseAdministratorActivity();
        else
            chooseClientActivity();
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Hello!This program allows you to work with students' personal files.\nChoose your role(write number): \n1.Administrator\n2.Client");
        var activity = chooseRole();
        SocketProvider socketProvider = new SocketProvider();
        var headers = socketProvider.readHeaders();
        System.out.println("List of files: ");
        printHeaders(headers);

        if (activity == 1) {
            System.out.println("Input file id: ");
            int id = in.nextInt();
           var file= socketProvider.read(id);
            repeatChooseActivity();
        } else if (activity == 2) {

            repeatChooseActivity();
        } else if (activity == 3) {

            repeatChooseActivity();
        }


//        String serverName = "localhost";
//        int port = 6665;
//        try {
//            Socket client = new Socket(serverName, port);
//            OutputStream outToServer = client.getOutputStream();
//            DataOutputStream out = new DataOutputStream(outToServer);
//
//            InputStream inFromServer = client.getInputStream();
//            DataInputStream in = new DataInputStream(inFromServer);
//
//            //
//            out.writeUTF("READ_HEADERS");
//
//            ArrayList<String> headers = (ArrayList<String>) ByteSerializer.deserialize(in.readAllBytes());
//            for (var header : headers)
//                System.out.println(header);
//            //
//            if (activity == 1) {
//                out.writeUTF("READ");
//                var ob=ByteSerializer.serialize(1);
//                out.write(ob);
//                var files = (ArrayList<String>) ByteSerializer.deserialize(in.readAllBytes());
//                for (var file : files) {
//
//                }
//                if (role == 1) {
//                    chooseAdministratorActivity();
//
//                } else {
//                    chooseClientActivity();
//                }
//
//
//            } else if (activity == 2) {
//                out.writeUTF("CREATE");
//
//            } else if (activity == 3) {
//                out.writeUTF("UPDATE");
//
//            }
//
//
//            client.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }
}
//    public static void main(String [] args) {
//        String serverName ="localhost";
//        int port = 6665;
//        try {
//            System.out.println("Подключение к " + serverName + " на порт " + port);
//            Socket client = new Socket(serverName, port);
//
//            System.out.println("Просто подключается к " + client.getRemoteSocketAddress());
//            OutputStream outToServer = client.getOutputStream();
//            DataOutputStream out = new DataOutputStream(outToServer);
//
//            out.writeUTF("Привет из " + client.getLocalSocketAddress());
//            InputStream inFromServer = client.getInputStream();
//            DataInputStream in = new DataInputStream(inFromServer);
//
//           ByteArrayInputStream b = new ByteArrayInputStream(in.readAllBytes());
//           ObjectInputStream o = new ObjectInputStream(b);
//var a=o.readObject();
//
//
//            System.out.println("Сервер ответил " + in.readUTF());
//           // client.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
