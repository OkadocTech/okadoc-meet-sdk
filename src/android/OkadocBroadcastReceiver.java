package com.cordova.plugin.okadoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

public class OkadocBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "OkadocBroadcastReceiver";
    private CallbackContext callbackContext;
    public static final String ACTION_BARCODE_SERVICE_BROADCAST = "action_barcode_broadcast";
    public static final String KEY_BARCODE_STR = "eventName";
    
    public OkadocBroadcastReceiver(CallbackContext callbackContext) {
        this.callbackContext = callbackContext;
    }

    public void onReceive(Context ctx, Intent intent) {
        if (intent.getAction().equals("onConferenceWillJoin")) {
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "CONFERENCE_WILL_JOIN");
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
        }

        if (intent.getAction().equals("onConferenceJoined")) {
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "CONFERENCE_JOINED");
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
        }

        if (intent.getAction().equals("onConferenceTerminated")) {
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "CONFERENCE_TERMINATED");
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
            
        }
    }
}