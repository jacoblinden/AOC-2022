import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.System.getenv;

public class solution7 {

    public static void main(String []args) throws Exception {
        final Stream<String> line = Files.lines(Path.of("day07/input.txt"));
        System.out.println("part2".equalsIgnoreCase(getenv("part")) ?
                part2(line) :
                part1(line));
    }
    static String addition ="";
    static command  now = null;
    static Node root  = new Node(null,0,"root");
    static AtomicReference<Node> current = new AtomicReference<>(root);


    static Node getFileSystem(Stream<String> line){
        line.forEach(l -> {
            if(l.startsWith("$") && now == command.LIST) {
                handleCommand(addition, current.get());
                now = command.of(l);
                handleCommand(l, current.get());
            }else if(l.startsWith("$") && !l.contains("ls")){
                now = command.of(l);
                current.set(now.getCommandFunction().apply(current.get(), l));
            }else if(l.contains("$ ls")){
                now = command.of(l);
            }else{ addition +="\n" + l;}
        });
        if(!addition.isBlank()) handleCommand(addition, current.get());
        return root;
    }
    static String part1(Stream<String> line){
        var node = getFileSystem(line);
        return node.children.stream().map(Node::subDir).flatMap(List::stream).toList().stream().filter(n ->!(n instanceof leafNode)).map(Node::sizeOfChildren).filter(i -> i<= 100000).reduce(Integer::sum) +"";
    }
    static void handleCommand(String currentLine, Node n){
        command c = command.of(currentLine);
        current.set(now.getCommandFunction().apply(n,currentLine));
        now= c;
        addition ="";

    }

    static String part2(Stream<String>lines){
        var node = getFileSystem(lines);
        int spaceToFree = 30000000 - (70000000 -node.sizeOfChildren()  );
        int min = node.children.stream().map(Node::subDir).flatMap(List::stream).filter(n ->!(n instanceof leafNode)).map(Node::sizeOfChildren).filter(i -> i >=spaceToFree).reduce(Integer::min).get();
        return "" + min;
    }
}
enum command{
    BACK{
        BiFunction<Node, String, Node> getCommandFunction(){
            return ((current, addition) ->{
                return current.parent;
            });
        }
    },INTO{
        BiFunction<Node, String, Node> getCommandFunction(){
            return ((current,addition) ->{
                Node next;
                String [] lines = addition.split("\n");
                String name = lines[lines.length -1].replace("$ cd ","");
                OptionalInt result = IntStream.range(0, current.children.size())
                        .filter(x -> name.equals(current.children.get(x).name))
                        .findFirst();
                if(result.isPresent()){
                    next = current.children.get(result.getAsInt());
                }else {
                    next = new Node(current, 0,name);
                    current.children.add(next);
                }
                return next;
            });
        }
    },LIST{
        BiFunction<Node, String, Node> getCommandFunction(){
            return ((current,addition) ->{
                Stream<String> allLines= Arrays.stream(addition.trim().split("\n"));
                allLines.map(s-> {
                    Node n;
                    if(s.contains("dir")){
                        String name = s.substring(4);
                        n = new Node(current,0,name);
                    }else {
                        String numbers = s.replaceAll("[^0-9]","");
                        String name = s.replaceAll("[0-9 $]*","");
                        int size= Integer.parseInt(numbers);
                        n = new leafNode(current,size,name);
                    }
                    n.parent = current;
                    return n;
                }).forEach(current.children::add);
                return current;
            });
        }
    },ROOT{
        BiFunction<Node, String, Node> getCommandFunction(){
            return ((current,next) ->{
                return current;
            });
        }
    };
    static command of(String s){
        return switch(s){
            case "$ cd .." -> BACK;
            case "$ ls" -> LIST;
            case "$ cd /" -> ROOT;
            default -> INTO;
        };
    }
    abstract BiFunction<Node,String,Node> getCommandFunction();
    }

class Node {
    String name;
    Node parent;
    List<Node> children;
    int size;
    public int sizeOfChildren(){
        int childrenSize = children.stream()
                .map(Node::sizeOfChildren)
                .reduce(Integer::sum).orElse(0);
        return  childrenSize;
    }


    public List<Node> subDir(){
        var subDirs = new ArrayList<>(children.stream()
                .filter(n -> !(n instanceof leafNode)).map(Node::subDir).flatMap(List::stream).toList());
        subDirs.add(this);
        return  subDirs;
    }
    public Node(Node parent, int size, String name) {
        this.parent = parent;
        this.name = name;
        this.size = size;
        children = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name) && Objects.equals(parent, node.parent);
    }

    @Override
    public String toString() {
        String c = children.stream().map(Node::toString).reduce((s1,s2) -> s1 + s2).orElse("");
        return String.format("""
                   %s (dir)
                              %s""",name,c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parent);
    }
}
class leafNode extends Node {
    public leafNode(Node parent, int size, String name) {
        super(parent, size,name);
    }
    public int sizeOfChildren(){
        return size;
    }
    @Override
    public String toString() {
        return String.format("""
                        - %s %d
                        """,name,size);
    }
}

