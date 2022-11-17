package providers;

import entities.File;

import java.util.ArrayList;

public interface BaseProvider {

    void update();
    void create();
    File read(int id);
    ArrayList<String> readHeaders();

}
