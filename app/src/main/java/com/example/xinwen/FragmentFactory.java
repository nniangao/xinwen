package com.example.xinwen;

import android.annotation.SuppressLint;

import java.util.HashMap;

import com.example.xinwen.fragment.BaseFragment;
import com.example.xinwen.fragment.EntertainmentFragment;
import com.example.xinwen.fragment.FashionFragment;
import com.example.xinwen.fragment.FinanceFragment;
import com.example.xinwen.fragment.MilitaryFragment;
import com.example.xinwen.fragment.SocietyFragment;
import com.example.xinwen.fragment.SportsFragment;
import com.example.xinwen.fragment.TechnologyFragment;
import com.example.xinwen.fragment.TopFragment;


//创建每个页面

public class FragmentFactory {
    @SuppressLint("UseSparseArrays")
    private static HashMap<Integer,BaseFragment> mFragmentMap=new HashMap<Integer, BaseFragment>();
    public static BaseFragment createFragment(int pos){
        BaseFragment fragment=mFragmentMap.get(pos);
        if(fragment==null){
            switch (pos){
                case 0:
                    fragment=new TopFragment();
                    break;
                case 1:
                    fragment=new SocietyFragment();
                    break;
                case 2:
                    fragment=new EntertainmentFragment();
                    break;
                case 3:
                    fragment=new SportsFragment();
                    break;
                case 4:
                    fragment=new FinanceFragment();
                    break;
                case 5:
                    fragment=new TechnologyFragment();
                    break;
                case 6:
                    fragment=new MilitaryFragment();
                    break;
                case 7:
                    fragment=new FashionFragment();
                    break;
                default:
                    break;
            }

            mFragmentMap.put(pos,fragment);
        }
        return fragment;
    }
}
