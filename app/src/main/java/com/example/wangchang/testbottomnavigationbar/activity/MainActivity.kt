package com.example.wangchang.testbottomnavigationbar.activity

import android.content.Context
import android.graphics.Color
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import butterknife.BindView
import com.example.wangchang.testbottomnavigationbar.R
import com.example.wangchang.testbottomnavigationbar.fragment.HomeFragment
import com.example.wangchang.testbottomnavigationbar.fragment.BookFragment
import com.example.wangchang.testbottomnavigationbar.fragment.MusicFragment
import com.example.wangchang.testbottomnavigationbar.fragment.TvFragment
import com.example.wangchang.testbottomnavigationbar.fragment.GameFragment
import com.ashokvarma.bottomnavigation.BadgeItem
import com.example.wangchang.testbottomnavigationbar.view.SimpleLoadDialog
import rx.subjects.PublishSubject
import com.example.wangchang.testbottomnavigationbar.base.ActivityLifeCycleEvent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.wangchang.testbottomnavigationbar.base.BaseActivity
import com.example.wangchang.testbottomnavigationbar.util.NetUtil
import rx.Completable
import java.util.ArrayList

class MainActivity : BaseActivity(), BottomNavigationBar.OnTabSelectedListener {
    @JvmField
    @BindView(R.id.toolBar_home)
    var toolBarHome: Toolbar? = null
    private val fragments: ArrayList<Fragment>? = null
    private var mContext: Context? = null
    private lateinit var homeFragment: HomeFragment
    private lateinit var bookFragment: BookFragment
    private lateinit var musicFragment: MusicFragment
    private lateinit var tvFragment: TvFragment
    private lateinit var gameFragment: GameFragment
    private var numberBadgeItem: BadgeItem? = null
    private var dialogHandler: SimpleLoadDialog? = null
    lateinit var lifeSubject: PublishSubject<ActivityLifeCycleEvent>
    private var bottomNavigationBar: BottomNavigationBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)
        lifeSubject = lifecycleSubject
        mContext = this
        ButterKnife.bind(this)
        initToolBar()
        dialogHandler = SimpleLoadDialog(this@MainActivity, null, true)
        bottomNavigationBar = findViewById<View>(R.id.bottom_navigation_bar) as BottomNavigationBar
        bottomNavigationBar?.setMode(BottomNavigationBar.MODE_FIXED)
        bottomNavigationBar?.setBackgroundStyle(
                BottomNavigationBar.BACKGROUND_STYLE_STATIC
            )
        bottomNavigationBar!!.setBarBackgroundColor(R.color.page_bg)
        mContext?.let {
            toolBarHome!!.setBackgroundColor(ContextCompat.getColor(it, R.color.page_bg))
        }

        mContext?.run {
            toolBarHome!!.setBackgroundColor(ContextCompat.getColor(this, R.color.page_bg))
        }

        numberBadgeItem = BadgeItem()
            .setBorderWidth(4)
            .setBackgroundColor(Color.RED)
            .setText("5")
            .setHideOnSelect(true)
        bottomNavigationBar!!.addItem(
            BottomNavigationItem(
                R.mipmap.ic_home_white_24dp,
                "Home"
            ).setActiveColorResource(R.color.orange)
        )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.ic_book_white_24dp,
                    "Books"
                ).setActiveColorResource(R.color.teal)
            )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.ic_music_note_white_24dp,
                    "Music"
                ).setActiveColorResource(R.color.blue)
            )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.ic_tv_white_24dp,
                    "Movies & TV"
                ).setActiveColorResource(R.color.brown)
            )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.ic_videogame_asset_white_24dp,
                    "Games"
                ).setActiveColorResource(R.color.grey).setBadgeItem(numberBadgeItem)
            )
            .setFirstSelectedPosition(0)
            .initialise()

//        fragments = getFragments();
        setDefaultFragment()
        bottomNavigationBar!!.setTabSelectedListener(this)
        if (!NetUtil.isConnected(mContext)) {
            bottomNavigationBar!!.unHide()
        }
    }

    private fun initToolBar() {
        toolBarHome!!.title = "TapTap"
        toolBarHome!!.subtitle = "这里是子标题"
        toolBarHome!!.setLogo(R.mipmap.ic_launcher)
        setSupportActionBar(toolBarHome)
    }

    private fun initToolBarPositionOne() {
        toolBarHome!!.title = "豆瓣电影Top250"
        toolBarHome!!.subtitle = ""
        //        toolBar.setLogo(R.mipmap.app_icon);
        setSupportActionBar(toolBarHome)
    }

    /**
     * 设置默认的
     */
    private fun setDefaultFragment() {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        homeFragment = HomeFragment.newInstance("Home")
        transaction.replace(R.id.layFrame, homeFragment)
        transaction.commit()
    }

    override fun onTabSelected(position: Int) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        when (position) {
            0 -> {
                initToolBar()
                homeFragment = HomeFragment.newInstance("Home")
                transaction.replace(R.id.layFrame, homeFragment)
                transaction.commit()
            }
            1 -> {
                initToolBarPositionOne()
                bookFragment = BookFragment.newInstance("Books")
                transaction.replace(R.id.layFrame, bookFragment)
                transaction.commit()
            }
            2 -> {
                musicFragment = MusicFragment.newInstance("Music")
                transaction.replace(R.id.layFrame, musicFragment)
                transaction.commit()
            }
            3 -> {
                tvFragment = TvFragment.newInstance("Movies & TV")
                transaction.replace(R.id.layFrame, tvFragment)
                transaction.commit()
            }
            4 -> {
                gameFragment = GameFragment.newInstance("Games")
                transaction.replace(R.id.layFrame, gameFragment)
                transaction.commit()
            }
            else -> {}
        }
    }

    override fun onTabUnselected(position: Int) {
        if (fragments != null) {
            if (position < fragments.size) {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val fragment = fragments[position]
                ft.remove(fragment)
                ft.commitAllowingStateLoss()
            }
        }
    }

    override fun onTabReselected(position: Int) {
    }

    companion object {
        var activity: MainActivity? = null
        @JvmStatic
        val instance: MainActivity?
            get() = if (activity != null) {
                activity
            } else activity
    }
}