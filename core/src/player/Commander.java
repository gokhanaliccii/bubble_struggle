package player;

import listener.BulletListener;
import animation.AnimationManager;
import animation.AnimationType;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import enums.Wall;

public class Commander implements IPosition {

	Vector2 position;
	Animation currentAnimation;
	AnimationType currentType;

	int width;
	int height;

	BulletListener bulletListener;

	public Commander(Vector2 position, AnimationType type) {

		this.position = position;

		init();
	}

	private void init() {

		currentAnimation = AnimationManager.getInstance().getAnimation(
				AnimationType.FORWARD_WALKING);
		TextureRegion region = currentAnimation.getKeyFrames()[1];

		width = region.getRegionWidth();
		height = region.getRegionHeight();
	}

	public void setBulletListener(BulletListener bulletListener) {
		this.bulletListener = bulletListener;
	}

	public void moveRight() {

		position.x = position.x + 1;
	}

	public void moveLeft() {

		position.x = position.x - 1;
	}

	public Animation getCurrentAnimation() {

		return currentAnimation;
	}

	public void update(Wall hitWall) {

		ApplicationType appType = Gdx.app.getType();

		switch (appType) {

		case Android:
		case iOS:

			mobileDevicesControl(hitWall);
			break;

		default:
			otherDevicesControl(hitWall);
			break;
		}
	}

	/** Ios,Android **/
	private void mobileDevicesControl(Wall hitWall) {

		float newXPos = 0;

		Orientation nativeOrientation = Gdx.input.getNativeOrientation();
		
		switch (nativeOrientation) {
		case Landscape:
			newXPos = Gdx.input.getAccelerometerX();
			break;

		default:
			
			newXPos = Gdx.input.getAccelerometerY();
			break;
		}

		AnimationType previousType = currentType;

		if (newXPos > 0) {

			if (!Wall.RIGHT.equals(hitWall))
				moveRight();

			currentType = AnimationType.RIGHT_WALKING;
		} else if (newXPos < 0) {

			if (!Wall.LEFT.equals(hitWall))
				moveLeft();
			currentType = AnimationType.LEFT_WALKING;
		} else
			currentType = AnimationType.FORWARD_WALKING;

		if (!currentType.equals(previousType))
			currentAnimation = AnimationManager.getInstance().getAnimation(
					currentType);

		/** listen for bullet **/
		if (Gdx.input.isTouched()) {

			if (bulletListener == null)
				return;

			bulletListener.shootBullet(createBullet());
		}

	}

	/**
	 * Desktop,Web Browser ...
	 */
	private void otherDevicesControl(Wall hitWall) {

		AnimationType previousType = currentType;

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

			if (!Wall.RIGHT.equals(hitWall))
				moveRight();

			currentType = AnimationType.RIGHT_WALKING;
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

			if (!Wall.LEFT.equals(hitWall))
				moveLeft();
			currentType = AnimationType.LEFT_WALKING;
		} else
			currentType = AnimationType.FORWARD_WALKING;

		if (!currentType.equals(previousType))
			currentAnimation = AnimationManager.getInstance().getAnimation(
					currentType);

		/** listen for bullet **/
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

			if (bulletListener == null)
				return;

			bulletListener.shootBullet(createBullet());
		}

	}

	private Weapon createBullet() {

		Weapon bullet = new Weapon(new Vector2(getCenterX(), -480 + position.y));
		bullet.setDraw(true);

		return bullet;
	}

	public int getCenterX() {

		int width = getWidth();
		int posX = (int) getPosition().x;

		return posX + width / 2;
	}

	/** Implemented methods **/

	@Override
	public Vector2 getPosition() {

		return position;
	}

	@Override
	public int getWidth() {

		return width;
	}

	@Override
	public int getHeight() {

		return height;
	}

}
