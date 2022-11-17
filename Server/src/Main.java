import data.BaseRepository;
import data.FileRepository;
import entities.File;

public class Main {

     public static void main(String[] args) {

         File file = new File();
         file.setName("sdf");
         file.setPhoneNumber(123456);
         file.setLastName("f");
         file.setMiddleName("qdf");
         file.setAddress("wsdr");
         file.setId(11);
//
//         File file1 = new File();
//         file1.setName("s111df");
//         file1.setPhoneNumber(123456);
//         file1.setLastName("111f");
//         file1.setMiddleName("qqqqdf");
//         file1.setAddress("w222sdr");
//         file1.setId(11);
//         ArrayList<File> files = new ArrayList<>();
//         files.add(file);
//         files.add(file1);

         BaseRepository baseRepository = new FileRepository();
        // baseRepository.create(file);

        var a=  baseRepository.read(11);
        //var aa=baseRepository.readFileModels();
        // baseRepository.create(file);
    }
}
