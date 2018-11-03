package tw.Utils;

public class RandomStringUtils {

    public static String random(int size) {
        String rs = "";
        for (int j = 0; j < size; j++) {
            char randomLetter = (char) ('a' + Math.random() * ('z'-'a' + 1));
            rs += randomLetter;
        }

        return rs;
    }

}
