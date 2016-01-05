package la.juju.android.yanwenzilayout.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by HelloVass on 16/1/1.
 */
public class ScreenUtil {

  private static final String TAG = ScreenUtil.class.getSimpleName();

  public static int getScreenWidth(Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    Log.i(TAG, "screen width -->>" + displayMetrics.widthPixels);
    return displayMetrics.widthPixels;
  }
}
