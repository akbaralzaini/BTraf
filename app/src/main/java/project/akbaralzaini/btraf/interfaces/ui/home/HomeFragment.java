package project.akbaralzaini.btraf.interfaces.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.adapter.TripListAdapter;
import project.akbaralzaini.btraf.models.trip.Trip;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.TripScheduleInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView rvListTrip;
    TripScheduleInterface tripScheduleInterface;
    private RecyclerView.Adapter mTripAdapter;
    private RecyclerView.LayoutManager mLayoutManagerTrip;
    MySession session;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        session = new MySession(getActivity());
        HashMap<String, String> sUsernya = session.getUserDetails();

        rvListTrip = rootView.findViewById(R.id.rvListTrip);
        mLayoutManagerTrip =new LinearLayoutManager(getActivity());
        rvListTrip.setLayoutManager(mLayoutManagerTrip);
        String key = sUsernya.get(MySession.KEY_TOKEN);
        tripScheduleInterface = ApiClient.getClient(""+key).create(TripScheduleInterface.class);
        refresh();

        // Inflate the layout for this fragment
        return rootView;
    }

    private void refresh() {
        HashMap<String, String> sUsernya = session.getUserDetails();
        Call<List<Trip>> listCall = tripScheduleInterface.getTripList();
        listCall.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                List<Trip> tripList = response.body();
                if (tripList != null && !tripList.isEmpty()){
                    mTripAdapter = new TripListAdapter(tripList,getContext());
                    rvListTrip.setAdapter(mTripAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {

            }
        });
    }
}