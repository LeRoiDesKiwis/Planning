package fr.leroideskiwis.planning.anime;

import fr.leroideskiwis.Day;
import fr.leroideskiwis.planning.PlanningElement;
import fr.leroideskiwis.planning.PlanningParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AnimePlanningParser implements PlanningParser {

    private final String path;

    public AnimePlanningParser(String path){
        this.path = path;
    }

    @Override
    public List<PlanningElement> parse() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            return bufferedReader.lines().map(this::parseLine).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlanningElement parseLine(String line) {
        try {
            String[] split = line.split("\\|\\|");
            String[] hours = split[1].split(":");

            Day day = Day.valueOf(hours[0]);
            int hour = Integer.parseInt(hours[1]);
            int minutes = Integer.parseInt(hours[2]);

            LocalDate firstAiring = split.length >= 3 ? LocalDate.parse(split[2], DateTimeFormatter.ofPattern("dd/MM/yy")) : null;
            int totalEpisodes = split.length >= 4 ? Integer.parseInt(split[3]) : 0;

            return new Anime.AnimeBuilder()
                    .name(split[0])
                    .day(day)
                    .hour(hour)
                    .minutes(minutes)
                    .totalEpisodes(totalEpisodes)
                    .firstAiring(firstAiring)
                    .build();
        }catch(Exception e){
            System.err.println("Error while parsing line: " + line);
            e.printStackTrace();
            return new CorruptedPlanningElement(line);
        }
    }

    @Override
    public void save(List<Anime> animes) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
