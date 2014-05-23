package bomb.hunt.treasure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					Intent newActivity = new Intent("MainActivity");
					startActivity(newActivity);
				}
			}
		}).start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
