package com.cordova.plugin.okadoc;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PermissionHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.Context;
import java.util.Map;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.Bundle;

import org.okadoc.meet.sdk.OkadocMeetView;
import org.okadoc.meet.sdk.OkadocMeetViewListener;
import org.okadoc.meet.sdk.OkadocMeet;
import org.okadoc.meet.sdk.OkadocMeetActivity;
import org.okadoc.meet.sdk.OkadocMeetConferenceOptions;
import org.okadoc.meet.sdk.OkadocMeetUserInfo;
import org.okadoc.meet.sdk.OkadocMeetActivityDelegate;
import org.okadoc.meet.sdk.OkadocMeetActivityInterface;
import android.view.View;

import org.apache.cordova.CordovaWebView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.facebook.react.modules.core.PermissionListener;

public class OkadocPlugin extends CordovaPlugin {
  private OkadocMeetView view;
  private static final String TAG = "cordova-plugin-okadoc";

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("call")) {
      String url = args.getString(0);
      String name = args.getString(1);
      String avatar = args.getString(2);
      String email = args.getString(3);
      this.call(url, name, avatar, email, callbackContext);
      return true;
    } else if (action.equals("destroy")) {
      this.destroy(callbackContext);
      return true;
    }
    return false;
  }

  private void call(
      final String url,
      final String displayName,
      final String avatar,
      final String email,
      final CallbackContext callbackContext
    ) {
    Log.e(TAG, "start called : "+url);
    
    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {

        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("onConferenceWillJoin");
            filter.addAction("onConferenceJoined");
            filter.addAction("onConferenceTerminated");
            OkadocBroadcastReceiver receiver = new OkadocBroadcastReceiver(callbackContext);                    
            getView().getContext().registerReceiver(receiver, filter);

            Intent intent = new Intent(cordova.getActivity(), OkadocActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("displayName", displayName);
            intent.putExtra("avatar", avatar);
            intent.putExtra("email", email);
            cordova.getActivity().startActivity(intent);   

        } catch (java.lang.Exception e) {
            callbackContext.error(e.getMessage());
        }
      }
    });
  }

  private void destroy(final CallbackContext callbackContext) {
    cordova.getActivity().runOnUiThread(new Runnable() {
      public void run() {
        //view.dispose();
        //view = null;
        //OkadocMeetView.onHostDestroy(cordova.getActivity());
        OkadocMeetActivityDelegate.onHostDestroy(cordova.getActivity());
        cordova.getActivity().setContentView(getView());
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "DESTROYED");
        pluginResult.setKeepCallback(true);
        callbackContext.sendPluginResult(pluginResult);
      }
    });
  }

  private View getView() {
    try {
      return (View) webView.getClass().getMethod("getView").invoke(webView);
    } catch (Exception e) {
      return (View) webView;
    }
  }
}
