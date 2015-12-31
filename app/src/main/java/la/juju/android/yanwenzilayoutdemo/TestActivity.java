package la.juju.android.yanwenzilayoutdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import la.juju.android.yanwenzilayout.widgets.DotViewLayout;
import la.juju.android.yanwenzilayout.widgets.TabPageIndicator;

/**
 * Created by HelloVass on 15/12/1.
 */
public class TestActivity extends AppCompatActivity {

  private DotViewLayout mDotViewLayout;

  private TabPageIndicator mTabPageIndicator;

  private ViewPager mViewPager;

  private MyFragmentStatePagerAdapter mStatePagerAdapter;

  private int mDotViewCount = 5;

  private String[] mGroupNames = { "三十日热门", "最新", "生活家", "热门", "推荐", "发现更多" };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_test);

    mDotViewLayout = (DotViewLayout) findViewById(R.id.dot_view_layout);
    mTabPageIndicator = (TabPageIndicator) findViewById(R.id.tab_page_indicator);
    mViewPager = (ViewPager) findViewById(R.id.view_pager);

    mStatePagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(mStatePagerAdapter);

    mTabPageIndicator.setViewPager(mViewPager);
    mDotViewLayout.setViewPager(mViewPager);
  }

  private class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    public MyFragmentStatePagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      return new TestFragment(mGroupNames[position]);
    }

    @Override public int getCount() {
      return mGroupNames.length;
    }

    @Override public CharSequence getPageTitle(int position) {
      return mGroupNames[position];
    }
  }
}
