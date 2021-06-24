package project.akbaralzaini.btraf.rest;

import project.akbaralzaini.btraf.models.user.UpadatePassword;
import project.akbaralzaini.btraf.models.user.UpdateUser;
import project.akbaralzaini.btraf.models.user.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserInterface {
    @GET("v1/user/")
    Call<User> getUserDetail();

    @PUT("v1/user/")
    Call<User> updateProfile(@Body UpdateUser updateUser);

    @PUT("v1/user/password")
    Call<User> updatePassword(@Body UpadatePassword upadatePassword);
}
