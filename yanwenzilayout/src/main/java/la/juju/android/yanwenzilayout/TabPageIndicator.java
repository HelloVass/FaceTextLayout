package la.juju.android.yanwenzilayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 显示组别名字的 ViewGroup，继承自 HorizontalScrollView
 */
public class TabPageIndicator extends HorizontalScrollView {

  public TabPageIndicator(Context context) {
    super(context, null);
  }

  public TabPageIndicator(Context context, AttributeSet attrs) {
    super(context, attrs, 0);
  }

  public TabPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }
}
