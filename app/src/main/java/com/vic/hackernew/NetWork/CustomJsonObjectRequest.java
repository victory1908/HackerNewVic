package com.vic.hackernew.NetWork;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victory1908 on 29-Dec-15.
 */
public class CustomJsonObjectRequest extends JsonObjectRequest {
    private Priority mPriority;

    public CustomJsonObjectRequest(int method, String url, JSONObject jsonRequest,
                                   Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        //this.setShouldCache(true);
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        // here you can write a custom retry policy
        return super.getRetryPolicy();
    }

    @Override
    public Priority getPriority() {
        return mPriority == null ? Priority.NORMAL : mPriority;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }
//
//    @Override
//    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//        try {
//            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
//            if (cacheEntry == null) {
//                cacheEntry = new Cache.Entry();
//            }
//            final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
//            final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
//            long now = System.currentTimeMillis();
//            final long softExpire = now + cacheHitButRefreshed;
//            final long ttl = now + cacheExpired;
//            cacheEntry.data = response.data;
//            cacheEntry.softTtl = softExpire;
//            cacheEntry.ttl = ttl;
//            String headerValue;
//            headerValue = response.headers.get("Date");
//            if (headerValue != null) {
//                cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
//            }
//            headerValue = response.headers.get("Last-Modified");
//            if (headerValue != null) {
//                cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
//            }
//            cacheEntry.responseHeaders = response.headers;
//            final String jsonString = new String(response.data,
//                    HttpHeaderParser.parseCharset(response.headers));
//            return Response.success(new JSONObject(jsonString), cacheEntry);
//        } catch (UnsupportedEncodingException e) {
//            return Response.error(new ParseError(e));
//        } catch (JSONException e) {
//            return Response.error(new ParseError(e));
//        }
//    }
//
//    @Override
//    protected void deliverResponse(JSONObject response) {
//        super.deliverResponse(response);
//    }
//
//    @Override
//    public void deliverError(VolleyError error) {
//        super.deliverError(error);
//    }
//
//    @Override
//    protected VolleyError parseNetworkError(VolleyError volleyError) {
//        return super.parseNetworkError(volleyError);
//    }

}
