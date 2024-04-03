package bidding.crew.repository.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

class TextFileLoaderTest {
    @Test
    void loadLines() throws FileNotFoundException {
        TextFileLoader fileLoader = new TextFileLoader("FlightsTest.txt");
        List<String> stringList = fileLoader.readFile();
        Assertions.assertEquals("✓ 04.30 REPORT BA0337 MRS",stringList.get(0));
        Assertions.assertEquals("✓ 04.40 REPORT BA0664 LCA", stringList.get(1));

    }

}