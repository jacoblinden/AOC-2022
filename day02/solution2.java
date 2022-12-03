import java.io.BufferedReader;
import java.io.FileReader;

public class solution2{
    public static void main(String []args) throws Exception {
        try (BufferedReader in = new BufferedReader(new FileReader("input.txt"))) {
            int value = in.lines().parallel().map((s) -> {
                var moves = s.split(" ");
                return Move.match(Move.of(moves[1]), Move.of(moves[0]));
            }).reduce(Integer::sum).get();
            System.out.println(value);
        }
        try (BufferedReader in = new BufferedReader(new FileReader("input.txt"))) {
            int value= in.lines().map(s -> {
                var moves = s.split(" ");
                return Move.rigged(Move.of(moves[0]), moves[1]);
            }).reduce(Integer::sum).get();
            System.out.println(value);
        }
    }
    }

