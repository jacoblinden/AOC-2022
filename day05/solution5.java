import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import static java.lang.Integer.*;
import static java.lang.System.getenv;
public class solution5 {
    public static void main(String []args) throws Exception {
        final List<String> lines = Files.readAllLines(Path.of("input.txt"));
        System.out.println("part2".equalsIgnoreCase(getenv("part")) ? task1(lines) : task2(lines));
    }

    record Stockpile(List<ArrayDeque>stockpile){
        public void moveCrate(Instruction in){
            IntStream.range(0,in.amount).forEach(i -> {
                var itemToMove = stockpile.get(in.from-1).pollLast();
                stockpile.get(in.to-1).offerLast(itemToMove);
            });
        }

        public void moveCrateImproved(Instruction in) {
            var itemsToMove= new ArrayDeque<Object>();
            IntStream.range(0, in.amount).forEach(i -> {
                var itemToMove = stockpile.get(in.from-1).pollLast();
                itemsToMove.addFirst(itemToMove);
            });
            itemsToMove.forEach(move -> stockpile.get(in.to-1).offerLast(move));
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            stockpile.forEach(l-> {
                sb.append(l.peekLast());
            });
            return sb.toString();
        }
    }

    record Instruction(int from, int to,int amount){
        public Instruction(String [] arr){
            this(parseInt(arr[1]),  parseInt(arr[2]), parseInt(arr[0]));
        }
    }

    static String [] findMatches(String s, Pattern p){
        var result = p.matcher(s.replace("    ","[0]") )
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);
        return result;
    }

    static List <ArrayDeque> createStack(List<String []> letters){
        var result = new ArrayList<ArrayDeque>();
        for(int i = 0; i < letters.size(); i++ )
            for (int y = 0; y < letters.get(i).length; y++){
                if(result.size() -1 < y){
                    var stack = new ArrayDeque<String>();
                    result.add(stack);
                    if (letters.get(i)[y].equals("0")) continue;
                    stack.offerFirst(letters.get(i)[y]);
                }else{
                    if (letters.get(i)[y].equals("0")) continue;
                    result.get(y).offerFirst(letters.get(i)[y]);
                }
        }
        return  result;
    }

    static Stockpile createStockpile(List<String> lines){
        Stockpile sp = new Stockpile(createStack(lines.stream().takeWhile(s -> !s.contains("1")).map(s-> findMatches(s, Pattern.compile("(?<=\\[)(.*?)(?=\\])"))).toList()));
        return sp;
    }

    static List<Instruction>createInstructions(List <String> lines){
        Pattern p = Pattern.compile("(?<=move )\\d*|((?<= from )\\d*)|((?<= to )\\d*)");
        return lines.stream().map(s->findMatches(s,p)).filter(arr -> arr.length > 2).map(Instruction::new).toList();
    }

    static String task1(final List<String> lines) {
        Stockpile s = createStockpile(lines);
        List<Instruction> instructions = createInstructions(lines);
        instructions.stream().forEach(s::moveCrate);
        return s.toString();
    }

     static String task2(final List<String> lines){
         Stockpile s = createStockpile(lines);
         List<Instruction> instructions = createInstructions(lines);
         instructions.stream().forEach(s::moveCrateImproved);
         return s.toString();
     }
}



