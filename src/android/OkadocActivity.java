package com.cordova.plugin.okadoc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.Window;
import android.view.WindowManager;

import com.facebook.react.bridge.UiThreadUtil;
//import com.facebook.react.modules.core.PermissionListener;

import org.okadoc.meet.sdk.OkadocMeetView;
import org.okadoc.meet.sdk.OkadocMeetActivity;
import org.okadoc.meet.sdk.OkadocMeetViewListener;
import org.okadoc.meet.sdk.OkadocMeetConferenceOptions;
import org.okadoc.meet.sdk.OkadocMeetUserInfo;
import org.okadoc.meet.sdk.OkadocMeetActivityDelegate;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

public class OkadocActivity extends OkadocMeetActivity {
    public OkadocMeetView view;
    public OkadocMeetUserInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);		
        view = new OkadocMeetView(this);
        Log.d("Listener", "entering");
               
        view.setListener(new OkadocMeetViewListener() {
            PluginResult pluginResult;
            
            private void on(String name, Map<String, Object> data) {
                UiThreadUtil.assertOnUiThread();

                // Log with the tag "ReactNative" in order to have the log
                // visible in react-native log-android as well.
                Log.d( "Listener", OkadocMeetViewListener.class.getSimpleName() + " " + name + " " + data);
                Intent intent = new Intent(name);
                intent.putExtra("eventName", name);
                sendBroadcast(intent);
            }


            @Override
            public void onConferenceJoined(Map<String, Object> data) {
                on("onConferenceJoined", data);                
            }

            @Override
            public void onConferenceWillJoin(Map<String, Object> data) {
                on("onConferenceWillJoin", data);
            }

            @Override
            public void onConferenceTerminated(Map<String, Object> data) {
                on("onConferenceTerminated", data); 
                view.dispose();
                view = null;
                finishAndRemoveTask();              
            }
        });

        String url = getIntent().getStringExtra("url");
        String displayName = getIntent().getStringExtra("displayName");
        String avatar = getIntent().getStringExtra("avatar");
        String email = getIntent().getStringExtra("email");


        Bundle userInfoBundle = new Bundle();

        if (displayName != null) {
            userInfoBundle.putString("displayName", displayName);
        }

        if (email != null) {
            userInfoBundle.putString("email", email);
        }

        if (avatar != null) {
            userInfoBundle.putString("avatarURL", avatar.toString());
        }

        OkadocMeetUserInfo userInfo = new OkadocMeetUserInfo(userInfoBundle);

        OkadocMeetConferenceOptions options = new OkadocMeetConferenceOptions.Builder()          
          .setRoom(url)
          .setUserInfo(userInfo)
          .setSubject("Telemedicine")
          .setFeatureFlag("chat.enabled", true)
          .setFeatureFlag("invite.enabled", false)          
          .setFeatureFlag("calendar.enabled", false)
          .setWelcomePageEnabled(false)
          .build();

        view.join(options);
        setContentView(view);
    }

    /**
     * The query to perform through {@link AddPeopleController} when the
     * {@code InviteButton} is tapped in order to exercise the public API of the
     * feature invite. If {@code null}, the {@code InviteButton} will not be
     * rendered.
     */
    private static final String ADD_PEOPLE_CONTROLLER_QUERY = null;
    
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        OkadocMeetActivityDelegate.onActivityResult(this, requestCode, resultCode, data);
    }
    
     @Override
    public void onBackPressed() {
        OkadocMeetActivityDelegate.onBackPressed();
    }
    
	@Override
    public void onDestroy() {
        super.onDestroy();
		
		if(view != null){
			view.dispose();
			view = null;
		}
        
        OkadocMeetActivityDelegate.onHostDestroy(this);
		finishAndRemoveTask();
    }

    @Override
    public void onNewIntent(Intent intent) {
        OkadocMeetActivityDelegate.onNewIntent(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            final int requestCode,
            final String[] permissions,
            final int[] grantResults) {
        OkadocMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();

        OkadocMeetActivityDelegate.onHostResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // OkadocMeetActivityDelegate.onHostPause(this);
		if(view != null){
			view.dispose();
			view = null;
		}
        
        OkadocMeetActivityDelegate.onHostDestroy(this);
		finishAndRemoveTask();
    }
}

