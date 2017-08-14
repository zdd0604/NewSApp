package com.frame.adapter;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.R;
import com.frame.model.BannerModel;
import com.frame.model.ItemModel;
import com.frame.model.ListModel;
import com.frame.utils.DevicePropertyUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangchang on 2016/2/23.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.BaseViewHolder> {

    private ArrayList<ItemModel> dataList = new ArrayList<>();


    public void addBanner(ItemModel list) {
        if (dataList.size() > 0) {
            ItemModel model = dataList.get(0);
            if (model.type != ItemModel.ITEM_VIEWPAGERE) {
                dataList.add(0, list);
            }
        } else {
            dataList.add(0, list);
        }
        notifyDataSetChanged();
    }

    public void AddNewsData(ItemModel itemModel) {
        dataList.add(itemModel);
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case ItemModel.ITEM_LIST:
                return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list, parent, false));
            case ItemModel.ITEM_VIEWPAGERE:
                return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_banner, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position).data, position);

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(Object model, int position) {


        }
    }

    class ListViewHolder extends BaseViewHolder {

        private ImageView iv;
        private TextView tvContent;
        private TextView tvPrivce;

        public ListViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.ivImg);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvPrivce = (TextView) itemView.findViewById(R.id.tvPrice);
        }

        @Override
        void setData(Object data, int position) {
            super.setData(data, position);
            ListModel model = (ListModel) data;
            Glide.with(itemView.getContext()).load(model.getImgUrl()).crossFade().into(iv);
            tvContent.setText(model.getText());
            tvPrivce.setText(model.getPrice());
        }
    }

    private static final long PERIOD = 3000;//轮播图滚动时间间隔

    class BannerViewHolder extends BaseViewHolder implements View.OnTouchListener, ViewPager.OnPageChangeListener {

        private ViewPager viewpager;
        private LinearLayout layDot;
        private BannerPagerAdapter bannerAdapter;
        private View layBanner;
        private View currentDot;
        private Timer timer;
        private TimerTask timerTask;
        private float x, y;//用于记录轮播图按下时的位置

        public BannerViewHolder(View itemView) {
            super(itemView);
            viewpager = (ViewPager) itemView.findViewById(R.id.viewPager);
            layDot = (LinearLayout) itemView.findViewById(R.id.layDot);
            layBanner = itemView.findViewById(R.id.layBanner);
            viewpager.setAdapter(bannerAdapter = new BannerPagerAdapter(itemView.getContext()));
            AppBarLayout.LayoutParams params = new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, DevicePropertyUtil.getScreenPixel(layBanner.getContext()).heightPixels / 3 * 1);
            viewpager.setOnTouchListener(this);
            viewpager.addOnPageChangeListener(this);
            layBanner.setLayoutParams(params);
        }

        @Override
        void setData(Object model, int position) {
            if (model instanceof ArrayList && model != null) {
                ArrayList<BannerModel> bannerModels = (ArrayList<BannerModel>) model;
                bannerAdapter.replaceAll(bannerModels);
                layDot.removeAllViews();
                if (bannerModels != null && bannerModels.size() > 0) {
                    int size = bannerModels.size();
                    int marginss = (int) viewpager.getContext().getResources().getDimension(R.dimen.space_4);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(marginss, marginss, marginss, marginss);
                    for (int i = 0; i < size; i++) {
                        ImageView imageView = new ImageView(viewpager.getContext());
                        imageView.setLayoutParams(layoutParams);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageView.setImageResource(R.drawable.bg_banner_circle);
                        layDot.addView(imageView);
                    }
                    currentDot = layDot.getChildAt(0);
                    currentDot.setSelected(true);
                    startTimer();
                }
            }

        }

        public void startTimer() {
            if (viewpager != null) {
                if (timerTask == null && timer == null) {
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            ((Activity) viewpager.getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                                }
                            });
                        }
                    };
                    timer = new Timer();
                    timer.schedule(timerTask, PERIOD, PERIOD);
                }
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.getId() == R.id.viewPager) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        stopTimer();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(event.getX() - x) < 10 && Math.abs(event.getY() - y) < 10) {
//                            BannerModel model = bannerAdapter.getCurrentBanner(viewPager.getCurrentItem() % layDot.getChildCount());
//                            if (model != null) {
//                                RouteManager.getInstance().toWebView((Activity) viewPager.getContext(), model.getOpenUrl(), true, "新闻详情", "");
//                            }
                        }
                        startTimer();
                        break;
                }
            }
            return false;
        }

        private void stopTimer() {
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            position %= layDot.getChildCount();
            if (currentDot != null) {
                currentDot.setSelected(false);
            }
            currentDot = layDot.getChildAt(position);
            currentDot.setSelected(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
