package project.akbaralzaini.btraf.rest;

import project.akbaralzaini.btraf.models.AuthLogin;
import project.akbaralzaini.btraf.models.LoginUser;
import project.akbaralzaini.btraf.models.User;
import project.akbaralzaini.btraf.models.UserRegister;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthInterface {
    @POST("auth/login")
    Call<AuthLogin> login(@Body LoginUser loginUser);

    @POST("auth/register")
    Call<User> register(@Body UserRegister userRegister);
}
