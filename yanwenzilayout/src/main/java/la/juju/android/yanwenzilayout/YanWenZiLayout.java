package la.juju.android.yanwenzilayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 管理 YanWenZiFlowLayout、TabPageIndicator、DotViewLayout 的 ViewGroup，继承自 LinearLayout
 */
public class YanWenZiLayout extends LinearLayout {

  public YanWenZiLayout(Context context) {
    super(context, null);
  }

  public YanWenZiLayout(Context context, AttributeSet attrs) {
    super(context, attrs, 0);
  }

  public YanWenZiLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    View.inflate(getContext(), R.layout.layout_yan_wen_zi, this);
  }
}
