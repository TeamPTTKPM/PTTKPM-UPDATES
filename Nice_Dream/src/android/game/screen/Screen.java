package android.game.screen;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.view.Display;

public class Screen extends SimpleBaseGameActivity{

	protected float CAMERA_WIDTH;
	protected float CAMERA_HEIGHT;
	
	protected Camera _camera;
	protected Scene _scene;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		Display display = getWindowManager().getDefaultDisplay();
		
		CAMERA_WIDTH = display.getWidth();
		CAMERA_HEIGHT = display.getHeight();
		
		_camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		  EngineOptions en = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
		    CAMERA_WIDTH, CAMERA_HEIGHT), _camera);
		 
		  return en;
	}
	@Override
	protected void onCreateResources() {
		
	}
	@Override
	protected Scene onCreateScene() {
		return null;
	}
	
}
