package la.juju.android.yanwenzilayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import la.juju.android.ftil.entities.FaceText;
import la.juju.android.ftil.widgets.FaceTextInputLayout;
import la.juju.android.yanwenzilayoutdemo.source.RawSource;

/**
 * Created by HelloVass on 15/12/31.
 */
public class FaceLayoutDemoActivity extends AppCompatActivity {

  private static final String TAG = FaceLayoutDemoActivity.class.getSimpleName();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_face_text_demo);

    FaceTextInputLayout faceTextInputLayout =
        (FaceTextInputLayout) findViewById(R.id.tv_face_text_input_layout);

    // 设置“颜文字source”
    faceTextInputLayout.setFaceTextSource(new RawSource(this, R.raw.face_text));

    // 设置点击事件
    faceTextInputLayout.setOnFaceTextClickListener(new FaceTextInputLayout.OnFaceTextClickListener() {
      @Override public void onFaceTextClick(FaceText faceText) {
        Toast.makeText(FaceLayoutDemoActivity.this, faceText.content, Toast.LENGTH_SHORT).show();
      }
    });

    faceTextInputLayout.updateUI();
  }
}
