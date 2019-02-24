import java.io.IOException;

public class Main {
    private static String input = "/Users/anatolii.stepaniuk/Education/google_hashcode_pizza_2019/src/main/resources/input/a_example.in";
    private static String output = "/Users/anatolii.stepaniuk/Education/google_hashcode_pizza_2019/src/main/resources/output/a_example.in";
    private static PizzaReader pizzaReader = new PizzaReader(input);
    private static ResultWriter resultWriter = new ResultWriter(output);
    public static void main(String[] args) throws IOException {
        Pizza pizza = pizzaReader.read();

        pizza.generateSlices();

        resultWriter.write(pizza.slices);
    }
}
