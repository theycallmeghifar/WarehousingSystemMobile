// Generated code from Butter Knife. Do not modify!
package fim.project.warehousingsystemmobile.activities;

import android.view.View;
import android.widget.ListView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import fim.project.warehousingsystemmobile.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BarangActivity_ViewBinding implements Unbinder {
  private BarangActivity target;

  @UiThread
  public BarangActivity_ViewBinding(BarangActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BarangActivity_ViewBinding(BarangActivity target, View source) {
    this.target = target;

    target.lvBarangList = Utils.findRequiredViewAsType(source, R.id.lv_barang, "field 'lvBarangList'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BarangActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lvBarangList = null;
  }
}
