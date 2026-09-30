(function() {
    var orig = MediaSource.isTypeSupported.bind(MediaSource);
    MediaSource.isTypeSupported = function(t) { return /vp8|vp09?|av01/i.test(t) ? false : orig(t); };
})();
