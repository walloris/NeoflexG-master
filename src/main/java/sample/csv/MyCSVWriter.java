package sample.csv;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

import static sample.Main.filename;

public class MyCSVWriter {
    public void write(String[] entries) {
        try {
            File f = new File("resources/" + filename);
            if (f.createNewFile()) System.out.println("Создан файл");
            else System.out.println("Файл существует");
            CSVWriter writer = new CSVWriter(new FileWriter(f), ':', CSVWriter.NO_QUOTE_CHARACTER);
            writer.writeNext(entries);
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
