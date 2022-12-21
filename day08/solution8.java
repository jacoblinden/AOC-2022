import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static java.lang.System.getenv;

public class solution8 {

    public static void main(String []args) throws Exception {
        var line = Files.newBufferedReader(Path.of("day08/input.txt"));
        System.out.println("part2".equalsIgnoreCase(getenv("part")) ?
                new solution8().part1(line) :
                new solution8().part1(line));
    }


    class Tree{
        boolean isCounted= false;
        int height;

        public Tree(int height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "Tree{" +
                    "isCounted=" + isCounted +
                    ", height=" + height +
                    '}';
        }
    }

    int part1(BufferedReader reader) throws IOException {
        var forest= new ArrayList<ArrayList<Tree>>();
        int row =0; char c;
        forest.add(new ArrayList<>());
        while( (c = (char)reader.read()) != '\uFFFF'){
            if (c=='\n'){ row +=1; forest.add(new ArrayList<>());}
            else forest.get(row).add(new Tree(c-48));
        }
        var trees = IntStream.range(0,4).
                peek(System.out::println).
                mapToObj(i ->test(forest)).
                map(solution8::counttrees).toList();

        System.out.println(trees);

        return 1;
    }
    private static long counttrees(ArrayList<ArrayList<Tree>> forest){
        int i = 0;
        var nextRow = new ArrayList<Integer>();
        var maxHeightOfPreviousTree = new ArrayList<Integer>();
        var count=  0;
        for (var row : forest){
            if( forest.size()-1 == i) break;
            int treeNo = 0;
            for( Tree tree : row ){
                if(i == 0)
                if( i == 0 && !forest.get(i).get(treeNo).isCounted) {
                    count++;
                    forest.get(i).get(treeNo).isCounted =true;
                }
                    Tree nextTree = forest.get(i + 1).get(treeNo);
                    int currentMax = maxHeightOfPreviousTree.size() > treeNo ? maxHeightOfPreviousTree.get(treeNo) : 0;
                    int max = Math.max(tree.height, currentMax);
                    int newValue = (nextTree.height - max);
                    maxHeightOfPreviousTree.add(max);
                    nextRow.add(newValue);
                    if (newValue > 0 && !nextTree.isCounted) {
                        nextTree.isCounted = true;
                        count++;

                }

                treeNo++;
            }
            i++;
        }
        return count;
    }
    static void printList(ArrayList<ArrayList<Tree>> forest){
        forest.forEach(characters -> {
                String s = characters.stream().map(c -> c +"").reduce((s1,s2) -> s1 + "" +s2).get();
            System.out.println(s);
    });
        System.out.println();
    }

    static ArrayList<ArrayList<Tree>> rotateMatrix(ArrayList<ArrayList<Tree>> forest){
        for(int i=0; i<forest.size(); i++) {
            for(int j=i; j<forest.get(i).size(); j++) {
                if(i!=j) {
                    Tree temp = forest.get(i).get(j);
                    forest.get(i).set(j,forest.get(j).get(i));
                    forest.get(j).set(i,temp);
                }
            }
        }

        for(int i=0; i<forest.size(); i++) {
            int col = forest.get(i).size();
            for(int j=i; j<col/2; j++)
            {
                if(i!=j)
                {
                    Tree temp = forest.get(i).get(j);
                    forest.get(i).set(j,forest.get(i).get(col-j-1));
                    forest.get(i).set(col-j-1,temp);
                }
            }
        }
        return forest;
    }

    static ArrayList<ArrayList<Tree>> test(ArrayList<ArrayList<Tree>> forest){
        var temp = new ArrayList<ArrayList<Tree>>(forest);
        for(int i=0;i<forest.size();i++) {
            for(int j=forest.get(i).size()-1;j>=0;j--) {
                temp.get(i).set(j,forest.get(j).get(i));
                System.out.print(forest.get(j).get(i)+"\t");
            }
            System.out.println();
        }
        return  temp;
    }
}



