package screen;

import listener.BulletListener;
import listener.GameNotifier;
import model.Score;
import player.Ball;
import player.Commander;
import player.Weapon;
import utility.AssetManager;
import utility.LevelGenerator;
import utility.ResourcePath;
import animation.AnimationManager;
import animation.AnimationType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import controller.GameController;
import enums.BallType;
import enums.GameState;
import enums.Level;

public class GameScreen implements IScreen, BulletListener, GameNotifier {

	private Viewport viewPort;
	private OrthographicCamera camera;
	private ScreenManager screenManager;

	float elapsedTime = 0f;

	AnimationManager animationManager;
	AssetManager assetManager;

	GameController gameController;

	ProgressBar timeBar;

	Score newScore;
	int retryCount = 3;

	Texture retryImage;
	BitmapFont font;
	GameState gameState = GameState.WAITING_TO_START;

	Level currentLevel = Level.LEVEL_1;
	LevelGenerator levelGenerator;

	/** Sounds **/
	Sound bulletSound, boomBallSound, playerHitSount, startSound, timeoutSound;

	@Override
	public void create() {

		camera = new OrthographicCamera();
		screenManager = ScreenManager.getInstance();
		viewPort = new FitViewport(screenManager.getScreenWidth(),
				screenManager.getScreenHeight(), camera);

		animationManager = AnimationManager.getInstance();
		assetManager = AssetManager.getInstance();

		font = assetManager.getDefaulFont();
		retryImage = assetManager.getTexture(ResourcePath.RETRY_COUNT_IMAGE);

		/** load sounds to memory **/

		bulletSound = Gdx.audio.newSound(Gdx.files
				.internal(ResourcePath.BULLET_SOUND));

		playerHitSount = Gdx.audio.newSound(Gdx.files
				.internal(ResourcePath.COMMANDER_KILL_SOUND));

		boomBallSound = Gdx.audio.newSound(Gdx.files
				.internal(ResourcePath.BALL_BOOM_SOUND));

		startSound = Gdx.audio.newSound(Gdx.files
				.internal(ResourcePath.START_SOUND));

		timeoutSound = Gdx.audio.newSound(Gdx.files
				.internal(ResourcePath.TIMEOUT_SOUND));

		newScore = new Score();
		levelGenerator = new LevelGenerator();

		gameController = new GameController(getWidth(), getHeight());
		gameController.setNotifier(this);

		reloadPlayers();
	}

