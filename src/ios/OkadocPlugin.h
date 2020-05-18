#import <Cordova/CDVPlugin.h>
#import <OkadocMeet/OkadocMeetViewDelegate.h>
#import <OkadocMeet/OkadocMeetView.h>

@interface OkadocPlugin : CDVPlugin<OkadocMeetViewDelegate> {
	OkadocMeetView* okadocMeetView;
	CDVInvokedUrlCommand* commandBack;
}

- (void)call:(CDVInvokedUrlCommand *)command;
- (void)destroy:(CDVInvokedUrlCommand *)command;

@end