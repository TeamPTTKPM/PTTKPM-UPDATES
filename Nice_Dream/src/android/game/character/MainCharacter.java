package android.game.character;

import java.util.Arrays;

import org.andengine.engine.Engine;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

public class MainCharacter implements ISprite {

	private Scene _myScene;

	private int MOVE_STATE = MoveStates.STAND;
	private static final long DURATION = 300;
	private long[] _duration;
	/*
	 * private final int[] ANIMATED_DOWN = { 0, 1, 2, 3, 4, 5, 6, 7 }; private
	 * final int[] ANIMATED_UP = { 8, 9, 10, 11, 12, 13, 14, 15 }; private final
	 * int[] ANIMATED_RIGHT = { 16, 17, 18, 19, 20, 21, 22, 23 }; private final
	 * int[] ANIMATED_LEFT = { 24, 25, 26, 27, 28, 29, 30, 31 };
	 */

	private final int[] ANIMATED_DOWN = { 0, 2, 4, 7 };
	private final int[] ANIMATED_UP = { 8, 10, 12, 15 };
	private final int[] ANIMATED_RIGHT = { 16, 18, 20, 23 };
	private final int[] ANIMATED_LEFT = { 24, 26, 28, 31 };

	private int _speed = 12;

	private float _positionX = 0;
	private float _positionY = 0;

	// no move Character
	private BitmapTextureAtlas _bta_charater_stand;
	private TiledTextureRegion _ttr_character_stand;
	private AnimatedSprite _as_character_stand;

	private BitmapTextureAtlas _bta_charater_move;
	private TiledTextureRegion _ttr_character_move;
	private AnimatedSprite _as_character_move;

	// private List<Items> _items;

	private boolean _isMoving = false;

	public MainCharacter() {
		// _items = new ArrayList<Items>();
		_duration = new long[8];
		Arrays.fill(_duration, DURATION);
	}

	// ========|| onLoadResources ||===============
	@Override
	public void onLoadResources(Engine engine, Context context) {

		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("gfx/Character/");

		// Load Image Relax State
		_bta_charater_stand = new BitmapTextureAtlas(
				engine.getTextureManager(), 128, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		_ttr_character_stand = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(_bta_charater_stand, context,
						"Character_Stand.png", 0, 0, 3, 1);

		engine.getTextureManager().loadTexture(_bta_charater_stand);

		// Load Image Move State
		_bta_charater_move = new BitmapTextureAtlas(engine.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		_ttr_character_move = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(_bta_charater_move, context,
						"Character.png", 0, 0, 8, 4);

		engine.getTextureManager().loadTexture(_bta_charater_move);
	}

	@Override
	public void onLoadScene(Engine engine, Scene scene) {
		_myScene = scene;

		_as_character_stand = new AnimatedSprite(_positionX, _positionY,
				_ttr_character_stand, engine.getVertexBufferObjectManager());

		_as_character_move = new AnimatedSprite(_positionX, _positionY,
				_ttr_character_move, engine.getVertexBufferObjectManager());

		_myScene.attachChild(_as_character_stand);
		playerMoveState();
	}

	private void playerMoveState() {

		if (_isMoving) {
			if (MOVE_STATE == MoveStates.STAND) {
				_myScene.detachChild(_as_character_stand);
				_as_character_move.setPosition(_positionX, _positionY);
				_myScene.attachChild(_as_character_move);
			}
		} else{
			if (MOVE_STATE != MoveStates.STAND) {
				_myScene.detachChild(_as_character_move);
				_as_character_stand.setPosition(_positionX, _positionY);
				_myScene.attachChild(_as_character_stand);
			}
		}
		
		for (int i = 0; i < _myScene.getChildCount(); i++) {
			IEntity entity = _myScene.getChildByIndex(i);
			if (entity.equals(_as_character_stand)) {
				_myScene.detachChild(entity);
				_myScene.attachChild(_as_character_move);
			} else {
				
			}
		}

		switch (MOVE_STATE) {
		case MoveStates.MOVE_LEFT: {
			_as_character_move.animate(_duration, 24, 31, 1);
			break;
		}
		case MoveStates.MOVE_RIGHT: {
			_as_character_move.animate(_duration, 16, 23, 1);
			break;
		}
		case MoveStates.MOVE_UP: {
			_as_character_move.animate(_duration, 8, 15, 1);
			break;
		}
		case MoveStates.MOVE_DOWN: {
			_as_character_move.animate(_duration, 0, 7, 1);
			break;
		}
		case MoveStates.UN_MOVE_LEFT: {
			// _as_character_move.animate(new long[]{100}, new int[]{24},
			// 1);;
			break;
		}
		case MoveStates.UN_MOVE_RIGHT: {
			// _as_character_move.animate(new long[]{100}, new int[]{16},
			// 1);
			break;
		}
		case MoveStates.UN_MOVE_UP: {
			// _as_character_move.animate(new long[]{100}, new int[]{8}, 1);
			break;
		}
		case MoveStates.UN_MOVE_DOWN: {
			// _as_character_move.animate(new long[]{100}, new int[]{0}, 1);
			break;
		}
		case MoveStates.STAND: {
			_as_character_stand.animate(DURATION);
			break;
		}
		}
	}

	// =========|| STATE ||================
	public void setMoveState(int state) {
		MOVE_STATE = state;
		playerMoveState();
	}

	public int getMoveState() {
		return MOVE_STATE;
	}

	// =========|| Position ||================
	public void setPositionX(float positionX) {
		_positionX = positionX;
	}

	public float getPositionX() {
		return _positionY;
	}

	public void setPositionY(float positionY) {
		_positionY = positionY;
	}

	public float getPositionY() {
		return _positionY;
	}

	public void setPositionXY(float positionX, float positionY) {
		setPositionX(positionX);
		setPositionY(positionY);
	}

	// =======================================|| Move
	// ||================================
	public void moveX(float moveX) {
		_positionX = moveX;
		move();
	}

	public void moveY(float moveY) {
		_positionY = moveY;
		move();
	}

	public void moveXY(float moveX, float moveY) {
		_positionX = moveX;
		_positionY = moveY;
		move();
	}

	public void moveRelativeX(float moveRelativeX) {
		_positionX += moveRelativeX;
		move();
	}

	public void moveRelativeY(float moveRelativeY) {
		_positionY += moveRelativeY;
		move();
	}

	public void moveRelativeXY(float moveRelativeX, float moveRelativeY) {
		_positionX += moveRelativeX;
		_positionY += moveRelativeY;
		move();
	}

	public void moveUp() {
		moveRelativeY(-_speed);
	}

	public void moveDown() {
		moveRelativeY(_speed);
	}

	public void moveLeft() {
		moveRelativeX(-_speed);
	}

	public void moveRight() {
		moveRelativeX(_speed);
	}

	/**
	 * Move player
	 */
	private void move() {
		_isMoving = true;
		_as_character_move.setPosition(_positionX, _positionY);
	}

	public void stopMove()
	{
		_isMoving = false;
		_as_character_move.stopAnimation();
	}
}
