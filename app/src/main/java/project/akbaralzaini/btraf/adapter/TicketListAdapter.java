package project.akbaralzaini.btraf.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import project.akbaralzaini.btraf.R;
import project.akbaralzaini.btraf.models.trip.Ticket;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.MyViewHolder>{

    List<Ticket> ticketList;
    Context context;
    MultiFormatWriter multi = new MultiFormatWriter();

    public TicketListAdapter(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public TicketListAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tiket_item, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TicketListAdapter.MyViewHolder holder, int position) {
        holder.tvBus.setText(ticketList.get(position).getTripSchedule().getTripDetail().getBus().getCode());
        holder.tvSource.setText(ticketList.get(position).getTripSchedule().getTripDetail().getSourceStop().getName());
        holder.tvDestination.setText(ticketList.get(position).getTripSchedule().getTripDetail().getDestStop().getName());
        holder.tvJourneyDate.setText(ticketList.get(position).getJourneyDate());
        String seatNumber = "Seat number "+ticketList.get(position).getSeatNumber();
        holder.tvSeatNumber.setText(seatNumber);
        Bitmap bitmap = null;
        try {
            String qrGenerator = ticketList.get(position).getJourneyDate()+ticketList.get(position).getSeatNumber();
            BitMatrix bitMatrix = multi.encode(qrGenerator, BarcodeFormat.QR_CODE, 300, 300);

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            holder.ivQrCode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
        Bitmap finalBitmap = bitmap;
        holder.itemView.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.show_ticket);
            dialog.setTitle("Position" + position);
            dialog.setCancelable(true);

            ImageView imgqrcodeScan = dialog.findViewById(R.id.qrcodeScan);
            imgqrcodeScan.setImageBitmap(finalBitmap);
            dialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvBus,tvSource,tvDestination,tvTime, tvSeatNumber, tvJourneyDate;
        public ImageView ivQrCode;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBus = itemView.findViewById(R.id.tvBus);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvSeatNumber = itemView.findViewById(R.id.tvSeatNumber);
            tvTime = itemView.findViewById(R.id.tvDuration);
            tvJourneyDate = itemView.findViewById(R.id.tvJourneyDate);
            ivQrCode = itemView.findViewById(R.id.QrCode);
        }
    }
}
