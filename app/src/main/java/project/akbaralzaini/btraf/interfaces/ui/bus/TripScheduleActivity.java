package project.akbaralzaini.btraf.interfaces.ui.bus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.adapter.TripListAdapter;
import project.akbaralzaini.btraf.adapter.TripSceduleListAdapter;
import project.akbaralzaini.btraf.models.trip.TripSchedule;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.TripScheduleInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripScheduleActivity extends AppCompatActivity {
    Integer idTrip;
    TripScheduleInterface tripScheduleInterface;
    RecyclerView rvTripSchedule;
    private RecyclerView.Adapter mTripAdapter;
    private RecyclerView.LayoutManager mLayoutManagerTrip;
    MySession session;
    ImageView imgNotFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_schedule);
        imgNotFound = findViewById(R.id.ivNotfound);
        Intent i = getIntent();
        idTrip = i.getIntExtra("idTrip",0);
        rvTripSchedule = findViewById(R.id.rvTripSchedule);
        session = new MySession(TripScheduleActivity.this);
        HashMap<String, String> sUsernya = session.getUserDetails();
        tripScheduleInterface = ApiClient.getClient(""+sUsernya.get(MySession.KEY_TOKEN)).create(TripScheduleInterface.class);

        mLayoutManagerTrip =new LinearLayoutManager(TripScheduleActivity.this);
        rvTripSchedule.setLayoutManager(mLayoutManagerTrip);
        refresh();

    }

    private void refresh() {
        Call<List<TripSchedule>> calltripScheduleList = tripScheduleInterface.getSchedule(idTrip);
        calltripScheduleList.enqueue(new Callback<List<TripSchedule>>() {
            @Override
            public void onResponse(Call<List<TripSchedule>> call, Response<List<TripSchedule>> response) {
                List<TripSchedule> tripSchedules = response.body();
                if (tripSchedules != null && !tripSchedules.isEmpty()){
                    rvTripSchedule.setVisibility(View.VISIBLE);
                    mTripAdapter = new TripSceduleListAdapter(tripSchedules,TripScheduleActivity.this);
                    rvTripSchedule.setAdapter(mTripAdapter);
                }
                else{
                    rvTripSchedule.setVisibility(View.GONE);
                    imgNotFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<TripSchedule>> call, Throwable t) {
                Toast.makeText(TripScheduleActivity.this, "No internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}