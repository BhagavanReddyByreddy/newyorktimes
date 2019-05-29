package com.example.newyorktimes.pojos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "books")
public class BookData {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "listName")
    private String listName;

    @ColumnInfo(name = "oldestPublishedDate")
    private String oldestPublishedDate;

    @ColumnInfo(name = "newestPublishedDate")
    private String newestPublishedDate;

    @ColumnInfo(name = "updated")
    private String updated;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getOldestPublishedDate() {
        return oldestPublishedDate;
    }

    public void setOldestPublishedDate(String oldestPublishedDate) {
        this.oldestPublishedDate = oldestPublishedDate;
    }

    public String getNewestPublishedDate() {
        return newestPublishedDate;
    }

    public void setNewestPublishedDate(String newestPublishedDate) {
        this.newestPublishedDate = newestPublishedDate;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
