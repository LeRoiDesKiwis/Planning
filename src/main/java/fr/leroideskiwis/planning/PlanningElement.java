package fr.leroideskiwis.planning;

import fr.leroideskiwis.Day;

public interface PlanningElement {

    int getDuration();
    boolean isDay(Day day);
    boolean isHour(int hour);
    int minutes();
    String name();
    String time();
    String nextOcurrence();
    void display(DisplayType type);

    enum DisplayType {
        DAY, NEXT_AIRING, LAST_AIRING
    }
}
