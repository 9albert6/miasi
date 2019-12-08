package pl.miasi.destinationlist;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class CSVLoaderTest {

    private CSVLoader csvLoader = new CSVLoader();
    private static final String TEST_RESOURCES_PATH = "target/test-classes/";
    private static final String TEST_FILE = "destination_list.csv";

    @Test
    void runBasicCountLettersTest() throws IOException {
        ArrayList<Destination> expectedDestinations = new ArrayList<>();
        expectedDestinations.add(new Destination("Spain", "Barcelona", "Hotel La Playa"));
        expectedDestinations.add(new Destination("Spain", "Valencia", "Acuarela Hotel"));
        expectedDestinations.add(new Destination("Italy", "Rome", "Novotel"));
        expectedDestinations.add(new Destination("Italy", "Rome", "NH Hotel"));
        expectedDestinations.add(new Destination("Italy", "Florence", "Hotel Monna Lisa"));
        testRead(TEST_FILE, expectedDestinations);
    }

    private void testRead(String filePath, ArrayList<Destination> expected) throws IOException {
        assertIterableEquals(expected, csvLoader.read(TEST_RESOURCES_PATH + filePath));
    }

}
