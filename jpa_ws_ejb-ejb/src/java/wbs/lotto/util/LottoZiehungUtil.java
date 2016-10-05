package wbs.lotto.util;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class LottoZiehungUtil {

    public static Integer[] zufall6aus49() {

        Set<Integer> lottozahlen = new TreeSet<>();

        while (lottozahlen.size() < 6) {
            lottozahlen.add(new Random().nextInt(49) + 1);
        }
        return lottozahlen.toArray(new Integer[6]);
    }

    public static Integer zufallSpiel77() {


        return new Random().nextInt(9999999+1);
    }
    public static Integer zufallSuper6() {


        return new Random().nextInt(999999+1);
    }
    public static Integer zufallSuperzahl() {


        return new Random().nextInt(10);
    }
}
