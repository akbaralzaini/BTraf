package project.akbaralzaini.btraf.models.trip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Agency implements Serializable {

    @SerializedName("id")
    public int id;
    @SerializedName("code")
    public String code;
    @SerializedName("name")
    public String name;
    @SerializedName("details")
    public String details;

    public Agency() {
    }

    public Agency(int id, String code, String name, String details) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.details = details;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
