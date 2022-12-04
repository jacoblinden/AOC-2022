import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.lang.System.getenv;
public class solution3 {
    public static void main(String []args) throws Exception {
        final Stream<String> lines = Files.lines(Path.of("input.txt"));
        System.out.println("part2".equalsIgnoreCase(getenv("part")) ? task1(lines) : task2(lines));
    }

     static int task1(final Stream<String> lines) {
           return lines.map(sf -> commonLetters(sf.substring(0, sf.length() / 2), sf.substring(sf.length() / 2)).get(0)).reduce(Integer::sum).get();
      }

     private static List<Integer> commonLetters(String... backpacks){
        String current = backpacks[0];
        Set <Integer> unique = current.chars().boxed().collect(Collectors.toSet());
        for (int i = 0; i < backpacks.length-1; i++){
            String next = backpacks[i+1];
            Set<Integer> finalUnique = unique;
            unique = next.chars()
                    .boxed()
                    .distinct()
                    .filter(c -> !finalUnique.add(c))
                    .collect(Collectors.toSet());
        };
        return unique.stream().map(solution3::castToOrder).toList();
     }

     static int task2(final Stream <String> lines){
        List<String> pairs = new ArrayList<>();
        var result = new ArrayList<Integer>();
        lines.toList().forEach(s -> {
            pairs.add(s);
            if(pairs.size() > 2) {
                result.addAll(commonLetters(pairs.get(0),pairs.get(1),pairs.get(2)));
                pairs.clear();
            }
        });
        return result.stream().reduce(Integer::sum).get();
     }

    private static int castToOrder(int i){
        if(i>= 97 ) return i -96;
        else return i - 38;
    }
}



