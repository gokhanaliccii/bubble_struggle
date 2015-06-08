package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import enums.Way;

public class IBall {

	Texture texture;
	Vector2 pos;
	Vector2 direction;
	Way goingWay = Way.SOUTH_EAST;

	public IBall(Texture tex, Vector2 pos, Vector2 dir) {

		this.texture = tex;
		this.pos = pos;
		this.direction = dir;
	}
	
	public int getWidth(){
		return texture.getWidth();
	}
	
	public int getHeight(){
		return texture.getHeight();
	}

	public void setPosition(int x, int y) {

		direction.x = x;
		direction.y = y;
		direction.scl(Gdx.graphics.getDeltaTime());

		pos.add(direction);
	}

	public Texture getTexture() {
		return texture;
	}

	public Vector2 getPos() {
		return pos;
	}

	public void update() {

		updatePosition();
	}

	public void render() {

		
	}

	public void updatePosition() {

		int x = 0;
		int y = 0;

		switch (goingWay) {

		case EAST:

			x++;
			break;

		case NORTH:

			y++;
			break;

		case NORTH_EAST:

			x++;
			y++;
			break;

		case NORTH_WEST:

			x--;
			y++;
			break;

		case SOUTH:

			y--;
			break;

		case SOUTH_EAST:

			y--;
			x++;
			break;

		case SOUTH_WEST:

			y--;
			x--;
			break;

		case WEST:

			x--;
			break;

		default:
			break;

		}

		x *= 5;
		y *= 5;

		setPosition(x, y);
	}
	
	public void checkBounce(){
	
	}
}