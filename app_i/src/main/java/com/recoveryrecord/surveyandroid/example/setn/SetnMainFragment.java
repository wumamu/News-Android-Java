package com.recoveryrecord.surveyandroid.example.setn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recoveryrecord.surveyandroid.example.R;
import com.recoveryrecord.surveyandroid.example.TestNest2Fragment;
import com.recoveryrecord.surveyandroid.example.chinatimes.Chinatimes10Fragment;
import com.recoveryrecord.surveyandroid.example.chinatimes.Chinatimes1Fragment;
import com.recoveryrecord.surveyandroid.example.chinatimes.Chinatimes2Fragment;
import com.recoveryrecord.surveyandroid.example.chinatimes.Chinatimes3Fragment;
import com.recoveryrecord.surveyandroid.example.chinatimes.Chinatimes4Fragment;
import com.recoveryrecord.surveyandroid.example.chinatimes.Chinatimes5Fragment;
import com.recoveryrecord.surveyandroid.example.chinatimes.Chinatimes6Fragment;
import com.recoveryrecord.surveyandroid.example.chinatimes.Chinatimes7Fragment;
import com.recoveryrecord.surveyandroid.example.chinatimes.Chinatimes8Fragment;
import com.recoveryrecord.surveyandroid.example.chinatimes.Chinatimes9Fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SetnMainFragment extends Fragment {

//    private Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2_media, container, false);
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
                    return Setn1Fragment.newInstance(1);
                case 1:
                    return Setn2Fragment.newInstance(2);
                case 2:
                    return Setn3Fragment.newInstance(3);
                case 3:
                    return Setn4Fragment.newInstance(4);
                case 4:
                    return Setn5Fragment.newInstance(5);
                case 5:
                    return Setn6Fragment.newInstance(6);
                case 6:
                    return Setn7Fragment.newInstance(7);
                case 7:
                    return Setn8Fragment.newInstance(8);
                case 8:
                    return Setn9Fragment.newInstance(9);
                case 9:
                    return Setn10Fragment.newInstance(10);
                case 10:
                    return Setn11Fragment.newInstance(11);
                case 11:
                    return Setn12Fragment.newInstance(12);
                case 12:
                    return Setn13Fragment.newInstance(13);
                case 13:
                    return Setn14Fragment.newInstance(14);
                case 14:
                    return Setn15Fragment.newInstance(15);
                case 15:
                    return Setn16Fragment.newInstance(16);
                case 16:
                    return Setn17Fragment.newInstance(17);
                case 17:
                    return Setn18Fragment.newInstance(18);
                case 18:
                    return Setn19Fragment.newInstance(19);
                default:
                    return TestNest2Fragment.newInstance(2);
            }

        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 19;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "政治";
                case 1:
                    return "娛樂";
                case 2:
                    return "生活";
                case 3:
                    return "健康";
                case 4:
                    return "社會";
                case 5:
                    return "運動";
                case 6:
                    return "旅遊";
                case 7:
                    return "國際";
                case 8:
                    return "名家";
                case 9:
                    return "汽車";
                case 10:
                    return "房產";
                case 11:
                    return "財經";
                case 12:
                    return "新奇";
                case 13:
                    return "寵物";
                case 14:
                    return "寶神";
                case 15:
                    return "日韓";
                case 16:
                    return "時尚";
                case 17:
                    return "電影";
                case 18:
                    return "音樂";
                default:
                    return "Nested 2";
            }


        }
    }


}