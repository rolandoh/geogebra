package com.himamis.retex.renderer.android.font;

import com.himamis.retex.renderer.android.BaseObjectHelper;
import com.himamis.retex.renderer.share.exception.ResourceParseException;
import com.himamis.retex.renderer.share.platform.font.Font;
import com.himamis.retex.renderer.share.platform.font.FontLoader;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontLoaderA implements FontLoader {

	private AssetManager mAssetManager;

	public FontLoaderA(AssetManager assetManager) {
		mAssetManager = assetManager;
	}

	public Font loadFont(Object fontType, String name) throws ResourceParseException {
		// TODO fontType should be a class object instead of inputstream
		Typeface typeface = Typeface.createFromAsset(mAssetManager, BaseObjectHelper.getPath(fontType, name));
		return new FontA(name, typeface, Math.round(PIXELS_PER_POINT));
	}

}
