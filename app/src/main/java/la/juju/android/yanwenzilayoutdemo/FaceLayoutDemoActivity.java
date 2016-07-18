package la.juju.android.yanwenzilayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import la.juju.android.ftil.entities.FaceText;
import la.juju.android.ftil.widgets.FaceTextInputLayout;
import la.juju.android.yanwenzilayoutdemo.source.RawSource;

/**
 * Created by HelloVass on 15/12/31.
 */
public class FaceLayoutDemoActivity extends AppCompatActivity {

  private static final String TAG = FaceLayoutDemoActivity.class.getSimpleName();

  private FaceTextInputLayout mFaceTextInputLayout;

  private SwitchCompat mEmotionTrigger;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_face_text_demo);
    initViews();
  }

  private void initViews() {

    // setup FaceTextInputLayout
    mFaceTextInputLayout = (FaceTextInputLayout) findViewById(R.id.tv_face_text_input_layout);
    mFaceTextInputLayout.setFaceTextSource(new RawSource(this, R.raw.face_text));
    mFaceTextInputLayout.setOnFaceTextClickListener(
        new FaceTextInputLayout.OnFaceTextClickListener() {
          @Override public void onFaceTextClick(FaceText faceText) {
            Toast.makeText(FaceLayoutDemoActivity.this, faceText.content, Toast.LENGTH_SHORT)
                .show();
          }
        });
    mFaceTextInputLayout.render();

    // setup Switch
    mEmotionTrigger = (SwitchCompat) findViewById(R.id.sw_trigger);
    mEmotionTrigger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          mFaceTextInputLayout.setVisibility(View.VISIBLE);
        } else {
          mFaceTextInputLayout.setVisibility(View.GONE);
        }
      }
    });
  }
}
