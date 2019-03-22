package threads;

import threads.utils.NumberConverter;
import threads.utils.Remover;

public class App {
    public static void main(String[] args) {
        Remover remover = new Remover();
        NumberConverter converter = new NumberConverter();

        try {
            remover.remover.join();
            converter.converter.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
