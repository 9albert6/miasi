package pl.miasi.destinationlist;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class CSVLoader {

    public ArrayList<Destination> read(String csvFilePath) {
        CSVReader reader = null;
        ArrayList<Destination> destinations = new ArrayList<>();
        try {
            reader = new CSVReader(new FileReader(csvFilePath));
            String[] line;
            while ((line = reader.readNext()) != null) {
                destinations.add(new Destination(line[0], line[1], line[2]));
                System.out.println("Destination [country = " + line[0] + ", city = " + line[1] + ", hotel = " + line[2] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destinations;
    }

}
