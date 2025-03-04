// Generated by view binder compiler. Do not edit!
package fim.project.warehousingsystemmobile.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import fim.project.warehousingsystemmobile.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDetailScanBarangKeluarBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final BottomNavigationView bottomMenu;

  @NonNull
  public final Button btnDetailKonfirmasiBarangKeluar;

  @NonNull
  public final TextInputEditText detailIdBarangKeluar;

  @NonNull
  public final EditText detailJumlahBarangKeluar;

  @NonNull
  public final EditText detailNamaBarangKeluar;

  @NonNull
  public final TextInputLayout detailNamaBarangKeluarParent;

  @NonNull
  public final TextView txtErrorBarangKeluar;

  private ActivityDetailScanBarangKeluarBinding(@NonNull FrameLayout rootView,
      @NonNull BottomNavigationView bottomMenu, @NonNull Button btnDetailKonfirmasiBarangKeluar,
      @NonNull TextInputEditText detailIdBarangKeluar, @NonNull EditText detailJumlahBarangKeluar,
      @NonNull EditText detailNamaBarangKeluar,
      @NonNull TextInputLayout detailNamaBarangKeluarParent,
      @NonNull TextView txtErrorBarangKeluar) {
    this.rootView = rootView;
    this.bottomMenu = bottomMenu;
    this.btnDetailKonfirmasiBarangKeluar = btnDetailKonfirmasiBarangKeluar;
    this.detailIdBarangKeluar = detailIdBarangKeluar;
    this.detailJumlahBarangKeluar = detailJumlahBarangKeluar;
    this.detailNamaBarangKeluar = detailNamaBarangKeluar;
    this.detailNamaBarangKeluarParent = detailNamaBarangKeluarParent;
    this.txtErrorBarangKeluar = txtErrorBarangKeluar;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDetailScanBarangKeluarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDetailScanBarangKeluarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_detail_scan_barang_keluar, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDetailScanBarangKeluarBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottom_menu;
      BottomNavigationView bottomMenu = ViewBindings.findChildViewById(rootView, id);
      if (bottomMenu == null) {
        break missingId;
      }

      id = R.id.btn_detail_konfirmasi_barang_keluar;
      Button btnDetailKonfirmasiBarangKeluar = ViewBindings.findChildViewById(rootView, id);
      if (btnDetailKonfirmasiBarangKeluar == null) {
        break missingId;
      }

      id = R.id.detail_id_barang_keluar;
      TextInputEditText detailIdBarangKeluar = ViewBindings.findChildViewById(rootView, id);
      if (detailIdBarangKeluar == null) {
        break missingId;
      }

      id = R.id.detail_jumlah_barang_keluar;
      EditText detailJumlahBarangKeluar = ViewBindings.findChildViewById(rootView, id);
      if (detailJumlahBarangKeluar == null) {
        break missingId;
      }

      id = R.id.detail_nama_barang_keluar;
      EditText detailNamaBarangKeluar = ViewBindings.findChildViewById(rootView, id);
      if (detailNamaBarangKeluar == null) {
        break missingId;
      }

      id = R.id.detail_nama_barang_keluar_parent;
      TextInputLayout detailNamaBarangKeluarParent = ViewBindings.findChildViewById(rootView, id);
      if (detailNamaBarangKeluarParent == null) {
        break missingId;
      }

      id = R.id.txt_error_barangKeluar;
      TextView txtErrorBarangKeluar = ViewBindings.findChildViewById(rootView, id);
      if (txtErrorBarangKeluar == null) {
        break missingId;
      }

      return new ActivityDetailScanBarangKeluarBinding((FrameLayout) rootView, bottomMenu,
          btnDetailKonfirmasiBarangKeluar, detailIdBarangKeluar, detailJumlahBarangKeluar,
          detailNamaBarangKeluar, detailNamaBarangKeluarParent, txtErrorBarangKeluar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
