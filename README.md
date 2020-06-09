# Okadoc Meet SDK

Instructions to use okadoc meet

### Outsystem Requirement

* Outsystem Service studio 11
* Minimum iOS Target Version **10.0**
* Minimum Android SDK Version **21**
* [Outsystems template plugin](https://www.outsystems.com/forge/component-overview/1676/template-plugin)


### Outsystem - Setup and Running

#### 1. Setup the Plugin/Forge
* You need create custom outsystems plugin for using this cordova plugin
Download the Template Plugin from the [Forge](https://www.outsystems.com/forge/component-overview/1676/template-plugin). After you’ve downloaded the plugin to your environment, open the Template_Plugin module, click the Module menu item, and then select Clone.

* Change the module configurations, like:
```Module Name```
```Module Client Actions```
```Module Extensibility configuration```
```Module Diagram```

* you must setup configuration match with the sample module in folder on this repo
```Outsystems/OkadocMeetPlugin.oml```

* click publish **(F5)**

#### 2. Open your project
* Open your project modules
* Add Extensibility Configurations, setup ```android-minSdkVersion``` to ***21***
* Open **Manage Depedencies (Ctrl + Q)** 
* Select and Apply this depedencies
```OkadocMeetPlugin``` 
```InAppBrowserPlugin``` (You can install this from Outsystems forge)
* Setup **InAppBrowserPlugin**
* On Client Action add **Javascript** form left widget panel for execute javascript code
* Add this example script to **Javascript Code**
```javascript
const url = "https://okadoc.com";
const target = "_blank";
const options = "location=yes";
const inAppBrowserRef = cordova.InAppBrowser.open(url, target, options);

function messageCallBack(params){
    const json = JSON.stringify(params.data) || "";
    $actions.OkadocMeetCall(json);
    inAppBrowserRef.hide();
}

inAppBrowserRef.addEventListener('message', messageCallBack);
```
* Click Done
* Publish and build your app
* You can check the example on this folder ```Outsystems/OkadocVideoConsultation.oml```
