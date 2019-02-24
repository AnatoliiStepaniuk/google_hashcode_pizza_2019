import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PizzaReader {

    private String fileName;

    public PizzaReader(String fileName) {
        this.fileName = fileName;
    }

    Pizza read() throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(fileName));
        String[] conditions = lines.get(0).split(" ");
        int rows = Integer.valueOf(conditions[0]);
        int columns = Integer.valueOf(conditions[1]);
        int minEach = Integer.valueOf(conditions[2]);
        int maxSliceSize = Integer.valueOf(conditions[3]);
        Pizza pizza = new Pizza(minEach, maxSliceSize, rows, columns);

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                pizza.isMushroom[r][c] = lines.get(r+1).charAt(c) == 'M';
            }
        }

        return pizza;
    }
}
