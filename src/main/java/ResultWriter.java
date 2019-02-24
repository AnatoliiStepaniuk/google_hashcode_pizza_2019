import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

public class ResultWriter {

    String fileName;

    public ResultWriter(String fileName) {
        this.fileName = fileName;
    }

    public void write(Collection<Area> slices) throws IOException {
        Path path = Paths.get(fileName);
        Files.write(path, (slices.size() + "\n").getBytes(), CREATE);
        for(Area slice : slices){
            Files.write(path, (slice.formatCoordinates() + "\n").getBytes(), APPEND);
        }
    }
}
