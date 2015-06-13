package screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IScreen {

	public void create();
	public void update();
	public void render(SpriteBatch spriteBatch);
	public void resize(int width,int height);
	public void dispose();
	public void pause();
	public void resume();
	
	public int getWidth();
	public int getHeight();
}
