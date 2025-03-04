// Generated by view binder compiler. Do not edit!
package fim.project.warehousingsystemmobile.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import fim.project.warehousingsystemmobile.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDetailBarangMasukBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final Button barangMasukBtnDetailKonfirmasi;

  @NonNull
  public final Button barangMasukBtnDetailScan;

  @NonNull
  public final CardView barangMasukBtnLokasi;

  @NonNull
  public final TextView barangMasukItemCodeValue;

  @NonNull
  public final TextView barangMasukItemNameValue;

  @NonNull
  public final TextView barangMasukNamaLemariValue;

  @NonNull
  public final TextView barangMasukNamaPaletValue;

  @NonNull
  public final TextView barangMasukNamaRakValue;

  @NonNull
  public final TextView barangMasukQtyInputValue;

  @NonNull
  public final TextView barangMasukTotalQtyValue;

  @NonNull
  public final TextView barangMasukTxtError;

  @NonNull
  public final BottomNavigationView bottomMenu;

  @NonNull
  public final Button btnKonfirmasiManual;

  @NonNull
  public final EditText idPalet;

  @NonNull
  public final WebView inWvGPIO1;

  @NonNull
  public final WebView inWvGPIO2;

  @NonNull
  public final WebView inWvGPIO3;

  @NonNull
  public final TextView txtErrorInputManual;

  private ActivityDetailBarangMasukBinding(@NonNull FrameLayout rootView,
      @NonNull Button barangMasukBtnDetailKonfirmasi, @NonNull Button barangMasukBtnDetailScan,
      @NonNull CardView barangMasukBtnLokasi, @NonNull TextView barangMasukItemCodeValue,
      @NonNull TextView barangMasukItemNameValue, @NonNull TextView barangMasukNamaLemariValue,
      @NonNull TextView barangMasukNamaPaletValue, @NonNull TextView barangMasukNamaRakValue,
      @NonNull TextView barangMasukQtyInputValue, @NonNull TextView barangMasukTotalQtyValue,
      @NonNull TextView barangMasukTxtError, @NonNull BottomNavigationView bottomMenu,
      @NonNull Button btnKonfirmasiManual, @NonNull EditText idPalet, @NonNull WebView inWvGPIO1,
      @NonNull WebView inWvGPIO2, @NonNull WebView inWvGPIO3,
      @NonNull TextView txtErrorInputManual) {
    this.rootView = rootView;
    this.barangMasukBtnDetailKonfirmasi = barangMasukBtnDetailKonfirmasi;
    this.barangMasukBtnDetailScan = barangMasukBtnDetailScan;
    this.barangMasukBtnLokasi = barangMasukBtnLokasi;
    this.barangMasukItemCodeValue = barangMasukItemCodeValue;
    this.barangMasukItemNameValue = barangMasukItemNameValue;
    this.barangMasukNamaLemariValue = barangMasukNamaLemariValue;
    this.barangMasukNamaPaletValue = barangMasukNamaPaletValue;
    this.barangMasukNamaRakValue = barangMasukNamaRakValue;
    this.barangMasukQtyInputValue = barangMasukQtyInputValue;
    this.barangMasukTotalQtyValue = barangMasukTotalQtyValue;
    this.barangMasukTxtError = barangMasukTxtError;
    this.bottomMenu = bottomMenu;
    this.btnKonfirmasiManual = btnKonfirmasiManual;
    this.idPalet = idPalet;
    this.inWvGPIO1 = inWvGPIO1;
    this.inWvGPIO2 = inWvGPIO2;
    this.inWvGPIO3 = inWvGPIO3;
    this.txtErrorInputManual = txtErrorInputManual;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDetailBarangMasukBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDetailBarangMasukBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_detail_barang_masuk, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDetailBarangMasukBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.barangMasuk_btnDetailKonfirmasi;
      Button barangMasukBtnDetailKonfirmasi = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukBtnDetailKonfirmasi == null) {
        break missingId;
      }

      id = R.id.barangMasuk_btnDetailScan;
      Button barangMasukBtnDetailScan = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukBtnDetailScan == null) {
        break missingId;
      }

      id = R.id.barangMasuk_btnLokasi;
      CardView barangMasukBtnLokasi = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukBtnLokasi == null) {
        break missingId;
      }

      id = R.id.barangMasuk_itemCodeValue;
      TextView barangMasukItemCodeValue = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukItemCodeValue == null) {
        break missingId;
      }

      id = R.id.barangMasuk_itemNameValue;
      TextView barangMasukItemNameValue = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukItemNameValue == null) {
        break missingId;
      }

      id = R.id.barangMasuk_namaLemariValue;
      TextView barangMasukNamaLemariValue = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukNamaLemariValue == null) {
        break missingId;
      }

      id = R.id.barangMasuk_namaPaletValue;
      TextView barangMasukNamaPaletValue = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukNamaPaletValue == null) {
        break missingId;
      }

      id = R.id.barangMasuk_namaRakValue;
      TextView barangMasukNamaRakValue = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukNamaRakValue == null) {
        break missingId;
      }

      id = R.id.barangMasuk_qtyInputValue;
      TextView barangMasukQtyInputValue = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukQtyInputValue == null) {
        break missingId;
      }

      id = R.id.barangMasuk_totalQtyValue;
      TextView barangMasukTotalQtyValue = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukTotalQtyValue == null) {
        break missingId;
      }

      id = R.id.barangMasuk_txt_error;
      TextView barangMasukTxtError = ViewBindings.findChildViewById(rootView, id);
      if (barangMasukTxtError == null) {
        break missingId;
      }

      id = R.id.bottom_menu;
      BottomNavigationView bottomMenu = ViewBindings.findChildViewById(rootView, id);
      if (bottomMenu == null) {
        break missingId;
      }

      id = R.id.btn_konfirmasi_manual;
      Button btnKonfirmasiManual = ViewBindings.findChildViewById(rootView, id);
      if (btnKonfirmasiManual == null) {
        break missingId;
      }

      id = R.id.id_palet;
      EditText idPalet = ViewBindings.findChildViewById(rootView, id);
      if (idPalet == null) {
        break missingId;
      }

      id = R.id.in_wvGPIO1;
      WebView inWvGPIO1 = ViewBindings.findChildViewById(rootView, id);
      if (inWvGPIO1 == null) {
        break missingId;
      }

      id = R.id.in_wvGPIO2;
      WebView inWvGPIO2 = ViewBindings.findChildViewById(rootView, id);
      if (inWvGPIO2 == null) {
        break missingId;
      }

      id = R.id.in_wvGPIO3;
      WebView inWvGPIO3 = ViewBindings.findChildViewById(rootView, id);
      if (inWvGPIO3 == null) {
        break missingId;
      }

      id = R.id.txt_error_input_manual;
      TextView txtErrorInputManual = ViewBindings.findChildViewById(rootView, id);
      if (txtErrorInputManual == null) {
        break missingId;
      }

      return new ActivityDetailBarangMasukBinding((FrameLayout) rootView,
          barangMasukBtnDetailKonfirmasi, barangMasukBtnDetailScan, barangMasukBtnLokasi,
          barangMasukItemCodeValue, barangMasukItemNameValue, barangMasukNamaLemariValue,
          barangMasukNamaPaletValue, barangMasukNamaRakValue, barangMasukQtyInputValue,
          barangMasukTotalQtyValue, barangMasukTxtError, bottomMenu, btnKonfirmasiManual, idPalet,
          inWvGPIO1, inWvGPIO2, inWvGPIO3, txtErrorInputManual);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
