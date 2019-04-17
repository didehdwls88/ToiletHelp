package edu.reveart.toilethelp;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Help implements Parcelable {

    String message;
    String reward;
    long date;
    /*double longitude;
    double latitude;*/

    public Help() {
    }

    public Help(String message, String reward, long date) {
        this.message = message;
        this.reward = reward;
        this.date = date;
        /*this.longitude = longitude;
        this.latitude = latitude;*/
    }

    protected Help(Parcel in) {
        message = in.readString();
        reward = in.readString();
        date = in.readInt();
        /*longitude = in.readDouble();
        latitude = in.readDouble();*/
    }

    public static final Creator<Help> CREATOR = new Creator<Help>() {
        @Override
        public Help createFromParcel(Parcel in) {
            return new Help(in);
        }

        @Override
        public Help[] newArray(int size) {
            return new Help[size];
        }
    };

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    /*public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }*/

    /*@Override
    public String toString() {
        return "Help{" +
                "message='" + message + '\'' +
                ", reward='" + reward + '\'' +
                ", date='" + date + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "Help{" +
                "message='" + message + '\'' +
                ", reward='" + reward + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(message);
        parcel.writeString(reward);
        parcel.writeLong(date);
        /*parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);*/
    }
}
