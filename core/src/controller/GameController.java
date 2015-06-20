package controller;

import listener.GameNotifier;
import player.Commander;
import player.Ball;
import player.IPosition;
import player.Weapon;
import utility.BallUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import enums.BallType;
import enums.Wall;
import enums.Way;

public class GameController {

	private int screenWidth;
	private int screenHeight;

	private Array<Ball> activeBalls;
	private Weapon activeWeapon;
	private Commander commander;

	private GameNotifier notifier;

	public GameController() {
	}
	
	public GameController(int width, int height) {

		this.screenWidth = width;
		this.screenHeight = height;

		activeBalls = new Array<Ball>();
	}

	public void setCommander(Commander commander) {
		this.commander = commander;
	}

	public Commander getCommander() {
		return commander;
	}

	public void addNewBall(Ball ball) {

		this.activeBalls.add(ball);
	}

	public void addNewBall(Array<Ball> balls) {

		if (balls == null)
			return;

		for (Ball ball : balls) {

			if (ball == null)
				continue;

			addNewBall(ball);
		}
	}

	public void setActiveWeapon(Weapon activeWeapon) {

		this.activeWeapon = activeWeapon;
	}

	public void setNotifier(GameNotifier notifier) {
		this.notifier = notifier;
	}

	public void update() {

		/** update balls position **/
		for (Ball iBall : activeBalls) {

			iBall.updatePosition();
			updateGoingWay(iBall);
		}

		/** update weapon position **/
		if (activeWeapon != null && activeWeapon.isDraw()) {

			activeWeapon.ascending();

			Vector2 pos = activeWeapon.getPosition();

			if (pos.y + activeWeapon.getHeight() > getScreenHeight()) {

				activeWeapon.setDraw(false);
				activeWeapon = null;
			}
		}

		/** update player position **/
		if (commander != null)
			commander.update(findHitWall(commander));

		/** check ball and weapon collision **/
		for (Ball iBall : activeBalls) {

			if (checkCollision(iBall, activeWeapon)) {

				Gdx.app.log("Collision", "ball and bullet");
				/** notify ball and new score **/

				notifyBallBoom();
				notifyNewScore(iBall.getType());

				BallUtils ballUtil = new BallUtils();

				if (ballUtil.isSpawnableBall(iBall)) {

					Array<Ball> spawnedBalls = ballUtil.spawnBalls(iBall);

					if (spawnedBalls != null)
						addNewBall(spawnedBalls);

				} else {

					if (activeBalls != null && activeBalls.size == 0)
						notifyLevelIsCompleted();
				}

				activeWeapon = null;
				activeBalls.removeValue(iBall, true);

				if (activeBalls.size == 0)
					notifyLevelIsCompleted();

				break;
			}
		}

		/** check ball and player collision **/
		for (Ball iBall : activeBalls) {

			if (checkCollision(iBall, commander)) {

				Gdx.app.log("Collision", "ball and player");

				setCommander(null);

				/** notify player player killed **/
				notifyPlayerKilled();

			}
		}

	}

	public void updateGoingWay(Ball iball) {

		Way goingWay = iball.getGoingWay();

		Wall hitWall = findHitWall(iball);

		boolean isExceedMaxHeight = isExceedMaxHight(iball);
		if (hitWall == null) {

			if (isExceedMaxHeight)
				hitWall = Wall.TOP;
			else
				return;
		}

		switch (hitWall) {

		case TOP:

			goingWay = findAfterHitTopWall(goingWay);
			break;

		case LEFT:

			goingWay = findAfterHitLeftWall(goingWay);
			break;

		case RIGHT:

			goingWay = findAfterHitRightWall(goingWay);
			break;

		case BOTTOM:

			goingWay = findAfterHitBottomWall(goingWay);
			break;

		default:
			break;

		}

		iball.setGoingWay(goingWay);

	}

	public int getScreenWidth() {

		return screenWidth;
	}

	public int getScreenHeight() {

		return screenHeight;
	}

	public Array<Ball> getActiveBalls() {

		return activeBalls;
	}

	public Weapon getActiveWeapon() {

		return activeWeapon;
	}

	/** functions **/

	private Way findAfterHitRightWall(Way goingWay) {

		Way newWay = goingWay;
		switch (goingWay) {

		case NORTH_EAST:

			newWay = Way.NORTH_WEST;
			break;

		case SOUTH_EAST:

			newWay = Way.SOUTH_WEST;
			break;

		default:
			break;
		}

		return newWay;
	}

	private Way findAfterHitLeftWall(Way goingWay) {

		Way newWay = goingWay;
		switch (goingWay) {

		case NORTH_WEST:

			newWay = Way.NORTH_EAST;
			break;

		case SOUTH_WEST:

			newWay = Way.SOUTH_EAST;
			break;

		default:
			break;
		}

		return newWay;
	}

	private Way findAfterHitTopWall(Way goingWay) {

		Way newWay = goingWay;
		switch (goingWay) {

		case NORTH_WEST:

			newWay = Way.SOUTH_WEST;
			break;

		case NORTH_EAST:

			newWay = Way.SOUTH_EAST;
			break;

		default:
			break;
		}

		return newWay;
	}

	private Way findAfterHitBottomWall(Way goingWay) {

		Way newWay = goingWay;
		switch (goingWay) {

		case SOUTH_WEST:

			newWay = Way.NORTH_WEST;
			break;

		case SOUTH_EAST:

			newWay = Way.NORTH_EAST;
			break;

		default:
			break;
		}

		return newWay;
	}

	private boolean isExceedMaxHight(Ball ball) {

		Vector2 position = ball.getPos();
		int currentY = (int) position.y + ball.getHeight();

		if (currentY > ball.getMaxHeight())
			return true;

		return false;
	}

	private Wall findHitWall(IPosition iposition) {

		int maxX = getScreenWidth();
		int minX = 0;

		int minY = 0;
		int maxY = getScreenHeight();

		Vector2 position = iposition.getPosition();

		/** Check Left Side **/
		if (position.x < minX)
			return Wall.LEFT;

		/** Check Right Side **/
		if (position.x + iposition.getWidth() > maxX)
			return Wall.RIGHT;

		/** Check Up Side **/
		if (position.y + iposition.getHeight() > maxY)
			return Wall.TOP;

		/** Check Down Side **/
		if (position.y < minY)
			return Wall.BOTTOM;

		return null;

	}

	private boolean checkCollision(IPosition iposition1, IPosition iposition2) {

		if (iposition1 == null)
			return false;

		if (iposition2 == null)
			return false;

		Vector2 pos1 = iposition1.getPosition();
		Vector2 pos2 = iposition2.getPosition();

		return pos1.x < pos2.x + iposition2.getWidth()
				&& pos1.x + iposition1.getWidth() > pos2.x
				&& pos1.y < pos2.y + iposition2.getHeight()
				&& pos1.y + iposition1.getHeight() > pos2.y;
	}

	/** notify operation **/

	private void notifyBallBoom() {

		if (notifier == null)
			return;

		notifier.onBallBoom();
	}

	private void notifyNewScore(BallType type) {

		if (notifier == null)
			return;

		notifier.onGetNewScore(type);
	}

	private void notifyPlayerKilled() {

		if (notifier == null)
			return;

		notifier.onCommanderKilled(commander);
	}

	private void notifyLevelIsCompleted() {

		if (notifier == null)
			return;

		notifier.onLevelComplete();
	}

	public void cleanPlayer() {

		commander = null;
		activeWeapon = null;
		activeBalls.clear();
	}

	public void resize(int screenWidth, int screenHeight) {

		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
}
