package la.juju.www.android.ftil.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import la.juju.www.android.ftil.listeners.OnFaceTextClickListener;
import la.juju.www.android.ftil.R;
import la.juju.www.android.ftil.adapters.FaceTextInputLineAdapter;
import la.juju.www.android.ftil.entities.FaceText;

/**
 * Created by HelloVass on 16/2/24.
 */
public class FaceTextInputLayoutHelper implements FaceTextProvider {

  // 每页的行数
  private static final int PAGE_ROW_COUNT = 3;
  // 每页的最大列数
  private static final int PAGE_MAX_COLUMN_COUNT = 4;

  private Context mContext;

  private List<FaceTextInputLineAdapter> mFaceTextInputLineAdapterList;

  private FaceTextInputLayoutHelper(Context context) {
    mContext = context;
    mFaceTextInputLineAdapterList = new ArrayList<>();
  }

  public static FaceTextInputLayoutHelper newInstance(Context context) {
    return new FaceTextInputLayoutHelper(context);
  }

  /**
   * 生成所有“颜文字”页面
   */
  public List<RecyclerView> generateAllPage() {
    ArrayList<ArrayList<ArrayList<FaceText>>> allPageFaceTextList = getAllPageFaceTextList();
    List<RecyclerView> pageList = new ArrayList<>();
    for (int i = 0; i < allPageFaceTextList.size(); i++) {
      RecyclerView eachPage = generateEachPage(allPageFaceTextList.get(i));
      pageList.add(eachPage);
    }
    return pageList;
  }

  public void register(OnFaceTextClickListener listener) {
    for (FaceTextInputLineAdapter adapter : mFaceTextInputLineAdapterList) {
      adapter.setOnFaceTextClickListener(listener);
    }
  }

  public void unregister() {
    for (FaceTextInputLineAdapter adapter : mFaceTextInputLineAdapterList) {
      adapter.setOnFaceTextClickListener(null);
    }
  }

  /**
   * 得到"颜文字"的数组
   */
  private ArrayList<FaceText> getFaceTextList() {
    ArrayList<FaceText> faceTextList = new ArrayList<>();
    for (String content : FaceTextProvider.FACE_TEXT_SOURCE) {
      FaceText faceText = new FaceText();
      faceText.content = content;
      faceTextList.add(faceText);
    }
    return faceTextList;
  }

  /**
   * 生成每个“颜文字”页面
   */
  private RecyclerView generateEachPage(ArrayList<ArrayList<FaceText>> faceTextList) {
    RecyclerView recyclerView = new RecyclerView(mContext);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    FaceTextInputLineAdapter faceTextInputLineAdapter = new FaceTextInputLineAdapter(mContext);
    mFaceTextInputLineAdapterList.add(faceTextInputLineAdapter);
    faceTextInputLineAdapter.setPageFaceTextList(faceTextList);
    recyclerView.setAdapter(faceTextInputLineAdapter);
    return recyclerView;
  }

  /**
   * 给颜文字排版
   */
  private ArrayList<ArrayList<ArrayList<FaceText>>> getAllPageFaceTextList() {
    ArrayList<FaceText> faceTextList = getFaceTextList();
    ArrayList<ArrayList<ArrayList<FaceText>>> allPageFaceTextList = new ArrayList<>();

    // 当前行数
    int currentLineNum = 0;
    // 列数
    int columnCount = 0;
    // 行的长度
    int lineWidth = 0;

    // 每页的 List
    ArrayList<ArrayList<FaceText>> pageFaceTextList = new ArrayList<>();
    // 每行的 List
    ArrayList<FaceText> lineFaceTextList = new ArrayList<>();
    // 将当前行添加到当前页
    pageFaceTextList.add(lineFaceTextList);
    // 将当前页添加到 页List 中
    allPageFaceTextList.add(pageFaceTextList);

    TextView faceTextView = getFaceTextView();
    for (int i = 0; i < faceTextList.size(); i++) {
      FaceText faceText = faceTextList.get(i);
      int itemWidth = measureFaceTextWidth(faceTextView, faceText) + generateHorizontalMargin();
      lineWidth += itemWidth;
      columnCount++;

      if ((lineWidth <= ScreenUtil.getScreenWidth(mContext) && columnCount <= PAGE_MAX_COLUMN_COUNT)
          || (columnCount == 1 && lineWidth > ScreenUtil.getScreenWidth(mContext))) {
        lineFaceTextList.add(faceText);
      } else {
        currentLineNum++;
        lineWidth = itemWidth;
        columnCount = 1;

        // 切换到下一个页面
        if (currentLineNum > PAGE_ROW_COUNT) {
          currentLineNum = 0;
          pageFaceTextList = new ArrayList<>();
          allPageFaceTextList.add(pageFaceTextList);
        }

        lineFaceTextList = new ArrayList<>();
        lineFaceTextList.add(faceText);
        pageFaceTextList.add(lineFaceTextList);
      }
    }

    return allPageFaceTextList;
  }

  /**
   * 获取一个 TextView
   */
  private TextView getFaceTextView() {
    return (TextView) LayoutInflater.from(mContext)
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
   * 生成 leftMargin 和 rightMargin
   */
  private int generateHorizontalMargin() {
    int leftMargin =
        mContext.getResources().getDimensionPixelOffset(R.dimen.face_text_view_left_margin);
    int rightMargin =
        mContext.getResources().getDimensionPixelOffset(R.dimen.face_text_view_right_margin);
    return leftMargin + rightMargin;
  }
}
