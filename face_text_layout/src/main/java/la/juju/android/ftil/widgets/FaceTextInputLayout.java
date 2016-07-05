package la.juju.android.ftil.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import la.juju.android.ftil.R;
import la.juju.android.ftil.adapters.FaceTextInputLineAdapter;
import la.juju.android.ftil.entities.FaceText;
import la.juju.android.ftil.source.FaceTextProvider;
import la.juju.android.ftil.utils.DensityUtil;
import la.juju.android.ftil.utils.ScreenUtil;

/**
 * Created by HelloVass on 16/2/24.
 */
public class FaceTextInputLayout extends LinearLayout {

  private static final String TAG = FaceTextInputLayout.class.getSimpleName();

  public static final int PAGE_MAX_LINE_NUM = 3; // 每页的最大行号

  public static final int PAGE_MAX_COLUMN_COUNT = 4; // 每页的最大列数

  private ViewPager mViewPager;

  private DotViewLayout mDotViewLayout;

  private MyPagerAdapter mMyPagerAdapter;

  private int mFaceTextViewLeftMargin;

  private int mFaceTextViewRightMargin;

  private int mFaceTextViewHeight;

  private LinearLayout.LayoutParams mFaceTextContainerLayoutParams;

  private TextView mTargetFaceTextView; // 用于测量颜文字长度的“TextView”

  private FaceTextProvider mFaceTextProvider; // “颜文字source”接口

  private OnFaceTextClickListener mOnFaceTextClickListener; // "颜文字"点击事件回调接口

  public FaceTextInputLayout(Context context) {
    this(context, null);
  }

