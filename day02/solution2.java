import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.lang.System.getenv;

public class solution2{
    public static void main(String []args) throws Exception {
            final Stream<String> lines = Files.lines(Path.of("input.txt"));
            System.out.println("part2".equalsIgnoreCase(getenv("part")) ? new solution2().task1(lines) : new solution2().task2(lines));
        }

        int task1(Stream<String> lines) {
            return lines.parallel().map((s) -> {
                    var moves = s.split(" ");
                    return Move.match(Move.of(moves[1]), Move.of(moves[0]));
                }).reduce(Integer::sum).get();
        }

        int task2(Stream<String> lines) {
            return lines.parallel().map(s -> {
                    var moves = s.split(" ");
                    return Move.rigged(Move.of(moves[0]), moves[1]);
                }).reduce(Integer::sum).get();
            }
        }