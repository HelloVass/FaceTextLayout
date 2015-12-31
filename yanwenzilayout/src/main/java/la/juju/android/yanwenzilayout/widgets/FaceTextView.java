package la.juju.android.yanwenzilayout.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import la.juju.android.yanwenzilayout.entities.FaceText;

/**
 * Created by HelloVass on 15/11/25.
 */
public class FaceTextView extends TextView {

  private FaceText mFaceText;

  public FaceTextView(Context context) {
    this(context,null);
  }

  public FaceTextView(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }


  public FaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public FaceText getFaceText() {
    return mFaceText;
  }

  public void setFaceText(FaceText faceText) {
    mFaceText = faceText;
    setText(faceText.content);
  }
}
