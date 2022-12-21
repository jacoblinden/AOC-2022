import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.System.getenv;

public class solution9 {

    public static void main(String []args) throws Exception {
        var line = Files.lines(Path.of("input.txt"));
        System.out.println(("part2".equalsIgnoreCase(getenv("part")))? new solution9().part1(line):
        new solution9().part2(line));
    }


static class Point {
        static int maxX,maxY;
        static int minX,minY;
        int x, y;
        final static Point [] POINTS = {new Point(Type.HEAD),new Point(Type.TAIL),};
        final static Point [] BODY = {new Point(Type.HEAD),new Point(Type.BODY),new Point(Type.BODY),new Point(Type.BODY),new Point(Type.BODY),new Point(Type.BODY),new Point(Type.BODY),new Point(Type.BODY),new Point(Type.BODY),new Point(Type.TAIL)};
        Type type;
        enum Type{VISITED("#"),TAIL("T"),HEAD("H"),START("s"),BODY("B");
            Type(String s) {
                rep =s ;
            }
        String rep;
            @Override
            public String toString() {
                return rep;
            }
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        if (x != point.x) return false;
        return y == point.y;
    }

    private Point(Type type){
            this.x = 100;
            this.y = 100;
            this.type =type;
    }
    public Point(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        if (x > maxX) maxX = x;
        else if(x < minX) minX = x;
        if (y > maxY) maxY = y;
        else if (y< minY) minY = y;
    }

    Point visitedCopy(){
            return new Point(x,y,Type.VISITED);
    }
}

    long part1(Stream<String> lines){
        List<Point> visited = new ArrayList<Point>();
        lines.map(s-> s.split(" ")).forEach(command -> move(command[0],Integer.parseInt(command[1]),visited,Point.POINTS));
        return visited.stream().filter(p -> p.type == Point.Type.VISITED).distinct().count();
    }

    long part2(Stream<String> lines){
        List<Point> visited = new ArrayList<Point>();
        lines.map(s-> s.split(" ")).forEach(command -> move(command[0],Integer.parseInt(command[1]),visited,Point.BODY));
        return visited.stream().filter(p -> p.type == Point.Type.VISITED).distinct().count() + 1;
    }

    int[] moveTail(List<Point> field, int velX, int velY, Point back, Point front, Point[]body){
        int distanceX =Math.abs(front.x - back.x);
        int distanceY = Math.abs(front.y - back.y);
        Point visited = back.visitedCopy();
        int newX = front.x - back.x;
        int newY = front.y - back.y;
        if(distanceX + distanceY > 3 ){
            back.y = back.y  +velY;
            back.x = back.x  +velX;
        }else if (distanceX ==2 ){
            back.x = back.x  +velX;
            back.y = front.y;
        }else if(distanceY == 2){
            back.y = back.y  +velY;
            back.x = front.x;
        }
        if (back.type == Point.Type.TAIL && !field.contains(visited)) {
            field.add(visited);
        }
        return new int[] {newX / Math.abs(newX== 0 ? 1 : newX),newY / Math.abs(newY==0 ? 1 : newY)};

    }

    List<Point> moveInDirection(List<Point> prevVisited, int velX, int velY, final Point[] body){
            IntStream.range(0,Math.abs(velX == 0 ? velY : velX)).forEach(i-> {
                int []vel = {velX / Math.abs(velX== 0 ? 1 : velX),velY / Math.abs(velY==0 ? 1 : velY)};
                body[0].x += vel[0];
                body[0].y += vel[1];
                for( int y = 1;  y< body.length; y++) {
                        vel = moveTail(prevVisited, vel[0],vel[1], body[y], body[y - 1],body);
                    }
            });
        return prevVisited;
    }

    List<Point> move(String direction, int velocity, List <Point> prevVisited,Point[] body ){
        switch (direction){
            case "R" -> moveInDirection(prevVisited, velocity,0,body);
            case "L" -> moveInDirection(prevVisited,velocity*-1,0,body);
            case "U" -> moveInDirection(prevVisited,0,velocity,body);
            case "D" -> moveInDirection(prevVisited, 0,-1*velocity,body);
            default ->{}
        }
        return prevVisited;
    }
}



