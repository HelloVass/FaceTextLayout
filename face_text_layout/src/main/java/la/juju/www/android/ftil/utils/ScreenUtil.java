package la.juju.www.android.ftil.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by HelloVass on 16/1/1.
 */
public class ScreenUtil {

  private static final String TAG = ScreenUtil.class.getSimpleName();

  public static int getScreenWidth(Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return displayMetrics.widthPixels;
  }
}
