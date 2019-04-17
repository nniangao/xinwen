package com.example.xinwen.fragment;

import com.example.xinwen.R;


public class EntertainmentFragment extends BaseFragment {
    @Override
    public void onGetNewType() {
        getDataFromServer(getString(R.string.type_entertainment));
    }
}
