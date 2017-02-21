package com.example.wangchang.testbottomnavigationbar.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangchang.testbottomnavigationbar.R;
import com.example.wangchang.testbottomnavigationbar.adapter.ZhuangbiListAdapter;
import com.example.wangchang.testbottomnavigationbar.model.ZhuangbiImage;
import com.helin.rxsample.view.SimpleLoadDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WangChang on 2016/5/15.
 */
public class HomeFragment extends Fragment {
    private Context mContext;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    ZhuangbiListAdapter adapter = new ZhuangbiListAdapter();
    List<ZhuangbiImage> images = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(this, view);
//        gridRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        gridRv.setLayoutManager(new LinearLayoutManager(mContext));
        gridRv.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        for (int i = 0; i < 15; i++) {
            ZhuangbiImage zhImage = new ZhuangbiImage();
            zhImage.setDescription("第"+String.valueOf(i)+"张");
            if(i%2 == 0)
                zhImage.setImage_url("http://ww2.sinaimg.cn/large/610dc034jw1faza3ghd2lj20f00k1gof.jpg");
            else
                zhImage.setImage_url("http://ww2.sinaimg.cn/large/610dc034jw1fawx09uje2j20u00mh43f.jpg");
            images.add(zhImage);
        }
        adapter.setImages(images);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static HomeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
