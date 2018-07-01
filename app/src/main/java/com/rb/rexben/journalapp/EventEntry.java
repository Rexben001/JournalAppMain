package com.rb.rexben.journalapp;

import android.os.Parcel;
import android.os.Parcelable;

public class EventEntry implements Parcelable {


    public static final Creator<EventEntry> CREATOR = new Creator<EventEntry>() {
        @Override
        public EventEntry createFromParcel(Parcel in) {
            return new EventEntry(in);
        }

        @Override
        public EventEntry[] newArray(int size) {
            return new EventEntry[size];
        }
    };

    protected EventEntry(Parcel in) {
        title = in.readString();
        details = in.readString();
        someMessage = in.readString();
        id = in.readString();
    }

    public EventEntry() {

    }

    String title;
    String details;
    String someMessage;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    int cid;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSomeMessage() {
        return someMessage;
    }

    public void setSomeMessage(String someMessage) {
        this.someMessage = someMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(details);
        parcel.writeString(someMessage);
        parcel.writeString(id);
    }
}