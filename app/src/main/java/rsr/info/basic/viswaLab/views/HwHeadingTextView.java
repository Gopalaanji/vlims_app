package rsr.info.basic.viswaLab.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class HwHeadingTextView extends TextView {

	public HwHeadingTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setTypeface(context);
	}

	public HwHeadingTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTypeface(context);
	}

	public HwHeadingTextView(Context context) {
		super(context);
		setTypeface(context);
	}

	private void setTypeface(Context context) {
		this.setTypeface(Typefaces.get(context, "HW_Headings.ttf"));
	}

}