package listener;

import player.Commander;
import enums.BallType;

public interface GameNotifier {

	public void onGetNewScore(BallType type);
	public void onLevelComplete();
	public void onCommanderKilled(Commander commander);
	public void onBallBoom();
}
