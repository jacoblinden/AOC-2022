import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import static java.lang.System.getenv;

public class solution{
        public static void main(String[] args) throws Exception {
            final Stream<String> lines = Files.lines(Path.of("input.txt"));
            System.out.println("part2".equalsIgnoreCase(getenv("part")) ? task1(lines) : task2(lines));
        }

        static List<Integer> solve(Stream<String> lines){
            var list = lines.toList();
                var map = new HashMap<Integer, Integer>();
                for (int i = 0,  y = 0; i < list.size(); i++){
                    if (list.get(i).isEmpty()) {
                        y++;
                        continue;
                    };
                    int value = Integer.parseInt(list.get(i));
                    if (map.containsKey(y)){
                        value =  Integer.parseInt(list.get(i)) + map.get(y);
                    }
                    map.put(y,value);
                }
                return  map.values().stream().sorted((x,z)-> z-x ).toList();
        }

        static int task1(Stream<String> lines) {
           return (solve(lines).get(0));
        }

        static int task2(Stream<String> lines) {
            var max = solve(lines);
            return max.get(0) + max.get(1) + max.get(2);
    }
}