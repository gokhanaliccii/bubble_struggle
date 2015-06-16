package screen;

public class ScreenManager {

	IScreen currentScreen;
	
	private int screenWidth=800;
	private int screenHeight=480;
	
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
	
	public int getScreenWidth(){
		
		return screenWidth;
	}
	
	public int getScreenHeight(){
		
		return screenHeight;
	}
	
	public void resize(int screenWidth,int screenHeight){
		
		this.screenHeight=screenHeight;
		this.screenWidth=screenWidth;
	}
}
