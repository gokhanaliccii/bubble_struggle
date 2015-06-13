package screen;

import player.IBall;
import player.Weapon;
import utility.AssetManager;
import utility.ImagePath;
import animation.AnimationManager;
import animation.AnimationType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import controller.GameController;

public class GameScreen implements IScreen {

	OrthographicCamera camera;
	Animation anim;
	float elapsedTime = 0f;

	IBall redBall, greenBall;
	Vector2 animPos = new Vector2(40, 40);
	AnimationManager animationManager;
	GameController gameController;
	Animation weaponAnim;

	@Override
	public void create() {

		Texture redTexture = AssetManager.getInstance().getTexture(
				ImagePath.RED_BALL);
		Texture greenTexture = AssetManager.getInstance().getTexture(
				ImagePath.GREEN_BALL);

		animationManager = AnimationManager.getInstance();

		camera = new OrthographicCamera();

		redBall = new IBall(redTexture, new Vector2(300, 200), new Vector2(100,
				0));
		greenBall = new IBall(greenTexture, new Vector2(400, 200), new Vector2(
				500, 0));

		weaponAnim = animationManager.getAnimation(AnimationType.WEAPON);
		weaponAnim.setFrameDuration(1 / 20f);

		gameController = new GameController(getWidth(), getHeight());
		gameController.newBall(greenBall);
		gameController.newBall(redBall);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {

		camera.setToOrtho(false);

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			anim = animationManager.getAnimation(AnimationType.RIGHT_WALKING);
			animPos.x += 1;
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			anim = animationManager.getAnimation(AnimationType.LEFT_WALKING);
			animPos.x -= 1;
		} else
			anim = animationManager.getAnimation(AnimationType.FORWARD_WALKING);

		Weapon activeWeapon = gameController.getActiveWeapon();
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && activeWeapon == null) {

			activeWeapon = new Weapon(new Vector2(animPos.x, -480));

			activeWeapon.setDraw(true);
			TextureRegion[] regions = weaponAnim.getKeyFrames();
			activeWeapon.setHeight(regions[0].getRegionHeight());
			activeWeapon.setWidth(regions[0].getRegionWidth());

			gameController.setActiveWeapon(activeWeapon);
		}

		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();
		elapsedTime += Gdx.graphics.getDeltaTime();

		if (activeWeapon != null) {

			Vector2 weaponPos = activeWeapon.getPosition();

			spriteBatch.draw(weaponAnim.getKeyFrame(elapsedTime, true),
					weaponPos.x, weaponPos.y);

		}

		spriteBatch.draw(anim.getKeyFrame(elapsedTime, true), animPos.x,
				animPos.y);

		/** drawing balls **/
		Array<IBall> balls = gameController.getActiveBalls();
		if (balls != null) {

			for (IBall iBall : balls) {

				if (iBall == null)
					continue;

				Vector2 ballPos = iBall.getPos();

				spriteBatch.draw(iBall.getTexture(), ballPos.x, ballPos.y,iBall.getWidth(),iBall.getHeight());
			}
		}

		spriteBatch.end();
	}

	@Override
	public void update() {

		gameController.update();
		camera.update();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public int getWidth() {

		return 800;
	}

	@Override
	public int getHeight() {

		return 480;
	}

}
