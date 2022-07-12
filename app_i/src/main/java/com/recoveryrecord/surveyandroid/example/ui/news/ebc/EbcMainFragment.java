package com.recoveryrecord.surveyandroid.example.ui.news.ebc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.recoveryrecord.surveyandroid.example.R;

public class EbcMainFragment extends Fragment {

//    private Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nested_layer1_media, container, false);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.container_main);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        return view;


    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            //super.destroyItem(container, position, object);
//        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Ebc1Fragment.newInstance(1);
                case 1:
                    return Ebc2Fragment.newInstance(2);
                case 2:
                    return Ebc3Fragment.newInstance(3);
                case 3:
                    return Ebc4Fragment.newInstance(4);
                case 4:
                    return Ebc5Fragment.newInstance(5);
                case 5:
                    return Ebc6Fragment.newInstance(6);
                case 6:
                    return Ebc7Fragment.newInstance(7);
                case 7:
                    return Ebc8Fragment.newInstance(8);
                case 8:
                    return Ebc9Fragment.newInstance(9);
                case 9:
                    return Ebc10Fragment.newInstance(10);
                case 10:
                    return Ebc11Fragment.newInstance(11);
                case 11:
                    return Ebc12Fragment.newInstance(12);
                case 12:
                    return Ebc13Fragment.newInstance(13);
                case 13:
                    return Ebc14Fragment.newInstance(14);
                case 14:
                    return Ebc15Fragment.newInstance(15);
                case 15:
                    return Ebc16Fragment.newInstance(16);
                case 16:
                    return Ebc17Fragment.newInstance(17);
                default:
                    return Ebc1Fragment.newInstance(1);
            }

        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 17;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "社會";
                case 1:
                    return "娛樂";
                case 2:
                    return "E星聞";
                case 3:
                    return "新奇";
                case 4:
                    return "暖聞";
                case 5:
                    return "政治";
                case 6:
                    return "國際";
                case 7:
                    return "兩岸";
                case 8:
                    return "生活";
                case 9:
                    return "財經";
                case 10:
                    return "星座";
                case 11:
                    return "房產";
                case 12:
                    return "體育";
                case 13:
                    return "汽車";
                case 14:
                    return "EBC森談";
                case 15:
                    return "健康";
                case 16:
                    return "旅遊";
                default:
                    return "Nested 2";
            }


        }
    }


}