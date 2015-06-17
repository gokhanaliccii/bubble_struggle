package screen;

public class ScreenManager {

	IScreen currentScreen;

	public static int DEFAULT_SCREEN_WIDTH = 800;
	public static int DEFAULT_SCREEN_HEIGHT = 480;

	private int screenWidth;
	private int screenHeight;
	private float aspectRatioHorizontal;
	private float aspectRatioVertical;

	private static ScreenManager screenManager;

	private ScreenManager() {

		this.screenWidth = DEFAULT_SCREEN_WIDTH;
		this.screenHeight = DEFAULT_SCREEN_HEIGHT;

		calcalutaAspectRatio();
	}

	public static ScreenManager getInstance() {

		if (screenManager == null)
			screenManager = new ScreenManager();

		return screenManager;
	}

	public IScreen getCurrentScreen() {

		return currentScreen;
	}

	public void setCurrentScreen(IScreen currentScreen) {

		this.currentScreen = currentScreen;
	}

	public int getScreenWidth() {

		return screenWidth;
	}

	public int getScreenHeight() {

		return screenHeight;
	}

	public float getAspectRatioHorizontal() {

		return aspectRatioHorizontal;
	}

	public float getAspectRatioVertical() {

		return aspectRatioVertical;
	}

	public void resize(int screenWidth, int screenHeight) {

		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;

		calcalutaAspectRatio();
	}

	private void calcalutaAspectRatio() {

		this.aspectRatioHorizontal = screenWidth / DEFAULT_SCREEN_WIDTH;
		this.aspectRatioVertical = screenHeight / DEFAULT_SCREEN_HEIGHT;
	}
}
