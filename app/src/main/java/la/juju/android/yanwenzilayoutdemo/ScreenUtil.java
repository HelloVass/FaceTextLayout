package la.juju.android.yanwenzilayoutdemo;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by HelloVass on 16/1/1.
 */
public class ScreenUtil {

  public static int getScreenWidth(Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return displayMetrics.widthPixels;
  }
}
