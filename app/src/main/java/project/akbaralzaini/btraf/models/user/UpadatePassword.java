package project.akbaralzaini.btraf.models.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpadatePassword implements Serializable {
    @SerializedName("password")
    private String password;

    public UpadatePassword() {
    }



    public UpadatePassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
