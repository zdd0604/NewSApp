package com.frame.adapter;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.R;

import java.util.ArrayList;


/**
 * tab+fragment适配器
 *
 * @author 张建光
 * @Date: 2015/12/21 13:55
 */
public class TabFragmentAdapter implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragments;
    private int contentId;
    private Activity activity;
    private int selectedIndex;

    public TabFragmentAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments, @IdRes int tabId, @IdRes int contentId) {
        this.fragmentManager = fragmentActivity.getSupportFragmentManager();
        this.fragments = fragments;
        this.contentId = contentId;
        this.tabLayout = (TabLayout) fragmentActivity.findViewById(tabId);
        this.activity = fragmentActivity;
        tabLayout.setOnTabSelectedListener(this);
    }


    public void addTab(View view) {
        addTab(view, false);
    }

    public void addTab(View view, boolean selected) {
        tabLayout.addTab(tabLayout.newTab().setCustomView(view), selected);

    }


    public void addTab(CharSequence charSequence) {
        addTab(charSequence, false);
    }

    public void addTab(CharSequence charSequence, boolean selected) {
        tabLayout.addTab(tabLayout.newTab().setText(charSequence), selected);
    }

    public Fragment getCurrentFragment() {
        return fragments.get(tabLayout.getSelectedTabPosition());
    }

    public int getCurrentIndex() {
        return tabLayout.getSelectedTabPosition();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (fragments != null) {
            int positon = tab.getPosition();
            if (positon < fragments.size()) {
                if (positon == 2) {
                    ImageView ivPoint = (ImageView) tabLayout.getTabAt(2).getCustomView().findViewById(R.id.iv_point);
                    ivPoint.setVisibility(View.VISIBLE);
                } else if (positon == 3) {
                    TextView tvPoint = (TextView) tabLayout.getTabAt(3).getCustomView().findViewById(R.id.tv_name);
                    tvPoint.setText("33");
                    tvPoint.setBackgroundResource(R.drawable.bg_tv);
                    tvPoint.setPadding(4, 2, 4, 2);
                    tvPoint.setVisibility(View.VISIBLE);
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment f = fragments.get(positon);
                if (f.isAdded()) {
                    transaction.show(f);
                } else {
                    transaction.add(contentId, f);
                }
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (fragments != null) {
            int positon = tab.getPosition();
            if (positon < fragments.size()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment f = fragments.get(positon);
                transaction.hide(f);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}