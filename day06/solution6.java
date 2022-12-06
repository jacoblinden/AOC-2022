import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static java.lang.System.getenv;

public class solution6 {

    public static void main(String []args) throws Exception {
        final String line = Files.readString(Path.of("input.txt"));
        System.out.println("part2".equalsIgnoreCase(getenv("part")) ?
                indexOfPattern(line, 4) :
                indexOfPattern(line, 14));
    }

    static int indexOfPattern(String s, int lettersToMatch){
        var m = Pattern.compile(buildRegex(lettersToMatch)).matcher(s);
        return m.find()? s.indexOf(m.group(0)) + lettersToMatch: -1;
    }

    static String buildRegex(int max){
        StringBuilder regex = new StringBuilder("((\\w)");
        for (int i = 1; i <= max-1; i++) {
            regex.append("(");
            for (int j = 2; j <= i + 1; j++) {
                regex.append("(?!\\").append(j).append(")");
            }
            regex.append("\\w)");
        }
        regex.append(")");
        return regex.toString();
    }
}



