package project.akbaralzaini.btraf.rest;

import project.akbaralzaini.btraf.models.user.login.AuthLogin;
import project.akbaralzaini.btraf.models.user.login.LoginUser;
import project.akbaralzaini.btraf.models.user.User;
import project.akbaralzaini.btraf.models.user.register.UserRegister;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthInterface {
    @POST("auth/auth")
    Call<AuthLogin> login(@Body LoginUser loginUser);

    @POST("auth/v1/user/signup")
    Call<User> register(@Body UserRegister userRegister);
}
