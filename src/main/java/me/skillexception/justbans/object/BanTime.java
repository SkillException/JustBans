package me.skillexception.justbans.object;

public class BanTime {
    private int time;
    private BanTimeUnit unit;


    public BanTime(int time, BanTimeUnit unit){
        this.time = time;
        this.unit = unit;
    }


    public int getTime() {
        return time;
    }
    public BanTimeUnit getUnit() {
        return unit;
    }


}
