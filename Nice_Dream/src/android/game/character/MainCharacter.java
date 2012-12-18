package android.game.character;

import java.util.Arrays;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;
import android.game.character.Const.CharacterImageStates;
import android.game.character.Const.CharacterStates;

public class MainCharacter implements ISprite {

	private Scene _myScene;
	private BoundCamera _myCamera;

	private int MoveState = CharacterStates.STAND;
	private int ImageState = CharacterImageStates.IMAGE_STAND;
	private static final long DURATION = 100;
	private long[] _duration;

	/*
	 * private final int[] ANIMATED_DOWN = { 0, 2, 4, 7 }; private final int[]
	 * ANIMATED_UP = { 8, 10, 12, 15 }; private final int[] ANIMATED_RIGHT = {
	 * 16, 18, 20, 23 }; private final int[] ANIMATED_LEFT = { 24, 26, 28, 31 };
	 */

	private int _speed = 8;
	private boolean _isMoving = false;

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

	private boolean _isFirst = true;

	private TMXTiledMap _tmxMap;
	private TMXLayer _tmxLayer;

	public MainCharacter(TMXTiledMap tmxMap, TMXLayer layer, float startX,
			float startY) {
		set_tmxMap(tmxMap);
		set_tmxLayer(layer);
		_duration = new long[8];
		Arrays.fill(_duration, DURATION);
		_positionX = startX;
		_positionY = startY;
	}

