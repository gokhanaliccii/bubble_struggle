package screen;

import listener.BulletListener;
import listener.GameNotifier;
import model.Score;
import player.Ball;
import player.Commander;
import player.Weapon;
import utility.AssetManager;
import utility.BallUtils;
import utility.LevelUtils;
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

	private BitmapFont font;
	private Texture retryImage;

	/** Frequently changes variables **/
	private Score newScore;
	private int retryCount = 3;
	private float passedTime = 0f;
	private Level currentLevel = Level.LEVEL_1;
	private GameState gameState = GameState.WAITING_TO_START;

	/** Utility **/
	private BallUtils ballUtil;
	private LevelUtils levelUtil;
	private AssetManager assetManager;
	private ScreenManager screenManager;
	private AnimationManager animationManager;

	/** Sounds **/
	Sound bulletSound, boomBallSound, playerHitSount, startSound, timeoutSound;

	/** Objects controller **/
	private GameController gameController;

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
		loadSounds();

		newScore = new Score();
		levelUtil = new LevelUtils();
		ballUtil = new BallUtils();

		gameController = new GameController(getWidth(), getHeight());
		gameController.setNotifier(this);

		reloadPlayers();
	}

	private void loadSounds() {

		bulletSound = assetManager.getSound(ResourcePath.BULLET_SOUND);
		playerHitSount = assetManager
				.getSound(ResourcePath.COMMANDER_KILL_SOUND);
		boomBallSound = assetManager.getSound(ResourcePath.BALL_BOOM_SOUND);
		startSound = assetManager.getSound(ResourcePath.START_SOUND);
		timeoutSound = assetManager.getSound(ResourcePath.TIMEOUT_SOUND);
	}

	private void reloadPlayers() {

		Commander commander = new Commander(new Vector2(400, 0),
				AnimationType.FORWARD_WALKING);
		commander.setBulletListener(this);

		Animation weaponAnim = animationManager
				.getAnimation(AnimationType.WEAPON);
		weaponAnim.setFrameDuration(1 / 20f);

		gameController.cleanPlayer();

		Array<Ball> levelBalls = levelUtil.getBalls(currentLevel);
		gameController.addNewBall(levelBalls);
		gameController.setCommander(commander);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {

		camera.setToOrtho(false);

		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.begin();
		passedTime += Gdx.graphics.getDeltaTime();

		/** drawing bullet **/
		Weapon activeWeapon = gameController.getActiveWeapon();
		if (activeWeapon != null) {

			Vector2 weaponPos = activeWeapon.getPosition();

			spriteBatch.draw(
					activeWeapon.getCurrentAnimation().getKeyFrame(passedTime,
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
					.getKeyFrame(passedTime, true);
			Vector2 commanderPosition = player.getPosition();

			spriteBatch.draw(commanderAnim, commanderPosition.x,
					commanderPosition.y);
		}

		/** draw try count **/

		int padding = 20;
		float retryPosY = getHeight() - retryImage.getHeight() - padding;
		for (int count = 0; count < retryCount; count++) {

			float retryPosX = padding + (count * retryImage.getWidth());
			spriteBatch.draw(retryImage, retryPosX, retryPosY);
		}
		drawGameTexts(spriteBatch);

		spriteBatch.end();
	}

	private void drawGameTexts(SpriteBatch spriteBatch) {

		int padding = 20;

		/** show score **/
		String scoreText = "" + newScore.getScore();
		font.draw(spriteBatch, scoreText, screenManager.getCenterX() - padding,
				screenManager.getScreenHeight() - padding);

		switch (gameState) {

		case WAITING_TO_START:

			font.draw(spriteBatch, "READY ?", screenManager.scaledX(300),
					screenManager.scaledY(280));

			if (Gdx.input.isTouched()) {

				gameState = GameState.RUNNING;
				startSound.play(1);
			}

			break;

		case GAME_OVER:

			font.draw(spriteBatch, "RETRY ?", screenManager.scaledX(300),
					screenManager.scaledY(280));

			if (Gdx.input.isTouched()) {

				gameState = GameState.RUNNING;
				restart();
			}
			break;

		case END_OF_LEVEL:

			if (levelUtil.gameIsFinished(currentLevel)) {

				font.draw(spriteBatch, "CONGR.. ", screenManager.scaledX(300),
						screenManager.scaledY(280));
				if (Gdx.input.isTouched()) {

					currentLevel = Level.LEVEL_1;
					gameState = GameState.RUNNING;
					restart();
				}
			} else {

				font.draw(spriteBatch, "NEXT LEVEL?",
						screenManager.scaledX(240), screenManager.scaledY(280));
				if (Gdx.input.isTouched()) {

					gameState = GameState.RUNNING;
					restart();
				}
			}

			break;

		default:
			break;
		}
	}

	@Override
	public void update() {

		if (gameState.equals(GameState.RUNNING))
			gameController.update();

		camera.update();
	}

	@Override
	public void resize(int width, int height) {

		viewPort.update(width, height);
		gameController.resize(width, height);
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

		int willAddScore = calculateNewScore(ballType);
		int currentScore = newScore.getScore();

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

		reloadPlayers();
		gameState = GameState.END_OF_LEVEL;
		currentLevel = levelUtil.getNextLevel(currentLevel);
	}

	private int calculateNewScore(BallType type) {

		float levelCoefficent = levelUtil.getLevelCoefficent(currentLevel);
		float ballCoefficent = ballUtil.getBallTypeCoefficent(type);

		int score = (int) (levelCoefficent * ballCoefficent * 5);
		return score;
	}

	@Override
	public void onBallBoom() {

		if (bulletSound != null)
			bulletSound.stop();

		if (boomBallSound != null)
			boomBallSound.play();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

		bulletSound.dispose();
		boomBallSound.dispose();
		playerHitSount.dispose();
		startSound.dispose();
		timeoutSound.dispose();

		retryImage.dispose();

		animationManager.freeToMap();
		assetManager.freeToMap();
	}
}
