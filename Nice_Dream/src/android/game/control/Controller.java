package android.game.control;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.Constants;

import android.view.MotionEvent;

public class Controller {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int INVALID_POINTER_ID = -1;

	// ===========================================================
	// Fields
	// ===========================================================
	private float _touchAreaLocalX;
	private float _touchAreaLocalY;

	private float _width, _height;

	// private final IOnScreenControlListener mOnScreenControlListener;
	private final IOnControllerListener _onControllerListener;

	private int _activePointerID = INVALID_POINTER_ID;

	private TouchEvent _touchEvent;

	private BoundCamera _camera;
	// ===========================================================
	// Constructors
	// ===========================================================

	public Controller(final Scene scene,final BoundCamera camera, final float width, final float height,
			final float _betweenTimeUpdates,
			final IOnControllerListener onControllerListener) {

		_camera = camera;
		_width = width;
		_height = height;

		this._onControllerListener = onControllerListener;

		scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(Scene pScene,
					TouchEvent pSceneTouchEvent) {
				return Controller.this.onHandlerControllerScene(
						pSceneTouchEvent, pSceneTouchEvent.getX(),
						pSceneTouchEvent.getY());
			}
		});

		scene.registerUpdateHandler(new TimerHandler(_betweenTimeUpdates, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						int result = getAreaTouch(_touchAreaLocalX,
								_touchAreaLocalY);
						Controller.this._onControllerListener.onControlChange(
								_activePointerID, _touchEvent,
								_touchAreaLocalX, _touchAreaLocalY, result);
					}
				}));

		scene.setOnSceneTouchListenerBindingOnActionDownEnabled(true);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	protected boolean onHandlerControllerScene(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		final int pointerID = pSceneTouchEvent.getPointerID();

		switch (pSceneTouchEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (this._activePointerID == INVALID_POINTER_ID) {
				this._activePointerID = pointerID;
				updateTouchPosition(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (this._activePointerID == pointerID) {
				this._activePointerID = INVALID_POINTER_ID;
				return true;
			}
			break;
		default:
			if (this._activePointerID == pointerID) {
				updateTouchPosition(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
				return true;
			}
			break;
		}
		return true;
	}

	protected void updateTouchPosition(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		_touchEvent = pSceneTouchEvent;
		_touchAreaLocalX = pTouchAreaLocalX;
		_touchAreaLocalY = pTouchAreaLocalY;
	}

	private int getAreaTouch(float fX, float fY) {
		float[] local = _camera.getCameraSceneCoordinatesFromSceneCoordinates(fX, fY);
		
		float x1 = _width / 4;
		float x2 = x1 * 3;

		float y1 = _height / 3;
		float y2 = y1 * 2;

		if (!(local[Constants.VERTEX_INDEX_X] > x1 && local[Constants.VERTEX_INDEX_X] < x2)) {
			if (local[Constants.VERTEX_INDEX_Y] < y1) {
				return Area.TOP;
			} else if (local[Constants.VERTEX_INDEX_Y] > y2) {
				return Area.BOTTOM;
			} else {
				if (local[Constants.VERTEX_INDEX_X] < x1) {
					return Area.CENTER_LEFT;
				} else {
					return Area.CENTER_RIGHT;
				}
			}
		}
		return Area.NONE;
	}
	
	/*private int getAreaTouch(float fX, float fY)
	{
		return 0;
	}*/

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static interface IOnControllerListener {

		public void onControlChange(final int activePointerID,
				final TouchEvent pSceneTouchEvent, final float pValueX,
				final float pValueY, final int iResultArea);
	}

}
