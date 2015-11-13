package com.igaitapp.virtualmd.igait;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jesus on 8/29/15.
 */
public class GaitHealth implements Serializable {
    private int health;
    private Date startTime, endTime;
    boolean video;

    public GaitHealth(int health, Date startTime, Date endTime, boolean video) {
        super();

        this.health = health;
        this.startTime = startTime;
        this.endTime = endTime;
        this.video = video;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean hasVideo() {
        return video;
    }

    public void setVideo(boolean priority) {
        this.video = priority;
    }
}