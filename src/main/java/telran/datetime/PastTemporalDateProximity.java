package telran.datetime;

import telran.utils.Arrays;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import static java.time.temporal.ChronoUnit.*;

public class PastTemporalDateProximity implements TemporalAdjuster
{
    Temporal[] temporal = {};

    public Temporal[] getTemporal()
    {
        return this.temporal;
    }

    public void addTemporal(Temporal temp)
    {
        this.temporal = Arrays.insert(this.temporal, temp);
    }

    public void removeTemporal(int index)
    {
        this.temporal = Arrays.remove(this.temporal, index);
    }

    public void getNearestBefore(Temporal current)
    {
        int delta = Integer.MAX_VALUE;
        for (Temporal tmp : this.temporal) {
            System.out.println(tmp.until(current, DAYS));
        }
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        long delta = Integer.MAX_VALUE;
        Temporal result = null;
        for (Temporal tmp : this.temporal) {
            long tmp_delta = tmp.until(temporal, DAYS);
            if (tmp_delta > 0 && tmp_delta <= delta) {
                delta = tmp_delta;
                result = tmp;
            }
        }
        return result;
    }
}
