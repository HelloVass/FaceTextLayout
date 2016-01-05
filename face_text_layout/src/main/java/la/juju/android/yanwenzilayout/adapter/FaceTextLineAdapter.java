package la.juju.android.yanwenzilayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import la.juju.android.yanwenzilayout.R;
import la.juju.android.yanwenzilayout.entities.FaceText;
import la.juju.android.yanwenzilayout.utils.DensityUtil;

/**
 * Created by HelloVass on 16/1/1.
 */
public class FaceTextLineAdapter
    extends RecyclerView.Adapter<FaceTextLineAdapter.FaceTextViewHolder> {

  private ArrayList<ArrayList<FaceText>> mPageFaceTextList;

  private Context mContext;

  public FaceTextLineAdapter(Context context) {
    mContext = context;
  }

  @Override public FaceTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new FaceTextViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.listitem_face_text, parent, false));
  }

  @Override public void onBindViewHolder(FaceTextViewHolder holder, int position) {

    List<FaceText> lineFaceTextList = mPageFaceTextList.get(position);

    final LayoutInflater inflater = LayoutInflater.from(mContext);
    for (int i = 0; i < lineFaceTextList.size(); i++) {
      FaceText faceText = lineFaceTextList.get(i);
      TextView faceTextContainer =
          (TextView) inflater.inflate(R.layout.container_face_text, holder.mLineContainer);
      LinearLayout.LayoutParams layoutParams = generateFaceTextContainerLayoutParams();
      holder.mLineContainer.addView(faceTextContainer, layoutParams);
      faceTextContainer.setText(faceText.content);
    }
  }

  private LinearLayout.LayoutParams generateFaceTextContainerLayoutParams() {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(0, DensityUtil.dip2px(mContext, 48), 1.0f);
    layoutParams.leftMargin = DensityUtil.dip2px(mContext, 4);
    layoutParams.rightMargin = DensityUtil.dip2px(mContext, 4);
    return layoutParams;
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
