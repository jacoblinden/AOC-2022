import java.io.*;
import java.util.HashMap;
import java.util.List;

public class solution{
    public static void main(String []args) throws Exception{
        String path = "day01/input.txt";
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
            List<Integer> max = map.values().stream().sorted((x,z)-> z-x ).toList();
            System.out.println(max.get(0));
            System.out.println(max.get(0) + max.get(1) + max.get(2));
        }
    }
}