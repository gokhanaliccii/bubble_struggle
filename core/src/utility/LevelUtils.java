package utility;

import player.Ball;
import screen.ScreenManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import enums.BallType;
import enums.Level;
import enums.Way;

public class LevelUtils {

	public Array<Ball> getBalls(Level level) {

		switch (level) {

		case LEVEL_1:

			return loadLevel1Balls();

		case LEVEL_2:

			return loadLevel2Balls();

		case LEVEL_3:
			return loadLevel3Balls();

		default:
			break;
		}

		return null;
	}

	private Array<Ball> loadLevel1Balls() {

		Array<Ball> level1Balls = new Array<Ball>();

		Texture pinkTexture = AssetManager.getInstance().getTexture(
				ResourcePath.PINK_BALL);

		BallUtils ballUtils = new BallUtils();

		ScreenManager screenManager = ScreenManager.getInstance();
		int height = screenManager.getScreenHeight();

		Vector2 pinkBallPos = new Vector2(50, height / 4);
		Ball pinkBall = new Ball(pinkTexture, pinkBallPos, new Vector2(100, 0));
		pinkBall.setType(BallType.MEDIUM);

		pinkBall.setGoingWay(Way.SOUTH_WEST);
		pinkBall.setMaxHeight(ballUtils.findMaxHeight(BallType.MEDIUM));

		level1Balls.add(pinkBall);
		return level1Balls;
	}

	private Array<Ball> loadLevel2Balls() {

		Array<Ball> level2Balls = new Array<Ball>();

		Texture greenTexture = AssetManager.getInstance().getTexture(
				ResourcePath.GREEN_BALL);

		ScreenManager screenManager = ScreenManager.getInstance();
		int height = screenManager.getScreenHeight();
		int centerX = screenManager.getCenterX();

		int padding = (int) (screenManager.getAspectRatioHorizontal() * 50);

		int leftPosX = centerX - padding;
		int rigtPosX = centerX + padding;

		Vector2 greenBallLeftPos = new Vector2(leftPosX, height / 4);
		Vector2 greenBallRightPos = new Vector2(rigtPosX, height / 4);

		Ball greenBallLeft = new Ball(greenTexture, greenBallLeftPos,
				new Vector2(100, 0));
		Ball greenBallRight = new Ball(greenTexture, greenBallRightPos,
				new Vector2(100, 0));

		BallType ballType = BallType.MEDIUM;

		BallUtils ballUtils = new BallUtils();
		int maxHeight = ballUtils.findMaxHeight(ballType);

		greenBallLeft.setType(ballType);
		greenBallRight.setType(ballType);

		greenBallLeft.setGoingWay(Way.SOUTH_WEST);
		greenBallRight.setGoingWay(Way.SOUTH_EAST);

		greenBallLeft.setMaxHeight(maxHeight);
		greenBallRight.setMaxHeight(maxHeight);

		level2Balls.add(greenBallLeft);
		level2Balls.add(greenBallRight);

		return level2Balls;
	}

	private Array<Ball> loadLevel3Balls() {

		Array<Ball> level3Balls = new Array<Ball>();

		Texture blueTexture = AssetManager.getInstance().getTexture(
				ResourcePath.BLUE_BALL);

		BallUtils ballUtils = new BallUtils();

		ScreenManager screenManager = ScreenManager.getInstance();
		int height = screenManager.getScreenHeight();

		Vector2 blueBallPos = new Vector2(50, height / 4);
		Ball blueBall = new Ball(blueTexture, blueBallPos, new Vector2(100, 0));
		blueBall.setType(BallType.BIG);

		blueBall.setGoingWay(Way.SOUTH_WEST);
		blueBall.setMaxHeight(ballUtils.findMaxHeight(BallType.BIG));

		level3Balls.add(blueBall);
		return level3Balls;

	}

	public Level getNextLevel(Level level) {

		switch (level) {

		case LEVEL_1:
			return Level.LEVEL_2;

		case LEVEL_2:
			return Level.LEVEL_3;

		default:
			break;
		}

		return Level.END_OF_GAME;
	}

	public float getLevelCoefficent(Level level) {

		switch (level) {

		case LEVEL_1:
			return 1;
		case LEVEL_2:
			return 1.5f;
		case LEVEL_3:
			return 2;

		default:
			break;
		}

		return 1;
	}

	public boolean gameIsFinished(Level level) {

		if (level.equals(Level.END_OF_GAME))
			return true;

		return false;
	}

}
