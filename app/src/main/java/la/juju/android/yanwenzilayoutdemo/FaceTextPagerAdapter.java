package la.juju.android.yanwenzilayoutdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by HelloVass on 15/12/31.
 */
public class FaceTextPagerAdapter extends FragmentPagerAdapter {

  private List<? extends Fragment> mFragments;

  public FaceTextPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public void setFragments(List<? extends Fragment> fragments) {
    mFragments = fragments;
  }

  @Override public Fragment getItem(int position) {
    if (mFragments == null) throw new IllegalStateException("记得要传入 Fragment 数组啊，大兄弟...");

    return mFragments.get(position);
  }

  @Override public int getCount() {
    return mFragments == null ? 0 : mFragments.size();
  }
}
