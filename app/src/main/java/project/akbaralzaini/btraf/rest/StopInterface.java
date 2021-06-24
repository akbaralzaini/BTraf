package project.akbaralzaini.btraf.rest;

import java.util.List;

import project.akbaralzaini.btraf.models.trip.Stop;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StopInterface {
    @GET("v1/reservation/stops")
    Call<List<Stop>> getStop();
}
