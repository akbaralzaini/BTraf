package project.akbaralzaini.btraf.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.interfaces.ui.bus.BookingTicketActivity;
import project.akbaralzaini.btraf.models.trip.Trip;
import project.akbaralzaini.btraf.models.trip.TripSchedule;

public class TripSceduleListAdapter extends RecyclerView.Adapter<TripSceduleListAdapter.MyViewHolder>{
    List<TripSchedule> tripList;
    Context context;


    public TripSceduleListAdapter(List<TripSchedule> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public TripSceduleListAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tripsecedule_item, parent, false);
        return new TripSceduleListAdapter.MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TripSceduleListAdapter.MyViewHolder holder, int position) {
        String fare = "Rp. "+ tripList.get(position).getTripDetail().getFare();
        String stop = tripList.get(position).getTripDetail().getSourceStop().getName()+" to "+tripList.get(position).getTripDetail().getDestStop().getName();
        String seat = tripList.get(position).getAvailableSeats() +" Seat available";
        holder.tvCost.setText(fare);
        holder.tvStop.setText(stop);
        holder.tvSeat.setText(seat);
        holder.tvAgency.setText(tripList.get(position).getTripDetail().getAgency().getName());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, BookingTicketActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("value", tripList.get(position));
            i.putExtras(bundle);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvCost,tvSeat,tvStop,tvAgency;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAgency = itemView.findViewById(R.id.tvAgency);
            tvCost = itemView.findViewById(R.id.tvFare);
            tvSeat = itemView.findViewById(R.id.tvAvailibleSeat);
            tvStop = itemView.findViewById(R.id.tvStop);
        }
    }
}
