package animation;

import utility.AssetManager;
import utility.ImagePath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationManager {

	public Animation getAnimation(AnimationType type) {

		AssetManager assetManager = AssetManager.getInstance();

		Animation rightAnimation = assetManager.getAnimation(type.name());

		if (rightAnimation != null)
			return rightAnimation;

		switch (type) {

		case FORWARD_WALKING:
			break;

		case LEFT_WALKING:

			return loadLeftWalkingAnimation();

		case RIGHT_WALKING:

			return loadRightWalkingAnimation();

		default:
			break;

		}

		return null;
	}

	private Animation loadLeftWalkingAnimation() {

		AssetManager assetManager = AssetManager.getInstance();
		Texture texture = assetManager
				.getTexture(ImagePath.SPRITE_WALKING_LEFT);

		Animation leftAnimation = createAnimation(texture, 35, 55, 5, 4);

		assetManager.cacheAnimation(AnimationType.LEFT_WALKING.name(),
				leftAnimation);

		return leftAnimation;
	}

	private Animation loadRightWalkingAnimation() {

		AssetManager assetManager = AssetManager.getInstance();
		Texture texture = assetManager
				.getTexture(ImagePath.SPRITE_WALKING_RIGHT);

		Animation rightAnimation = createAnimation(texture, 35, 55, 5, 4);

		assetManager.cacheAnimation(AnimationType.RIGHT_WALKING.name(),
				rightAnimation);

		return rightAnimation;
	}

	private Animation createAnimation(Texture texture, int width, int height,
			int row, int col) {

		TextureRegion[][] txtRegion = TextureRegion.split(texture, width,
				height);

		TextureRegion[] textureRegions = new TextureRegion[row * col];

		int index = 0;

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				textureRegions[index++] = txtRegion[i][j];
			}
		}

		Animation animation = new Animation(1f / (row * col), textureRegions);
		return animation;
	}

}
