package project.akbaralzaini.btraf.interfaces.ui.ticket;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
import java.util.List;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.adapter.TicketListAdapter;
import project.akbaralzaini.btraf.adapter.TripListAdapter;
import project.akbaralzaini.btraf.models.trip.Ticket;
import project.akbaralzaini.btraf.rest.ApiClient;
import project.akbaralzaini.btraf.rest.TicketInterface;
import project.akbaralzaini.btraf.utils.MySession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketFragment extends Fragment {

    TicketInterface ticketInterface;
    private RecyclerView.Adapter mTicketAdapter;
    private RecyclerView.LayoutManager mLayoutManagerTicket;
    RecyclerView rvTicketList;
    MySession session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ticket, container, false);
        rvTicketList = rootView.findViewById(R.id.rvTicket);
        mLayoutManagerTicket =new LinearLayoutManager(getActivity());
        rvTicketList.setLayoutManager(mLayoutManagerTicket);

        session = new MySession(getActivity());
        HashMap<String, String> sUsernya = session.getUserDetails();
        ticketInterface = ApiClient.getClient(sUsernya.get(MySession.KEY_TOKEN)).create(TicketInterface.class);

        Call<List<Ticket>> callList = ticketInterface.getAllTicket();
        callList.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                List<Ticket> tickets = response.body();
                if (tickets != null) {
                    mTicketAdapter = new TicketListAdapter(tickets, getContext());
                    rvTicketList.setAdapter(mTicketAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {

            }
        });
        // Inflate the layout for this fragment


        return rootView;
    }
}