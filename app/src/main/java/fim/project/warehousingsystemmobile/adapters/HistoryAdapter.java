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
import fim.project.warehousingsystemmobile.responses.HistoryDataResponse;

public class HistoryAdapter extends ArrayAdapter<HistoryDataResponse>  {

    private List<HistoryDataResponse> mHistoryDataResponseList;
    private Context context;
    private LayoutInflater layoutInflater;

    public HistoryAdapter(@NonNull Context context, List<HistoryDataResponse> data) {
        super(context, R.layout.item_history,data);
        this.context = context;
        mHistoryDataResponseList = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mHistoryDataResponseList.size();
    }

    @Nullable
    @Override
    public HistoryDataResponse getItem(int position) {
        return mHistoryDataResponseList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.item_history,null);
        }
        TextView purchaseOrder = view.findViewById(R.id.txtPurchaseOrderHistory);
        TextView deliveryNote = view.findViewById(R.id.txtDeliveryNoteHistory);
        TextView itemCode = view.findViewById(R.id.txtItemCodeHistory);
        TextView itemName = view.findViewById(R.id.txtItemNameHistory);
        TextView namaPalet = view.findViewById(R.id.txtNamaPaletHistory);
        TextView jumlahMasukParent = view.findViewById(R.id.txtJumlahMasukHistoryParent);
        TextView jumlahKeluarParent = view.findViewById(R.id.txtJumlahKeluarHistoryParent);
        TextView jumlahMasuk = view.findViewById(R.id.txtJumlahMasukHistory);
        TextView jumlahKeluar = view.findViewById(R.id.txtJumlahKeluarHistory);
        ImageView imgFotoStatus = view.findViewById(R.id.img_history_status);
        TextView namaLemari = view.findViewById(R.id.txtNamaLemariHistory);
        TextView namaRak = view.findViewById(R.id.txtNamaRakHistory);
        TextView tanggal = view.findViewById(R.id.txtTanggalHistory);
        TextView mainItemCode = view.findViewById(R.id.txtMainItemCodeHistory);
        TextView mainItemName = view.findViewById(R.id.txtMainItemNameHistory);

        HistoryDataResponse mydata = getItem(position);

        String statusImg = mydata.getJumlahKeluar();

        if (statusImg.equals("0")){
            imgFotoStatus.setImageResource(R.drawable.box_in);
            jumlahMasuk.setText(mydata.getJumlahMasuk());
            jumlahKeluar.setText("0");
            jumlahMasuk.setVisibility(View.VISIBLE);
            jumlahMasukParent.setVisibility(View.VISIBLE);
            jumlahKeluar.setVisibility(View.GONE);
            jumlahKeluarParent.setVisibility(View.GONE);
        }else{
            imgFotoStatus.setImageResource(R.drawable.box_out);
            jumlahKeluar.setText(mydata.getJumlahKeluar());
            jumlahMasuk.setText("0");
            jumlahKeluar.setVisibility(View.VISIBLE);
            jumlahKeluarParent.setVisibility(View.VISIBLE);
            jumlahMasuk.setVisibility(View.GONE);
            jumlahMasukParent.setVisibility(View.GONE);
        }

        //SET VALUE
        itemCode.setText(mydata.getItemCode());
        itemName.setText(mydata.getItemName());
        namaPalet.setText(mydata.getDeskripsiPalet());
        namaLemari.setText(mydata.getDeskripsiLemari());
        namaRak.setText(mydata.getDeskripsiRak());
        tanggal.setText(mydata.getTanggal());
        mainItemCode.setText(mydata.getMainItemCode());
        mainItemName.setText(mydata.getMainItemName());
        purchaseOrder.setText(mydata.getPurchaseOrder());
        deliveryNote.setText(mydata.getDeliveryNote());

        if (namaPalet.getText().toString().equals("")){
            namaPalet.setText("-");
        }else{

        }

        return view;
    }

}
