package com.kwarkbit.xcom11;

import java.util.GregorianCalendar;
import java.util.TimeZone;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity implements OnSharedPreferenceChangeListener{
    /** Called when the activity is first created. */
	
	long nowTimeMillis, endTimeMillis;
	private Handler mHandler = new Handler();
	TextView days, hours, minutes, seconds, endstr;
	SharedPreferences sp;
	String day;
	GregorianCalendar endTime;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(this);
        day = sp.getString("typePref", "4");
        
        days = (TextView) findViewById(R.id.days);
        hours = (TextView) findViewById(R.id.hours);
        minutes = (TextView) findViewById(R.id.minutes);
        seconds = (TextView) findViewById(R.id.seconds);
        endstr = (TextView) findViewById(R.id.endstr);
        
        if (day.compareTo("10") == 0) {
			endstr.setText(R.string.endStr2);
		} else if (day.compareTo("4") == 0) {
			endstr.setText(R.string.endStr3);
		} else {
			endstr.setText(R.string.endStr);
		}

        endTime = new GregorianCalendar(2011, 3, Integer.parseInt(day));
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
		if (day.compareTo("10") == 0) {
			endstr.setText(R.string.endStr2);
		} else if (day.compareTo("4") == 0) {
			endstr.setText(R.string.endStr3);
		} else {
			endstr.setText(R.string.endStr);
		}
		endTime.set(2011, 3, Integer.parseInt(day));
		endTimeMillis = endTime.getTimeInMillis();
        mHandler.post(mUpdateTimeTask);
		
	}
    
           
}