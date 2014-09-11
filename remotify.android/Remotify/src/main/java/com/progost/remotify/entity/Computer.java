package com.progost.remotify.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ostap on 9/13/13.
 */
public class Computer implements Parcelable{

    public enum Status{
        ONLINE, OFFLINE
    }

    public String name;
    public Status status;
    public String connectionKey;

    @Override
    public String toString() {
        return name + "\t" + status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(status.toString());
        parcel.writeString(connectionKey);
    }


    public static final Parcelable.Creator<Computer> CREATOR
            = new Parcelable.Creator<Computer>() {

        public Computer createFromParcel(Parcel in) {
            Computer comp = new Computer();
            comp.name = in.readString();
            comp.status = Status.valueOf(in.readString());
            comp.connectionKey = in.readString();
            return comp;
        }

        public Computer[] newArray(int size) {
            return new Computer[size];
        }
    };

    public static Computer fromJson(JSONObject compJson) throws JSONException {
        Computer comp = new Computer();
        comp.status = Status.valueOf(compJson.getString("status").toUpperCase());
        comp.name = compJson.getString("name");
        comp.connectionKey = compJson.getString("connectionKey");
        return comp;
    }

}
