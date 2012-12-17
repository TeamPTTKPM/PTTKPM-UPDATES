package android.game.screen;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;

import android.game.character.Const.CharacterStates;
import android.game.character.MainCharacter;
import android.game.control.Area;
import android.game.control.Controller;
import android.game.control.Controller.IOnControllerListener;
import android.game.maps.Maps;

public class PlayScreen extends Screen {

	private TMXTiledMap _tmxTiledMap;
	private TMXLayer tmxLayer;
	private static MainCharacter _mainCharacter = new MainCharacter();

	private SurfaceScrollDetector _scrollDetector;

	@Override
	protected void onCreateResources() {
		super.onCreateResources();

		_mainCharacter.onLoadResources(mEngine, this);

		loadImage();
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		_myScene = new Scene();

		// Load Map
		loadMap();

		_mainCharacter.set_tmxMap(_tmxTiledMap);
		_mainCharacter.set_tmxLayer(tmxLayer);

		// Load MainCharacter
		_mainCharacter.onLoadScene(mEngine, _myScene, _myCamera);

		// ScrollScreen
		scrollScreen();

		// load Control
		loadControl();
		
		return this._myScene;
	}

	/**
	 * METHOD
	 */

	private void loadImage() {
	}

	private void loadMap() {
		Maps._context = this;
		_tmxTiledMap = Maps.getTMXTiledMap(_myScene, mEngine, this,
				"level1.tmx");

		tmxLayer = this._tmxTiledMap.getTMXLayers().get(0);

		_myScene.attachChild(tmxLayer);
	}

	private void scrollScreen() {

		/* Make the camera not exceed the bounds of the TMXEntity. */
		this._myCamera.setBounds(0, 0, tmxLayer.getWidth(),
				tmxLayer.getHeight());
		this._myCamera.setBoundsEnabled(true);

		this._scrollDetector = new SurfaceScrollDetector(
				new IScrollDetectorListener() {

					@Override
					public void onScrollStarted(ScrollDetector arg0, int arg1,
							float pDistanceX, float pDistanceY) {
						// _myCamera.offsetCenter(-pDistanceX,-pDistanceY);
					}

					@Override
					public void onScrollFinished(ScrollDetector arg0, int arg1,
							float pDistanceX, float pDistanceY) {
						// _myCamera.offsetCenter(-pDistanceX,-pDistanceY);
					}

					@Override
					public void onScroll(ScrollDetector pScrollDetector,
							int arg1, float pDistanceX, float pDistanceY) {

						/*if (_mainCharacter.isMoving()) {
							float disXMin = _myCamera.getXMin()
									- _mainCharacter.getPositionX();
							float disXMax = _myCamera.getXMax()
									- _mainCharacter.getPositionX();
							float disYMin = _myCamera.getYMin()
									- _mainCharacter.getPositionY();
							float disYMax = _myCamera.getYMax()
									- _mainCharacter.getPositionY();

							if (Math.abs(disXMax) < _tmxTiledMap.getTileWidth() * 4
									|| Math.abs(disXMin) < _tmxTiledMap
											.getTileWidth() * 4
									|| Math.abs(disYMin) < _tmxTiledMap
											.getTileHeight() * 4
									|| Math.abs(disYMax) < _tmxTiledMap
											.getTileHeight() * 4) {
								switch (_mainCharacter.getMoveState()) {
								case CharacterStates.MOVE_UP:
									_myCamera.offsetCenter(0, -_mainCharacter.getSpeed());
									break;
								case CharacterStates.MOVE_DOWN:
									_myCamera.offsetCenter(0, _mainCharacter.getSpeed());
									break;
								case CharacterStates.MOVE_LEFT:
									_myCamera.offsetCenter(-_mainCharacter.getSpeed(), 0);
									break;
								case CharacterStates.MOVE_RIGHT:
									_myCamera.offsetCenter(_mainCharacter.getSpeed(), 0);
									break;
									
								default:
									break;
								}
							} */
				
							_myCamera.offsetCenter(-pDistanceX, -pDistanceY);
					}
				});
	}

	private void loadControl() {
		new Controller(_myScene, _myCamera, CAMERA_WIDTH, CAMERA_HEIGHT, 0.1f,
				new IOnControllerListener() {

					@Override
					public void onControlChange(int activePointID,
							TouchEvent pSceneTouchEvent, float pValueX,
							float pValueY, int iResultArea) {
						if (activePointID == -1) {
							_mainCharacter.stopMove();
						} else {
							switch (iResultArea) {
							case Area.TOP:
								if (_mainCharacter.getMoveState() != CharacterStates.MOVE_UP) {
									_mainCharacter
											.setMoveState(CharacterStates.MOVE_UP);
								}
								_mainCharacter.moveUp();
								break;

							case Area.BOTTOM:
								if (_mainCharacter.getMoveState() != CharacterStates.MOVE_DOWN) {
									_mainCharacter
											.setMoveState(CharacterStates.MOVE_DOWN);
								}
								_mainCharacter.moveDown();
								break;

							case Area.CENTER_RIGHT:
								if (_mainCharacter.getMoveState() != CharacterStates.MOVE_RIGHT) {
									_mainCharacter
											.setMoveState(CharacterStates.MOVE_RIGHT);
								}
								_mainCharacter.moveRight();
								break;
							case Area.CENTER_LEFT:
								if (_mainCharacter.getMoveState() != CharacterStates.MOVE_LEFT) {
									_mainCharacter
											.setMoveState(CharacterStates.MOVE_LEFT);
								}
								_mainCharacter.moveLeft();
								break;
							case Area.NONE:
								if (!_mainCharacter.isMoving()) {
									_scrollDetector.setEnabled(true);
									_scrollDetector.onTouchEvent(pSceneTouchEvent);
								} else {
									_scrollDetector.setEnabled(false);
								}
							default:
								break;
							}
						}

					}
				});
	}
}
