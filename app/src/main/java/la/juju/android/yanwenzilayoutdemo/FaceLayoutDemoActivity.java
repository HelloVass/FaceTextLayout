package la.juju.android.yanwenzilayoutdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.List;
import la.juju.android.yanwenzilayout.widgets.DotViewLayout;

/**
 * Created by HelloVass on 15/12/31.
 */
public class FaceLayoutDemoActivity extends AppCompatActivity {

  private ViewPager mViewPager;

  private DotViewLayout mDotViewLayout;

  private FaceTextPagerAdapter mFaceTextPagerAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_face_demo);
    setUpView();
  }

  private void setUpView() {
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mDotViewLayout = (DotViewLayout) findViewById(R.id.dotview_layout);
    mFaceTextPagerAdapter = new FaceTextPagerAdapter(getSupportFragmentManager());
    updateFaceTextViewPager(FaceTextHelper.calculateFragmentCount());
    mDotViewLayout.setViewPager(mViewPager);
  }

  private void updateFaceTextViewPager(List<? extends Fragment> fragments) {
    if (fragments == null) {
      throw new IllegalStateException("Fragments 为 null");
    }
    mFaceTextPagerAdapter.setFragments(fragments);
    mViewPager.setAdapter(mFaceTextPagerAdapter);
    mViewPager.setOffscreenPageLimit(mFaceTextPagerAdapter.getCount());
  }
}
