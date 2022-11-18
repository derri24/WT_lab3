import providers.SocketProvider;
import serializer.ByteSerializer;
import entities.File;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static int chooseAdministratorActivity() {
        Scanner in = new Scanner(System.in);
        System.out.println("\nChoose activity: \n0.Read headers\n1.Read\n2.Create\n3.Update");
        int activity = in.nextInt();
        if (activity==0||activity == 1 || activity == 2 || activity == 3)
            return activity;
        else {
            System.out.println("Error! You should enter '0' or '1' or '2' or '3'");
            return chooseAdministratorActivity();
        }
    }

    public static int chooseClientActivity() {
        Scanner in = new Scanner(System.in);
        System.out.println("\nChoose activity: \n0.Read headers\n1.Read");
        int activity = in.nextInt();
        if (activity == 1)
            return activity;
        else {
            System.out.println("Error! You should enter '0' or '1'");
            return chooseClientActivity();
        }
    }

    private static int role;

    public static int chooseActivity() {
        Scanner in = new Scanner(System.in);
        //write headers
        role = in.nextInt();
        if (role == 1)
            return chooseAdministratorActivity();
        else if (role == 2)
            return chooseClientActivity();
        else {
            System.out.println("Error! You should enter '1' or '2'");
            return chooseActivity();
        }
    }

    private static void printHeaders(ArrayList<String> headers) {
        for (var header : headers)
            System.out.println(header);
    }

    private static int repeatChooseActivity() {
        int activity;
        if (role == 1)
            activity= chooseAdministratorActivity();
        else
            activity=chooseClientActivity();
        return activity;
    }

    private static void printFile(File file) {

        System.out.println("Id: " + file.getId() +
                "\nName: " + file.getName() +
                "\nLast name: " + file.getLastName() +
                "\nMiddle name: " + file.getMiddleName() +
                "\nAge: " + file.getAge() +
                "\nPhone number: " + file.getPhoneNumber() +
                "\nAddress: " + file.getAddress());
    }

    private static File updateFile() {
        Scanner sc = new Scanner(System.in);
        File file = new File();

        System.out.println("Input Id: ");
        file.setId(sc.nextInt());

        System.out.println("Input name: ");
        file.setName(sc.next());

        System.out.println("Input last name: ");
        file.setLastName(sc.next());

        System.out.println("Input middle name: ");
        file.setMiddleName(sc.next());

        System.out.println("Input age: ");
        file.setAge(sc.nextInt());

        System.out.println("Input phone number: ");
        file.setPhoneNumber(sc.nextInt());

        System.out.println("Input address: ");
        file.setAddress(sc.next());

        return file;
    }
    private static File createFile() {
        Scanner sc = new Scanner(System.in);
        File file = new File();
        System.out.println("Input file id for update: ");
        int id = sc.nextInt();
        file.setId(id);

        System.out.println("Input name: ");
        file.setName(sc.next());

        System.out.println("Input last name: ");
        file.setLastName(sc.next());

        System.out.println("Input middle name: ");
        file.setMiddleName(sc.next());

        System.out.println("Input age: ");
        file.setAge(sc.nextInt());

        System.out.println("Input phone number: ");
        file.setPhoneNumber(sc.nextInt());

        System.out.println("Input address: ");
        file.setAddress(sc.next());

        return file;
    }


    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.println("Hello!This program allows you to work with students' personal files.\nChoose your role(write number): \n1.Administrator\n2.Client");
        var activity = chooseActivity();
        SocketProvider socketProvider = new SocketProvider();

        while (activity!=4){
            if (activity == 0) {
                var headers = socketProvider.readHeaders();
                System.out.println("List of files: ");
                printHeaders(headers);
                activity= repeatChooseActivity();
            }
            if (activity == 1) {
                System.out.println("Input file id: ");
                int id = in.nextInt();
                var file = socketProvider.read(id);
                printFile(file);
                activity = repeatChooseActivity();
            } else if (activity == 2) {
                var file = createFile();
                socketProvider.create(file);
                System.out.println("Success creating!");
                activity = repeatChooseActivity();
            } else if (activity == 3) {

                var file = updateFile();
                socketProvider.update(file);
                System.out.println("Success updating!");
                activity =  repeatChooseActivity();
            }
        }

       // while (true) ;
    }
}

