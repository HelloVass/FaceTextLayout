package la.juju.android.yanwenzilayoutdemo;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import java.util.ArrayList;
import java.util.List;
import la.juju.android.yanwenzilayout.entities.FaceText;

/**
 * Created by HelloVass on 15/12/31.
 */
public class FaceTextHelper implements FaceTextProvider {

  // 每页的行数
  private static final int PAGE_ROW_COUNT = 3;

  // 每页的最大列数
  private static final int PAGE_MAX_COLUMN_COUNT = 4;

  /**
   * 得到 颜文字 的数组
   */
  private static ArrayList<FaceText> getFaceTextList() {
    ArrayList<FaceText> faceTextList = new ArrayList<>();
    for (String content : FACE_TEXT_SOURCE) {
      FaceText faceText = new FaceText();
      faceText.content = content;
      faceTextList.add(faceText);
    }
    return faceTextList;
  }

  /**
   * 计算 Fragment 的数量
   */
  public static List<? extends Fragment> calculateFragmentCount() {
    ArrayList<ArrayList<ArrayList<FaceText>>> allPageFaceTextList = getAllPageFaceTextList();
    List<Fragment> fragments = new ArrayList<>();
    for (int i = 0; i < allPageFaceTextList.size(); i++) {
      Fragment faceTextFragment = FaceTextFragment.newInstance(allPageFaceTextList.get(i));
      fragments.add(faceTextFragment);
    }
    return fragments;
  }

  /**
   * 给颜文字排版
   */
  private static ArrayList<ArrayList<ArrayList<FaceText>>> getAllPageFaceTextList() {
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

    for (int i = 0; i < faceTextList.size(); i++) {
      FaceText faceText = faceTextList.get(i);
      int itemWidth = measureFaceTextWidth(faceText);
      lineWidth += itemWidth;
      columnCount++;

      if ((lineWidth <= ScreenUtil.getScreenWidth(FaceApp.get())
          && columnCount <= PAGE_MAX_COLUMN_COUNT) || (columnCount == 1
          && lineWidth > ScreenUtil.getScreenWidth(FaceApp.get()))) {
        lineFaceTextList.add(faceText);
      } else {
        currentLineNum++;
        lineWidth = itemWidth;
        columnCount = 1;

        // 切换到下一个 Fragment
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
   * 测量 颜文字 的长度
   */
  private static int measureFaceTextWidth(FaceText faceText) {
    Rect bounds = new Rect();
    TextPaint faceTextPaint = new TextPaint();
    faceTextPaint.getTextBounds(faceText.content, 0, faceText.content.length(), bounds);
    return bounds.width();
  }
}
