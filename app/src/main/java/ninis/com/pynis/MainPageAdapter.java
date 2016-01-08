package ninis.com.pynis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ninis.com.pynis.data.ClienTabData;

/**
 * Created by gypark on 2016. 1. 5..
 */
public class MainPageAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> mFragments = new ArrayList<>();
    private final ArrayList<String> mFragmentTitles = new ArrayList<>();

    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, ClienTabData.TabItem data) {
        mFragments.add(fragment);
        mFragmentTitles.add(data.getTitle());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
