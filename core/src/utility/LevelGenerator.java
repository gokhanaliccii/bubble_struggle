package utility;

import player.Ball;
import screen.ScreenManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import enums.Level;
import enums.Way;

public class LevelGenerator {

	public Array<Ball> getBalls(Level level) {

		switch (level) {

		case LEVEL_1:

			return loadLevel1Balls();

		case LEVEL_2:

			return loadLevel2Balls();

		default:
			break;
		}

		return null;
	}

	private Array<Ball> loadLevel1Balls() {

		Array<Ball> level1Balls = new Array<Ball>();

		Texture redTexture = AssetManager.getInstance().getTexture(
				ResourcePath.RED_BALL);

		ScreenManager screenManager = ScreenManager.getInstance();
		int height = screenManager.getScreenHeight();

		Vector2 redBallPos = new Vector2(50, height / 4);
		Ball redBall = new Ball(redTexture, redBallPos, new Vector2(100, 0));

		redBall.setGoingWay(Way.SOUTH_WEST);
		redBall.setMaxHeight(300);

		level1Balls.add(redBall);
		return level1Balls;
	}

	private Array<Ball> loadLevel2Balls() {

		Array<Ball> level2Balls = new Array<Ball>();

		Texture redTexture = AssetManager.getInstance().getTexture(
				ResourcePath.RED_BALL);

		ScreenManager screenManager = ScreenManager.getInstance();
		int height = screenManager.getScreenHeight();

		Vector2 redBallLeftPos = new Vector2(50, height / 4);
		Vector2 redBallRightPos = new Vector2(700, height / 4);

		Ball redBallLeft = new Ball(redTexture, redBallLeftPos, new Vector2(
				100, 0));
		Ball redBallRight = new Ball(redTexture, redBallRightPos, new Vector2(
				100, 0));

		redBallLeft.setGoingWay(Way.SOUTH_WEST);
		redBallLeft.setGoingWay(Way.SOUTH_EAST);

		redBallLeft.setMaxHeight(300);
		redBallRight.setMaxHeight(300);

		level2Balls.add(redBallLeft);
		level2Balls.add(redBallRight);

		return level2Balls;
	}

}
