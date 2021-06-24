package project.akbaralzaini.btraf.interfaces.ui.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.interfaces.MainActivity;
import project.akbaralzaini.btraf.models.ticket.BookingTicket;
import project.akbaralzaini.btraf.models.ticket.MessageTicket;
import project.akbaralzaini.btraf.models.trip.TripSchedule;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.TicketInterface;
import project.akbaralzaini.btraf.rest.TripScheduleInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingTicketActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnBuy;
    TextView tvFare, tvRoute, tvDate;
    ViewGroup layout;
    TicketInterface ticketInterface;
    MySession session;
    String seats =
              "AA_AA/"
            + "AA_AA/"
            + "AA_AA/"
            + "AA_AA/"
            + "AA_AA/"
            + "AA_AA/"
            + "AA_AA/"
            + "AA_AA/";

    List<TextView> seatViewList = new ArrayList<>();
    int seatSize = 100;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";
    Boolean seatPicked = false;
    int numberSeatPicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_ticket);
        btnBuy = findViewById(R.id.btnBuy);
        tvDate = findViewById(R.id.tvJourneyDate);
        tvFare = findViewById(R.id.tvFare);
        tvRoute = findViewById(R.id.tvRoute);

        layout = findViewById(R.id.layoutSeat);
        session = new MySession(BookingTicketActivity.this);
        HashMap<String, String> sUsernya = session.getUserDetails();
        ticketInterface = ApiClient.getClient(""+sUsernya.get(MySession.KEY_TOKEN)).create(TicketInterface.class);

        seats = "/" + seats;

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping, 8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;

        for (int index = 0; index < seats.length(); index++) {
            if (seats.charAt(index) == '/') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (seats.charAt(index) == 'U') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_booked);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_BOOKED);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'A') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_book);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(STATUS_AVAILABLE);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == 'R') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_reserved);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_RESERVED);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(index) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
        }


        Intent i = getIntent();
        TripSchedule tripSchedule = (TripSchedule) i.getParcelableExtra("schedule");

        String route = tripSchedule.getTripDetail().getSourceStop().getName()+" to "+tripSchedule.getTripDetail().getDestStop().getName();
        String fares = "Rp. "+tripSchedule.getTripDetail().getFare();
        tvFare.setText(fares);
        tvRoute.setText(route);
        tvDate.setText(tripSchedule.getTripDate());
        btnBuy.setOnClickListener(v -> {
            BookingTicket bookingTicket = new BookingTicket(false,tripSchedule.getTripDate(),Integer.parseInt(Objects.requireNonNull(sUsernya.get(MySession.KEY_ID))),numberSeatPicked,tripSchedule.getId());
            Call<MessageTicket> call = ticketInterface.bookingTicket(bookingTicket);
            call.enqueue(new Callback<MessageTicket>() {
                @Override
                public void onResponse(Call<MessageTicket> call, Response<MessageTicket> response) {
                    MessageTicket messageTicket = response.body();
                    if (messageTicket != null){
                        new AlertDialog.Builder(BookingTicketActivity.this)
                                .setTitle("Information")
                                .setMessage(messageTicket.getMessage())
                                .setCancelable(false)
                                .setPositiveButton("ok", (dialog, which) -> {
                                    Intent intent = new Intent(BookingTicketActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }).show();
                    }
                }

                @Override
                public void onFailure(Call<MessageTicket> call, Throwable t) {

                }
            });
        });

    }

    @Override
    public void onClick(View view) {
        if ((int) view.getTag() == STATUS_AVAILABLE) {
            if (selectedIds.contains(view.getId() + ",")) {
                selectedIds = selectedIds.replace(+view.getId() + ",", "");
                view.setBackgroundResource(R.drawable.ic_seats_book);
                numberSeatPicked = 0;
                seatPicked = false;
            } else if(!seatPicked){
                selectedIds = selectedIds + view.getId() + ",";
                view.setBackgroundResource(R.drawable.ic_seats_selected);
                numberSeatPicked = view.getId();
                seatPicked = true;
            } else{
                Toast.makeText(this, "cancel " + numberSeatPicked + " before pick another seat", Toast.LENGTH_SHORT).show();
            }
        } else if ((int) view.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) view.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + view.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }
    }
}