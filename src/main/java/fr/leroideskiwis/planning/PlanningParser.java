package fr.leroideskiwis.planning;

import fr.leroideskiwis.planning.anime.Anime;

import java.util.List;

public interface PlanningParser {

    List<PlanningElement> parse();
    PlanningElement parseLine(String line);

    void save(List<Anime> animes);

}
