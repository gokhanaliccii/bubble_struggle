package utility;

import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AssetManager {

	private static AssetManager assetManager;

	private ConcurrentHashMap<String, Texture> textureMap;
	private ConcurrentHashMap<String, Animation> animationMap;

	public AssetManager() {

		textureMap = new ConcurrentHashMap<String, Texture>();
		animationMap = new ConcurrentHashMap<String, Animation>();
	}

	public static AssetManager getInstance() {

		if (assetManager == null)
			assetManager = new AssetManager();

		return assetManager;
	}

	public Texture getTexture(String texturePath) {

		if (texturePath == null)
			return null;

		if (isTextureExistAtCache(texturePath))
			return textureMap.get(texturePath);

		Texture texture = loadTexture(texturePath);
		if (texture == null)
			return null;

		cacheTexture(texturePath, texture);

		return texture;
	}

	public Texture loadTexture(String texturePath) {

		if (texturePath == null)
			return null;

		return new Texture(Gdx.files.internal(texturePath));
	}

	public boolean isTextureExistAtCache(String textureName) {

		if (textureMap == null)
			return false;

		return textureMap.containsKey(textureName);
	}

	public boolean cacheTexture(String name, Texture texture) {

		if (name == null || texture == null)
			return false;

		textureMap.put(name, texture);
		return true;
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

	public Animation getAnimation(String animationPath) {

		if (animationPath == null)
			return null;

		if (isAnimationExistAtCache(animationPath))
			return animationMap.get(animationPath);

		return null;
	}
}
