package la.juju.android.yanwenzilayoutdemo;

import android.app.Application;

/**
 * Created by HelloVass on 16/1/1.
 */
public class FaceApp extends Application {

  private static FaceApp sFaceApp;

  @Override public void onCreate() {
    super.onCreate();

    sFaceApp = this;
  }

  public static FaceApp get() {
    return sFaceApp;
  }
}
