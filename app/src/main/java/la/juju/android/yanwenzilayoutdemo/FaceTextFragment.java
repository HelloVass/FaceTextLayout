package la.juju.android.yanwenzilayoutdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import la.juju.android.yanwenzilayout.adapter.FaceTextLineAdapter;
import la.juju.android.yanwenzilayout.entities.FaceText;

/**
 * Created by HelloVass on 15/12/31.
 */
public class FaceTextFragment extends Fragment {

  private static final String EXTRAS_FACE_TEXT = "extras_face_text";

  private ArrayList<ArrayList<FaceText>> mPageFaceTextList;

  private FaceTextLineAdapter mFaceTextLineAdapter;

  private RecyclerView mRecyclerView;

  private Context mContext;

  public static Fragment newInstance(ArrayList<ArrayList<FaceText>> faceTextList) {
    Fragment fragment = new FaceTextFragment();
    Bundle args = new Bundle();
    args.putSerializable(EXTRAS_FACE_TEXT, faceTextList);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
  }

  @Override public void onDetach() {
    super.onDetach();
    mContext = null;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mFaceTextLineAdapter = new FaceTextLineAdapter(mContext);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    parseArguments();
    View view = inflater.inflate(R.layout.fragment_face_text, null);
    setUpView(view);
    return view;
  }

  private void setUpView(View view) {
    mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    mFaceTextLineAdapter.setPageFaceTextList(mPageFaceTextList);
    mRecyclerView.setAdapter(mFaceTextLineAdapter);
  }

  /**
   * 解析参数，获取其中的颜文字
   */
  private void parseArguments() {
    Bundle args = getArguments();
    mPageFaceTextList = (ArrayList<ArrayList<FaceText>>) args.getSerializable(EXTRAS_FACE_TEXT);
  }
}
