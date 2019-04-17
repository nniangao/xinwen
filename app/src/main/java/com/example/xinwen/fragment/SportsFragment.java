package com.example.xinwen.fragment;

import com.example.xinwen.R;


public class SportsFragment extends BaseFragment {
    @Override
    public void onGetNewType() {
        getDataFromServer(getString(R.string.type_sports));
    }
}
