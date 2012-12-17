package android.game.screen;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.util.color.Color;

import android.content.Intent;
import android.os.Handler;

public class SplashScreen extends Screen {

	private static final long DELAY = 1000;

	@Override
	protected void onCreateResources() {
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		_myScene = new Scene();
		_myScene.setBackground(new Background(Color.WHITE));	
		
		handler.sendEmptyMessageDelayed(1, DELAY);
		
		return _myScene;
	}
	
	
	
	private Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			Intent myIntent = new Intent(SplashScreen.this, MenuScreen.class);
    		SplashScreen.this.startActivity(myIntent);
    		SplashScreen.this.finish();
		};
	};
}
