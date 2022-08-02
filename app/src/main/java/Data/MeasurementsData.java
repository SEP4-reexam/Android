package Data;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;

public class MeasurementsData extends RealmObject {

    private float xAxis;
    private float yAxis;
    Date currentTime;

    public MeasurementsData() {
    }

    public MeasurementsData(float xAxis, float yAxis, Date currentTime) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.currentTime = currentTime;
    }

    public float getxAxis() {
        return xAxis;
    }

    public void setxAxis(float xAxis) {
        this.xAxis = xAxis;
    }

    public float getyAxis() {
        return yAxis;
    }

    public void setyAxis(float yAxis) {
        this.yAxis = yAxis;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
}
