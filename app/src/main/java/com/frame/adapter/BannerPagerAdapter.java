package com.frame.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.frame.R;
import com.frame.model.BannerModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class BannerPagerAdapter extends PagerAdapter {

    private WeakReference<Context> weakReference;
    private HashMap<Integer, ImageView> hm = new HashMap<>();// 放置图片的列表
    private ArrayList<BannerModel> bannerModels = new ArrayList<>();

    public BannerPagerAdapter(@NonNull Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void replaceAll(ArrayList<BannerModel> list) {
        bannerModels.clear();
        hm.clear();
        if (list != null) {
            bannerModels.addAll(list);
        }
        notifyDataSetChanged();
    }

    public ArrayList<BannerModel> getBannerModels() {
        return bannerModels;
    }

    public BannerModel getCurrentBanner(int position) {
        if (bannerModels != null && position < bannerModels.size()) {
            return bannerModels.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return bannerModels == null ? 0 : ((bannerModels.size() == 0 || bannerModels.size() == 1) ? bannerModels.size() : Integer.MAX_VALUE);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= bannerModels.size();
        if (position < 0) {
            position = bannerModels.size() + position;
        }
        ImageView imageView = null;
        if (hm.containsKey(position)) {
            imageView = hm.get(position);
        }
        if (imageView == null) {
            imageView = new ImageView(weakReference.get());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(weakReference.get()).load(bannerModels.get(position).getImgUrl()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).crossFade().into(imageView);
            hm.put(position, imageView);
        }
        if (imageView.getParent() != null) {
            ViewGroup parent = (ViewGroup) imageView.getParent();
            parent.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }


}