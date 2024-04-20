package fr.leroideskiwis.planning.anime;

import fr.leroideskiwis.Day;
import fr.leroideskiwis.planning.PlanningElement;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class Anime implements PlanningElement {

    private final String name;
    private final Day day;
    private final int hour;
    private final int minutes;

    private Anime(String name, Day day, int hour, int minutes){
        this.name = name;
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
    }

    @Override
    public int getDuration() {
        return 24;
    }

    @Override
    public boolean isDay(Day day) {
        return this.day == day;
    }

    @Override
    public boolean isHour(int hour) {
        return hour == this.hour;
    }

    @Override
    public int minutes() {
        return minutes;
    }

    @Override
    public String name() {
        return name;
    }

    private String beautifyTime(int time){
        return time < 10 ? "0" + time : String.valueOf(time);
    }

    @Override
    public String time() {
        return String.format("%s:%s", beautifyTime(hour), beautifyTime(minutes));
    }

    @Override
    public String nextOcurrence() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextOccurrence = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.valueOf(day.toString())))
                .withHour(hour)
                .withMinute(minutes)
                .withSecond(0)
                .withNano(0);
        if (!now.isBefore(nextOccurrence)) {
            nextOccurrence = nextOccurrence.plusDays(7);
        }
        long daysUntilNextOccurrence = now.until(nextOccurrence, ChronoUnit.DAYS);
        long hoursUntilNextOccurrence = now.until(nextOccurrence, ChronoUnit.HOURS) % 24;
        long minutesUntilNextOccurrence = now.until(nextOccurrence, ChronoUnit.MINUTES) % 60;
        return String.format("%d days, %d hours, and %d minutes", daysUntilNextOccurrence, hoursUntilNextOccurrence, minutesUntilNextOccurrence);
    }

    public static class AnimeBuilder{
        private String name;
        private Day day;
        private int hour;
        private int minutes;

        public AnimeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AnimeBuilder day(Day day) {
            this.day = day;
            return this;
        }

        public AnimeBuilder hour(int hour) {
            this.hour = hour;
            return this;
        }

        public AnimeBuilder minutes(int minutes) {
            this.minutes = minutes;
            return this;
        }

        public Anime build(){
            return new Anime(name, day, hour, minutes);
        }
    }
}
