package android.game.maps;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.debug.Debug;

import android.content.Context;

public class Maps {
	
	public static Context _context;
	
	public static  TMXTiledMap getTMXTiledMap(Scene myScene, Engine engine, Context context, String nameMap)
	{
		try {
		TMXTiledMap tiledMap;
		final TMXLoader tmxLoader = new TMXLoader(context.getAssets(),
				engine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, 
				engine.getVertexBufferObjectManager(),
				new ITMXTilePropertiesListener() {
			
			@Override
			public void onTMXTileWithPropertiesCreated(TMXTiledMap pTMXTiledMap,
					TMXLayer pTMXLayer, TMXTile pTMXTile,
					TMXProperties<TMXTileProperty> pTMXTileProperties) {
				if(pTMXTileProperties.containsTMXProperty("Collide", "true")) {
					// Count++;
				}
			}
		});
		
		// Toast.makeText(_context, "Num of Collide " + Count , Toast.LENGTH_SHORT).show();
		
		String path = "tmx/" + nameMap;
		tiledMap = tmxLoader.loadFromAsset(path);
		
		
		return tiledMap;
		
		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}
		return null;
		
	}
}
