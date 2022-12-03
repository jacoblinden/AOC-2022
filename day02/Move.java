public enum Move {
    ROCK(1),
    PAPER(2),
    SCISSOR(3);
    static {
        ROCK.weakAgainst = PAPER;
        ROCK.effectiveAgainst = SCISSOR;
        PAPER.weakAgainst = SCISSOR;
        PAPER.effectiveAgainst = ROCK;
        SCISSOR.weakAgainst = ROCK;
        SCISSOR.effectiveAgainst = PAPER;
    }
    private Move weakAgainst;
    private Move effectiveAgainst;
    private final int POINT_FOR_MOVE;

    Move(int point_for_move) {
        POINT_FOR_MOVE = point_for_move;
    }

    public static int match(Move m1, Move m2){
        int points;
        if (m1.effectiveAgainst == m2) {
            points = 6;
        }else if (m1.weakAgainst == m2)
            points = 0;
        else{
            points = 3;
        }
        return points + m1.POINT_FOR_MOVE;
    }

    public static int rigged(Move m, String condition){
        Move toMake = m;
        if(condition.equals("X")){
            toMake= m.effectiveAgainst;
        }else if(condition.equals("Z")){
            toMake = m.weakAgainst;
        }
        return match(toMake, m);
    }


    public  static Move of(String s){
        return switch (s){
            case "A","X" -> ROCK;
            case "B","Y" -> PAPER;
            case "C","Z" -> SCISSOR;
            default -> throw new IllegalArgumentException();
        };
    };
}





