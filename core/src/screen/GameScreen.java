package screen;

import player.IBall;
import utility.AssetManager;
import utility.ImagePath;
import animation.AnimationManager;
import animation.AnimationType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements IScreen {

	OrthographicCamera camera;
	Animation anim;
	float elapsedTime = 0f;

	IBall iball;

	@Override
	public void create() {

		Texture texture = AssetManager.getInstance().getTexture(
				ImagePath.RED_BALL);

		camera = new OrthographicCamera();
		AnimationManager animationManager = new AnimationManager();
		anim = animationManager.getAnimation(AnimationType.LEFT_WALKING);
		iball = new IBall(texture, new Vector2(30, 30), new Vector2(0, 0));

	}

	@Override
	public void render(SpriteBatch spriteBatch) {

		camera.setToOrtho(false);
		Vector2 pos = iball.getPos();

		elapsedTime += Gdx.graphics.getDeltaTime();

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();

		spriteBatch.draw(iball.getTexture(), pos.x, pos.y);
		spriteBatch.draw(anim.getKeyFrame(elapsedTime), 0, 0);

		spriteBatch.end();

	}

	@Override
	public void update() {
		
		iball.update();
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

}
