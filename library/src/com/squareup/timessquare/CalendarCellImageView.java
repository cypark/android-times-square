package com.squareup.timessquare;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.timessquare.MonthCellDescriptor.RangeState;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class CalendarCellImageView extends FrameLayout {
	
	  private ImageView imageView;
	  private CalendarCellView textView;

	  public CalendarCellImageView(Context context) {
	    this(context, null);
	  }

	  public CalendarCellImageView(Context context, AttributeSet attrs) {
	    this(context, attrs, 0);
	  }

	  public CalendarCellView getTextView(){
		  return textView;
	  }
	  
	  public CalendarCellImageView(Context context, AttributeSet attrs, int defaultStyle) {
		  
	    super(context, attrs, defaultStyle);
	    
	    imageView = new ImageView(getContext());
	    imageView.setScaleType(ScaleType.CENTER_CROP);
	    
	    textView = new CalendarCellView(context, attrs);
	    
	    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	    this.addView(textView, params);
	    this.addView(imageView, 0);
	    
	    setClickable(false);
	    setFocusable(false);
	    
	  }

	  public void setText(CharSequence text) {
	    textView.setText(text);
	  }

	  public void setImageURL(String imageURL) {
		  if (TextUtils.isEmpty(imageURL) == false ) {
			  ImageLoader.getInstance().displayImage(imageURL, imageView);
			  textView.setTextColor(getResources().getColor(R.color.calendar_text_selected));
		  }
		  else{
			  imageView.setImageResource(android.R.color.transparent);
		  }
	  }
}
