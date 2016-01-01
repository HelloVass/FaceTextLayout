package la.juju.android.yanwenzilayoutdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import la.juju.android.yanwenzilayout.entities.FaceText;

/**
 * Created by HelloVass on 16/1/1.
 */
public class FaceTextAdapter extends RecyclerView.Adapter<FaceTextAdapter.FaceTextViewHolder> {

  private ArrayList<ArrayList<FaceText>> mPageFaceTextList;

  private Context mContext;

  public FaceTextAdapter(Context context) {
    mContext = context;
  }

  @Override public FaceTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new FaceTextViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_face_text, parent, false));
  }

  @Override public void onBindViewHolder(FaceTextViewHolder holder, int position) {

    List<FaceText> lineFaceTextList = mPageFaceTextList.get(position);

    for (int i = 0; i < lineFaceTextList.size(); i++) {
      FaceText faceText = lineFaceTextList.get(i);
      View faceTextContainer =
          LayoutInflater.from(mContext).inflate(R.layout.layout_face_text, null);
      holder.mLineContainer.addView(faceTextContainer);
      TextView textView = (TextView) faceTextContainer.findViewById(R.id.tv_face_text);
      textView.setText(faceText.content);
      textView.setGravity(Gravity.CENTER);
      LinearLayout.LayoutParams layoutParams =
          (LinearLayout.LayoutParams) faceTextContainer.getLayoutParams();
      layoutParams.weight = 1;
      layoutParams.width = 0;
      layoutParams.height = DensityUtil.dip2px(mContext, 48);
    }
  }

  @Override public int getItemCount() {
    return mPageFaceTextList.size();
  }

  public void setPageFaceTextList(ArrayList<ArrayList<FaceText>> pageFaceTextList) {
    mPageFaceTextList = pageFaceTextList;
  }

  public static class FaceTextViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout mLineContainer;

    public FaceTextViewHolder(View itemView) {
      super(itemView);
      mLineContainer = (LinearLayout) itemView.findViewById(R.id.ll_line_container);
    }
  }
}
