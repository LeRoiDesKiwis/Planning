package fr.leroideskiwis.planning;

import fr.leroideskiwis.planning.anime.Anime;

import java.util.List;

public interface PlanningParser {

    List<Anime> parse();
    void save(List<Anime> animes);

}
