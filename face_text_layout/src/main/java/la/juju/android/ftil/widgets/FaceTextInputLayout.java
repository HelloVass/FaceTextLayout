package la.juju.android.ftil.widgets;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import java.util.List;
import la.juju.android.ftil.R;
import la.juju.android.ftil.listeners.OnFaceTextClickListener;
import la.juju.android.ftil.source.FaceTextProvider;
import la.juju.android.ftil.utils.FaceTextInputLayoutHelper;

/**
 * Created by HelloVass on 16/2/24.
 */
public class FaceTextInputLayout extends RelativeLayout {

  private FaceTextInputLayoutHelper mFaceTextInputLayoutHelper;

  private ViewPager mViewPager;

  private DotViewLayout mDotViewLayout;

  private List<RecyclerView> mAllPageList;

  private MyPagerAdapter mMyPagerAdapter;

  public FaceTextInputLayout(Context context) {
    this(context, null);
  }

  public FaceTextInputLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FaceTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mFaceTextInputLayoutHelper.unregister();
    mFaceTextInputLayoutHelper.setFaceTextProvider(null);
  }

  private void init(Context context, AttributeSet attrs) {
    View.inflate(getContext(), R.layout.layout_face_text_input, this);

    mViewPager = (ViewPager) findViewById(R.id.pager);
    mDotViewLayout = (DotViewLayout) findViewById(R.id.dotview_layout);

    mMyPagerAdapter = new MyPagerAdapter();
    mFaceTextInputLayoutHelper = FaceTextInputLayoutHelper.getInstance(context);

    // 没有设置“颜文字source”则 return
    if (mFaceTextInputLayoutHelper.getFaceTextProvider() == null) return;

    updateUI();
  }

  private void updateUI() {
    // TODO: 生成页面在主线程，需要放到非 UI线程
    mAllPageList = mFaceTextInputLayoutHelper.generateAllPage();
    mMyPagerAdapter.setFaceTextInputPageList(mAllPageList);
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
