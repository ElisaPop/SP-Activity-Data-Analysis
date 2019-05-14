package main;

import analysis.DataAnalyzer;
import analysis.DataAnalyzerInterface;
import javafx.application.Application;
import javafx.stage.Stage;
import processing.DataProcessing;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //CustomersController mainCtrl = new CustomersController();

        //primaryStage.setScene(mainCtrl.getSceneCustomers());
        primaryStage.setTitle("Order Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        DataProcessing newDP = new DataProcessing();
        DataAnalyzerInterface newInterface = ( data -> {
            DataAnalyzer newData = new DataAnalyzer();

            newData.setData(data);
            newData.showData();

            newData.getNrDistinctDays();
            newData.getActivityOcuurence();
            newData.printActivityOccurence();
            newData.printOccurenceMap();

            newData.getActivityDailyOccurence();
            newData.printDailyOccurenceMap();

            newData.getActivityDuration();
            newData.printActivityDuration();

            newData.getActivityDurationLess();
            newData.printActivity90();
        });

        newInterface.start(newDP.getData());

    }
}