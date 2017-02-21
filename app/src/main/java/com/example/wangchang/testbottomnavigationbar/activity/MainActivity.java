package com.example.wangchang.testbottomnavigationbar.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.wangchang.testbottomnavigationbar.R;
import com.example.wangchang.testbottomnavigationbar.fragment.BookFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.GameFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.HomeFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.MusicFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.TvFragment;
import com.helin.rxsample.base.ActivityLifeCycleEvent;
import com.helin.rxsample.base.BaseActivity;
import com.helin.rxsample.enity.Subject;
import com.helin.rxsample.http.Api;
import com.helin.rxsample.http.HttpUtil;
import com.helin.rxsample.http.ProgressSubscriber;
import com.helin.rxsample.view.SimpleLoadDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private ArrayList<Fragment> fragments;

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    private BadgeItem numberBadgeItem;
    private SimpleLoadDialog dialogHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        ButterKnife.bind(this);
        initToolBar();
        dialogHandler = new SimpleLoadDialog(MainActivity.this, null, true);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
         numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColor(Color.RED)
                .setText("5")
                .setHideOnSelect(true);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.mipmap.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                .addItem(new BottomNavigationItem(R.mipmap.ic_videogame_asset_white_24dp, "Games").setActiveColorResource(R.color.grey).setBadgeItem(numberBadgeItem))
                .setFirstSelectedPosition(0)
                .initialise();

        fragments = getFragments();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    private void initToolBar(){
        toolBar.setTitle("TapTap");
        toolBar.setSubtitle("这里是子标题");
        toolBar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolBar);
    }

    private void initToolBarPosition_One(){
        toolBar.setTitle("第二个fragment");
        toolBar.setSubtitle("这里是子标题");
        toolBar.setLogo(R.mipmap.app_icon);
        setSupportActionBar(toolBar);
    }
    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.layFrame, HomeFragment.newInstance("Home"));
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("Home"));
        fragments.add(BookFragment.newInstance("Books"));
        fragments.add(MusicFragment.newInstance("Music"));
        fragments.add(TvFragment.newInstance("Movies & TV"));
        fragments.add(GameFragment.newInstance("Games"));
        return fragments;
    }


    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                if(position == 0){
                    initToolBar();
                    doGet();
                    numberBadgeItem.setBorderWidth(4)
                            .setBackgroundColor(Color.RED)
                            .setText("4")
                            .setHideOnSelect(true);
                }
                else if(position == 1){
                    initToolBarPosition_One();
                }else{
                    initToolBar();
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.replace(R.id.layFrame, fragment);
                } else {
                    ft.add(R.id.layFrame, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    private void doGet() {
        //获取豆瓣电影TOP 100
        Observable ob = Api.getDefault().getTopMovie(0, 100);
        //嵌套请求
//        ob.flatMap(new Func1<String, Observable<HttpResult<Subject>>>() {
//
//            @Override
//            public Observable<HttpResult<Subject>> call(String s) {
//                return Api.getDefault().getUser("aa");
//            }
//        });


        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<List<Subject>>(this) {
            @Override
            protected void _onError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            protected void _onNext(List<Subject> list) {
                String str = "";
                for (int i = 0; i < list.size(); i++) {
                    str += "电影名：" + list.get(i).getTitle() + "\n";
                }
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            }
        }, "cacheKey", ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false);
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}
