package la.juju.www.android.ftil.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import la.juju.www.android.ftil.listeners.OnFaceTextClickListener;
import la.juju.www.android.ftil.R;
import la.juju.www.android.ftil.entities.FaceText;
import la.juju.www.android.ftil.utils.DensityUtil;

/**
 * Created by HelloVass on 16/1/1.
 */
public class FaceTextInputLineAdapter
    extends RecyclerView.Adapter<FaceTextInputLineAdapter.FaceTextViewHolder> {

  private ArrayList<ArrayList<FaceText>> mPageFaceTextList;

  private Context mContext;

  private LayoutInflater mInflater;

  private OnFaceTextClickListener mOnFaceTextClickListener;

  public FaceTextInputLineAdapter(Context context) {
    mContext = context;
    mInflater = LayoutInflater.from(context);
  }

  @Override public FaceTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new FaceTextViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.listitem_face_text_input, parent, false));
  }

  @Override public void onBindViewHolder(FaceTextViewHolder holder, int position) {

    List<FaceText> lineFaceTextList = mPageFaceTextList.get(position);

    for (int i = 0; i < lineFaceTextList.size(); i++) {
      final FaceText faceText = lineFaceTextList.get(i);
      TextView faceTextView = (TextView) mInflater.inflate(R.layout.view_face_text, null);
      LinearLayout.LayoutParams layoutParams = generateFaceTextContainerLayoutParams();
      holder.mLineContainer.addView(faceTextView, layoutParams);
      faceTextView.setText(faceText.content);
      faceTextView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (mOnFaceTextClickListener != null) {
            mOnFaceTextClickListener.onFaceTextClick(faceText);
          }
        }
      });
    }
  }

  private LinearLayout.LayoutParams generateFaceTextContainerLayoutParams() {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(0, DensityUtil.dip2px(mContext, 48), 1.0f);
    layoutParams.leftMargin =
        mContext.getResources().getDimensionPixelOffset(R.dimen.face_text_view_left_margin);
    layoutParams.rightMargin =
        mContext.getResources().getDimensionPixelOffset(R.dimen.face_text_view_right_margin);
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

  public void setOnFaceTextClickListener(OnFaceTextClickListener onFaceTextClickListener) {
    mOnFaceTextClickListener = onFaceTextClickListener;
  }
}
