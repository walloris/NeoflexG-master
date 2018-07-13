package sample.csv;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import static sample.Main.filename;

public class MyCSVReader {
    public String[] read() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(Files.newBufferedReader(Paths.get("resources/" + filename)), ':');
            String[] line = reader.readNext();
            return line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
