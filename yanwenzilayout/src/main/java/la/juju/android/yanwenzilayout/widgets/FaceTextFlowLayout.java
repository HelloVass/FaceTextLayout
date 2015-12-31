package la.juju.android.yanwenzilayout.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 显示、排版颜文字的 ViewGroup
 */
public class FaceTextFlowLayout extends ViewGroup {

  private static final String TAG = FaceTextFlowLayout.class.getSimpleName();

  private List<FaceTextView> mFaceTextViewList;

  public FaceTextFlowLayout(Context context) {
    this(context, null);
  }

  public FaceTextFlowLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FaceTextFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    measureChildren(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {

    for (int index = 0; index < getChildCount(); index++) {

      View child = getChildAt(index);

      if (child.getVisibility() == View.GONE) {
        continue;
      }

      int childLeft = child.getLeft();
      int childTop = child.getTop();
      int childWidth = child.getMeasuredWidth();
      int childHeight = child.getMeasuredHeight();

      child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
    }
  }

  public void setFaceTextViewList(List<List<FaceTextView>> faceTextViewList) {
    if (faceTextViewList == null) {
      return;
    }
  }

  public List<FaceTextView> getFaceTextViewList() {
    return mFaceTextViewList;
  }
}
