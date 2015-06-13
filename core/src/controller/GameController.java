package controller;

import player.IBall;
import player.IPosition;
import player.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import enums.BallType;
import enums.Wall;
import enums.Way;

public class GameController {

	private int screenWidth;
	private int screenHeight;

	private Array<IBall> activeBalls;
	private Weapon activeWeapon;

	public GameController(int width, int height) {

		this.screenWidth = width;
		this.screenHeight = height;

		activeBalls = new Array<IBall>();
	}

	public void newBall(IBall ball) {

		this.activeBalls.add(ball);
	}

	public void setActiveWeapon(Weapon activeWeapon) {

		this.activeWeapon = activeWeapon;
	}

	public void update() {

		/** update balls position **/
		for (IBall iBall : activeBalls) {

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

		/** check ball and weapon collision **/
		for (IBall iBall : activeBalls) {

			if (checkCollision(iBall, activeWeapon)) {

				if (iBall.getType().equals(BallType.BIG)) {

					iBall.setType(BallType.SMALL);
					activeWeapon = null;
					break;
				} else {

					Gdx.app.log("Collision", "col");
					activeBalls.removeValue(iBall, true);
					activeWeapon = null;
					break;
				}
			}
		}

		/** check ball and player collision **/

	}

	public void updateGoingWay(IBall iball) {

		Way goingWay = iball.getGoingWay();

		Wall hitWall = findHitWall(iball);
		if (hitWall == null)
			return;

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

	public Array<IBall> getActiveBalls() {

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
}
