package bomb.hunt.treasure;

import net.simonvt.numberpicker.NumberPicker;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import bomb.hunt.treasure.utility.Cons;
import bomb.hunt.treasure.utility.Time;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class BombActivity extends Activity {
	NumberPicker hourViewer, minViewer, secViewer;
	Time time;
	ImageView red, green, blue;
	WakeLock wakelock;
	MediaPlayer timer, explosion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bomb);

		 AdView ad = (AdView) findViewById(R.id.ad);
		 ad.loadAd(new AdRequest());

		PowerManager pM = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakelock = pM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Whatever");
		timer = MediaPlayer.create(this, R.raw.bomb_timer);
		explosion = MediaPlayer.create(this, R.raw.explosion);
		if (Cons.WAKE)
			wakelock.acquire();
		final Context con = this;
		time = Time.getInstance();

		red = (ImageView) findViewById(R.id.wire_red);
		red.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!Cons.stopped) {
					Cons.stopped = true;
					Toast.makeText(con, "Bomb defused successfuly",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		green = (ImageView) findViewById(R.id.wire_green);
		green.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(con, "Nothing happened", Toast.LENGTH_SHORT)
						.show();
			}
		});
		blue = (ImageView) findViewById(R.id.wire_blue);
		blue.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!Cons.stopped) {
					Toast.makeText(con, "The bomb activated",
							Toast.LENGTH_SHORT).show();
					Cons.stopped = true;
					explosion.start();
				}
			}
		});

		hourViewer = (NumberPicker) findViewById(R.id.hourViewer);
		hourViewer.setMaxValue(99);
		hourViewer.setMinValue(0);
		hourViewer.setEnabled(false);
		hourViewer.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		hourViewer.setValue(time.getHours());

		minViewer = (NumberPicker) findViewById(R.id.minViewer);
		minViewer.setMaxValue(59);
		minViewer.setMinValue(0);
		minViewer.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		minViewer.setEnabled(false);
		minViewer.setValue(time.getMins());

		secViewer = (NumberPicker) findViewById(R.id.secViewer);
		secViewer.setMaxValue(59);
		secViewer.setMinValue(0);
		secViewer.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		secViewer.setEnabled(false);
		secViewer.setValue(time.getSecs());

		Cons.counter = new CountDownTimer(time.getTotMilliSeconds(), 100) {

			@Override
			public void onTick(long millisUntilFinished) {
				hourViewer.setValue(time.getHours());
				minViewer.setValue(time.getMins());
				secViewer.setValue(time.getSecs());
				if (Cons.stopped) {
					timer.stop();
					cancel();
				} else {
					time.decrementMilliSec(100);
				}
				if (time.getTotMilliSeconds() == 7000) {
					timer.start();
				}
				hourViewer.setValue(time.getHours());
				minViewer.setValue(time.getMins());
				secViewer.setValue(time.getSecs());
			}

			@Override
			public void onFinish() {
				if (!Cons.stopped) {
					Cons.stopped = true;
					time.decrementOneMilliSec();
					hourViewer.setValue(time.getHours());
					minViewer.setValue(time.getMins());
					secViewer.setValue(time.getSecs());
					explosion.start();
				}
			}
		}.start();

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (Cons.WAKE)
			wakelock.release();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Cons.WAKE)
			wakelock.acquire();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (Cons.WAKE)
			wakelock.release();
		timer.stop();
	}
}
