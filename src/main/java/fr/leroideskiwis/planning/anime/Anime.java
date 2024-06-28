package fr.leroideskiwis.planning.anime;

import fr.leroideskiwis.Day;
import fr.leroideskiwis.planning.PlanningElement;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import static fr.leroideskiwis.util.Util.print;

public class Anime implements PlanningElement {

    private final String name;
    private final Day day;
    private final int hour;
    private final int minutes;
    private final int totalEpisodes;
    private final LocalDate firstAiring;

    private Anime(String name, Day day, int hour, int minutes, LocalDate firstAiring, int totalEpisodes){
        this.name = name;
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
        this.firstAiring = firstAiring;
        this.totalEpisodes = totalEpisodes;
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

    public String until(LocalDateTime now, LocalDateTime date){
        long daysUntilNextOccurrence = now.until(date, ChronoUnit.DAYS);
        long hoursUntilNextOccurrence = now.until(date, ChronoUnit.HOURS) % 24;
        long minutesUntilNextOccurrence = now.until(date, ChronoUnit.MINUTES) % 60;
        return String.format("%d days, %d hours, and %d minutes", Math.abs(daysUntilNextOccurrence), Math.abs(hoursUntilNextOccurrence), Math.abs(minutesUntilNextOccurrence));
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
        return until(now, nextOccurrence);
    }

    public LocalDateTime lastOccurenceDate(){
        return firstAiring.plusDays((totalEpisodes - 1) * 7L).atTime(hour, minutes);
    }

    public String lastOccurenceTime(){
        return until(LocalDateTime.now(), lastOccurenceDate());
    }

    @Override
    public void display(DisplayType type) {
        switch(type){
            case NEXT_OCCURENCE -> print("%s is airing in %s\n", name, nextOcurrence());
            case DAY -> print("\t%s at %s", name, time());
            case LAST_OCCURENCE -> print("%s last aired in %s (date: %s)\n", name, lastOccurenceTime(), formatDate(lastOccurenceDate()));
            default -> print("Not supported");
        }
    }

    private String formatDate(LocalDateTime date){
        return String.format("%s/%s/%s", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }

    public static class AnimeBuilder{
        private String name;
        private Day day;
        private int hour;
        private int minutes;
        private LocalDate firstAiring;
        private int totalEpisodes;

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

        public AnimeBuilder firstAiring(LocalDate firstAiring) {
            this.firstAiring = firstAiring;
            return this;
        }

        public AnimeBuilder totalEpisodes(int totalEpisodes) {
            this.totalEpisodes = totalEpisodes;
            return this;
        }

        public Anime build(){
            return new Anime(name, day, hour, minutes, firstAiring, totalEpisodes);
        }
    }
}
