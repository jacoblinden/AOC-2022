import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.lang.System.getenv;

public class solution3 {

    public static void main(String []args) throws Exception {
        final Stream<String> lines = Files.lines(Path.of("day03/input.txt"));
        System.out.println("part2".equalsIgnoreCase(getenv("part")) ? task1(lines) : task2(lines));
    }

     static int task1(final Stream<String> lines) {
           return lines.map(sf -> {
               var s2 = sf.substring(sf.length() / 2).chars().boxed().collect(Collectors.toSet());
               return sf.substring(0, sf.length() / 2).chars()
                       .boxed()
                       .distinct()
                       .filter(c -> !s2.add(c))
                       .map(solution3::castToOrder)
                       .reduce(Integer::sum).get();
           }).reduce(Integer::sum).get();
        }

     static int task2(final Stream <String> lines){
        return -1;
     }

    private static int castToOrder(int i){
        if(i>= 97 ) return i -96;
        else return i - 38;
    }
}



