package player;

import animation.AnimationManager;
import animation.AnimationType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Weapon implements IPosition {

	Animation currentAnimation;
	Vector2 position;
	boolean draw = false;
	int velocity = 100;

	int height;
	int width;

	public Weapon(Vector2 pos) {

		this.position = pos;

		init();
	}

	private void init() {

		currentAnimation = AnimationManager.getInstance().getAnimation(
				AnimationType.WEAPON);

		TextureRegion region = currentAnimation.getKeyFrames()[1];

		width = region.getRegionWidth();
		height = region.getRegionHeight();
	}

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}
	
	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public boolean isDraw() {
		return draw;
	}

	public void ascending() {

		Vector2 newV = new Vector2(0, velocity);
		newV.scl(Gdx.graphics.getDeltaTime());

		position.add(newV);
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/** Implemented methods **/

	public Vector2 getPosition() {

		return position;
	}

	public int getHeight() {

		return height;
	}

	@Override
	public int getWidth() {

		return width;
	}
}
