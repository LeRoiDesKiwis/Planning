package fr.leroideskiwis.planning;

import fr.leroideskiwis.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fr.leroideskiwis.util.Util.print;

public class Planning {

    private final List<PlanningElement> elements = new ArrayList<>();

    public Planning(PlanningElement... element){
        elements.addAll(Arrays.asList(element));
    }

    public Planning(PlanningParser planningParser){
        elements.addAll(planningParser.parse());
    }

    public int count(Day day){
        return (int)elements.stream().filter(planningElement -> planningElement.isDay(day)).count();
    }

    public int avg(){
        return (int)Arrays.stream(Day.values()).mapToInt(this::count).average().orElse(0);
    }

    public void display(){
        for(Day day : Day.values()){
            print("Day: %s", day);
            elements.stream().
                    filter(planningElement -> planningElement.isDay(day)).
                    forEach(planningElement -> planningElement.display(PlanningElement.DisplayType.DAY));
            print("\tTotal: %d", count(day));
        }
    }

    public void display(PlanningElement.DisplayType type){
        elements.forEach(planningElement -> planningElement.display(type));
    }
}
