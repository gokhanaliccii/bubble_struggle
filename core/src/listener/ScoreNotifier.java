package listener;

import enums.BallType;

public interface ScoreNotifier {

	public void tookNewScore(BallType type);
}
