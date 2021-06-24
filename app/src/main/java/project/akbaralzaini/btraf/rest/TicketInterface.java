package project.akbaralzaini.btraf.rest;

import java.util.List;

import project.akbaralzaini.btraf.models.ticket.BookingTicket;
import project.akbaralzaini.btraf.models.ticket.MessageTicket;
import project.akbaralzaini.btraf.models.trip.Ticket;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TicketInterface {
    @GET("v1/reservation/getTicket")
    Call<List<Ticket>> getAllTicket();

    @POST("v1/reservation/bookticket")
    Call<MessageTicket> bookingTicket(@Body BookingTicket bookingTicket);

}
