package com.android.volley.toolbox;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * Helper Class for Volley Bitmap ImageCache, since it need LruCache
 * Memory Cache Class
 * 
 * This Class used for HoneyComb or above,
 * if you want do something the same, 
 * LruCache can use android.support.v4.util.LruCache
 * 
 * @author extralm@github
 *
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class VolleyBitmapLruCache extends LruCache<String, Bitmap> implements ImageCache {

	public static int getDefaultLruCacheSize() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;
		return cacheSize;
	}

	public VolleyBitmapLruCache(Context context) {
		this(context , getDefaultLruCacheSize());
	}

	public VolleyBitmapLruCache(Context context , int sizeInKiloBytes) {
		super(sizeInKiloBytes);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight() / 1024;
	}

	@Override
	public Bitmap getBitmap(String key) {
		return this.get(key);
	}

	@Override
	public void putBitmap(String key, Bitmap bitmap) {
		put(key, bitmap);
	}

	public void evictCache() {
		VolleyLog.e("evict all cache");
		this.evictAll();
	}

}