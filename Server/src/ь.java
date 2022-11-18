import data.BaseRepository;
import data.FileRepository;
import entities.File;

import java.io.IOException;

public class ь {

    public static void main(String [] args) {
        File file = new File();
        file.setAge(18);
        file.setPhoneNumber(3388472);
        file.setAddress("Михалова 5");
        file.setName("Кирилл");
        file.setLastName("Клименко");
        file.setId(1);
        file.setMiddleName("Николаевич");

        File file1 = new File();
        file1.setAge(19);
        file1.setPhoneNumber(3228477);
        file1.setAddress("Октябрьская 33");
        file1.setName("Алексей");
        file1.setLastName("Зубов");
        file1.setMiddleName("Максимович");
        file1.setId(2);

        File file2 = new File();
        file2.setAge(20);
        file2.setPhoneNumber(7262377);
        file2.setAddress("Минская 13");
        file2.setName("Анастасия");
        file2.setLastName("Мирова");
        file2.setMiddleName("Александровна");
        file2.setId(3);

        File file3 = new File();
        file3.setAge(2);
        file3.setPhoneNumber(9002371);
        file3.setAddress("Гикало 2");
        file3.setName("Мария");
        file3.setLastName("Кородкина");
        file3.setMiddleName("Алексеевна");
        file3.setId(4);

        BaseRepository baseRepository = new FileRepository();
        baseRepository.create(file);
        baseRepository.create(file1);
        baseRepository.create(file2);
        baseRepository.create(file3);

    }
}
