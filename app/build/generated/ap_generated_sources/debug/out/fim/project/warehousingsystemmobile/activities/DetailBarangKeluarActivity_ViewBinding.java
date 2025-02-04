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

public class DetailBarangKeluarActivity_ViewBinding implements Unbinder {
  private DetailBarangKeluarActivity target;

  @UiThread
  public DetailBarangKeluarActivity_ViewBinding(DetailBarangKeluarActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailBarangKeluarActivity_ViewBinding(DetailBarangKeluarActivity target, View source) {
    this.target = target;

    target.lvBarangKeluarList = Utils.findRequiredViewAsType(source, R.id.lv_barangkeluar, "field 'lvBarangKeluarList'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailBarangKeluarActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lvBarangKeluarList = null;
  }
}
