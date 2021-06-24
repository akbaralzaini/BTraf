package project.akbaralzaini.btraf.models.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Bus implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("code")
    public String code;
    @SerializedName("capacity")
    public int capacity;
    @SerializedName("make")
    public String make;
    @SerializedName("agency")
    public Agency agency;

    public Bus() {
    }

    public Bus(int id, String code, int capacity, String make, Agency agency) {
        this.id = id;
        this.code = code;
        this.capacity = capacity;
        this.make = make;
        this.agency = agency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }
}
