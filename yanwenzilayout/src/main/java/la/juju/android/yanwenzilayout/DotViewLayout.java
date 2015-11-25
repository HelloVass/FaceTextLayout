package la.juju.android.yanwenzilayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 管理小圆点的 ViewGroup，继承自 LinearLayout
 */
public class DotViewLayout extends LinearLayout {

  public DotViewLayout(Context context) {
    super(context,null);
  }

  public DotViewLayout(Context context, AttributeSet attrs) {
    super(context, attrs,0);
  }

  public DotViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }
}
