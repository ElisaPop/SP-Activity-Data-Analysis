package model;

import java.util.LinkedList;

public class ActivityLabel {

    public static LinkedList<String> getList()
    {
        LinkedList<String> Activity = new LinkedList<>();
        Activity.add("Leaving");
        Activity.add("Toileting");
        Activity.add("Showering");
        Activity.add("Sleeping");
        Activity.add("Breakfast");
        Activity.add("Lunch");
        Activity.add("Dinner");
        Activity.add("Snack");
        Activity.add("Spare_Time/TV");
        Activity.add("Grooming");
        return Activity;
    }
}
