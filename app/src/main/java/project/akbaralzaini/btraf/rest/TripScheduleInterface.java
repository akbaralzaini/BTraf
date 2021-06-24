package project.akbaralzaini.btraf.rest;

import java.util.List;

import project.akbaralzaini.btraf.models.trip.Trip;
import project.akbaralzaini.btraf.models.trip.TripSchedule;
import project.akbaralzaini.btraf.models.tripscheduleReq.TripScheduleReq;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TripScheduleInterface {
    @GET("tripSchedule")
    Call<TripSchedule> getTripSchedule();

    @GET("v1/reservation/getAllTrips")
    Call<List<Trip>> getTripList();

    @POST("v1/reservation/tripsbystops")
    Call<List<Trip>> getTripByStop(@Body TripScheduleReq tripScheduleReq);

    @POST("v1/reservation/getTripScheduleByTrip/{id}")
    Call<List<TripSchedule>> getSchedule(@Path("id") Integer id);
}
