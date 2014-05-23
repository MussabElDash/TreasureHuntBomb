package bomb.hunt.treasure.utility;

public class Time {
	private static Time TIME;
	int milliSecs;

	private Time(int totSecs) {
		if (totSecs < 10)
			milliSecs = 10000;
		else
			milliSecs = totSecs * 1000;
	}

	public int getHours() {
		return milliSecs / 3600000;
	}

	public int getMins() {
		int sec = milliSecs;
		sec -= getHours() * 3600000;
		return sec / 60000;
	}

	public int getSecs() {
		int sec = milliSecs;
		sec -= getHours() * 3600000;
		sec -= getMins() * 60000;
		return sec / 1000;
	}

	public void decrementOneMilliSec() {
		milliSecs--;
	}

	public void decrementMilliSec(int milliSec) {
		milliSecs -= milliSec;
	}

	public int getTotMilliSeconds() {
		return milliSecs;
	}

	public static Time getInstance() {
		return TIME;
	}

	public static void init(int secs) {
		if (TIME == null)
			TIME = new Time(secs);
		else if (secs < 10)
			TIME.milliSecs = 10000;
		else
			TIME.milliSecs = secs * 1000;

	}
}
