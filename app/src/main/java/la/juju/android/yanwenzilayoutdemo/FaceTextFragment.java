package la.juju.android.yanwenzilayoutdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import la.juju.android.yanwenzilayout.entities.FaceText;

/**
 * Created by HelloVass on 15/12/31.
 */
public class FaceTextFragment extends Fragment {

  private static final String EXTRAS_FACE_TEXT = "extras_face_text";

  private ArrayList<ArrayList<FaceText>> mPageFaceTextList;

  private FaceTextAdapter mFaceTextAdapter;

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
    mFaceTextAdapter = new FaceTextAdapter(mContext);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_face_text, null);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    parseArguments();
    mFaceTextAdapter.setPageFaceTextList(mPageFaceTextList);
    mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    mRecyclerView.setAdapter(mFaceTextAdapter);
  }

  private void parseArguments() {
    Bundle args = getArguments();
    mPageFaceTextList = args.getParcelable(EXTRAS_FACE_TEXT);
  }
}
