package analysis;

import model.MonitoredData;

import java.util.LinkedList;

@FunctionalInterface
public interface DataAnalyzerInterface {
    void start(LinkedList<MonitoredData> data);
}
