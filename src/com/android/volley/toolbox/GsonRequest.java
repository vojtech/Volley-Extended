package com.android.volley.toolbox;

/*
 * Copyright 2013 Ognyan Bankov
 * 
 * Source from: https://github.com/ogrebgr/android_volley_examples
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonRequest<T> extends Request<T> {

	private static final String PROTOCOL_CHARSET = "utf-8";

	/** Content type for request. */
	private static final String PROTOCOL_CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";
	

    private String mBody = null;

	private Map<String, String> mParams;

	private final Gson mGson;
	private final Class<T> mClazz;
	private final Listener<T> mListener;
	private final boolean isArray;
	private final String array_key;


	/**
	 * Method for JSONObject
	 * @param method
	 * @param url
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 */
	public GsonRequest(int method,
			String url,
			Class<T> clazz,
			Listener<T> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.isArray = false;
		this.array_key = null;
		mGson = new Gson();
	}


	/**
	 * Method for JSONObject
	 * @param method
	 * @param url
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 * @param gson
	 */
	public GsonRequest(int method,
			String url,
			Class<T> clazz,
			Listener<T> listener,
			ErrorListener errorListener,
			Gson gson) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.isArray = false;
		this.array_key = null;
		mGson = gson;
	}
	
	/**
	 * Method for JSONObject with body
	 * @param method
	 * @param url
	 * @param body
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 * @param gson
	 */
	public GsonRequest(int method,
			String url,
			String body,
			Class<T> clazz,
			Listener<T> listener,
			ErrorListener errorListener,
			Gson gson) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.isArray = false;
		this.array_key = null;
		this.mBody = body;
		mGson = gson;
	}
	
	/**
	 * Method for JSONObject with body
	 * @param method
	 * @param url
	 * @param body
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 */
	public GsonRequest(int method,
			String url,
			String body,
			Class<T> clazz,
			Listener<T> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.isArray = false;
		this.array_key = null;
		this.mBody = body;
		this.mGson = new Gson();
	}
	
	/**
	 * Method for JSONObject with body and params
	 * @param method
	 * @param url
	 * @param params
	 * @param body
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 * @param gson
	 */
	public GsonRequest(int method,
			String url,
			Map<String, String> params,
			String body,
			Class<T> clazz,
			Listener<T> listener,
			ErrorListener errorListener,
			Gson gson) {
		super(Method.GET, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.isArray = false;
		this.array_key = null;
		this.mBody = body;
		this.mParams = params;
		mGson = gson;
	}
	
	/**
	 * Method for JSONObject with body and params
	 * @param method
	 * @param url
	 * @param params
	 * @param body
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 */
	public GsonRequest(int method,
			String url,
			Map<String, String> params,
			String body,
			Class<T> clazz,
			Listener<T> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.isArray = false;
		this.array_key = null;
		this.mBody = body;
		this.mParams = params;
		mGson = new Gson();
	}
	
	/**
	 * Method for JSONObject with params
	 * @param method
	 * @param url
	 * @param params
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 * @param gson
	 */
	public GsonRequest(int method,
			String url,
			Map<String, String> params,
			Class<T> clazz,
			Listener<T> listener,
			ErrorListener errorListener,
			Gson gson) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.isArray = false;
		this.array_key = null;
		this.mParams = params;
		mGson = gson;
	}
	
	/**
	 * Method for JSONObject with params
	 * @param method
	 * @param url
	 * @param params
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 */
	public GsonRequest(int method,
			String url,
			Map<String, String> params,
			Class<T> clazz,
			Listener<T> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.isArray = false;
		this.array_key = null;
		this.mParams = params;
		mGson = new Gson();
	}
	

	/**
	 * Method for JSONArray
	 * @param method
	 * @param url
	 * @param clazz
	 * @param arrayKey
	 * @param listener
	 * @param errorListener
	 * @param gson
	 */
	public GsonRequest(int method,
			String url,
			Class<T> clazz,
			String arrayKey,
			Listener<T> listener,
			ErrorListener errorListener,
			Gson gson) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.isArray = true;
		this.array_key = null;
		mGson = gson;
	}
		

	/**
	 * MEthod for JSONArray
	 * @param method
	 * @param url
	 * @param clazz
	 * @param arrayKey
	 * @param listener
	 * @param errorListener
	 */
	public GsonRequest(int method,
			String url,
			Class<T> clazz,
			String arrayKey,
			Listener<T> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.isArray = true;
		this.array_key = arrayKey;
		mGson = new Gson();
	}


	@Override
	public Map<String, String> getParams() {
		return mParams;
	}
	
	/* (non-Javadoc)
	 * @see com.android.volley.Request#getBody()
	 */
	@Override
    public byte[] getBody() throws AuthFailureError {
		try {
            return mBody == null ? null : mBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
            		mBody, PROTOCOL_CHARSET);
            return null;
        }
    }
	
	@Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }


	@Override
	protected void deliverResponse(T response, Object tag) {
		mListener.onResponse(response, tag);
	}


	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			getLastModified(response.headers);
			if(isArray) {
				try {
					JSONObject object = new JSONObject();
					object.put(array_key, new JSONArray(json));
					return Response.success(mGson.fromJson(object.toString(), mClazz),
							HttpHeaderParser.parseCacheHeaders(response));
				} catch(JSONException e) {
					Log.e("", e.toString());
					return Response.error(new ParseError(e));
				}
			} else {
				return Response.success(mGson.fromJson(json, mClazz),
						HttpHeaderParser.parseCacheHeaders(response));
			}
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}


	/* (non-Javadoc)
	 * @see com.android.volley.Request#getHeaders()
	 */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders();

		if (headers == null
				|| headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
		}


		return headers;
	}

	/**
	 * function to get last modified params from header
	 * @param headers
	 * @return
	 */
	private String getLastModified(Map<String, String> headers) {
		if(headers != null) {
			if(headers.containsKey("Last-Modified")) {
				return headers.get("Last-Modified");
			}
		}
		return null;
	}

	@Override
	public void progressResponse(long[] response) {

	}


}