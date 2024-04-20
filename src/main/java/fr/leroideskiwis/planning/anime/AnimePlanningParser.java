package fr.leroideskiwis.planning.anime;

import fr.leroideskiwis.Day;
import fr.leroideskiwis.planning.PlanningParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimePlanningParser implements PlanningParser {

    private final String path;

    public AnimePlanningParser(String path){
        this.path = path;
    }

    @Override
    public List<Anime> parse() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            List<Anime> animes = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null){
                String[] split = line.split("\\|\\|");
                String[] hours = split[1].split(":");
                animes.add(new Anime.AnimeBuilder()
                        .name(split[0])
                        .day(Day.valueOf(hours[0]))
                        .hour(Integer.parseInt(hours[1]))
                        .minutes(Integer.parseInt(hours[2]))
                        .build());
            }
            return animes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(List<Anime> animes) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
