import providers.SocketProvider;
import entities.File;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static int chooseAdministratorActivity() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nChoose activity: \n0.Exit\n1.Read headers\n2.Read\n3.Create\n4.Update");
        int activity = scanner.nextInt();
        if (activity == 0 || activity == 1 || activity == 2 || activity == 3 || activity == 4)
            return activity;
        else {
            System.out.println("Error! You should enter '0' or '1' or '2' or '3' or '4'");
            return chooseAdministratorActivity();
        }
    }

    public static int chooseClientActivity() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nChoose activity: \n0.Exit\n1.Read headers\n2.Read");
        int activity = scanner.nextInt();
        if (activity == 0 || activity == 1 || activity == 2)
            return activity;
        else {
            System.out.println("Error! You should enter '0' or '1' or '2'");
            return chooseClientActivity();
        }
    }

    private static int role;

    public static int chooseActivity() {
        Scanner scanner = new Scanner(System.in);
        role = scanner.nextInt();
        if (role == 1)
            return chooseAdministratorActivity();
        else if (role == 2)
            return chooseClientActivity();
        else {
            System.out.println("\nError! You should enter '1' or '2'");
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
            activity = chooseAdministratorActivity();
        else
            activity = chooseClientActivity();
        return activity;
    }

    private static void printFile(File file) {

        System.out.println("\nId: " + file.getId() +
                "\nName: " + file.getName() +
                "\nLast name: " + file.getLastName() +
                "\nMiddle name: " + file.getMiddleName() +
                "\nAge: " + file.getAge() +
                "\nPhone number: " + file.getPhoneNumber() +
                "\nAddress: " + file.getAddress());
    }

    private static File createFile() {
        Scanner scanner = new Scanner(System.in);
        File file = new File();

        System.out.println("\nEnter name: ");
        file.setName(scanner.next());

        System.out.println("Enter last name: ");
        file.setLastName(scanner.next());

        System.out.println("Enter middle name: ");
        file.setMiddleName(scanner.next());

        System.out.println("Enter the age: ");
        file.setAge(scanner.nextInt());

        System.out.println("Enter phone number: ");
        file.setPhoneNumber(scanner.nextInt());

        System.out.println("Enter address: ");
        file.setAddress(scanner.next());

        return file;
    }

    private static File updateFile() {
        Scanner scanner = new Scanner(System.in);
        File file = new File();
        System.out.println("\nEnter file id for update: ");
        int id = scanner.nextInt();
        file.setId(id);

        System.out.println("Enter name: ");
        file.setName(scanner.next());

        System.out.println("Enter last name: ");
        file.setLastName(scanner.next());

        System.out.println("Enter middle name: ");
        file.setMiddleName(scanner.next());

        System.out.println("Enter age: ");
        file.setAge(scanner.nextInt());

        System.out.println("Enter phone number: ");
        file.setPhoneNumber(scanner.nextInt());

        System.out.println("Enter address: ");
        file.setAddress(scanner.next());

        return file;
    }


    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        SocketProvider socketProvider = new SocketProvider();

        System.out.println("\nHello!This program allows you to work with students' personal files.\nChoose your role(write number): \n1.Administrator\n2.Client");
        var activity = chooseActivity();

        while (ActivityType.ConvertIntToActivityType(activity) != ActivityType.Exit) {
            if (ActivityType.ConvertIntToActivityType(activity) == ActivityType.ReadHeaders) {
                var headers = socketProvider.readHeaders();
                System.out.println("\nList of headers: ");
                printHeaders(headers);
                activity = repeatChooseActivity();
            } else if (ActivityType.ConvertIntToActivityType(activity) == ActivityType.Read) {
                System.out.println("\nEnter file id: ");
                int id = in.nextInt();
                var file = socketProvider.read(id);
                printFile(file);
                activity = repeatChooseActivity();
            } else if (ActivityType.ConvertIntToActivityType(activity) == ActivityType.Create) {
                var file = createFile();
                socketProvider.create(file);
                System.out.println("\nSuccess creating!");
                activity = repeatChooseActivity();
            } else if (ActivityType.ConvertIntToActivityType(activity) == ActivityType.Update) {
                var file = updateFile();
                socketProvider.update(file);
                System.out.println("\nSuccess updating!");
                activity = repeatChooseActivity();
            }
        }
    }
}

