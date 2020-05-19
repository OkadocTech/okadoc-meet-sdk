# Okadoc Meet SDK

Instructions to use okadoc meet

### Outsystem Requirement

* Outsystem Service studio 11
* Minimum iOS Target Version **10.0**
* Minimum Android SDK Version **21**

### Outsystem - Setup and Running

#### 1. Setup the Plugin/Forge
* You need download the Plugin/Forge in folder on this repo
```Outsystems/OkadocMeetPlugin.oml```
* open with
```Outsystem Service Studio 11```
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
* You can check the example on this folder ```Outsystems/OkadocDemo.oml```
