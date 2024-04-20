package fr.leroideskiwis.planning.anime;

import fr.leroideskiwis.Day;
import fr.leroideskiwis.planning.PlanningElement;

public class CorruptedPlanningElement implements PlanningElement {

    private final String line;

    public CorruptedPlanningElement(String line) {
        this.line = line;
    }

    @Override
    public int getDuration() {
        return -1;
    }

    @Override
    public boolean isDay(Day day) {
        return false;
    }

    @Override
    public boolean isHour(int hour) {
        return false;
    }

    @Override
    public int minutes() {
        return -1;
    }

    @Override
    public String name() {
        return "ERROR";
    }

    @Override
    public String time() {
        return null;
    }

    @Override
    public String nextOcurrence() {
        return "error";
    }

    @Override
    public void display(DisplayType type) {
        System.err.println("Corrupted line: " + line);
    }
}