	private void reloadPlayers() {

		Commander commander = new Commander(new Vector2(400, 0),
				AnimationType.FORWARD_WALKING);
		commander.setBulletListener(this);

		Animation weaponAnim = animationManager
				.getAnimation(AnimationType.WEAPON);
		weaponAnim.setFrameDuration(1 / 20f);

		gameController.cleanPlayer();

		Array<Ball> levelBalls = levelGenerator.getBalls(currentLevel);
		for (Ball ball : levelBalls) {

			gameController.newBall(ball);
		}

		gameController.setCommander(commander);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {

		camera.setToOrtho(false);

		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();
		elapsedTime += Gdx.graphics.getDeltaTime();

		/** drawing bullet **/
		Weapon activeWeapon = gameController.getActiveWeapon();
		if (activeWeapon != null) {

			Vector2 weaponPos = activeWeapon.getPosition();

			spriteBatch.draw(
					activeWeapon.getCurrentAnimation().getKeyFrame(elapsedTime,
							true), weaponPos.x, weaponPos.y);

		}

		/** drawing balls **/
		Array<Ball> balls = gameController.getActiveBalls();
		if (balls != null) {

			for (Ball iBall : balls) {

				if (iBall == null)
					continue;

				Vector2 ballPos = iBall.getPos();
				spriteBatch.draw(iBall.getTexture(), ballPos.x, ballPos.y,
						iBall.getWidth(), iBall.getHeight());
			}
		}

		/** drawing commander **/
		Commander player = gameController.getCommander();
		if (player != null) {

			TextureRegion commanderAnim = player.getCurrentAnimation()
					.getKeyFrame(elapsedTime, true);
			Vector2 commanderPosition = player.getPosition();

			spriteBatch.draw(commanderAnim, commanderPosition.x,
					commanderPosition.y);
		}

		/** draw try count **/

		int padding = 20;
		for (int count = 0; count < retryCount; count++) {

			spriteBatch.draw(retryImage,
					padding + (count * retryImage.getWidth()), getHeight()
							- retryImage.getHeight() - padding);
		}

		/** show score **/
		String scoreText = "" + newScore.getScore();
		font.draw(spriteBatch, scoreText, screenManager.getCenterX() - padding,
				screenManager.getScreenHeight() - padding);

		int textX = screenManager.scaledX(240);
		int textY = screenManager.scaledY(280);

		switch (gameState) {

		case WAITING_TO_START:

			font.draw(spriteBatch, "READY ?", textX, textY);

			if (Gdx.input.isTouched()) {

				gameState = GameState.RUNNING;
				startSound.play(1);
			}

			break;

		case GAME_OVER:

			font.draw(spriteBatch, "RETRY ?", textX, textY);

			if (Gdx.input.isTouched()) {

				gameState = GameState.RUNNING;
				restart();
			}
			break;
		case END_OF_LEVEL:

			if (gameIsFinished(currentLevel)) {

				font.draw(spriteBatch, "CONGR.. ", textX, textY);
				if (Gdx.input.isTouched()) {

					currentLevel = Level.LEVEL_1;
					gameState = GameState.RUNNING;
					reloadPlayers();
				}
			} else {

				font.draw(spriteBatch, "NEXT LEVEL?", textX, textY);
				if (Gdx.input.isTouched()) {

					gameState = GameState.RUNNING;
					restart();
				}
			}

			break;
		case RUNNING:
			break;
		default:
			break;
		}

		spriteBatch.end();
	}

	@Override
	public void update() {

		switch (gameState) {

		case RUNNING:

			gameController.update();
			break;

		default:
			break;
		}

		camera.update();
	}

	@Override
	public void resize(int width, int height) {

		viewPort.update(width, height);
		gameController.resize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public int getWidth() {

		return ScreenManager.getInstance().getScreenWidth();
	}

	@Override
	public int getHeight() {

		return ScreenManager.getInstance().getScreenHeight();
	}

	@Override
	public void shootBullet(Weapon weapon) {

		if (!gameState.equals(GameState.RUNNING))
			return;

		Weapon bullet = gameController.getActiveWeapon();
		if (bullet != null)
			return;

		/** Play sounds **/
		bulletSound.play();

		gameController.setActiveWeapon(weapon);
	}

	@Override
	public void onCommanderKilled(Commander commander) {

		retryCount--;

		/** Play Sound **/
		playerHitSount.play();

		if (retryCount == 0)
			gameState = GameState.GAME_OVER;
		else {

			newScore.setScore(0);
			reloadPlayers();
			gameState = GameState.WAITING_TO_START;
		}
	}

	@Override
	public void onGetNewScore(BallType ballType) {

		int willAddScore = 0;
		int currentScore = newScore.getScore();

		switch (ballType) {

		case SMALL:

			willAddScore = 5;
			break;

		case BIG:

			willAddScore = 10;
			break;

		default:
			break;
		}

		/** increment score **/

		currentScore += willAddScore;
		newScore.setScore(currentScore);

	}

	private void restart() {

		retryCount = 3;
		newScore = new Score();

		reloadPlayers();
	}

	@Override
	public void onLevelComplete() {

		timeoutSound.play();

		gameState = GameState.END_OF_LEVEL;
		reloadPlayers();
		currentLevel = goNextLevel();
	}

	private boolean gameIsFinished(Level level) {

		if (level.equals(Level.END_OF_GAME))
			return true;

		return false;
	}

	private Level goNextLevel() {

		switch (currentLevel) {

		case LEVEL_1:
			return Level.LEVEL_2;

		default:
			break;
		}

		return Level.END_OF_GAME;
	}

	@Override
	public void onBallBoom() {

		if (bulletSound != null)
			bulletSound.stop();

		if (boomBallSound != null)
			boomBallSound.play();
	}

	@Override
	public void dispose() {

		bulletSound.dispose();
		boomBallSound.dispose();
		playerHitSount.dispose();
		startSound.dispose();
		timeoutSound.dispose();

		retryImage.dispose();
	}
}
