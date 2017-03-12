package com.yi.db;

import java.sql.Timestamp;

/**
 * Created by jianguog on 17/3/12.
 */
public class Selection {
    long selection_id;
    int status;
    String description;
    Timestamp creation_date;

    public long getSelection_id() {
        return selection_id;
    }

    public void setSelection_id(long selection_id) {
        this.selection_id = selection_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Timestamp creation_date) {
        this.creation_date = creation_date;
    }
}
