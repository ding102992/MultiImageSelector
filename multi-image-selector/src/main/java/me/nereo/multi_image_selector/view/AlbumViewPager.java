package me.nereo.multi_image_selector.view;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import me.nereo.multi_image_selector.R;
import me.nereo.multi_image_selector.view.photoview.PhotoView;
import me.nereo.multi_image_selector.view.photoview.PhotoViewAttacher;

public class AlbumViewPager extends ViewPager {

	private PhotoViewAttacher.OnPhotoTapListener mOnPhotoTapListener;

	public AlbumViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener onPhotoTapListener){
		mOnPhotoTapListener = onPhotoTapListener;
	}

	public class ViewPagerAdapter extends PagerAdapter {
		private List<String> paths;

		public ViewPagerAdapter(List<String> paths){
			this.paths =paths;
		}

		@Override
		public int getCount() {
			return paths.size();
		}

		@Override
		public Object instantiateItem(ViewGroup viewGroup, int position) {
			//注意，这里不可以加inflate的时候直接添加到viewGroup下，而需要用addView重新添加
			//因为直接加到viewGroup下会导致返回的view为viewGroup
			View imageLayout = inflate(getContext(), R.layout.item_album_pager, null);
			viewGroup.addView(imageLayout);
			PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image);
			imageView.setOnPhotoTapListener(mOnPhotoTapListener);
			String path= paths.get(position);
			Picasso.with(getContext())
					.load(new File(path))
					.placeholder(R.drawable.default_error)
					.resize(viewGroup.getMeasuredWidth(), viewGroup.getMeasuredHeight())
					.centerCrop()
					.into(imageView);
			return imageLayout;
		}

		@Override
		public int getItemPosition(Object object) {
			//在notifyDataSetChanged时返回None，重新绘制
			return POSITION_NONE;
		}

		@Override
		public void destroyItem(ViewGroup container, int arg1, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}
}