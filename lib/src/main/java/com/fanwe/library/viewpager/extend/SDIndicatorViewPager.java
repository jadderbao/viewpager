package com.fanwe.library.viewpager.extend;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fanwe.library.gridlayout.SDGridLayout;
import com.fanwe.library.viewpager.R;
import com.fanwe.library.viewpager.SDGridViewPager;
import com.fanwe.library.viewpager.SDViewPager;

/**
 * Created by Administrator on 2017/8/10.
 */

public class SDIndicatorViewPager extends FrameLayout
{
    public SDIndicatorViewPager(@NonNull Context context)
    {
        super(context);
        init();
    }

    public SDIndicatorViewPager(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDIndicatorViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private SDGridViewPager mViewPager;
    private SDGridLayout mViewIndicator;

    private IndicatorConfig mIndicatorConfig;

    private int mPageCount;
    private int mSelectedPosition;

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_vpg_view_indicator_viewpager, this, true);
        mViewPager = (SDGridViewPager) findViewById(R.id.lib_vpg_viewpager);
        mViewIndicator = (SDGridLayout) findViewById(R.id.lib_vpg_indicator);

        initViewPager();
        initViewPagerIndicator();
    }

    private void initViewPager()
    {
        mViewPager.setOnPageCountChangeCallback(new SDViewPager.OnPageCountChangeCallback()
        {
            @Override
            public void onPageCountChanged(int oldCount, int newCount)
            {
                mPageCount = newCount;
                mIndicatorAdapter.notifyDataSetChanged();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            @Override
            public void onPageSelected(int position)
            {
                mSelectedPosition = position;
                mIndicatorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });
    }

    private void initViewPagerIndicator()
    {
        mViewIndicator.setOrientation(SDGridLayout.HORIZONTAL);
        mViewIndicator.setAdapter(mIndicatorAdapter);
    }

    /**
     * 获得ViewPager
     *
     * @return
     */
    public SDGridViewPager getViewPager()
    {
        return mViewPager;
    }

    /**
     * 获得指示器view
     *
     * @return
     */
    public View getViewIndicator()
    {
        return mViewIndicator;
    }

    public IndicatorConfig getIndicatorConfig()
    {
        if (mIndicatorConfig == null)
        {
            mIndicatorConfig = new IndicatorConfig();
        }
        return mIndicatorConfig;
    }

    private BaseAdapter mIndicatorAdapter = new BaseAdapter()
    {
        @Override
        public int getCount()
        {
            return mPageCount;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView = new ImageView(getContext());
            if (position == mSelectedPosition)
            {
                imageView.setImageResource(getIndicatorConfig().imageResIdSelected);
            } else
            {
                imageView.setImageResource(getIndicatorConfig().imageResIdNormal);
            }

            final int width = getIndicatorConfig().width;
            final int height = getIndicatorConfig().height;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
            imageView.setLayoutParams(params);

            return imageView;
        }
    };

    private class IndicatorConfig
    {
        public int imageResIdNormal;
        public int imageResIdSelected;
        public int width;
        public int height;
        public int margin;

        public IndicatorConfig()
        {
            this.imageResIdNormal = R.drawable.lib_vpg_ic_indicator_normal;
            this.imageResIdSelected = R.drawable.lib_vpg_ic_indicator_selected;
            this.width = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_width);
            this.height = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_height);
            this.margin = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_margin);
        }
    }
}