  public FaceTextInputLayout(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.FaceTextInputLayoutStyle);
  }

  public FaceTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr, 0);
  }

  /**
   * 设置“颜文字source”
   */
  public void setFaceTextSource(FaceTextProvider provider) {
    mFaceTextProvider = provider;
  }

  /**
   * 设置“颜文字”点击事件回调
   */
  public void setOnFaceTextClickListener(OnFaceTextClickListener onFaceTextClickListener) {
    mOnFaceTextClickListener = onFaceTextClickListener;
  }

  public void updateUI() {
    // 如果用户未设置“颜文字source”,辣么 return
    if (mFaceTextProvider == null) {
      return;
    }

    // TODO: 生成页面在主线程，需要放到非 UI线程
    List<RecyclerView> allPageList = generateAllPage();
    mMyPagerAdapter.setFaceTextInputPageList(allPageList);
    mViewPager.setOffscreenPageLimit(mMyPagerAdapter.getCount());
    mViewPager.setAdapter(mMyPagerAdapter);
    mDotViewLayout.setViewPager(mViewPager);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    mFaceTextProvider = null;
    mOnFaceTextClickListener = null;
  }

  private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

    setBackgroundColor(Color.parseColor("#efefef"));
    setOrientation(VERTICAL);
    View.inflate(getContext(), R.layout.layout_face_text_input, this);

    mViewPager = (ViewPager) findViewById(R.id.pager);
    mDotViewLayout = (DotViewLayout) findViewById(R.id.dotview_layout);

    mMyPagerAdapter = new MyPagerAdapter();
    mTargetFaceTextView = inflateTargetFaceTextView();

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

    mFaceTextViewLeftMargin =
        typedArray.getResourceId(R.styleable.FaceTextInputLayout_faceTextViewLeftMargin,
            DensityUtil.dip2px(getContext(), 2));

    mFaceTextViewRightMargin =
        typedArray.getResourceId(R.styleable.FaceTextInputLayout_faceTextViewLeftMargin,
            DensityUtil.dip2px(getContext(), 2));

    mFaceTextViewHeight =
        typedArray.getResourceId(R.styleable.FaceTextInputLayout_faceTextViewLeftMargin,
            DensityUtil.dip2px(getContext(), 48));

    mFaceTextContainerLayoutParams = generateFaceTextContainerLayoutParams();

    typedArray.recycle();
  }

  /**
   * 生成所有“颜文字”页面
   */
  private List<RecyclerView> generateAllPage() {
    List<List<List<FaceText>>> allPageFaceTextList = getAllPageFaceTextList();
    List<RecyclerView> pageList = new ArrayList<>();
    for (int i = 0; i < allPageFaceTextList.size(); i++) {
      RecyclerView eachPage = generateEachPage(allPageFaceTextList.get(i));
      pageList.add(eachPage);
    }
    return pageList;
  }

  /**
   * 颜文字排版算法
   */
  private List<List<List<FaceText>>> getAllPageFaceTextList() {
    List<FaceText> faceTextList = mFaceTextProvider.provideFaceTextList();
    List<List<List<FaceText>>> allPageFaceTextList = new ArrayList<>();

    // 当前行号
    int currentLineNum = 0;
    // 列数
    int columnCount = 0;
    // 行的长度
    int lineWidth = 0;

    // 每页的 List
    List<List<FaceText>> pageFaceTextList = new ArrayList<>();
    // 每行的 List
    List<FaceText> lineFaceTextList = new ArrayList<>();
    // 保存每行所有“item宽度”的 List
    List<Integer> lineItemWidthList = new ArrayList<>(PAGE_MAX_COLUMN_COUNT);
    // 将当前行添加到当前页
    pageFaceTextList.add(lineFaceTextList);
    // 将当前页添加到“页List”中
    allPageFaceTextList.add(pageFaceTextList);

    for (int i = 0; i < faceTextList.size(); i++) {
      FaceText faceText = faceTextList.get(i);

      int itemWidth = measureFaceTextWidth(mTargetFaceTextView, faceText)
          + mFaceTextViewLeftMargin
          + mFaceTextViewRightMargin;

      lineWidth += itemWidth;
      columnCount++;
      lineItemWidthList.add(itemWidth);

      if (canPlaceMutipileItems(lineWidth, lineItemWidthList, columnCount) || canPlaceSingleItem(
          columnCount, lineWidth)) {
        lineFaceTextList.add(faceText);
      } else {
        currentLineNum++;
        lineWidth = itemWidth;
        columnCount = 1;

        // 切换到下一个页面
        if (currentLineNum >= PAGE_MAX_LINE_NUM) {
          currentLineNum = 0;
          pageFaceTextList = new ArrayList<>();
          allPageFaceTextList.add(pageFaceTextList);
        }

        lineItemWidthList = new ArrayList<>();
        lineItemWidthList.add(itemWidth);

        lineFaceTextList = new ArrayList<>();
        lineFaceTextList.add(faceText);
        pageFaceTextList.add(lineFaceTextList);
      }
    }

    return allPageFaceTextList;
  }

  /**
   * 生成每个“颜文字”页面
   */
  private RecyclerView generateEachPage(List<List<FaceText>> faceTextList) {
    RecyclerView recyclerView = new RecyclerView(getContext());
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setHasFixedSize(true);
    recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

    FaceTextInputLineAdapter faceTextInputLineAdapter = new FaceTextInputLineAdapter(getContext());
    faceTextInputLineAdapter.setPageFaceTextList(faceTextList);
    faceTextInputLineAdapter.setFaceTextContainerLayoutParams(mFaceTextContainerLayoutParams);
    faceTextInputLineAdapter.setOnFaceTextClickListener(mOnFaceTextClickListener);

    recyclerView.setAdapter(faceTextInputLineAdapter);
    return recyclerView;
  }

  /**
   * 生成每个“颜文字” item 对应的 layoutParams
   */
  private LinearLayout.LayoutParams generateFaceTextContainerLayoutParams() {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(0, mFaceTextViewHeight, 1.0f);
    layoutParams.leftMargin = mFaceTextViewLeftMargin;
    layoutParams.rightMargin = mFaceTextViewRightMargin;
    return layoutParams;
  }

  /**
   * 能否在单行中摆放
   *
   * @param columnCount 列数
   * @param lineWidth 行宽
   */
  private boolean canPlaceSingleItem(int columnCount, int lineWidth) {
    return columnCount == 1 && lineWidth > ScreenUtil.getScreenWidth(getContext());
  }

  /**
   * 能否在一行中摆放多个 item
   */
  private boolean canPlaceMutipileItems(int lineWidth, List<Integer> lineItemWidthList,
      int columnCount) {

    for (int itemWidth : lineItemWidthList) {
      if (itemWidth > ScreenUtil.getScreenWidth(getContext()) / columnCount) {
        return false;
      }
    }

    return lineWidth <= ScreenUtil.getScreenWidth(getContext())
        && columnCount <= PAGE_MAX_COLUMN_COUNT;
  }

  /**
   * 获取一个用于测量“颜文字”长度的 TextView
   */
  private TextView inflateTargetFaceTextView() {
    return (TextView) LayoutInflater.from(getContext())
        .inflate(R.layout.wrapper_face_text, null)
        .findViewById(R.id.tv_face_text);
  }

  /**
   * 测量 颜文字 的长度
   */
  private int measureFaceTextWidth(TextView faceTextView, FaceText faceText) {
    if (faceTextView == null || faceText == null) {
      return 0;
    }
    faceTextView.setText(faceText.content);
    faceTextView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    return faceTextView.getMeasuredWidth();
  }

  /**
   * “颜文字”点击接口
   */
  public interface OnFaceTextClickListener {
    void onFaceTextClick(FaceText faceText);
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
