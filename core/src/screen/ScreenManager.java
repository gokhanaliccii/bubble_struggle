package screen;

public class ScreenManager {

	IScreen currentScreen;
	private static ScreenManager screenManager;

	private ScreenManager() {
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
}
