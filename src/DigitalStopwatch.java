
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DigitalStopwatch extends Thread {

    long HOURS = 1000 * 60 * 60;
    long MINUTES = 1000 * 60;
    long SECONDS = 1000;

    boolean useOwnCalculations = true;
    
    public int[] time = {0,0,0,0};

    public DigitalStopwatch(boolean useOwnCalculations) {
        this.useOwnCalculations = useOwnCalculations;
    }

    public int[] calcTime(long elapsedTime) { // calculates the time without the use of SimpleDateFormat.format()
        int hours = (int) (elapsedTime / HOURS);
        elapsedTime = elapsedTime % HOURS;

        int mins = (int) (elapsedTime / MINUTES);
        elapsedTime = elapsedTime % MINUTES;

        int secs = (int) (elapsedTime / SECONDS);
        elapsedTime = elapsedTime % SECONDS;

        int ms = (int) elapsedTime;
        return new int[]{hours, mins, secs, ms};
    }

    @Override
    public void run() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS"); // HH:mm:ss:SSS
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        long lastTime = System.currentTimeMillis();
        while (!isInterrupted()) {
            try {
                sleep(10); // sleep to not fry the cpu :)
            } catch (InterruptedException ex) { // when thread is interrupted while sleeping, catch it and terminate it
                interrupt();
                return;
            }
            long now = System.currentTimeMillis();
            long delta = now - lastTime; // calculate elapsed Time
            if (useOwnCalculations) {
                time = calcTime(delta);
                DecimalFormat dt = new DecimalFormat("00");
                DecimalFormat dms = new DecimalFormat("000");
                Main.jLabel1.setText(dt.format(time[0]) + ':' + dt.format(time[1]) + ':' + dt.format(time[2]) + ':' + dms.format(time[3]));
            } else {
                Main.jLabel1.setText(formatter.format(new Date(delta)));
            }
        }
    }
}
