package com.frame.activity;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.R;
import com.frame.adapter.TabFragmentAdapter;
import com.frame.fragment.BuyBusFragment;
import com.frame.fragment.MainFragment;
import com.frame.fragment.MyFragment;
import com.frame.fragment.SelectFragment;
import com.frame.fragment.TypeFragment;

import java.util.ArrayList;

/**
 * 说点什么
 * Created by liyingfeng on 2016/1/29.
 */
public class MainActivity extends BaseActivity {
    public static TabFragmentAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        adapter = new TabFragmentAdapter(this, getFragments(), R.id.tabLayout, R.id.main_content);
        adapter.addTab(getTabView(R.drawable.bottom_select), true);
        adapter.addTab(getTabView(R.drawable.bottom_select_1));
        adapter.addTab(getTabView(R.drawable.bottom_select_2));
        adapter.addTab(getTabView(R.drawable.bottom_select_3));
        adapter.addTab(getTabView(R.drawable.bottom_select_4));
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MainFragment());
        fragments.add(new TypeFragment());
        fragments.add(new SelectFragment());
        fragments.add(new BuyBusFragment());
        fragments.add(new MyFragment());
        return fragments;
    }

    private View getTabView(int imgRes) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        imageView.setImageResource(imgRes);
        return view;
    }
}