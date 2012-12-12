package android.game.screen;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.util.color.Color;

public class SplashScreen extends Screen{
	
	@Override
	protected void onCreateResources() {
	}
	
	@Override
	protected Scene onCreateScene() {
		_scene = new Scene();
		_scene.setBackground(new Background(Color.GREEN));
		
		return _scene;
	}
}
