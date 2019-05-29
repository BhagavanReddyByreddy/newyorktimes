package com.example.newyorktimes.interfaces;

import org.json.JSONObject;

public interface ServiceReponse {

  void onResponse(JSONObject responseObj,String serviceUrl);
}
