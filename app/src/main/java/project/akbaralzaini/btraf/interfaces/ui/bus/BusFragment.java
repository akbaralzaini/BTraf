package project.akbaralzaini.btraf.interfaces.ui.bus;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.adapter.TripListAdapter;
import project.akbaralzaini.btraf.models.trip.Stop;
import project.akbaralzaini.btraf.models.trip.Trip;
import project.akbaralzaini.btraf.models.trip.TripSchedule;
import project.akbaralzaini.btraf.models.tripscheduleReq.TripScheduleReq;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.StopInterface;
import project.akbaralzaini.btraf.rest.TripScheduleInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusFragment extends Fragment {
    Spinner spDest, spSource;
    Button btnSearch;
    StopInterface stopInterface;
    TripScheduleInterface tripScheduleInterface;
    private RecyclerView.Adapter mTripAdapter;
    private RecyclerView.LayoutManager mLayoutManagerTrip;
    RecyclerView rvListTrip;
    MySession session;
    List<Stop> stopList;
    List<Integer> listid = new ArrayList<Integer>();
    ImageView ivNotfound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bus, container, false);
        session = new MySession(getActivity());
        HashMap<String, String> sUsernya = session.getUserDetails();
        getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.back_base));
        ivNotfound = rootView.findViewById(R.id.ivNotfound);
        // Inflate the layout for this fragment
        spSource = rootView.findViewById(R.id.spSourceStop);
        spDest = rootView.findViewById(R.id.spDestinationStop);
        btnSearch = rootView.findViewById(R.id.btnSearch);
        stopInterface = ApiClient.getClient(""+sUsernya.get(MySession.KEY_TOKEN)).create(StopInterface.class);
        tripScheduleInterface = ApiClient.getClient(""+sUsernya.get(MySession.KEY_TOKEN)).create(TripScheduleInterface.class);
        getDataStop();

        rvListTrip = rootView.findViewById(R.id.listHasil);
        mLayoutManagerTrip =new LinearLayoutManager(getActivity());
        rvListTrip.setLayoutManager(mLayoutManagerTrip);


        btnSearch.setOnClickListener(v -> search());

        return rootView;

    }

    private void search() {
        ivNotfound.setVisibility(View.GONE);
        int idxSource = spSource.getSelectedItemPosition();
        Integer idSource = listid.get(idxSource);

        int idxDest = spDest.getSelectedItemPosition();
        Integer idDest = listid.get(idxDest);


        TripScheduleReq tripScheduleReq = new TripScheduleReq(idDest,idSource);
        Call<List<Trip>> listCall = tripScheduleInterface.getTripByStop(tripScheduleReq);
        listCall.enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
               List<Trip> tripList = response.body();
                if (tripList != null && !tripList.isEmpty()) {
                    rvListTrip.setVisibility(View.VISIBLE);
                    Log.d("testx",tripList.get(0).toString());
                    mTripAdapter = new TripListAdapter(tripList,getContext());
                    rvListTrip.setAdapter(mTripAdapter);
                }
                else{
                    rvListTrip.setVisibility(View.GONE);
                    ivNotfound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {

            }
        });
    }

    private void getDataStop(){
        Call<List<Stop>> getstop = stopInterface.getStop();
        getstop.enqueue(new Callback<List<Stop>>() {
            @Override
            public void onResponse(Call<List<Stop>> call, Response<List<Stop>> response) {
                if (response.body() != null){
                    List<String> listSpinner = new ArrayList<String>();
                    stopList = response.body();

                    for (int i = 0; i<stopList.size();i++){
                        listSpinner.add(stopList.get(i).getName());
                        listid.add(stopList.get(i).getId());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spDest.setAdapter(adapter);
                    spSource.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Stop>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.base_color));
    }
}