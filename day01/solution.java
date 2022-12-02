import java.io.*;
import java.util.Comparator;
import java.util.HashMap;

public class solution{
    public static void main(String []args) throws Exception{
        String path = "input.txt";
        try(BufferedReader in = new BufferedReader(new FileReader(path))){
            var list = in.lines().toList();
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
            int max = map.values().stream().max(Comparator.comparingInt(i -> i)).get();
            System.out.println(max);
        }
    }
}