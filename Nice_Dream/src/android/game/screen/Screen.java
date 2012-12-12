package android.game.screen;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Color;
import android.view.Display;

public class Screen extends SimpleBaseGameActivity{

	
	// ========================|| FIELD || ======================== 
	protected float CAMERA_WIDTH;
	protected float CAMERA_HEIGHT;
	
	protected Camera _camera;
	protected Scene _scene;
	
	// Font
	protected BitmapTextureAtlas _fontTexture;
	protected Font _font;
	
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
		loadFont();
		
	}
	@Override
	protected Scene onCreateScene() {
		return null;
	}
	
	//======================== || METHOD || =======================
	private void loadFont() {
		_fontTexture = new BitmapTextureAtlas(getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		FontFactory.setAssetBasePath("font/");
		_font = FontFactory.createFromAsset(getFontManager(), _fontTexture,
				getAssets(), "Flubber.ttf", 32, true, Color.WHITE);
		mEngine.getTextureManager().loadTexture(this._fontTexture);
		mEngine.getFontManager().loadFont(this._font);
	}
}
