
//微信分享2015前（微信6.0以前版本）
$(document).bind('WeixinJSBridgeReady', function onBridgeReady() {	
        // 发送给好友 
        WeixinJSBridge.on('menu:share:appmessage', function(argv) {
            WeixinJSBridge.invoke('sendAppMessage', {
                "img_url": window.shareData.imgUrl,
                "link": window.shareData.timeLineLink,
                "desc": window.shareData.tContent,
                "title": window.shareData.tTitle
            }, function(res) {
				//分享后回调
                //document.location.href = mebtnopenurl;
            });
        });
        // 分享到朋友圈 
        WeixinJSBridge.on('menu:share:timeline', function (argv) {
            WeixinJSBridge.invoke('shareTimeline', {
                "img_url": window.shareData.imgUrl,
                //"img_width": "120",
                //"img_height": "120", 
                "link": window.shareData.timeLineLink,
                "desc": window.shareData.tContent,
                "title": window.shareData.tContent //window.shareData.tTitle
            }, function (res) {
                //分享后回调
            });
        });
        // 分享到微博 
        WeixinJSBridge.on('menu:share:weibo', function (argv) {
            WeixinJSBridge.invoke('shareWeibo', {
                "img_url": window.shareData.imgUrl,
                "content": window.shareData.tContent,
                "url": window.shareData.timeLineLink
            }, function (res) {
                //分享后回调
            });
        });

}, false);




