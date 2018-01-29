package dev.info.basic.viswaLab.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class LeagueGothicTextView extends TextView {

	public LeagueGothicTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setTypeface(context);
	}

	public LeagueGothicTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTypeface(context);
	}

	public LeagueGothicTextView(Context context) {
		super(context);
		setTypeface(context);
	}

	private void setTypeface(Context context) {
		this.setTypeface(Typefaces.get(context, "league_gothic.ttf"));
	}
}