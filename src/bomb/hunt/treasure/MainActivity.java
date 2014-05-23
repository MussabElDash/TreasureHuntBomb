package bomb.hunt.treasure;

import net.simonvt.numberpicker.NumberPicker;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ToggleButton;
import bomb.hunt.treasure.utility.Cons;
import bomb.hunt.treasure.utility.Time;

public class MainActivity extends Activity {
	NumberPicker hourPicker, minPicker, secPicker;
	Button start;
	ToggleButton wake;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		wake = (ToggleButton) findViewById(R.id.toggleButton1);

		hourPicker = (NumberPicker) findViewById(R.id.hourPicker);
		hourPicker.setMaxValue(99);
		hourPicker.setMinValue(0);
		hourPicker.setFocusable(true);
		hourPicker.setFocusableInTouchMode(true);

		minPicker = (NumberPicker) findViewById(R.id.minPicker);
		minPicker.setMaxValue(59);
		minPicker.setMinValue(0);
		minPicker.setFocusable(true);
		minPicker.setFocusableInTouchMode(true);

		secPicker = (NumberPicker) findViewById(R.id.secPicker);
		secPicker.setMaxValue(59);
		secPicker.setMinValue(0);
		secPicker.setFocusable(true);
		secPicker.setFocusableInTouchMode(true);

		start = (Button) findViewById(R.id.startButton);
		start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				hourPicker.clearFocus();
				minPicker.clearFocus();
				secPicker.clearFocus();
				int secs = hourPicker.getValue() * 3600;
				secs += minPicker.getValue() * 60;
				secs += secPicker.getValue();

				Time.init(secs);

				Cons.WAKE = wake.isChecked();
				Cons.stopped = false;

				Intent newActivity = new Intent("BombActivity");
				startActivity(newActivity);

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		Cons.stopped = true;
		if (Cons.counter != null) {
			Cons.counter.cancel();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		hourPicker.clearFocus();
		minPicker.clearFocus();
		secPicker.clearFocus();
	}
}
