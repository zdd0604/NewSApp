package com.frame;

import com.frame.model.BannerModel;
import com.frame.model.ListModel;

import java.util.ArrayList;

/**
 * 测试数据
 * Created by wangchang on 2016/2/23.
 */
public class TestData {

    private static final String[] IMAGE_URL = {"http://h.hiphotos.baidu.com/image/pic/item/8b13632762d0f7037653e38b0bfa513d2797c580.jpg"
            , "http://d.hiphotos.baidu.com/image/pic/item/b3119313b07eca8056fe3eec932397dda04483c5.jpg"
            , "http://c.hiphotos.baidu.com/image/pic/item/d439b6003af33a87c3810411c45c10385243b5c8.jpg"
            , "http://h.hiphotos.baidu.com/image/pic/item/94cad1c8a786c917f8715532cb3d70cf3bc7574e.jpg"
            , "http://pic18.nipic.com/20111208/8817745_104926183165_2.jpg"
            , "http://pic46.nipic.com/20140821/17520916_150827473000_2.jpg"
            , "http://www.beihaichanyuan.org/d/file/chan/hanzang/xinyang/2010-05-18/93a834752c0b3e0d1d62d9bad09dcc53.jpg"
            , "http://image.techweb.com.cn/edit/2013/1024/56851382591845.jpg"
            , "http://2.im.guokr.com/gkimage/qe/b3/1i/qeb31i.png"
            , "http://img0.imgtn.bdimg.com/it/u=4055946124,363698976&fm=21&gp=0.jpg"
            , "http://img.sucai.redocn.com/attachments/images/201009/20100929/Redocn_2010092616252960.jpg"
            , "http://pic.nipic.com/2008-07-15/2008715225626215_2.jpg"
            , "http://e.hiphotos.baidu.com/zhidao/pic/item/3801213fb80e7bec28ee35b62f2eb9389b506bb4.jpg"};

    public static String getNetImage() {
        return IMAGE_URL[((int) (Math.random() * IMAGE_URL.length))];
    }

    public static ArrayList<BannerModel> bannerList() {
        ArrayList<BannerModel> imgList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            BannerModel model=new BannerModel();
            model.setImgUrl(getNetImage());
            imgList.add(model);
        }
        return imgList;
    }

    public static ListModel contentList() {
            ListModel model = new ListModel();
            model.setImgUrl(getNetImage());
            model.setPrice("$66.6");
            model.setText("人生若只如初见，何事秋风悲画扇");
        return model;
    }
}