package fim.project.warehousingsystemmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fim.project.warehousingsystemmobile.R;
import fim.project.warehousingsystemmobile.responses.BarangDataResponse;
import fim.project.warehousingsystemmobile.responses.HistoryDataResponse;

public class BarangAdapter extends ArrayAdapter<BarangDataResponse>  {

    private List<BarangDataResponse> mBarangDataResponseList;
    private Context context;
    private LayoutInflater layoutInflater;

    public BarangAdapter(@NonNull Context context, List<BarangDataResponse> data) {
        super(context, R.layout.item_barang,data);
        this.context = context;
        mBarangDataResponseList = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mBarangDataResponseList.size();
    }

    @Nullable
    @Override
    public BarangDataResponse getItem(int position) {
        return mBarangDataResponseList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.item_barang,null);
        }
        ImageView imageBarang = view.findViewById(R.id.img_barang);
        TextView itemCode = view.findViewById(R.id.txtItemCodeBarang);
        TextView itemName = view.findViewById(R.id.txtItemNameBarang);
        TextView jumlahItem = view.findViewById(R.id.txtJumlahItemBarang);
        TextView netQuantity = view.findViewById(R.id.txtNetQuantityBarang);
        TextView lemari = view.findViewById(R.id.txtNamaLemariBarang);
        TextView rak = view.findViewById(R.id.txtNamaRakBarang);
        TextView palet = view.findViewById(R.id.txtNamaPaletBarang);

        BarangDataResponse mydata = getItem(position);

        String statusImg = mydata.getPosition();

        if (statusImg.equals("10")){
            imageBarang.setVisibility(view.VISIBLE);
            imageBarang.setImageResource(R.drawable.pin_text);
        }else if(statusImg.equals("20")){
            imageBarang.setVisibility(view.VISIBLE);
            imageBarang.setImageResource(R.drawable.ring_text);
        }else if(statusImg.equals("30")){
            imageBarang.setVisibility(view.VISIBLE);
            imageBarang.setImageResource(R.drawable.snap_ring_text);
        }else if(statusImg.equals("40")){
            imageBarang.setVisibility(view.VISIBLE);
            imageBarang.setImageResource(R.drawable.dus_text);
        }else if(statusImg.equals("50")){
            imageBarang.setVisibility(view.VISIBLE);
            imageBarang.setImageResource(R.drawable.dus_text);
        }else if(statusImg.equals("60")){
            imageBarang.setVisibility(view.VISIBLE);
            imageBarang.setImageResource(R.drawable.dus_text);
        }else if(statusImg.equals("70")){
            imageBarang.setImageResource(R.drawable.dus_text);
        }else{
            imageBarang.setVisibility(view.GONE);
        }

        //SET VALUE
        itemCode.setText(mydata.getItemCode());
        itemName.setText(mydata.getItemName());
        jumlahItem.setText(mydata.getJumlahItem());
        netQuantity.setText(mydata.getNetQuantity());
        lemari.setText(mydata.getDeskripsiLemari());
        rak.setText(mydata.getDeskripsiRak());
        palet.setText(mydata.getDeskripsiPalet());

        return view;
    }

}
