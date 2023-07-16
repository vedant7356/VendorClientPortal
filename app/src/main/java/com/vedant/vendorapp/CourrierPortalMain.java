package com.vedant.vendorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;
import com.vedant.vendorapp.LocationFragments.LocationFour;
import com.vedant.vendorapp.LocationFragments.LocationOne;
import com.vedant.vendorapp.LocationFragments.LocationThree;
import com.vedant.vendorapp.LocationFragments.LocationTwo;

public class CourrierPortalMain extends AppCompatActivity {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courrier_portal_main);
    // get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
// Create a new Tab named "First"
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Loc-1"); // set the Text for the first Tab
        firstTab.setIcon(R.drawable.ic_baseline_location_on_24); // set an icon for the
// first tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
// Create a new Tab named "Second"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Loc-2"); // set the Text for the second Tab
        secondTab.setIcon(R.drawable.ic_baseline_location_on_24); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
// Create a new Tab named "Third"
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Loc-3"); // set the Text for the first Tab
        thirdTab.setIcon(R.drawable.ic_baseline_location_on_24); // set an icon for the first tab
        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout

        // Create a new Tab named "Fourth"
        TabLayout.Tab fourthTab = tabLayout.newTab();
        fourthTab.setText("Loc-4"); // set the Text for the first Tab
        fourthTab.setIcon(R.drawable.ic_baseline_location_on_24); // set an icon for the first tab
        tabLayout.addTab(fourthTab); // add  the tab at in the TabLayout

        Fragment fragment= new LocationOne();

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.simpleFrameLayout,fragment)
                .commit();

// perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new LocationOne();
                        break;
                    case 1:
                        fragment = new LocationTwo();
                        break;
                    case 2:
                        fragment = new LocationThree();
                        break;
                    case 3:
                        fragment = new LocationFour();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}