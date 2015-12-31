package la.juju.android.yanwenzilayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by HelloVass on 15/12/1.
 */
public class TestFragment extends Fragment {

  private String mTitle;

  public TestFragment() {
  }

  public TestFragment(String title) {
    mTitle = title;
  }


  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_test, null);
    TextView title = (TextView) view.findViewById(R.id.tv_title);
    title.setText(mTitle);
    return view;
  }
}
