package processing;

import datastream.ReadFile;
import model.MonitoredData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;


public class DataProcessing {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static LinkedList newList = new LinkedList<>();

    public DataProcessing()
    {
        newList = transformData(ReadFile.getLines());
    }

    private static LinkedList<MonitoredData> transformData(List<String> dataList)
    {

        dataList.forEach( e -> {
            String[] tokens = e.split("[ \\s]{2,}");

            String firstDate = tokens[0];
            String secondDate = tokens[1];
            String activityLabel = tokens[2];

            LocalDateTime newFirstDate = LocalDateTime.parse(firstDate,formatter);
            LocalDateTime newSecondDate = LocalDateTime.parse(secondDate,formatter);

            newList.add(new MonitoredData(newFirstDate, newSecondDate, activityLabel));
        });

        //newList.forEach(e -> System.out.println(e.toString()));
        return newList;
    }


    public static LinkedList getData()
    {
        return newList;
    }
}
