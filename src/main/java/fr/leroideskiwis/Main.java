package fr.leroideskiwis;

import fr.leroideskiwis.planning.Planning;
import fr.leroideskiwis.planning.anime.AnimePlanningParser;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Planning planning = new Planning(new AnimePlanningParser("planning.txt"));
        planning.displayNext();
    }
}