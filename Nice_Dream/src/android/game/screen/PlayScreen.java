package android.game.screen;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;

import android.game.character.MainCharacter;
import android.game.character.MoveStates;
import android.game.control.Area;
import android.game.control.Controller;
import android.game.control.Controller.IOnControllerListener;
import android.game.maps.Maps;

public class PlayScreen extends Screen {

	private TMXTiledMap _tmxTiledMap;
	private TMXLayer tmxLayer;
	private static MainCharacter _mainCharacter = new MainCharacter();

	private SurfaceScrollDetector _scrollDetector;
	private boolean _isScroll = false;
	private boolean _isMove = false;

	@Override
	protected void onCreateResources() {
		super.onCreateResources();
		
		_mainCharacter.onLoadResources(mEngine, this);
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		_scene = new Scene();

		// Load Map
		loadMap();

		// Load MainCharacter
		_mainCharacter.onLoadScene(mEngine, _scene);

		// TouchScreen
		//touchScene();
		
		// ScrollScreen
		scrollScreen();
		
		// load Control
		loadControl();
		
		return this._scene;
	}

	/**
	 * METHOD
	 */

	private void loadMap() {
		Maps._context = this;
		_tmxTiledMap = Maps.getTMXTiledMap(_scene, mEngine, this, "level1.tmx");

		tmxLayer = this._tmxTiledMap.getTMXLayers().get(0);

		_scene.attachChild(tmxLayer);
	}

	private void scrollScreen() {

		/* Make the camera not exceed the bounds of the TMXEntity. */
		this._camera.setBounds(0, 0, tmxLayer.getWidth(), tmxLayer.getHeight());
		this._camera.setBoundsEnabled(true);

		this._scrollDetector = new SurfaceScrollDetector(
				new IScrollDetectorListener() {

					@Override
					public void onScrollStarted(ScrollDetector arg0, int arg1, float pDistanceX, float pDistanceY) {
						_camera.offsetCenter(-pDistanceX,-pDistanceY);
					}

					@Override
					public void onScrollFinished(ScrollDetector arg0, int arg1, float pDistanceX, float pDistanceY) {
						_camera.offsetCenter(-pDistanceX,-pDistanceY);
					}

					@Override
					public void onScroll(ScrollDetector pScrollDetector,
							int arg1, float pDistanceX, float pDistanceY) {
						_camera.offsetCenter(-pDistanceX,-pDistanceY);
					}
				});
	}

	private void loadControl()
	{
		new Controller(_scene, _camera,  CAMERA_WIDTH, CAMERA_HEIGHT, 0.1f, new IOnControllerListener() {
			
			@Override
			public void onControlChange(int activePointID,
					TouchEvent pSceneTouchEvent, float pValueX, float pValueY,
					int iResultArea) {
				if (activePointID == -1) {
					_mainCharacter.stopMove();
				}
				else
				{
					switch (iResultArea) {
					case Area.TOP:
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_mainCharacter.setMoveState(MoveStates.MOVE_UP);
								_mainCharacter.moveUp();
							}
						});
						
						break;

					case Area.BOTTOM:
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_mainCharacter.setMoveState(MoveStates.MOVE_DOWN);
								_mainCharacter.moveDown();
							}
						});
						break;

					case Area.CENTER_RIGHT:
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_mainCharacter.setMoveState(MoveStates.MOVE_RIGHT);
								_mainCharacter.moveRight();
							}
						});
						break;
					case Area.CENTER_LEFT:
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_mainCharacter.setMoveState(MoveStates.MOVE_LEFT);
								_mainCharacter.moveLeft();
							}
						});

					case Area.NONE:
						_scrollDetector.setEnabled(true);
						_scrollDetector.onTouchEvent(pSceneTouchEvent);
					default:
						break;
					}
				}
				
			}
		});
	}
}
