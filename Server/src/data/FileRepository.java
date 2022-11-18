package data;

import entities.File;
import serializers.XmlSerializer;

import java.util.ArrayList;

public class FileRepository implements BaseRepository {

    public void update(File updFile) {
        var files = XmlSerializer.readAll();
        for (var file : files)
            if (file.getId() == updFile.getId()) {
                file.setName(updFile.getName());
                file.setMiddleName(updFile.getMiddleName());
                file.setLastName(updFile.getLastName());
                file.setAge(updFile.getAge());
                file.setPhoneNumber(updFile.getPhoneNumber());
                file.setAddress(updFile.getAddress());
            }
        XmlSerializer.createNew(files);
    }

    public void create(File file) {
        var files = XmlSerializer.readAll();
        file.setId(files.size()+1);
        files.add(file);
        XmlSerializer.createNew(files);
    }

    public File read(int id) {
        var files = XmlSerializer.readAll();
        for (var file : files)
            if (file.getId() == id)
                return file;
        return null;
    }

    public ArrayList<String> readHeaders() {
        var files = XmlSerializer.readAll();
        var headers = new ArrayList<String>();
        for (var file : files) {
            String header = file.getId() + " " + file.getLastName() + " " + file.getName() + " " + file.getMiddleName();
            headers.add(header);
        }
        return headers;
    }

}

