package project.akbaralzaini.btraf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.models.trip.Trip;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.MyViewHolder>{

    List<Trip> tripList;
    Context context;

    public TripListAdapter(List<Trip> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public TripListAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_trip_item, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TripListAdapter.MyViewHolder holder, int position) {
        String fare = "Rp. "+ String.valueOf(tripList.get(position).getFare());
        String times = "- "+ tripList.get(position).getJourneyTime() + " menit -";
        holder.tvBus.setText(tripList.get(position).getBus().getCode());
        holder.tvSource.setText(tripList.get(position).getSourceStop().name);
        holder.tvDestination.setText(tripList.get(position).getDestStop().getName());
        holder.tvTime.setText(times);
        holder.tvFare.setText(fare);
        holder.tvAgency.setText(tripList.get(position).getAgency().getName());
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvBus,tvSource,tvDestination,tvTime,tvAgency,tvFare;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBus = itemView.findViewById(R.id.tvBus);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvAgency = itemView.findViewById(R.id.tvAgency);
            tvTime = itemView.findViewById(R.id.tvDuration);
            tvFare = itemView.findViewById(R.id.tvFare);
        }
    }
}
