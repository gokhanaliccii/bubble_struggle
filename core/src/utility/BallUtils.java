package utility;

import player.Ball;
import screen.ScreenManager;

import com.badlogic.gdx.utils.Array;

import enums.BallType;
import enums.Way;

public class BallUtils {

	public Array<Ball> spawnBalls(Ball ball) {

		if (!isSpawnableBall(ball))
			return null;

		Array<Ball> spawnedBalls =new Array<Ball>();
		
		Ball leftBall = new Ball(ball);
		Ball rightBall = new Ball(ball);

		/** split balls **/
		leftBall.setPosition(-10, 0);
		rightBall.setPosition(10, 0);

		leftBall.setGoingWay(Way.NORTH_WEST);
		rightBall.setGoingWay(Way.NORTH_EAST);

		BallType newBallType = findSmallerType(ball.getType());
		leftBall.setType(newBallType);
		rightBall.setType(newBallType);

		int maxHeight = findMaxHeight(newBallType);
		leftBall.setMaxHeight(maxHeight);
		rightBall.setMaxHeight(maxHeight);
		
		spawnedBalls.add(rightBall);
		spawnedBalls.add(leftBall);
		
		return spawnedBalls;
	}

	public boolean isSpawnableBall(Ball ball) {

		if (ball == null)
			return false;

		BallType ballType = ball.getType();

		if (ballType.equals(BallType.SMALL))
			return false;

		return true;
	}

	public BallType findSmallerType(BallType ballType) {

		switch (ballType) {

		case BIG:
			return BallType.MEDIUM;
		case MEDIUM:
			return BallType.SMALL;

		default:
			break;
		}

		return BallType.SMALL;
	}

	public int findMaxHeight(BallType ballType) {

		int maxHeight = 0;

		switch (ballType) {

		case BIG:

			maxHeight = 350;
			break;

		case MEDIUM:

			maxHeight = 270;
			break;

		case SMALL:

			maxHeight = 200;
			break;

		default:
			break;
		}

		ScreenManager screenManager = ScreenManager.getInstance();

		maxHeight = (int) (screenManager.getAspectRatioVertical() * maxHeight);

		return maxHeight;
	}
	
	public float getBallTypeCoefficent(BallType type) {

		switch (type) {

		case BIG:
			return 2.2f;
		case MEDIUM:
			return 1.7f;
		case SMALL:
			return 1.2f;

		default:
			break;
		}

		return 1f;
	}
}
