package serializers;

import entities.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public abstract class XmlSerializer {
    static String path = "files.xml";

    public static ArrayList<File> readAll() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            java.beans.XMLDecoder xmlDecoder = new java.beans.XMLDecoder(fileInputStream);
            var files = xmlDecoder.readObject();
            xmlDecoder.close();
            fileInputStream.close();
            return (ArrayList<File>) files;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public static void createNew(ArrayList<File> files) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            java.beans.XMLEncoder xmlEncoder = new java.beans.XMLEncoder(fileOutputStream);
            xmlEncoder.writeObject(files);
            xmlEncoder.close();
            fileOutputStream.close();
        } catch (Exception ex) {
        }
    }
}
