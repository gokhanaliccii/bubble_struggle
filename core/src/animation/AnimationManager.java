package animation;

import java.util.concurrent.ConcurrentHashMap;

import utility.ResourcePath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationManager {

	private static AnimationManager animationManager;

	private ConcurrentHashMap<String, Animation> animationMap;

	private AnimationManager() {

		animationMap = new ConcurrentHashMap<String, Animation>();
	}

	public static AnimationManager getInstance() {

		if (animationManager == null)
			animationManager = new AnimationManager();

		return animationManager;
	}

	public boolean cacheAnimation(String name, Animation animation) {

		if (name == null || animation == null)
			return false;

		animationMap.put(name, animation);

		return true;
	}

	public boolean isAnimationExistAtCache(String textureName) {

		if (animationMap == null)
			return false;

		return animationMap.containsKey(textureName);
	}

	public Animation getAnimation(AnimationType type) {

		String animationPath = null;

		switch (type) {

		case FORWARD_WALKING:

			animationPath = ResourcePath.SPRITE_WALKING_FORWARD;
			break;

		case LEFT_WALKING:

			animationPath = ResourcePath.SPRITE_WALKING_LEFT;
			break;

		case RIGHT_WALKING:

			animationPath = ResourcePath.SPRITE_WALKING_RIGHT;
			break;

		case WEAPON:

			animationPath = ResourcePath.WEAPON;
			break;
		default:
			return null;

		}

		return getAtlasAnimation(animationPath, true);
	}

	public Animation getAtlasAnimation(String animationPath, boolean cache) {

		Animation animation = getAtlasAnimation(animationPath);

		if (!cache)
			return animation;

		if (animation == null)
			return null;

		if (isAnimationExistAtCache(animationPath))
			return animation;

		cacheAnimation(animationPath, animation);
		return animation;
	}

	public Animation getAtlasAnimation(String animationPath) {

		if (animationPath == null)
			return null;

		if (isAnimationExistAtCache(animationPath))
			return animationMap.get(animationPath);

		Animation animation = loadAtlasAnimation(animationPath);
		return animation;
	}

	private Animation loadAtlasAnimation(String path) {

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path));

		Array<AtlasRegion> regions = atlas.getRegions();
		if (regions == null)
			return null;
		float duration = 1 / (float) regions.size;
		Animation animation = new Animation(duration, regions);

		return animation;
	}

	public void freeToMap() {

		animationMap.clear();
	}
}
