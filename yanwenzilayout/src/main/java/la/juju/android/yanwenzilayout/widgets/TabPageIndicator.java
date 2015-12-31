package la.juju.android.yanwenzilayout.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import la.juju.android.yanwenzilayout.R;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 显示组别名字的 ViewGroup，继承自 HorizontalScrollView
 */
public class TabPageIndicator extends HorizontalScrollView {

  private static final String TAG = TabPageIndicator.class.getSimpleName();
  // 当前 position
  private int mCurrentPosition = -1;

  // Tab 的总数
  private int mTabCount;

  private ViewPager mViewPager;

  // Tab 的容器
  private LinearLayout mTabsContainer;

  private PageListener mPageListener = new PageListener();

  private ViewPager.OnPageChangeListener mDelegatePageListener;

  private LinearLayout.LayoutParams mDefaultTabLayoutParams;

  private LinearLayout.LayoutParams mExpandTabLayoutParams;

  private boolean mShouldExpand;

  private int mTabPadding;

  private int mTabTextSize;

  private int mNormalTabTextColor;

  private int mSelectedTabTextColor;

  private int mScrollOffset;

  private int mTabIndicatorBackgroundResId;

  private int mLastScrollX;

  public TabPageIndicator(Context context) {
    this(context, null);
  }

  public TabPageIndicator(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TabPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {

    // 子控件的 width 小于 HorizontalScrollView 时，
    // android:layout_height="match_parent" 是不起作用的
    setFillViewport(true);
    addTabsContainer();

    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

    // 默认 Tab 的 padding 5dp
    mTabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, displayMetrics);
    // 默认选中 Tab 的滑动的距离 50dp
    mScrollOffset =
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
    // 默认字体大小 14sp
    mTabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, displayMetrics);

    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabPageIndicator);

    mTabPadding =
        typedArray.getDimensionPixelSize(R.styleable.TabPageIndicator_tab_padding, mTabPadding);
    mScrollOffset = typedArray.getDimensionPixelSize(R.styleable.TabPageIndicator_tab_scroll_offset,
        mScrollOffset);
    mTabTextSize =
        typedArray.getDimensionPixelSize(R.styleable.TabPageIndicator_tab_text_size, mTabTextSize);
    mNormalTabTextColor = typedArray.getColor(R.styleable.TabPageIndicator_normal_tab_text_color,
        mNormalTabTextColor);
    mSelectedTabTextColor =
        typedArray.getColor(R.styleable.TabPageIndicator_selected_tab_text_color,
            mSelectedTabTextColor);
    mShouldExpand = typedArray.getBoolean(R.styleable.TabPageIndicator_should_expand, true);
    mTabIndicatorBackgroundResId =
        typedArray.getResourceId(R.styleable.TabPageIndicator_tab_background,
            R.drawable.bg_tab_inticator);

    typedArray.recycle();

    generateTabsLayoutParams();
  }

  // 将 LinearLayout 作为 Tab 的容器，添加到 TabPageIndicator 中
  private void addTabsContainer() {
    mTabsContainer = new LinearLayout(getContext());
    mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
    mTabsContainer.setLayoutParams(
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    addView(mTabsContainer);
  }

  // 生成 Tab 的 LayoutParams
  private void generateTabsLayoutParams() {
    mDefaultTabLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        LayoutParams.MATCH_PARENT);
    mExpandTabLayoutParams =
        new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
  }

  public void setViewPager(ViewPager viewPager) {
    mViewPager = viewPager;
    if (viewPager.getAdapter() == null) {
      throw new IllegalStateException("ViewPager 没有 adapter 实例");
    }
    viewPager.addOnPageChangeListener(mPageListener);
    notifyDataSetChanged();
  }

  public void setOnPageChangeListener(ViewPager.OnPageChangeListener pageListener) {
    mDelegatePageListener = pageListener;
    if (mViewPager != null) {
      mViewPager.addOnPageChangeListener(pageListener);
    }
  }

  private void notifyDataSetChanged() {
    mTabsContainer.removeAllViews();

    mTabCount = mViewPager.getAdapter().getCount();

    for (int i = 0; i < mTabCount; i++) {
      addTabText(i, mViewPager.getAdapter().getPageTitle(i).toString());
    }

    // 更新 Tab 的样式
    updateTabStyles();

    getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {

        mCurrentPosition = mViewPager.getCurrentItem();
        scrollToChild(mCurrentPosition, 0);
        updateSelectedTabTextColor();

        // 讨厌的兼容问题
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
          getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
          getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
      }
    });
  }

  /**
   * 更新 Tab 的样式
   */
  private void updateTabStyles() {

    for (int i = 0; i < mTabCount; i++) {

      View v = mTabsContainer.getChildAt(i);
      v.setBackgroundResource(mTabIndicatorBackgroundResId);

      if (v instanceof TextView) {
        TextView tab = (TextView) v;
        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
        tab.setTextColor(mNormalTabTextColor);
      }
    }
  }

  /**
   * @param position 当前的位置
   * @param offset 偏移量
   */
  private void scrollToChild(int position, int offset) {

    if (mTabCount == 0) {
      return;
    }

    int newScrollX = mTabsContainer.getChildAt(position).getLeft() + offset;

    if (position > 0 || offset > 0) {
      newScrollX -= mScrollOffset;
    }

    if (mLastScrollX != newScrollX) {
      mLastScrollX = newScrollX;
      smoothScrollTo(newScrollX, 0);
      invalidate();
    }
  }

  private void addTabText(int position, String title) {
    TextView tab = new TextView(getContext());

    tab.setText(title);
    tab.setGravity(Gravity.CENTER);
    tab.setSingleLine();

    addTab(position, tab);
  }

  private void addTab(final int position, TextView tab) {
    tab.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        mViewPager.setCurrentItem(position);
      }
    });
    tab.setPadding(mTabPadding, 0, mTabPadding, 0);
    tab.setLayoutParams(mShouldExpand ? mExpandTabLayoutParams : mDefaultTabLayoutParams);
    mTabsContainer.addView(tab, position);
  }

  private class PageListener implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      Log.i(TAG, "-->> onPageScrolled");

      int offset = (int) (positionOffset * mTabsContainer.getChildAt(position).getWidth());
      scrollToChild(position, offset);
    }

    @Override public void onPageScrollStateChanged(int state) {
      Log.i(TAG, "-->> onPageScrollStateChanged");

      if (state == ViewPager.SCROLL_STATE_IDLE) {
        scrollToChild(mViewPager.getCurrentItem(), 0);
      }
    }

    @Override public void onPageSelected(int position) {
      mCurrentPosition = position;
      Log.i(TAG, "-->> onPageSelected");
      updateSelectedTabTextColor();
    }
  }

  private void updateSelectedTabTextColor() {
    for (int index = 0; index < mTabCount; index++) {
      TextView textView = (TextView) mTabsContainer.getChildAt(index);
      if (index == mCurrentPosition) {
        textView.setTextColor(mSelectedTabTextColor);
      } else {
        textView.setTextColor(mNormalTabTextColor);
      }
    }
  }
}
