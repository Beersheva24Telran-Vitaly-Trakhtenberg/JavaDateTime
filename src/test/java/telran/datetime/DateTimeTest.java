package telran.datetime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Locale;
import java.util.Arrays;

public class DateTimeTest
{
    @Test
    void localDateTime()
    {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime currentTime = LocalDateTime.now();
        ZonedDateTime currentZonedTime = ZonedDateTime.now();
        Instant currentInstant = Instant.now();
        LocalTime localTime = LocalTime.now();

        System.out.printf("Current date is %s (in ISO format) \n", currentDate);
        System.out.printf("Current date & time is %s (in ISO format) \n", currentTime);
        System.out.printf("Current zoned date & time is %s (in ISO format) \n", currentZonedTime);
        System.out.printf("Current instant is %s (in ISO format) \n", currentInstant);
        System.out.printf("Current local time is %s (in ISO format) \n", localTime);

        System.out.printf("Current date is %s in dd/mm/yyyy \n",currentDate.format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy", Locale.forLanguageTag("he"))));
    }

    @Test
    void nextFriday13Test()
    {
        LocalDate current = LocalDate.of(2024, 8, 11);
        LocalDate expected = LocalDate.of(2024, 9, 13);
        TemporalAdjuster adjuster = new NextFriday13();

        assertEquals(expected, current.with(adjuster));
        assertThrows(RuntimeException.class, () -> LocalTime.now().with(adjuster));
    }

    @Test
    void pastTemporalDateProximityTest()
    {
        Temporal[] temporals = {
                LocalDate.of(2024, 4, 16),
                LocalDate.of(1971, 8, 16),
                LocalDate.of(2022, 11, 30),
        };
        Temporal[] one_item_temporals = {
                LocalDate.of(2024, 4, 16),
        };
        Temporal[] empty_temporals = {};

        PastTemporalDateProximity ptdp = new PastTemporalDateProximity(temporals);

        Temporal res = ptdp.adjustInto(LocalDate.of(2024, 8, 16));
        assertNull(res);

        res = ptdp.adjustInto(LocalDate.of(2024, 3, 16));
        assertEquals("2022-11-30", res.toString());

        res = ptdp.adjustInto(LocalDate.of(2024, 4, 16));
        assertEquals("2024-04-16", res.toString());

        res = ptdp.adjustInto(LocalDate.of(2022, 11, 30));
        assertEquals("2022-11-30", res.toString());

        ptdp = new PastTemporalDateProximity(one_item_temporals);
        res = ptdp.adjustInto(LocalDate.of(2024, 5, 16));
        assertNull(res);

        ptdp = new PastTemporalDateProximity(empty_temporals);
        res = ptdp.adjustInto(LocalDate.of(2024, 5, 16));
        assertNull(res);
    }
}
