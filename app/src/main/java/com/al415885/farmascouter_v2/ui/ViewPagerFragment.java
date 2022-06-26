package com.al415885.farmascouter_v2.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.al415885.farmascouter_v2.DrugActivity;
import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.adapters.VPA;
import com.google.android.material.tabs.TabLayout;

public class ViewPagerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_view_pager, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // find views by id
        ViewPager viewPager = view.findViewById(R.id.vpDrug);
        TabLayout tabLayout = view.findViewById(R.id.tlDrug);

        // attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);

        VPA adapter = new VPA(getChildFragmentManager());

        // add your fragments
        Activity activity = requireActivity();
        if(activity.getClass().equals(MainActivity.class)){
            adapter.addFrag(new HomeFragmentPS(), getResources().getString(R.string.supplyProb));
            adapter.addFrag(new HomeFragmentRC(), getResources().getString(R.string.changelog));
        }
        else if(activity.getClass().equals(DrugActivity.class)) {
            adapter.addFrag(new DrugFragment(), getResources().getString(R.string.generalInfo));
            adapter.addFrag(new DrugUMLSFragment(), getResources().getString(R.string.moreInfo));
        }

        // set adapter on viewpager
        viewPager.setAdapter(adapter);
    }
}