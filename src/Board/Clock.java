package Board;

import Detectores.CheckMate;
import Piecs.ChessColor;
import main.GamePanel;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    boolean isTimeOk;
    int days, hours, minutes, seconds;
    ChessColor color;

    public Clock(int days, int hours, int minutes, int seconds, ChessColor color) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.color = color;
        isTimeOk = isOk(days, hours, minutes, seconds);
    }

    public Clock(int hours, int minutes, int seconds, ChessColor color) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.color = color;
        isTimeOk = isOk(0, hours, minutes, seconds);
    }

    public Clock(int minutes, int seconds, ChessColor color) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.color = color;
        isTimeOk = isOk(0, 0, minutes, seconds);
    }

    public Clock(int seconds, ChessColor color) {
        this.seconds = seconds;
        this.color = color;
        isTimeOk = isOk(0, 0, 0, seconds);
    }

    private boolean isOk(int days, int hours, int minutes, int seconds) {
        return !(days < 0 && hours < 0 && minutes < 0 && seconds < 0) &&
                (days <= 360 || hours <= 24 || minutes <= 60 || seconds <= 60);
    }

    public void startClock(GamePanel panel) {
        if (isTimeOk) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    secondPassed(panel);
                }
            }, 0, 1000);
        }
    }

    private void secondPassed(GamePanel panel) {
        if (panel.isWhiteTurn() && color.equals(ChessColor.WHITE) ||
                !panel.isWhiteTurn() && !color.equals(ChessColor.WHITE)) {
            if (seconds > 0) {
                seconds--;
            } else if (minutes > 0) {
                minutes--;
                seconds = 60;
            } else if (hours > 0) {
                hours--;
                minutes = 60;
                seconds = 60;
            } else {
                if (days == 0)
                    CheckMate.EndClock(color);
                else {
                    days--;
                    hours = 24;
                    minutes = 60;
                    seconds = 60;
                }
            }
        }
    }

    public boolean isTimeOk() {
        return isTimeOk;
    }

    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public ChessColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        String SDays = "" + days,SHours= "" + hours,SMinutes= "" + minutes,SSeconds= "" + seconds;

        if (hours < 10)
            SHours = "0" + SHours;
        if (minutes < 10)
            SMinutes = "0" + SMinutes;
        if (seconds < 10)
            SSeconds = "0" + SSeconds;

        String st;
        if (days > 0)
            st = days + "days";
        else
            st = SHours + " : " + SMinutes + " : " + SSeconds;


        return st;
    }
}
