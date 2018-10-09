import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * ZonedDateTime is fully aware of DST.
 *
 * For an example, let's take a country where DST is fully observed, like Italy (UTC/GMT +2).
 *
 * In 2015, DST started in Italy on March, 29th and ended on October, 25th. This means that on:
 *
 * March, 26 2017 at 2:00:00 A.M. clocks were turned forward 1 hour to
 * March, 26 2017 at 3:00:00 A.M. local daylight time instead
 * (So a time like March, 26 2015 2:30:00 A.M. didn't actually exist!)
 *
 * October, 29 2017 at 3:00:00 A.M. clocks were turned backward 1 hour to
 * October, 29 2017 at 2:00:00 A.M. local daylight time instead
 * (So a time like October, 29 2017 2:30:00 A.M. actually existed twice!)
 */
public class PolandMain {
    public static void main(String[] args) {

        LocalDateTime ldt = LocalDateTime.of(2017, 3, 26, 2, 30);
        System.out.println(ldt);

        // But if we create an instance of ZonedDateTime for Italy (notice that the format uses a city, not a country) and printed:
        ZonedDateTime zdt = ZonedDateTime.of(
                2017, 3, 26, 1, 30, 0, 0, ZoneId.of("Europe/Warsaw"));
        System.out.println(zdt);

        zdt = ZonedDateTime.of(
                2017, 3, 26, 2, 30, 0, 0, ZoneId.of("Europe/Warsaw"));
        System.out.println(zdt);


        zdt = ZonedDateTime.of(
                2017, 3, 26, 3, 30, 0, 0, ZoneId.of("Europe/Warsaw"));
        System.out.println(zdt);


        // But be careful. We have to use a regional ZoneId,
        // a ZoneOffset won't do the trick because this class doesn't have the zone rules information to account for DST:

        ZonedDateTime zdt1 = ZonedDateTime.of(
                2017, 3, 26, 2, 30, 0, 0, ZoneOffset.ofHours(2));
        ZonedDateTime zdt2 = ZonedDateTime.of(
                2017, 3, 26, 2, 30, 0, 0, ZoneId.of("UTC+2"));
        System.out.println(zdt1); System.out.println(zdt2);

        //        When DST ends, something similar happens

        ldt = LocalDateTime.of(2017, 10, 29, 3, 30);
        System.out.println(ldt);

        // When we create an instance of ZonedDateTime for Italy, we have to add an hour to see the effect
        // (otherwise we we'll be creating the ZonedDateTime at 3:00 of the new time)

        zdt = ZonedDateTime.of(
                2017, 10, 29, 2, 30, 0, 0, ZoneId.of("Europe/Warsaw"));
        zdt2 = zdt.plusHours(1);
        System.out.println(zdt);
        System.out.println(zdt2);


        // We also need to be careful when adjusting the time across the DST boundary with a version of the methods plus()
        //   and minus() that takes a TemporalAmount implementation, in other words, a Period or a Duration.
        // This is because both differ in their treatment of daylight savings time.

        // Consider one hour before the beginning of DST in Italy:

        zdt = ZonedDateTime.of(
                2017, 3, 26, 1, 0, 0, 0, ZoneId.of("Europe/Warsaw"));

//        System.out.println(zdt.plus(Duration.ofDays(1)));
//        System.out.println(zdt.plus(Period.ofDays(1)));
    }
}
