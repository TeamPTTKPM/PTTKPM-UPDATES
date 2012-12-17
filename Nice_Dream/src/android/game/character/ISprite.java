package android.game.character;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;

import android.content.Context;

public interface ISprite {
	public void onLoadResources(Engine engine, Context context);
	public void onLoadScene(Engine engine, Scene scene, BoundCamera camera);
}
