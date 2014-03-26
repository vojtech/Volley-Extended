package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.google.gson.JsonSyntaxException;

/**
 * @author Vojtech Hrdina
 *
 */
public class UploadStringRequest extends Request<String> {
	

    private static final String PROTOCOL_CHARSET = "utf-8";

	private final Listener<String> mListener;
    private final String mBody;
    private int requestId;

	private Map<String, String> mParams;

	public UploadStringRequest(String url, String param2, Listener<String> listener, ErrorListener errorListener) {
		super(Method.POST, url, errorListener);
		mListener = listener;
		mBody = param2;

	}
	
	public UploadStringRequest(String url, Map<String, String> postParams, String param2, Listener<String> listener, ErrorListener errorListener) {
		super(Method.POST, url, errorListener);
		mListener = listener;
		mBody = param2;
		mParams = postParams;

	}

	@Override
	public Map<String, String> getParams() {
		return mParams;
	}
	
	

	@Override
	protected void deliverResponse(String response, Object tag) {
		mListener.onResponse(response, tag);

	}

	/* (non-Javadoc)
	 * @see com.android.volley.Request#getBody()
	 */
	@Override
    public byte[] getBody() {
        try {
            return mBody == null ? null : mBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
            		mBody, PROTOCOL_CHARSET);
            return null;
        }
    }

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(json,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	/**
	 * @return the requestId
	 */
	public int getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	@Override
	public void progressResponse(long[] response) {
		
	}
	
	
	
	
}