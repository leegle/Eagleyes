package com.egova.baselibrary.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.egova.baselibrary.R;
import com.egova.baselibrary.databinding.ActivityPhotoPreviewBinding;
import com.egova.baselibrary.util.AnimationUtil;
import com.egova.baselibrary.util.StatusBarUtil;
import com.github.chrisbanes.photoview.PhotoView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 图片预览页面
 */
public class PhotoPreviewActivity extends SwipeBackActivity implements ViewPager.OnPageChangeListener {

    private ActivityPhotoPreviewBinding binding;

    private List<String> data;
    private int current = 0;

    protected boolean isUp;

    private Boolean isShowDeleteButton;
    private Boolean isNetworkPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.immersive(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_preview);
        StatusBarUtil.setMargin(this, binding.topBar);

        data = getIntent().getStringArrayListExtra("images");
        current = getIntent().getIntExtra("position", 0);
        isShowDeleteButton = getIntent().getBooleanExtra("isShowDeleteButton", true);
        isNetworkPath = getIntent().getBooleanExtra("isNetworkPath", false);
        initViewPager();
        updatePercent();
    }

    private void initViewPager() {
        binding.viewPager.setAdapter(mPagerAdapter);
        binding.viewPager.setCurrentItem(current);
        binding.viewPager.addOnPageChangeListener(this);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPreviewActivity.this.finish();
            }
        });
        binding.deleteBtn.setVisibility(isShowDeleteButton ? View.VISIBLE : View.GONE);
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.remove(current);
                EventBus.getDefault().post(String.valueOf(current));
                if (data.size() == 0) {
                    PhotoPreviewActivity.this.finish();
                }
                if (current >= data.size()) {
                    current = data.size() - 1;
                }
                updatePercent();
                binding.viewPager.setAdapter(mPagerAdapter);
                binding.viewPager.setCurrentItem(current);
            }
        });
    }

    private PagerAdapter mPagerAdapter = new PagerAdapter() {


        @Override
        public int getCount() {
            if (data == null) {
                return 0;
            } else {
                return data.size();
            }
        }

        @Override
        public View instantiateItem(final ViewGroup container, final int position) {
            View view = LayoutInflater.from(PhotoPreviewActivity.this).inflate(R.layout.item_image, null);
            PhotoView imageView = view.findViewById(R.id.imageView);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            container.addView(view);
            if (isNetworkPath) {
                Glide.with(PhotoPreviewActivity.this).load(data.get(position)).into(imageView);
            } else {
                Glide.with(PhotoPreviewActivity.this).load(new File(data.get(position))).into(imageView);
            }
            imageView.setOnClickListener(onClickListener);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isUp) {
                new AnimationUtil(getApplicationContext(), R.anim.translate_up)
                        .setInterpolator(new LinearInterpolator()).setFillAfter(true).startAnimation(binding.topBar);
//                binding.topBar.setVisibility(View.GONE);
                isUp = true;
            } else {
                new AnimationUtil(getApplicationContext(), R.anim.translate_down_current)
                        .setInterpolator(new LinearInterpolator()).setFillAfter(true).startAnimation(binding.topBar);
//                binding.topBar.setVisibility(View.VISIBLE);
                isUp = false;
            }
        }
    };

    protected void updatePercent() {
        binding.count.setText((current + 1) + "/" + data.size());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        current = position;
        updatePercent();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
