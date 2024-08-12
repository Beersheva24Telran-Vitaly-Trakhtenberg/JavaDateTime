package telran.datetime;

import java.util.Arrays;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

import static java.time.temporal.ChronoUnit.*;

public class PastTemporalDateProximity implements TemporalAdjuster
{
    Temporal[] temporals;

    public PastTemporalDateProximity(Temporal[] temporals)
    {
        this.temporals = temporals;
    }

    @Override
    public Temporal adjustInto(Temporal zerropoint_temporal) {
        Arrays.sort(this.temporals, new ComparatorTemporalDate());

        Temporal result = null;
        if (this.temporals.length > 0) {
            long delta = zerropoint_temporal.until(this.temporals[this.temporals.length - 1], DAYS);
            if (delta >= 0) {
                int res = Arrays.binarySearch(this.temporals, zerropoint_temporal, new ComparatorTemporalDate());
                if (res < 0) {
                    result = zerropoint_temporal.with(temporal -> this.temporals[-res - 2]);
                } else {
                    result = zerropoint_temporal.with(temporal -> this.temporals[res]);
                }
            }
        }

        return result;
    }
}
