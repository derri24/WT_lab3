package providers;

import entities.File;

import java.util.ArrayList;

public interface BaseProvider {

    void update(File file);
    void create(File file);
    File read(int id);
    ArrayList<String> readHeaders();

}
