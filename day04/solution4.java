import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.lang.System.getenv;

public class solution4 {

    public static void main(String []args) throws Exception {
        final Stream<String> lines = Files.lines(Path.of("input.txt"));
        System.out.println("part2".equalsIgnoreCase(getenv("part")) ? task1(lines) : task2(lines));
    }

     static long task1(final Stream<String> lines) {
        return lines.filter(s ->{
            int[] numbers =  extractNumbers(s);
            return (numbers[0] <= numbers[2] && numbers[1] >= numbers[3]|| (numbers[0] >= numbers[2] && numbers[1]<= numbers[3]));
        }).count();
    }

    static int [] extractNumbers(String s){
        var split = s.split(",");
        var first = split[0].split("-");
        var second = split[1].split("-");
        return new int[]{Integer.parseInt(first[0]),Integer.parseInt(first[1]),Integer.parseInt(second[0]),Integer.parseInt(second[1])};
    }

    static BiFunction<Integer,Integer,List<Integer>>  createInterval(){
        return (i1,i2) -> IntStream.rangeClosed(i1,i2).boxed().toList();
    }

     static long task2(final Stream <String> lines){
         return lines.map(s ->{
             var arr = extractNumbers(s);
             List <Integer> l1 = new ArrayList<Integer>(createInterval().apply(arr[0],arr[1]));
             var l2 = createInterval().apply(arr[2],arr[3]);
            return l1.removeAll(l2);
         }).filter(b -> b).count();
     }
}



