package project.akbaralzaini.btraf.models.user.register;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserRegister {
    @SerializedName("username")
    public String username;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("mobileNumber")
    public String mobileNumber;
    @SerializedName("password")
    public String password;
    @SerializedName("role")
    public ArrayList<String> role;

    public UserRegister() {
    }

    public UserRegister(String username, String firstName, String lastName, String mobileNumber, String password, ArrayList<String> roles) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.role = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getRoles() {
        return role;
    }

    public void setRoles(ArrayList<String> roles) {
        this.role = roles;
    }
}