	public MainCharacter() {
		_duration = new long[8];
		Arrays.fill(_duration, DURATION);
		_positionX = 64;
		_positionY = 32;
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
	public void onLoadScene(Engine engine, Scene scene, BoundCamera camera) {
		_myScene = scene;
		_myCamera = camera;

		_as_character_stand = new AnimatedSprite(_positionX, _positionY,
				_ttr_character_stand, engine.getVertexBufferObjectManager());

		_as_character_move = new AnimatedSprite(_positionX, _positionY,
				_ttr_character_move, engine.getVertexBufferObjectManager());

		/*
		 * final PhysicsHandler physicsHandlerStand = new
		 * PhysicsHandler(_as_character_stand);
		 * _as_character_stand.registerUpdateHandler(physicsHandlerStand);
		 * 
		 * final PhysicsHandler physicsHandlerMove = new
		 * PhysicsHandler(_as_character_move);
		 * _as_character_move.registerUpdateHandler(physicsHandlerMove);
		 */

		playAnimation();
	}

	private void playAnimation() {

		if (_isFirst) {
			_myScene.attachChild(_as_character_stand);
			_isFirst = false;
		} else {
			if (ImageState == CharacterImageStates.IMAGE_STAND) {
				_myScene.detachChild(_as_character_stand);
				_as_character_move.setPosition(_positionX, _positionY);
				_myScene.attachChild(_as_character_move);
				ImageState = CharacterImageStates.IMAGE_MOVE;
			} else if (ImageState == CharacterImageStates.IMAGE_MOVE) {
				// _myScene.detachChild(_as_character_move);
				// _as_character_stand.setPosition(_positionX, _positionY);
				// _myScene.attachChild(_as_character_stand);
				ImageState = CharacterImageStates.IMAGE_UN_MOVE;
			} else {

			}
		}

		switch (MoveState) {
		case CharacterStates.MOVE_LEFT: {
			_as_character_move.animate(_duration, 24, 31, 1000);
			break;
		}
		case CharacterStates.MOVE_RIGHT: {
			_as_character_move.animate(_duration, 16, 23, 1000);
			break;
		}
		case CharacterStates.MOVE_UP: {
			_as_character_move.animate(_duration, 8, 15, 1000);
			break;
		}
		case CharacterStates.MOVE_DOWN: {
			_as_character_move.animate(_duration, 0, 7, 1000);
			break;
		}
		case CharacterStates.UN_MOVE_LEFT: {
			_as_character_move.animate(new long[] { 100, 100 }, new int[] { 31,
					31 }, 1);
			break;
		}
		case CharacterStates.UN_MOVE_RIGHT: {
			_as_character_move.animate(new long[] { 100, 100 }, new int[] { 23,
					23 }, 1);
			break;
		}
		case CharacterStates.UN_MOVE_UP: {
			_as_character_move.animate(new long[] { 100, 100 }, new int[] { 15,
					15 }, 1);
			break;
		}
		case CharacterStates.UN_MOVE_DOWN: {
			_as_character_move.animate(new long[] { 100, 100 }, new int[] { 7,
					7 }, 1);
			break;
		}
		case CharacterStates.STAND: {
			_as_character_stand.animate(DURATION);
			break;
		}
		}
	}

	// =========|| STATE ||================
	public void setMoveState(int state) {
		MoveState = state;
		playAnimation();
	}

	public int getMoveState() {
		return MoveState;
	}

	// =========|| Position ||=============
	public void setPositionX(float positionX) {
		_positionX = positionX;
	}

	public float getPositionX() {
		return _positionY;
	}

	public void setPositionY(float positionY) {
		_positionY = positionY;
	}

	public float getCenterX() {
		return _positionX + (_ttr_character_move.getWidth()) / 2;
	}

	public float getCenterY() {
		return _positionY + (_ttr_character_move.getHeight()) / 2;
	}

	public float getPositionY() {
		return _positionY;
	}

	public void setPositionXY(float positionX, float positionY) {
		setPositionX(positionX);
		setPositionY(positionY);
	}

	// =========|| Move ||==========
	public void moveUp() {
		_positionY -= _speed;

		TMXTile tileLeft = getTMXTileAt(get_tmxLayer(), _positionX + 5, getCenterY());
		TMXTile tileRight = getTMXTileAt(get_tmxLayer(), _positionX + _ttr_character_move.getWidth() - 5, getCenterY());
		
		if (collisionWall()) {
			_positionY -= _speed;
			return;
		}

		if (tileLeft != null && collisionWith(tileLeft)) {
			_positionY += _speed;
			return;
		}
		
		if (tileRight != null && collisionWith(tileRight)) {
			_positionY += _speed;
			return;
		}

		move();
	}

	public void moveDown() {
		_positionY += _speed;
		TMXTile tileLeft = getTMXTileAt(get_tmxLayer(), _positionX + 5, _positionY
				+ _ttr_character_move.getHeight());
		TMXTile tileRight = getTMXTileAt(get_tmxLayer(), _positionX + _ttr_character_move.getWidth() - 5, _positionY
				+ _ttr_character_move.getHeight());
		
		if (collisionWall()) {
			_positionY -= _speed;
			return;
		}

		if (tileLeft != null && collisionWith(tileLeft)) {
			_positionY -= _speed;
			return;
		}
		
		if (tileRight != null && collisionWith(tileRight)) {
			_positionY -= _speed;
			return;
		}

		move();
	}

	public void moveLeft() {
		_positionX -= _speed;
		TMXTile tileBottom = getTMXTileAt(get_tmxLayer(), _positionX, _positionY
				+ _ttr_character_move.getHeight());
		TMXTile tileCenter = getTMXTileAt(get_tmxLayer(), _positionX, getCenterY());

		if (collisionWall()) {
			_positionX = 0;
			return;
		}
		
		if (tileCenter != null && collisionWith(tileCenter)) {
			_positionX = (tileCenter.getTileColumn() + 1) * 32;
			return;
		}
		
		if (tileBottom != null && collisionWith(tileBottom)) {
			_positionX = (tileBottom.getTileColumn() + 1) * 32;
			return;
		}

		move();
	}

	public void moveRight() {
		_positionX += _speed;
		TMXTile tileCenter = getTMXTileAt(get_tmxLayer(), _positionX
				+ _ttr_character_move.getWidth(), getCenterY());
		TMXTile tileBottom = getTMXTileAt(get_tmxLayer(), _positionX
				+ _ttr_character_move.getWidth(), _positionY
				+ _ttr_character_move.getHeight());
		if (collisionWall()) {
			_positionX -= _speed;
			return;
		}

		if (tileCenter != null && collisionWith(tileCenter)) {
			_positionX = (tileCenter.getTileColumn() - 1) * 32;
			return;
		}
		
		if (tileBottom != null && collisionWith(tileBottom)) {
			_positionX = (tileBottom.getTileColumn() - 1) * 32;
			return;
		}

		move();
	}

	private boolean collisionWall() {
		if (_positionX < 0) {
			return true;
		}

		if (_positionX + _ttr_character_move.getWidth() > _tmxLayer.getWidth()) {
			return true;
		}

		if (_positionY < 0) {
			return true;
		}

		if (_positionY + _ttr_character_move.getHeight() > _tmxLayer.getHeight()) {
			return true;
		}
		return false;
	}

	/**
	 * Move player
	 */
	private void move() {
		// stop animation of _as_character_stand
		_isMoving = true;
		_as_character_stand.stopAnimation();
		_as_character_move.setPosition(_positionX, _positionY);
	}

	public void stopMove() {
		_isMoving = false;
		_as_character_move.stopAnimation();
		// ImageState = CharacterImageStates.IMAGE_UN_MOVE;
		switch (this.MoveState) {
		case CharacterStates.MOVE_UP:
			this.setMoveState(CharacterStates.UN_MOVE_UP);
			break;
		case CharacterStates.MOVE_DOWN:
			this.setMoveState(CharacterStates.UN_MOVE_DOWN);
			break;
		case CharacterStates.MOVE_LEFT:
			this.setMoveState(CharacterStates.UN_MOVE_LEFT);
			break;
		case CharacterStates.MOVE_RIGHT:
			this.setMoveState(CharacterStates.UN_MOVE_RIGHT);
			break;
		default:
			break;
		}
	}

	public boolean collisionWith(TMXTile tmxTile) {
		TMXProperties<TMXTileProperty> tmxTileProperty = tmxTile
				.getTMXTileProperties(get_tmxMap());
		if (tmxTileProperty != null) {
			String tileName = tmxTileProperty.get(0).getName();
			if (tileName.equals("Collide")) {
				return true;
			}
		}
		return false;
	}

	public TMXTile getTMXTileAt(TMXLayer tmxLayer, float fX, float fY) {
		TMXTile tmxTile = tmxLayer.getTMXTileAt(fX, fY);
		return tmxTile;
	}

	public TMXTiledMap get_tmxMap() {
		return _tmxMap;
	}

	public void set_tmxMap(TMXTiledMap _tmxMap) {
		this._tmxMap = _tmxMap;
	}

	public TMXLayer get_tmxLayer() {
		return _tmxLayer;
	}

	public void set_tmxLayer(TMXLayer _tmxLayer) {
		this._tmxLayer = _tmxLayer;
	}

	public boolean isMoving() {
		return _isMoving;
	}
	
	public int getSpeed()
	{
		return _speed; 
	}

	public AnimatedSprite getSpriteMove()
	{
		return _as_character_move;
	}
	
	public AnimatedSprite getSpriteStand()
	{
		return _as_character_stand;
	}
}
