package com.squareup.timessquare;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.timessquare.MonthCellDescriptor.RangeState;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class CalendarCellImageView extends FrameLayout {

	private ImageView imageView;
	private TextView textView;

	public CalendarCellImageView(Context context) {
		this(context, null);
	}

	public CalendarCellImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TextView getTextView() {
		return textView;
	}

	public CalendarCellImageView(Context context, AttributeSet attrs,
			int defaultStyle) {

		super(context, attrs, defaultStyle);

		imageView = new ImageView(getContext());
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setClickable(false);
		imageView.setFocusable(false);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addView(imageView, params);

		textView = new TextView(context, attrs);
		FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params2.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
		textView.setBackgroundResource(android.R.color.transparent);
		textView.setClickable(false);
		textView.setFocusable(false);
		textView.setDuplicateParentStateEnabled(true);
		this.addView(textView, params2);

		setClickable(true);
		setFocusable(true);
	}

	public void setText(CharSequence text) {
		textView.setText(text);
	}

	public void setImageURL(String imageURL) {
		if (TextUtils.isEmpty(imageURL) == false) {
			//	이미지가 있다면,
			ImageLoader.getInstance().displayImage(imageURL, imageView);
			textView.setTextColor(getResources().getColor(R.color.calendar_text_selected));
			textView.setShadowLayer((float) 1.5, 3, 3, 0x7f000000);
		} else {
			//	이미지가 없다면,
			imageView.setImageResource(android.R.color.transparent);
			//	textView.setTextColor(getResources().getColor(R.color.calendar_text_selected));
			textView.setShadowLayer(0,0,0,0);	//	clear shadow layer.
		}
	}

	private static final int[] STATE_SELECTABLE = { R.attr.state_selectable };
	private static final int[] STATE_CURRENT_MONTH = { R.attr.state_current_month };
	private static final int[] STATE_TODAY = { R.attr.state_today };
	private static final int[] STATE_HIGHLIGHTED = { R.attr.state_highlighted };
	private static final int[] STATE_RANGE_FIRST = { R.attr.state_range_first };
	private static final int[] STATE_RANGE_MIDDLE = { R.attr.state_range_middle };
	private static final int[] STATE_RANGE_LAST = { R.attr.state_range_last };

	private boolean isSelectable = false;
	private boolean isCurrentMonth = false;
	private boolean isToday = false;
	private boolean isHighlighted = false;
	private RangeState rangeState = RangeState.NONE;

	public void setSelectable(boolean isSelectable) {
		this.isSelectable = isSelectable;
		refreshDrawableState();
	}

	public void setCurrentMonth(boolean isCurrentMonth) {
		this.isCurrentMonth = isCurrentMonth;
		refreshDrawableState();
	}

	public void setToday(boolean isToday) {
		this.isToday = isToday;
		refreshDrawableState();
	}

	public void setRangeState(MonthCellDescriptor.RangeState rangeState) {
		this.rangeState = rangeState;
		refreshDrawableState();
	}

	public void setHighlighted(boolean highlighted) {
		isHighlighted = highlighted;
		refreshDrawableState();
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 5);

		if (isSelectable) {
			mergeDrawableStates(drawableState, STATE_SELECTABLE);
		}

		if (isCurrentMonth) {
			mergeDrawableStates(drawableState, STATE_CURRENT_MONTH);
		}

		if (isToday) {
			mergeDrawableStates(drawableState, STATE_TODAY);
		}

		if (isHighlighted) {
			mergeDrawableStates(drawableState, STATE_HIGHLIGHTED);
		}

		if (rangeState == MonthCellDescriptor.RangeState.FIRST) {
			mergeDrawableStates(drawableState, STATE_RANGE_FIRST);
		} else if (rangeState == MonthCellDescriptor.RangeState.MIDDLE) {
			mergeDrawableStates(drawableState, STATE_RANGE_MIDDLE);
		} else if (rangeState == RangeState.LAST) {
			mergeDrawableStates(drawableState, STATE_RANGE_LAST);
		}

		return drawableState;
	}
}
