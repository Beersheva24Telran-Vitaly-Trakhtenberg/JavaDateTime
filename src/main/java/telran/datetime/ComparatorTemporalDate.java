package telran.datetime;

import java.time.temporal.Temporal;
import java.util.Comparator;

import static java.time.temporal.ChronoUnit.DAYS;

public class ComparatorTemporalDate  implements Comparator<Temporal>
{
    @Override
    public int compare(Temporal o1, Temporal o2) {
        long delta = o2.until(o1, DAYS);
        return delta<0?-1:delta>0?1:0;
    }
}
