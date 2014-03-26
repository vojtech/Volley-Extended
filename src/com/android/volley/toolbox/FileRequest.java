/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.android.volley.toolbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

/**
 * A canned request for getting an file at a given URL and calling
 * back with a File.
 * 
 * like BitmapDowlload one
 * 
 * @author extralam@github 
 */
public class FileRequest extends Request<File> {
    
	/** Socket timeout in milliseconds for image requests */
    private static final int FILE_TIMEOUT_MS = 6000;
    /** Default number of retries for image requests */
    private static final int FILE_MAX_RETRIES = 3;
    /** Default backoff multiplier for image requests */
    private static final float FILE_BACKOFF_MULT = 2f;

    private final Response.Listener<File> mListener;

    /** Decoding lock so that we don't decode more than one image at a time (to avoid OOM's) */
    private static final Object sDecodeLock = new Object();
    
    private File mFile = null;

    /**
     * 
     * @param url
     * @param file	provide a File in main Activity
     * @param listener
     * @param errorListener
     */
    public FileRequest(int method ,String url, File file, Response.Listener<File> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        setRetryPolicy(
                new DefaultRetryPolicy(FILE_TIMEOUT_MS, FILE_MAX_RETRIES, FILE_BACKOFF_MULT));
        mListener = listener;
        mFile = file;
    }
    
    public FileRequest(String url, File file, Response.Listener<File> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        setRetryPolicy(
                new DefaultRetryPolicy(FILE_TIMEOUT_MS, FILE_MAX_RETRIES, FILE_BACKOFF_MULT));
        mListener = listener;
        mFile = file;
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }    

    @Override
    protected Response<File> parseNetworkResponse(NetworkResponse response) {
        // Serialize all decode on a global lock to reduce concurrent heap usage.
        synchronized (sDecodeLock) {
            try {
                return doParse(response);
            } catch (OutOfMemoryError e) {
                VolleyLog.e("Caught OOM for %d byte image, url=%s", response.data.length, getUrl());
                return Response.error(new ParseError(e));
            }
        }
    }

    /**
     * The real guts of parseNetworkResponse. Broken out for readability.
     */
    private Response<File> doParse(NetworkResponse response) {
        byte[] bytes = response.data;
        //convert array of bytes into file
        FileOutputStream stream;
		try {
			stream = new FileOutputStream(mFile);
			stream.write(bytes);
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        if (mFile == null) {
            return Response.error(new ParseError(response));
        } else {
            return Response.success(mFile, HttpHeaderParser.parseCacheHeaders(response));
        }
    }

    @Override
    protected void deliverResponse(File response, Object customTag) {
        mListener.onResponse(response, customTag);
    }

	@Override
	public void progressResponse(long[] response) {
	}

}
