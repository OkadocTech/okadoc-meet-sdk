// Empty constructor
function Plugin() {}

const get = (obj, path, defaultValue = undefined) => {
    const travel = regexp =>
      String.prototype.split
        .call(path, regexp)
        .filter(Boolean)
        .reduce((res, key) => (res !== null && res !== undefined ? res[key] : res), obj);
    const result = travel(/[,[\]]+?/) || travel(/[,[\].]+?/);
    return result === undefined || result === obj ? defaultValue : result;
};

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
Plugin.prototype.call = function(data, success, error) {
    if ((data || '').toLowerCase().includes('video_url')) {        
        const params = JSON.parse(data || '{}') || {};
        const url = get(params, 'meetUrl', '');
        const name = get(params, 'displayName', '');
        const avatar = get(params, 'avatar', '');
        const email = get(params, 'email', '');
        cordova.exec(success, error, "OkadocPlugin", "call", [url, name, avatar, email]);
    }
}

// Installation constructor that binds OkadocPlugin to window
Plugin.install = function() {
    if (!window.plugins) {
        window.plugins = {};
    }
    window.plugins.OkadocMeetPlugin = new Plugin();
    return window.plugins.OkadocMeetPlugin;
};

cordova.addConstructor(Plugin.install);
