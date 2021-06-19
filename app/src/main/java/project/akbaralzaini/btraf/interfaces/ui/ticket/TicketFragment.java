package project.akbaralzaini.btraf.interfaces.ui.ticket;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import project.akbaralzaini.btraf.R;

public class TicketFragment extends Fragment {
    String qrGenerator;
    ImageView ivQrCode;

    MultiFormatWriter multi = new MultiFormatWriter();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ticket, container, false);
        // Inflate the layout for this fragment
        ivQrCode = rootView.findViewById(R.id.QrCode);

        try {
            qrGenerator = "bcafinance";
            BitMatrix bitMatrix = multi.encode(qrGenerator, BarcodeFormat.QR_CODE, 300, 300);

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQrCode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }

        return rootView;
    }
}