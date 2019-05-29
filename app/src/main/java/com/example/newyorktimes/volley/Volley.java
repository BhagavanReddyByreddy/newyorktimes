package com.example.newyorktimes.volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.newyorktimes.application.MyApplication;
import com.example.newyorktimes.constants.Globals;
import com.example.newyorktimes.interfaces.ServiceReponse;
import com.example.newyorktimes.utilities.NetworkUtil;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Volley {

    private Context context;
    private ServiceReponse callback;
    private ProgressDialog pDialog;

    public Volley(Context context, ServiceReponse callback) {
        this.context = context;
        this.callback = callback;
    }

    public void serviceRequest(final String serviceMethodInUrl, JSONObject requestJsonObj, int methodName){
        if(NetworkUtil.getConnectivityStatusBoolean(context)) {

            StringBuffer finalServiceUrl = new StringBuffer(Globals.BASE_URL);
            finalServiceUrl.append(serviceMethodInUrl);
            finalServiceUrl.append(Globals.API_KEY);


            final ProgressDialog pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();


            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(methodName,
                    finalServiceUrl.toString(), requestJsonObj,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            if(pDialog.isShowing()){
                                pDialog.dismiss();
                            }

                            Log.e("response::",response.toString());

                            try {
                                if(response.getString("status").equalsIgnoreCase("OK")){

                                    callback.onResponse(response,serviceMethodInUrl);

                                }else{
                                    Toast.makeText(context,"Invalid Response,Please try gain after some time",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    if(pDialog.isShowing()){
                        pDialog.dismiss();
                    }

                    VolleyLog.d("onErrorResponse", "Error: " + error.getMessage());

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Log.e("Volley_Error::", "network_time_out");
                    } else if (error instanceof AuthFailureError) {
                        Log.e("Volley_Error::", "AuthFailureError");
                    } else if (error instanceof ServerError) {
                        Log.e("Volley_Error::", "ServerError");
                    } else if (error instanceof NetworkError) {
                        Log.e("Volley_Error::", "NetworkError");
                    } else if (error instanceof ParseError) {
                        Log.e("Volley_Error::", "ParseError");

                    }

                }
            });

            MyApplication.getInstance().addToRequestQueue(jsonObjReq, "booksJsonRequest");

        }else{
            Toast.makeText(context,"Please Check Your Network Connection",Toast.LENGTH_LONG).show();
        }


    }
}
