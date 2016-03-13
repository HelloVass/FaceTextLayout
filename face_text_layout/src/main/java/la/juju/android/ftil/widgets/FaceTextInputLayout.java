package la.juju.android.ftil.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.List;
import la.juju.android.ftil.R;
import la.juju.android.ftil.listeners.OnFaceTextClickListener;
import la.juju.android.ftil.source.FaceTextProvider;
import la.juju.android.ftil.utils.DensityUtil;
import la.juju.android.ftil.utils.FaceTextInputLayoutHelper;

/**
 * Created by HelloVass on 16/2/24.
 */
public class FaceTextInputLayout extends LinearLayout {

  private static final String TAG = FaceTextInputLayout.class.getSimpleName();

  private FaceTextInputLayoutHelper mFaceTextInputLayoutHelper;

  private ViewPager mViewPager;

  private DotViewLayout mDotViewLayout;

  private MyPagerAdapter mMyPagerAdapter;

  public FaceTextInputLayout(Context context) {
    this(context, null);
  }

  public FaceTextInputLayout(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.FaceTextInputLayoutStyle);
  }

  public FaceTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr, 0);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mFaceTextInputLayoutHelper.unregister();
    mFaceTextInputLayoutHelper.setFaceTextProvider(null);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    setOrientation(VERTICAL);
    View.inflate(getContext(), R.layout.layout_face_text_input, this);

    mViewPager = (ViewPager) findViewById(R.id.pager);
    mDotViewLayout = (DotViewLayout) findViewById(R.id.dotview_layout);

    mMyPagerAdapter = new MyPagerAdapter();
    mFaceTextInputLayoutHelper = FaceTextInputLayoutHelper.newInstance(context);

    applyXMLAttributes(attrs, defStyleAttr, defStyleRes);
  }

  private void applyXMLAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    TypedArray typedArray =
        getContext().obtainStyledAttributes(attrs, R.styleable.FaceTextInputLayout, defStyleAttr,
            defStyleRes);

    // 默认"indicator"间距 10dp
    int indicatorSpacing =
        typedArray.getDimensionPixelSize(R.styleable.FaceTextInputLayout_indicatorSpacing,
            DensityUtil.dip2px(getContext(), 10));
    mDotViewLayout.setIndicatorSpacing(indicatorSpacing);

    // 获取被选中的"indicator"图片资源 Id
    int selectedIndicatorResId =
        typedArray.getResourceId(R.styleable.FaceTextInputLayout_selectedSrc, -1);
    mDotViewLayout.setSelectedIndicatorResId(selectedIndicatorResId);

    // 获取未选中的"indicator" 图片资源 Id
    int unSelectedIndicatorResId =
        typedArray.getResourceId(R.styleable.FaceTextInputLayout_unselectedSrc, -1);
    mDotViewLayout.setUnSelectedIndicatorResId(unSelectedIndicatorResId);

    int faceTextViewLeftMargin =
        typedArray.getResourceId(R.styleable.FaceTextInputLayout_faceTextViewLeftMargin,
            DensityUtil.dip2px(getContext(), 2));
    mFaceTextInputLayoutHelper.setFaceTextViewLeftMargin(faceTextViewLeftMargin);

    int faceTextViewRightMargin =
        typedArray.getResourceId(R.styleable.FaceTextInputLayout_faceTextViewLeftMargin,
            DensityUtil.dip2px(getContext(), 2));
    mFaceTextInputLayoutHelper.setFaceTextViewRightMargin(faceTextViewRightMargin);

    int faceTextViewHeight =
        typedArray.getResourceId(R.styleable.FaceTextInputLayout_faceTextViewLeftMargin,
            DensityUtil.dip2px(getContext(), 48));
    mFaceTextInputLayoutHelper.setFaceTextViewHeight(faceTextViewHeight);

    typedArray.recycle();
  }

  private void updateUI() {

    // 如果用户未设置“颜文字source”,辣么 return
    if (mFaceTextInputLayoutHelper.getFaceTextProvider() == null) return;

    // TODO: 生成页面在主线程，需要放到非 UI线程
    List<RecyclerView> allPageList = mFaceTextInputLayoutHelper.generateAllPage();
    mMyPagerAdapter.setFaceTextInputPageList(allPageList);
    mViewPager.setOffscreenPageLimit(mMyPagerAdapter.getCount());
    mViewPager.setAdapter(mMyPagerAdapter);
    mDotViewLayout.setViewPager(mViewPager);
  }

  /**
   *
   * @param onFaceTextClickListener
   */
  public void setOnFaceTextClickListener(OnFaceTextClickListener onFaceTextClickListener) {
    mFaceTextInputLayoutHelper.register(onFaceTextClickListener);
  }

  /**
   * 设置“颜文字source”
   */
  public void setFaceTextSource(FaceTextProvider provider) {
    mFaceTextInputLayoutHelper.setFaceTextProvider(provider);
    updateUI();
  }

  private static class MyPagerAdapter extends PagerAdapter {

    private List<RecyclerView> mFaceTextInputPageList;

    public MyPagerAdapter() {

    }

    public MyPagerAdapter(List<RecyclerView> recyclerViewList) {
      mFaceTextInputPageList = recyclerViewList;
    }

    @Override public int getCount() {
      return mFaceTextInputPageList.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      container.addView(mFaceTextInputPageList.get(position));
      return mFaceTextInputPageList.get(position);
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView(mFaceTextInputPageList.get(position));
    }

    public void setFaceTextInputPageList(List<RecyclerView> faceTextInputPageList) {
      mFaceTextInputPageList = faceTextInputPageList;
    }
  }
}
