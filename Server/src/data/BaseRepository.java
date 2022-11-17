package data;
import entities.File;
import java.util.ArrayList;

public interface BaseRepository {
    void create(File files);
    void update (File updFile);
    File read(int id);
    ArrayList<String> readHeaders();
}
