package la.juju.android.yanwenzilayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 显示、排版颜文字的 ViewGroup
 */
public class YanWenZiFlowLayout extends ViewGroup {

  public YanWenZiFlowLayout(Context context) {
    super(context,null);
  }

  public YanWenZiFlowLayout(Context context, AttributeSet attrs) {
    super(context, attrs,0);
  }

  public YanWenZiFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {

  }
}
