package la.juju.android.ftil.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 管理小圆点的 ViewGroup，继承自 LinearLayout
 */
public class DotViewLayout extends LinearLayout implements ViewPager.OnPageChangeListener {

  // 被选中的 indicator 的 index
  private int mSelectedIndex = -1;

  // indicator 之间的间隔
  private int mIndicatorSpacing;

  // 被选中的 indicator 图片资源 ID
  private int mSelectedIndicatorResId;

  // 未选中的 indicator 图片资源 ID
  private int mUnSelectedIndicatorResId;

  private ViewPager mViewPager;

  public DotViewLayout(Context context) {
    this(context, null);
  }

  public DotViewLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    setOrientation(HORIZONTAL);
    setGravity(Gravity.CENTER);
  }

  public void setViewPager(ViewPager viewPager) {
    mViewPager = viewPager;
    mViewPager.addOnPageChangeListener(this);
    // 添加 indicator 到 DotViewLayout 中
    if (mViewPager != null) {
      addIndicator(mViewPager.getAdapter().getCount());
    }
  }

  private void addIndicator(int count) {

    clearIndicator();

    // 如果 count 为零，不需要添加 indicator
    if (count <= 0) {
      return;
    }

    for (int i = 0; i < count; i++) {
      ImageView imageView = new ImageView(getContext());
      LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.WRAP_CONTENT);
      layoutParams.leftMargin = mIndicatorSpacing;
      layoutParams.rightMargin = mIndicatorSpacing;
      // 设置未选中时的图片
      imageView.setImageResource(mUnSelectedIndicatorResId);
      addView(imageView, layoutParams);
    }

    // 更新 Indicator
    updateIndicator(mViewPager.getCurrentItem());
  }

  private void updateIndicator(int position) {

    // 如果 mSelectedIndex 不等于 position
    if (mSelectedIndex != position) {
      // 如果 mSelectedIndex 还未初始化
      if (mSelectedIndex == -1) {
        ImageView selectedIndicator = (ImageView) getChildAt(position);
        selectedIndicator.setImageResource(mSelectedIndicatorResId);
        mSelectedIndex = position;
        return;
      }

      ImageView selectedIndicator = (ImageView) getChildAt(position);
      ImageView unselectedIndicator = (ImageView) getChildAt(mSelectedIndex);
      selectedIndicator.setImageResource(mSelectedIndicatorResId);
      unselectedIndicator.setImageResource(mUnSelectedIndicatorResId);
      mSelectedIndex = position;
    }
  }

  // 移除“DotViewLayout”中的 indicator
  private void clearIndicator() {
    removeAllViews();
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {
    updateIndicator(position);
  }

  @Override public void onPageScrollStateChanged(int state) {

  }

  public int getIndicatorSpacing() {
    return mIndicatorSpacing;
  }

  public void setIndicatorSpacing(int indicatorSpacing) {
    mIndicatorSpacing = indicatorSpacing;
  }

  public int getSelectedIndicatorResId() {
    return mSelectedIndicatorResId;
  }

  public void setSelectedIndicatorResId(int selectedIndicatorResId) {
    mSelectedIndicatorResId = selectedIndicatorResId;
  }

  public int getUnSelectedIndicatorResId() {
    return mUnSelectedIndicatorResId;
  }

  public void setUnSelectedIndicatorResId(int unSelectedIndicatorResId) {
    mUnSelectedIndicatorResId = unSelectedIndicatorResId;
  }
}
