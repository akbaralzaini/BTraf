package project.akbaralzaini.btraf.rest;

import java.util.List;

import project.akbaralzaini.btraf.models.trip.Trip;
import project.akbaralzaini.btraf.models.trip.TripSchedule;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TripScheduleInterface {
    @GET("tripSchedule")
    Call<TripSchedule> getTripSchedule();

    @GET("v1/trip/")
    Call<List<Trip>> getTripList();
}
