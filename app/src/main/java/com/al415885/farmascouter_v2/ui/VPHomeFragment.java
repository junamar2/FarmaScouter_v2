package com.al415885.farmascouter_v2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.al415885.farmascouter_v2.MainActivity;
import com.al415885.farmascouter_v2.R;
import com.al415885.farmascouter_v2.adapters.VPA;
import com.google.android.material.tabs.TabLayout;

public class VPHomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_view_pager_home, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // find views by id
        ViewPager viewPager = view.findViewById(R.id.vpHome);
        TabLayout tabLayout = view.findViewById(R.id.tlHome);

        // Check the navigation item
        setNavigationDrawerCheckedItem();

        // attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);

        VPA adapter = new VPA(getChildFragmentManager());

        adapter.addFrag(new HomeFragmentPS(), getResources().getString(R.string.supplyProb));
        adapter.addFrag(new HomeFragmentRC(), getResources().getString(R.string.changelog));

        // set adapter on viewpager
        viewPager.setAdapter(adapter);
    }

    /**
     * Method that checks the corresponding button on the Navigation Drawer
     */
    private void setNavigationDrawerCheckedItem() {
        for (int i = 0; i < 5; i++) {
            MenuItem item = ((MainActivity) requireActivity()).getNavigationDrawer().getMenu().getItem(i);
            if(item.getItemId() == R.id.navigation_home){
                item.setChecked(true);
            }
            else item.setChecked(false);
        }
    }
}