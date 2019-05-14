package model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

public class MonitoredData {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String activityLabel;

    public MonitoredData( LocalDateTime startTime, LocalDateTime endTime, String activityLabel)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityLabel = activityLabel;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String toString() {
        return (this.startTime+ " " + this.endTime + " " + this.activityLabel +"      duration:" +  getTime());
    }

    public LocalTime getTime()
    {

        long minutes = ChronoUnit.MINUTES.between(startTime, endTime) %60;
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        long seconds = ChronoUnit.SECONDS.between(startTime, endTime) %60;

        LocalTime localTime;
        localTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);
        return localTime;
    }

    public LocalDate getDate(LocalDateTime a) {

        LocalDate localDate;
        localDate = LocalDate.of(a.getYear (), a.getMonth(), a.getDayOfMonth());
        return localDate;
    }

    public LinkedList<LocalDate> getDateInBetween() {

        long days = ChronoUnit.DAYS.between(startTime, endTime);

        LinkedList<LocalDate> dates = new LinkedList<>();
        for(int i = 0; i<= (int)days; i++)
        {
            LocalDate newDate = LocalDate.of(startTime.getYear (), startTime.getMonth(), startTime.getDayOfMonth());
            newDate.plusDays((long)i);
            dates.add(newDate);
        }
        return dates;
    }

    public boolean isWithinRange(LocalDate testDate) {
        return !(testDate.isBefore(ChronoLocalDate.from(this.startTime)) || testDate.isAfter(ChronoLocalDate.from(this.endTime)));
    }

}
