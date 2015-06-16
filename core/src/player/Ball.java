package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import enums.BallType;
import enums.Way;

public class Ball implements IPosition {

	BallType type;
	Texture texture;
	Vector2 pos;
	Vector2 direction;
	Way goingWay = Way.SOUTH_EAST;
	int width, height;
	int maxHeight;

	public Ball(Texture tex, Vector2 pos, Vector2 dir) {

		this(tex, pos, dir, BallType.BIG);
	}

	public Ball(Ball ball) {

		this.type = ball.type;
		this.texture = ball.texture;
		this.pos = new Vector2(ball.pos);
		this.direction = new Vector2(ball.direction);
		this.goingWay = ball.goingWay;
		this.width = ball.width;
		this.height = ball.height;
		this.maxHeight = ball.maxHeight;
	}

	public Ball(Texture tex, Vector2 pos, Vector2 dir, BallType type) {

		this.texture = tex;
		this.pos = pos;
		this.direction = dir;
		this.type = type;
		this.maxHeight = (int) pos.y;

		init();
	}

	private void init() {

		switch (type) {

		case BIG:

			this.width = texture.getWidth();
			this.height = texture.getHeight();
			break;

		case SMALL:

			this.width = texture.getWidth() / 2;
			this.height = texture.getHeight() / 2;
			break;
		default:
			break;
		}
	}

	public BallType getType() {
		return type;
	}

	public void setType(BallType type) {
		this.type = type;
		init();
	}

	public void setGoingWay(Way goingWay) {
		this.goingWay = goingWay;
	}

	public void setInitialPosition(Vector2 position) {

		pos = new Vector2(position);
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

	public Way getGoingWay() {
		return goingWay;
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

		x *= 50;
		y *= 50;

		setPosition(x, y);
	}

	public int getMaxHeight() {
		
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {

		this.maxHeight = maxHeight;
	}

	/** Implemented methods **/

	@Override
	public int getWidth() {

		return width;
	}

	@Override
	public int getHeight() {

		return height;
	}

	@Override
	public Vector2 getPosition() {

		return pos;
	}
}