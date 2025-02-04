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
import fim.project.warehousingsystemmobile.responses.MainItemDataResponse;

public class BarangKeluarItemAdapter extends ArrayAdapter<MainItemDataResponse>  {

    private List<MainItemDataResponse> barangKeluarList;
    private Context context;
    private LayoutInflater layoutInflater;

    public BarangKeluarItemAdapter(@NonNull Context context, List<MainItemDataResponse> data) {
        super(context, R.layout.item_barang_keluar,data);
        this.context = context;
        barangKeluarList = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return barangKeluarList.size();
    }

    @Nullable
    @Override
    public MainItemDataResponse getItem(int position) {
        return barangKeluarList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.item_barang_keluar,null);
        }
        TextView mainItemCode = view.findViewById(R.id.txtMainItemCodeBarangKeluar);
        TextView itemCode = view.findViewById(R.id.txtItemCodeBarangKeluar);
        TextView itemName = view.findViewById(R.id.txtItemNameBarangKeluar);
        TextView namaPalet = view.findViewById(R.id.txtNamaPaletBarangKeluar);
        TextView jumlah = view.findViewById(R.id.txtJumlahBarangKeluar);
        ImageView imgFotoStatus = view.findViewById(R.id.img_barang_keluar_status);
        TextView status = view.findViewById(R.id.txtStatusBarangKeluar);
        TextView idPalet = view.findViewById(R.id.txtPaletIdBarangKeluar);
        TextView idBarang = view.findViewById(R.id.txtIdBarangBarangKeluar);
        TextView currentQty = view.findViewById(R.id.txtCurrentQtyBarangKeluar);
        TextView namaLemari = view.findViewById(R.id.txtNamaLemariBarangKeluar);
        TextView namaRak = view.findViewById(R.id.txtNamaRakBarangKeluar);
        TextView maxBarang = view.findViewById(R.id.txtMaxBarangBarangKeluar);
        TextView ipAddress = view.findViewById(R.id.txtIpAddress);
        TextView gpio1 = view.findViewById(R.id.txtGpio1);
        TextView gpio2 = view.findViewById(R.id.txtGpio2);
        TextView gpio3 = view.findViewById(R.id.txtGpio3);
        TextView gpioStatus = view.findViewById(R.id.txtGpioStatus);

        MainItemDataResponse mydata = getItem(position);

        //SET VALUE
        mainItemCode.setText(mydata.getMainItemCode());
        itemCode.setText(mydata.getItemCode());
        itemName.setText(mydata.getItemName());
        namaPalet.setText(mydata.getDeskripsiPalet());
        jumlah.setText(mydata.getJumlahInput());
        status.setText(mydata.getStatus());
        idPalet.setText(mydata.getIdPalet());
        idBarang.setText(mydata.getIdBarang());
        currentQty.setText(mydata.getJumlahItem());
        namaLemari.setText(mydata.getDeskripsiLemari());
        namaRak.setText(mydata.getDeskripsiRak());
        maxBarang.setText(mydata.getMaxBarang());
        ipAddress.setText(mydata.getIpAddress());
        gpio1.setText(mydata.getGpio1());
        gpio2.setText(mydata.getGpio2());
        gpio3.setText(mydata.getGpio3());
        gpioStatus.setText(mydata.getGpioStatus());

        if (namaPalet.getText().toString().equals("")){
            namaPalet.setText("-");
        }else{

        }

        String statusImg = mydata.getStatus();
        String stock = mydata.getJumlahItem();
        String inputQty = mydata.getJumlahInput();

        if (statusImg.equals("2")){
            if (Integer.parseInt(stock) < Integer.parseInt(inputQty)){
                imgFotoStatus.setImageResource(R.drawable.scan_out_of_stock);
            }else{
                imgFotoStatus.setImageResource(R.drawable.scan_required);
            }
        }else if(statusImg.equals("1")){
            imgFotoStatus.setImageResource(R.drawable.scan_completed);
        }else{

        }

        return view;
    }

}
