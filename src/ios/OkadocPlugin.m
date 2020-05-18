#import "OkadocPlugin.h"
#import <OkadocMeet/OkadocMeetConferenceOptions.h>
#import <OkadocMeet/OkadocMeetUserInfo.h>

@implementation OkadocPlugin

CDVPluginResult *pluginResult = nil;

- (void)call:(CDVInvokedUrlCommand *)command {
    NSString* url = [command.arguments objectAtIndex:0];
    NSString* name = [command.arguments objectAtIndex:1];
    NSString* avatar = [command.arguments objectAtIndex:2];
    NSString* email = [command.arguments objectAtIndex:3];

    commandBack = command;
    OkadocMeetUserInfo * _userInfo = [[OkadocMeetUserInfo alloc] init];

    if (name) {
        _userInfo.displayName = name;
    }

    if (avatar) {
        NSURL *url = [NSURL URLWithString:[avatar stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]]];
        _userInfo.avatar = url;
    }

    if (email) {
        _userInfo.email = email;
    }

    okadocMeetView = [[OkadocMeetView alloc] initWithFrame:self.viewController.view.frame];
    okadocMeetView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    okadocMeetView.delegate = self;

    OkadocMeetConferenceOptions *options = [OkadocMeetConferenceOptions fromBuilder:^(OkadocMeetConferenceOptionsBuilder *builder) {
        builder.room = url;
        builder.userInfo = _userInfo;
        builder.subject = @"Telemedicine";
        // builder.audioOnly = YES;
    }];

    [okadocMeetView join:options];
    [self.viewController.view addSubview:okadocMeetView];
}

- (void)destroy:(CDVInvokedUrlCommand *)command {
    NSLog(@"OkadocMeet destroy");
    if(okadocMeetView){
        [okadocMeetView removeFromSuperview];
        okadocMeetView = nil;
    }
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"DESTROYED"];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

void _onOkadocMeetViewDelegateEvent(NSString *name, NSDictionary *data) {
    NSLog(
        @"[%s:%d] OkadocMeetViewDelegate %@ %@",
        __FILE__, __LINE__, name, data);

}

- (void)conferenceTerminated:(NSDictionary *)data {
    _onOkadocMeetViewDelegateEvent(@"CONFERENCE_TERMINATED", data);
    if (okadocMeetView){
        [okadocMeetView removeFromSuperview];
        okadocMeetView = nil;
    }
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"CONFERENCE_TERMINATED"];
    [pluginResult setKeepCallbackAsBool:YES];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:commandBack.callbackId];
}

- (void)conferenceJoined:(NSDictionary *)data {
    _onOkadocMeetViewDelegateEvent(@"CONFERENCE_JOINED", data);
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"CONFERENCE_JOINED"];
    [pluginResult setKeepCallbackAsBool:YES];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:commandBack.callbackId];
}

- (void)conferenceWillJoin:(NSDictionary *)data {
    _onOkadocMeetViewDelegateEvent(@"CONFERENCE_WILL_JOIN", data);
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"CONFERENCE_WILL_JOIN"];
    [pluginResult setKeepCallbackAsBool:YES];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:commandBack.callbackId];
}

@end
