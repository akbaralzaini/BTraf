package project.akbaralzaini.btraf.models;

import com.google.gson.annotations.SerializedName;

public class Role {
    @SerializedName("id")
    public int id;
    @SerializedName("role")
    public String role;

    public Role() {
    }

    public Role(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
