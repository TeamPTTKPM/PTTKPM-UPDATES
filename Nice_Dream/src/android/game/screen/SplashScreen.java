package android.game.screen;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.util.color.Color;

import android.content.Intent;
import android.os.Handler;

public class SplashScreen extends Screen {

	@Override
	protected void onCreateResources() {
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		_scene = new Scene();
		_scene.setBackground(new Background(Color.BLUE));	
		
		handler.sendEmptyMessageDelayed(1, 5000);
		
		return _scene;
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
