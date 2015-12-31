package la.juju.android.yanwenzilayout.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import la.juju.android.yanwenzilayout.R;
import la.juju.android.yanwenzilayout.entities.FaceText;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 管理 YanWenZiFlowLayout、TabPageIndicator、DotViewLayout 的 ViewGroup，继承自 LinearLayout
 */
public class FaceTextLayout extends LinearLayout {

  private int mVerticalSpace;

  private int mHorizontalSpace;

  private int mFaceTextSize;

  private int mRowCount;

  private int mMaxColumnCount;

  private LinkedHashMap<String, List<FaceText>> mYanWenZiGroup;

  private TabPageIndicator mTabPageIndicator;

  private ViewPager mViewPager;

  private DotViewLayout mDotViewLayout;

  public FaceTextLayout(Context context) {
    this(context, null);
  }

  public FaceTextLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FaceTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    View.inflate(getContext(), R.layout.layout_face_text, this);

    setUpView();

    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

    mVerticalSpace =
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, displayMetrics);
    mHorizontalSpace =
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, displayMetrics);

    mFaceTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, displayMetrics);

    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FaceTextLayout);

    mVerticalSpace = typedArray.getDimensionPixelSize(R.styleable.FaceTextLayout_vertical_spacing,
        mVerticalSpace);
    mHorizontalSpace =
        typedArray.getDimensionPixelSize(R.styleable.FaceTextLayout_horizontal_spacing,
            mHorizontalSpace);
    mFaceTextSize =
        (int) typedArray.getDimension(R.styleable.FaceTextLayout_face_text_size, mFaceTextSize);

    mMaxColumnCount = typedArray.getInt(R.styleable.FaceTextLayout_max_column_count, 4);
    mRowCount = typedArray.getInt(R.styleable.FaceTextLayout_row_count, 3);

    typedArray.recycle();
  }

  private void setUpView() {
    // 初始化内部控件
    mTabPageIndicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
    mViewPager = (ViewPager) findViewById(R.id.view_pager);
    mDotViewLayout = (DotViewLayout) findViewById(R.id.dot_view_layout);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    measureChildren(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
  }

  public LinkedHashMap<String, List<FaceText>> getYanWenZiGroup() {
    return mYanWenZiGroup;
  }

  public void setYanWenZiGroup(LinkedHashMap<String, List<FaceText>> yanWenZiGroup) {
    if (yanWenZiGroup == null) {
      return;
    }
    mYanWenZiGroup = yanWenZiGroup;

    int selfWidth = getWidth();
    int selfHeight = getHeight();

    int tabPageIndicatorHeight = mTabPageIndicator.getHeight();
    int dotViewLayoutHeight = mDotViewLayout.getHeight();

    int pageHeight = selfHeight - tabPageIndicatorHeight - dotViewLayoutHeight;

    int faceTextViewHeight = (pageHeight - (mRowCount + 1) * mVerticalSpace) / mRowCount;

    Iterator<String> iterator = yanWenZiGroup.keySet().iterator();
    TextPaint textPaint = new TextPaint();
    //textPaint.setTextSize();
    //    Rect bounds = new Rect();

    if (iterator.hasNext()) {
      String groupName = iterator.next();
      List<FaceText> faceTextList = yanWenZiGroup.get(groupName);
      //      List<YanWenZiView> yanWenZiViewList = new ArrayList<>();
      int listSize = faceTextList.size();

      //int pageIndex;
      //int row, column;
      //      List<List<YanWenZi>> pages;

      // 当前行的行号
      int currentRowIndex = 0;
      // 当前行已摆放的个数
      int currentRowItemCount = 0;
      // 当前行能摆放的最多个数（由当前行中最小的均分母决定）
      int currentRowItemMaxCount = mMaxColumnCount;

      for (int i = 0; i < listSize; i++) {
        FaceText faceText = faceTextList.get(i);
        //YanWenZiView yanWenZiView = new YanWenZiView(getContext());
        //yanWenZiView.setYanWenZi(yanWenZi);
        //yanWenZiView.setLayoutParams();
        //yanWenZiView.setTextSize();
        float textWidth = textPaint.measureText(faceText.content);
        // 计算出最大均分母
        int maxAvgDenominator = calcMaxAvgDenominator(textWidth);

        // 当前行已摆放个数<当前行最大能摆放数 && 当前颜文字的最大均分母>当前行已摆放个数，可以放在同一行
        if (currentRowItemCount < currentRowItemMaxCount
            && maxAvgDenominator > currentRowItemCount) {

          faceText.row = currentRowIndex;

          if (i == 0) {
            faceText.column = 0;
          } else {
            FaceText previousFaceText = faceTextList.get(i - 1);
            faceText.column = previousFaceText.column + 1;
          }
          currentRowItemCount++;
          if (currentRowItemMaxCount > maxAvgDenominator) {
            currentRowItemMaxCount = maxAvgDenominator;
          }
        } else {
          // 否则就只能换行
          // 这里需要先判断是否需要先换页
          currentRowIndex++;
          faceText.row = currentRowIndex;
          faceText.column = 0;
          currentRowItemCount = 1;
          currentRowItemMaxCount = maxAvgDenominator;
        }
      }

      //for (int i = 0; i < listSize; i++) {
      //  FaceText faceText = faceTextList.get(i);
      //  Log.d("yanWenZi display",
      //      faceText.content + "    row:" + faceText.row + ", column:" + faceText.column);
      //}
    }
  }

  private int calcMaxAvgDenominator(float textWidth) {
    int maxAvgDenominator = mMaxColumnCount;
    for (int selfWidth = getMeasuredWidth(); maxAvgDenominator > 1; maxAvgDenominator--) {
      float minAvgWidth = selfWidth * 1.0f / maxAvgDenominator;
      if (textWidth <= minAvgWidth) break;
    }
    return maxAvgDenominator;
  }

  //        // 开始下一行
  //        if (currentRowItemCount == currentRowItemMaxCount) {
  //
  //        }
  //        if (currentRowIndex == mRowCount) {
  //          // TODO: page ++
  //        }
  //
  //        if (mMaxColumnCount < 2) {
  //          //row = i;
  //          yanWenZi.row = i;
  //          //column = 1;
  //          yanWenZi.column = 1;
  //          continue;
  //          //int yanWenZiItemWidth = selfWidth - 2 * mHorizontalSpace;
  //          //LayoutParams lp = new LayoutParams(yanWenZiItemWidth, yanWenZiItemHeight);
  //          //yanWenZiView.setLayoutParams(lp);
  //          //yanWenZiView.setLeft(mHorizontalSpace);
  //          //yanWenZiView.setTop(mVerticalSpace * (i + 1) + yanWenZiItemHeight * i);
  //        }
  //
  //        if (0 == i) {
  //          //row = i;
  //          yanWenZi.row = i;
  //          yanWenZi.column = i;
  //        } else {
  //          YanWenZi preYanWenZi = yanWenZiList.get(i - 1);
  //          if (textWidth > selfWidth / 2) {
  //            //row = i;
  //            yanWenZi.row = preYanWenZi.row + 1;
  //            // TODO: page ++ judge
  //            //column = 1;
  //            yanWenZi.column = 1;
  //          } else {
  //
  //          }
  //        }
  //yanWenZiViewList.add(yanWenZiView);

  private class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      return null;
    }

    @Override public int getCount() {
      return 0;
    }
  }
}
