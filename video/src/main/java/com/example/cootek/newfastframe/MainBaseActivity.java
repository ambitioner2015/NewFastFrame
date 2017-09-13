package com.example.cootek.newfastframe;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.example.commonlibrary.BaseActivity;
import com.example.commonlibrary.mvp.presenter.BasePresenter;
import com.example.cootek.newfastframe.ui.BottomFragment;
import com.example.cootek.newfastframe.view.slide.SlidingPanelLayout;

/**
 * Created by COOTEK on 2017/8/11.
 */

public abstract class MainBaseActivity<T, P extends BasePresenter> extends BaseActivity<T, P> {


    protected SlidingPanelLayout slidingPanelLayout;
    private BottomFragment baseFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MusicManager.getInstance().bindService(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initBaseView() {

        slidingPanelLayout = (SlidingPanelLayout) LayoutInflater.from(this).inflate(R.layout.view_music_content, null);
        slidingPanelLayout.addView(contentView, 0);
        setContentView(slidingPanelLayout);
        super.initBaseView();
        showBottomFragment(true);
    }


    private View contentView;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        if (slidingPanelLayout != null) {
            super.setContentView(layoutResID);
        }
    }


    @Override
    public void setContentView(View view) {
        if (contentView == null) {
            contentView = view;
        }
        if (slidingPanelLayout != null) {
            super.setContentView(slidingPanelLayout);
        }
    }


    /**
     * @param show 显示或关闭底部播放控制栏
     */
    protected void showBottomFragment(boolean show) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (show) {
            if (baseFragment == null) {
                baseFragment = BottomFragment.newInstance();
                ft.add(R.id.fl_view_music_content_bottom, baseFragment).show(baseFragment).commitAllowingStateLoss();
            } else {
                ft.show(baseFragment).commitAllowingStateLoss();
            }
        } else {
            if (baseFragment != null)
                ft.hide(baseFragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicManager.getInstance().unBindService(this);
    }

    @Override
    public void onBackPressed() {
        if (slidingPanelLayout.getPanelState() == SlidingPanelLayout.PanelState.EXPANDED) {
            slidingPanelLayout.setPanelState(SlidingPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}
