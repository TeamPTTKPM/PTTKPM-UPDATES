package android.game.screen;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import android.content.Intent;

public class MenuScreen extends Screen implements IOnMenuItemClickListener {

	// ================ || FIELD || =========================
	private String[] _listNameMenuItem;
	
	private BitmapTextureAtlas _bg_BitmapTextureAtlas;
	private BitmapTextureAtlas _menuItem_TextureAtlas;

	private MenuScene _menuScene;
	private TextureRegion _menuItem_TextureRegion;
	
	private TextureRegion _bg_TextureRegion;
	private Sprite _bg_Sprite;

	// ================ || OVERIDE METHOD || ================
	public MenuScreen() {
		_listNameMenuItem = new String[MenuStates.NUM_MENU];
	}

	@Override
	protected void onCreateResources() {
		super.onCreateResources();
		onLoadMenuName();
		loadImage();
		
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		_scene = new Scene();

		// init
		createStaticMenuScene();

		_bg_Sprite = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, _bg_TextureRegion, getVertexBufferObjectManager());
		 _scene.setBackground(new SpriteBackground(_bg_Sprite));

		// add object
		_scene.setChildScene(_menuScene);
		return _scene;
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		
		Intent intent;
		switch (pMenuItem.getID()) {
		case MenuStates.MENU_NEWGAME:
			intent = new Intent(this, PlayScreen.class);
			startActivity(intent);
			break;
		case MenuStates.MENU_OPTIONS:
			intent = new Intent(this, OptionScreen.class);
			startActivity(intent);
			break;
		case MenuStates.MENU_ABOUT:
			intent = new Intent(this, AboutScreen.class);
			startActivity(intent);
			break;
		case MenuStates.MENU_QUIT:
			this.finish();
			break;
		}
		return true;
	}

	// ================ || METHOD || ========================
	private void onLoadMenuName() {
		_listNameMenuItem[MenuStates.MENU_NEWGAME] = getString(R.string.newgame);
		_listNameMenuItem[MenuStates.MENU_OPTIONS] = getString(R.string.options);
		_listNameMenuItem[MenuStates.MENU_ABOUT] = getString(R.string.about);
		_listNameMenuItem[MenuStates.MENU_QUIT] = getString(R.string.exit);
	}

	private void loadImage() {

		// load Background MainMenu
		_bg_BitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(),1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		_bg_TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(_bg_BitmapTextureAtlas, this,
						"background.png", 0, 0);
		mEngine.getTextureManager().loadTexture(_bg_BitmapTextureAtlas);

		// Load Bitmap for Button
		_menuItem_TextureAtlas = new BitmapTextureAtlas(getTextureManager(), 512, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		_menuItem_TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(_menuItem_TextureAtlas, this, "MenuItem.png",
						0, 0);

		mEngine.getTextureManager().loadTexture(_menuItem_TextureAtlas);
	}
	
	private void createStaticMenuScene() {
		_menuScene = new MenuScene(_camera);
		
		for (int i = MenuStates.MENU_NEWGAME; i <= MenuStates.MENU_QUIT; i++) {
			
			SpriteMenuItem newMenuItem = new SpriteMenuItem(i, _menuItem_TextureRegion, getVertexBufferObjectManager());
			int cenX = 0;
			int cenY = 0;

			
			Text text = new Text(cenX, cenY, _font, _listNameMenuItem[i], getVertexBufferObjectManager());
			
			cenX = (int) (newMenuItem.getWidth() - text.getWidth() )/ 2;
			cenY = (int) (newMenuItem.getHeight() - _font.getLineHeight()) / 2;
			text.setPosition(cenX, cenY);
			
			newMenuItem.attachChild(text);

			_menuScene.addMenuItem(new ScaleMenuItemDecorator(newMenuItem,
					1.2f, 1.0f));
		}

		_menuScene.buildAnimations();

		_menuScene.setBackgroundEnabled(false);

		_menuScene.setOnMenuItemClickListener(this);
	}
}
