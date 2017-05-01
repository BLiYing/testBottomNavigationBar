package com.example.wangchang.testbottomnavigationbar.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.wangchang.testbottomnavigationbar.R;
import com.example.wangchang.testbottomnavigationbar.base.ActivityLifeCycleEvent;
import com.example.wangchang.testbottomnavigationbar.base.BaseActivity;
import com.example.wangchang.testbottomnavigationbar.fragment.BookFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.GameFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.HomeFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.MusicFragment;
import com.example.wangchang.testbottomnavigationbar.fragment.TvFragment;
import com.example.wangchang.testbottomnavigationbar.util.NetUtil;
import com.example.wangchang.testbottomnavigationbar.view.SimpleLoadDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.toolBar_home)
    Toolbar toolBarHome;
    private ArrayList<Fragment> fragments;
    private Context mContext;

    private HomeFragment homeFragment;
    private BookFragment bookFragment;
    private MusicFragment musicFragment;
    private TvFragment tvFragment;
    private GameFragment gameFragment;

    private BadgeItem numberBadgeItem;
    private SimpleLoadDialog dialogHandler;
    public static MainActivity activity;

    public static MainActivity getInstance() {
        if (activity != null) {
            return activity;
        }
        return activity;
    }

    public PublishSubject<ActivityLifeCycleEvent> getLifeSubject() {
        return lifecycleSubject;
    }

    BottomNavigationBar bottomNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_new);

        mContext = this;
        activity = this;
        ButterKnife.bind(this);
        initToolBar();
        dialogHandler = new SimpleLoadDialog(MainActivity.this, null, true);
         bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar.setBarBackgroundColor(R.color.page_bg);
        toolBarHome.setBackgroundColor(ContextCompat.getColor(mContext,R.color.page_bg));
        numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColor(Color.RED)
                .setText("5")
                .setHideOnSelect(true);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.mipmap.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                .addItem(new BottomNavigationItem(R.mipmap.ic_videogame_asset_white_24dp, "Games").setActiveColorResource(R.color.grey).setBadgeItem(numberBadgeItem))
                .setFirstSelectedPosition(0)
                .initialise();

//        fragments = getFragments();





        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
        if(!NetUtil.isConnected(mContext)){
            bottomNavigationBar.unHide();
        }
    }

    private void initToolBar() {
        toolBarHome.setTitle("TapTap");
        toolBarHome.setSubtitle("这里是子标题");
        toolBarHome.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolBarHome);
    }

    private void initToolBarPosition_One() {
        toolBarHome.setTitle("豆瓣电影Top250");
        toolBarHome.setSubtitle("");
//        toolBar.setLogo(R.mipmap.app_icon);
        setSupportActionBar(toolBarHome);
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = HomeFragment.newInstance("Home");
        transaction.replace(R.id.layFrame, homeFragment);
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        homeFragment = (HomeFragment.newInstance("Home"));
        fragments.add(homeFragment);
        bookFragment = BookFragment.newInstance("Books");
        fragments.add(bookFragment);
        musicFragment = MusicFragment.newInstance("Music");
        fragments.add(musicFragment);
        tvFragment = TvFragment.newInstance("Movies & TV");
        fragments.add(tvFragment);
        gameFragment = GameFragment.newInstance("Games");
        fragments.add(gameFragment);
        return fragments;
    }


    @Override
    public void onTabSelected(int position) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                initToolBar();
                if (homeFragment == null) {
                    homeFragment = (HomeFragment.newInstance("Home"));
                }

                transaction.replace(R.id.layFrame, homeFragment);
                transaction.commit();
                break;
            case 1:
                initToolBarPosition_One();
                if (bookFragment == null) {
                    bookFragment = BookFragment.newInstance("Books");
                }
                transaction.replace(R.id.layFrame, bookFragment);
                transaction.commit();
                break;
            case 2:
                if (musicFragment == null) {
                    musicFragment = MusicFragment.newInstance("Music");
                }
                transaction.replace(R.id.layFrame, musicFragment);
                transaction.commit();
                break;
            case 3:
                if (tvFragment == null) {
                    tvFragment = TvFragment.newInstance("Movies & TV");
                }
                transaction.replace(R.id.layFrame, tvFragment);
                transaction.commit();
                break;
            case 4:
                if (gameFragment == null) {
                    gameFragment = GameFragment.newInstance("Games");
                }
                transaction.replace(R.id.layFrame, gameFragment);
                transaction.commit();
                break;
            default:
                break;
        }


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
