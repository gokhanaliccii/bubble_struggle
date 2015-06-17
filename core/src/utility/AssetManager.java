package utility;

import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetManager {

	private static AssetManager assetManager;

	private ConcurrentHashMap<String, Texture> textureMap;
	private ConcurrentHashMap<String, BitmapFont> fontMap;

	public AssetManager() {

		textureMap = new ConcurrentHashMap<String, Texture>();
		fontMap = new ConcurrentHashMap<String, BitmapFont>();
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

	/** fonts functions **/
	public BitmapFont getFont(String fontPath, String fontImage) {

		if (fontPath == null)
			return null;

		if (isFontExistAtCache(fontPath))
			return fontMap.get(fontPath);

		BitmapFont font = loadFont(fontPath, fontImage);
		if (font == null)
			return null;

		cacheFont(fontPath, font);

		return font;
	}

	public BitmapFont loadFont(String fontPath, String fontImagePath) {

		if (fontPath == null)
			return null;

		return new BitmapFont(Gdx.files.internal(fontPath),
				Gdx.files.internal(fontImagePath), false);
	}

	public boolean isFontExistAtCache(String textureName) {

		if (fontMap == null)
			return false;

		return fontMap.containsKey(textureName);
	}

	public boolean cacheFont(String name, BitmapFont font) {

		if (name == null || font == null)
			return false;

		fontMap.put(name, font);
		return true;
	}

	public BitmapFont getDefaulFont() {
		return getFont(ResourcePath.FONT_PATH, ResourcePath.FONT_IMAGE);
	}

	/** font functions **/

	public Sound getSound(String path) {

		return Gdx.audio.newSound(Gdx.files.internal(path));
	}
	
	public void freeToMap(){
		
		textureMap.clear();
		fontMap.clear();
	}
}
