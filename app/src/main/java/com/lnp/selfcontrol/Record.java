package com.lnp.selfcontrol;

import java.util.Date;
import java.util.UUID;

public class Record {
    private UUID id;
    private String title;
    private Date date;
    private int value;
    private int status;
    public Record(UUID uid) {
        id = uid;
        date = new Date();
        title = "无标题";
        value = 0;
        status = 0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Record() {
        this(UUID.randomUUID());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
