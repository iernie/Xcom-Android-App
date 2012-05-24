package com.kwarkbit.xcom11;

import java.util.GregorianCalendar;
import java.util.TimeZone;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

//public class MainActivity extends Activity implements OnSharedPreferenceChangeListener{
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	long nowTimeMillis, endTimeMillis;
	private Handler mHandler = new Handler();
	TextView days, hours, minutes, seconds, endstr, colon;
	SharedPreferences sp;
	String day;
	GregorianCalendar endTime;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Typeface ptsansFont = Typeface.createFromAsset(getAssets(),"fonts/ptsans.ttf");
        Typeface lcdFont = Typeface.createFromAsset(getAssets(),"fonts/lcd.ttf");
        
        //sp = PreferenceManager.getDefaultSharedPreferences(this);
        //sp.registerOnSharedPreferenceChangeListener(this);
        //day = sp.getString("typePref", "4");
        
        days = (TextView) findViewById(R.id.daysStr);
        days.setTypeface(lcdFont);
        
        days = (TextView) findViewById(R.id.days);
        days.setTypeface(lcdFont);
        hours = (TextView) findViewById(R.id.hours);
        hours.setTypeface(lcdFont);
        minutes = (TextView) findViewById(R.id.minutes);
        minutes.setTypeface(lcdFont);
        seconds = (TextView) findViewById(R.id.seconds);
        seconds.setTypeface(lcdFont);
        endstr = (TextView) findViewById(R.id.endstr);
        endstr.setTypeface(ptsansFont);
        
        colon = (TextView) findViewById(R.id.colon);
        colon.setTypeface(lcdFont);
        colon = (TextView) findViewById(R.id.colon2);
        colon.setTypeface(lcdFont);
        
		endstr.setText(R.string.endStr);
		endstr.setTypeface(ptsansFont);

        endTime = new GregorianCalendar(2012, 2, 23, 07, 20);
        endTime.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        
        endTimeMillis = endTime.getTimeInMillis();

        mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.post(mUpdateTimeTask);
        
    }
    
    private Runnable mUpdateTimeTask = new Runnable() {
    	public void run() {
			nowTimeMillis = System.currentTimeMillis();
			long diffTimeMillis = Math.abs(endTimeMillis - nowTimeMillis);
			
			long diffToDays = diffTimeMillis / (1000*60*60*24);
			days.setText(Long.toString(diffToDays));	
			
			long diffToHours = (diffTimeMillis % 86400000) / (1000*60*60);
			String hoursStr = Long.toString(diffToHours);			
			if (diffToHours < 10) {
				hoursStr = "0" + hoursStr;
			}
			hours.setText(hoursStr);
			
			long diffToMinutes = (diffTimeMillis % 3600000) / (1000*60);
			String minutesStr = Long.toString(diffToMinutes);
			if (diffToMinutes < 10) {
				minutesStr = "0" + minutesStr;
			}
			minutes.setText(minutesStr);  
			
			long diffToSeconds = (diffTimeMillis % 60000) / (1000);
			String secondsStr = Long.toString(diffToSeconds);
			if (diffToSeconds < 10) {
				secondsStr = "0" + secondsStr;
			}
			seconds.setText(secondsStr);
			
			mHandler.postDelayed(this, 1000);
    	}
	};
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.preferences:
	        Intent intent = new Intent(this, PrefActivity.class);
	        startActivity(intent);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		day = sp.getString("typePref", "4");
		endstr.setText(R.string.endStr);
		endTime.set(2011, 3, Integer.parseInt(day));
		endTimeMillis = endTime.getTimeInMillis();
        mHandler.post(mUpdateTimeTask);
		
	}
    */
           
}