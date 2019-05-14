package analysis;

import model.ActivityLabel;
import model.MonitoredData;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataAnalyzer implements DataAnalyzerInterface {

    private LinkedList<MonitoredData> data;
    private HashSet<LocalDate> distinctDays = new HashSet<>();
    private HashMap<String, Integer> activityOccurence = new HashMap<>();
    private LinkedHashMap<LocalDate, HashMap<String, Integer>> activityDailyOccurence = new LinkedHashMap<>();
    private HashMap<String, Integer> activityDuration = new HashMap<>();
    private HashMap<String, Integer> activity90 = new HashMap<>();

    public DataAnalyzer() {
        this.data = new LinkedList<>();
    }

    public void showData() {
        data.forEach(e -> System.out.println(e.toString()));
    }

    public void getNrDistinctDays() {
        data.forEach( e -> distinctDays.addAll(e.getDateInBetween()));
        System.out.println("\nThe number of distinct days: " + distinctDays.size());
    }

    public void getActivityOcuurence(){
        LinkedList<String> labels = ActivityLabel.getList();
        labels.forEach(e -> activityOccurence.put(e, getFilterOutput(data, e)));
    }

    public void getActivityDuration(){
        LinkedList<String> labels = ActivityLabel.getList();
        labels.forEach(e -> activityDuration.put(e, getFilterDuration(data, e)));
    }

    public void getActivityDurationLess(){
        LinkedList<String> labels = ActivityLabel.getList();

        labels.forEach(e -> {
            double percentageDouble = 0;
            if(getFilterDuration(data, e) != 0) {
                percentageDouble = (double)getFilterDurationLess(data,e)/getFilterDuration(data, e);
            }
            Integer percentage = (int)((percentageDouble) * 100);
            if(percentage >= 90)
                activity90.put(e, percentage);
        });
    }


    private Integer getFilterDuration(LinkedList<MonitoredData> lines, String filter) {
        LinkedList<Integer> result = new LinkedList<>();

        /*
        lines.stream()
                .filter(e ->  e.getActivityLabel().equals(filter) || e.getActivityLabel().equals(filter + "\t"))
                .map( e -> e.getTime().getSecond())
                .collect(Collectors.toList());
        */
        lines.forEach( e -> {
            if(e.getActivityLabel().equals(filter) || e.getActivityLabel().equals(filter + "\t"))
                result.add(e.getTime().getSecond());
        });

        return result.stream().flatMapToInt(i -> IntStream.of(i.intValue())).sum();
    }

    private static int getFilterOutput(LinkedList<MonitoredData> lines, String filter) {
        ArrayList<MonitoredData> result;

        result = (ArrayList<MonitoredData>) lines.stream()
                .filter(e -> e.getActivityLabel().equals(filter) || e.getActivityLabel().equals(filter + "\t"))
                .collect(Collectors.toList());

        /*lines.forEach( e -> {
                if(e.getActivityLabel().equals(filter) == true || e.getActivityLabel().equals(filter + "\t") == true)
                    result.add(e);
        });
        */
        return result.size();
    }

    private static int getFilterDurationLess(LinkedList<MonitoredData> lines, String filter) {
        ArrayList<MonitoredData> result;

        result = (ArrayList<MonitoredData>) lines.stream()
                .filter(e -> e.getActivityLabel().equals(filter) || e.getActivityLabel().equals(filter + "\t") || e.getTime().getMinute() <= 5)
                .collect(Collectors.toList());

        return result.size();
    }

    private static LinkedList<MonitoredData> getFilterDateOutput(LinkedList<MonitoredData> lines, LocalDate datum) {
        LinkedList<MonitoredData> result1 = new LinkedList<>();
        lines.forEach( e -> {
            if(e.isWithinRange(datum))
                result1.add(e);
        });

        return result1;
    }

    public void getActivityDailyOccurence() {
        LinkedList<String> labels = ActivityLabel.getList();
        distinctDays.forEach( e -> {
            LinkedList<MonitoredData> newList = getFilterDateOutput(data, e);
            HashMap<String,Integer> newHashMap = new HashMap<>();
            labels.forEach(p -> {
                newHashMap.put(p, getFilterOutput(newList,p));
                activityDailyOccurence.put(e, newHashMap);
            });
        });
    }

    public void printActivityOccurence(){
        System.out.println("\n Activities and their occurrence frequency" );
        activityOccurence.forEach((a,b) -> System.out.println(a + ": " + b));
    }
    public void printActivityDuration(){
        System.out.println("\n Activities and their total duration" );
        activityDuration.forEach((a,b) -> System.out.println(a + ": " + b));
    }
    public void printActivity90(){
        System.out.println("\n Activities with duration <5 min for 90% or more of the sample" );
        activity90.forEach((a,b) -> System.out.println(a + ": " + b));
    }


    public void printOccurenceMap() {
        List<String> lines = new ArrayList<>();
        activityOccurence.forEach((a,b) -> lines.add(a + ": " + b));

        Path file = Paths.get("OccurenceMap.txt");

        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printDailyOccurenceMap() {
        List<String> lines = new ArrayList<>();
        activityDailyOccurence.forEach((a,b) -> {
            lines.add(" ");
            lines.add(a + " ");
            b.forEach((c,d) -> lines.add(c + ": " + d));
        });

        Path file = Paths.get("DailyOccurenceMap.txt");

        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(LinkedList<MonitoredData> data) {
        this.data = data;
    }

    @Override
    public void start(LinkedList<MonitoredData> data) {

    }
}
