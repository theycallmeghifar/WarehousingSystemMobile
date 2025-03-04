// Generated by view binder compiler. Do not edit!
package fim.project.warehousingsystemmobile.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import fim.project.warehousingsystemmobile.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemBarangBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView imgBarang;

  @NonNull
  public final TextView txtItemCodeBarang;

  @NonNull
  public final TextView txtItemNameBarang;

  @NonNull
  public final TextView txtJumlahItemBarang;

  @NonNull
  public final TextView txtNamaLemariBarang;

  @NonNull
  public final TextView txtNamaPaletBarang;

  @NonNull
  public final TextView txtNamaRakBarang;

  @NonNull
  public final TextView txtNetQuantityBarang;

  private ItemBarangBinding(@NonNull LinearLayout rootView, @NonNull ImageView imgBarang,
      @NonNull TextView txtItemCodeBarang, @NonNull TextView txtItemNameBarang,
      @NonNull TextView txtJumlahItemBarang, @NonNull TextView txtNamaLemariBarang,
      @NonNull TextView txtNamaPaletBarang, @NonNull TextView txtNamaRakBarang,
      @NonNull TextView txtNetQuantityBarang) {
    this.rootView = rootView;
    this.imgBarang = imgBarang;
    this.txtItemCodeBarang = txtItemCodeBarang;
    this.txtItemNameBarang = txtItemNameBarang;
    this.txtJumlahItemBarang = txtJumlahItemBarang;
    this.txtNamaLemariBarang = txtNamaLemariBarang;
    this.txtNamaPaletBarang = txtNamaPaletBarang;
    this.txtNamaRakBarang = txtNamaRakBarang;
    this.txtNetQuantityBarang = txtNetQuantityBarang;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemBarangBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemBarangBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_barang, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemBarangBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.img_barang;
      ImageView imgBarang = ViewBindings.findChildViewById(rootView, id);
      if (imgBarang == null) {
        break missingId;
      }

      id = R.id.txtItemCodeBarang;
      TextView txtItemCodeBarang = ViewBindings.findChildViewById(rootView, id);
      if (txtItemCodeBarang == null) {
        break missingId;
      }

      id = R.id.txtItemNameBarang;
      TextView txtItemNameBarang = ViewBindings.findChildViewById(rootView, id);
      if (txtItemNameBarang == null) {
        break missingId;
      }

      id = R.id.txtJumlahItemBarang;
      TextView txtJumlahItemBarang = ViewBindings.findChildViewById(rootView, id);
      if (txtJumlahItemBarang == null) {
        break missingId;
      }

      id = R.id.txtNamaLemariBarang;
      TextView txtNamaLemariBarang = ViewBindings.findChildViewById(rootView, id);
      if (txtNamaLemariBarang == null) {
        break missingId;
      }

      id = R.id.txtNamaPaletBarang;
      TextView txtNamaPaletBarang = ViewBindings.findChildViewById(rootView, id);
      if (txtNamaPaletBarang == null) {
        break missingId;
      }

      id = R.id.txtNamaRakBarang;
      TextView txtNamaRakBarang = ViewBindings.findChildViewById(rootView, id);
      if (txtNamaRakBarang == null) {
        break missingId;
      }

      id = R.id.txtNetQuantityBarang;
      TextView txtNetQuantityBarang = ViewBindings.findChildViewById(rootView, id);
      if (txtNetQuantityBarang == null) {
        break missingId;
      }

      return new ItemBarangBinding((LinearLayout) rootView, imgBarang, txtItemCodeBarang,
          txtItemNameBarang, txtJumlahItemBarang, txtNamaLemariBarang, txtNamaPaletBarang,
          txtNamaRakBarang, txtNetQuantityBarang);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
