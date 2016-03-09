<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="mpjsconfig.aspx.cs" Inherits="PortalWeb.mw.wap.mpjsconfig" %>
//var script = document.createElement("script");
//script.src = "http://res.wx.qq.com/open/js/jweixin-1.0.0.js";
//document.body.appendChild(script);
//<%=jsapiTicket %>
//<%=url %>
document.writeln(unescape("%3Cscript src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js' type='text/javascript'%3E%3C/script%3E"));
wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: '<%=appId%>', // 必填，公众号的唯一标识
    timestamp:'<%=timestamp%>', // 必填，生成签名的时间戳
    nonceStr: '<%=nonceStr%>', // 必填，生成签名的随机串
    signature: '<%=signature%>',// 必填，签名，见附录1
    jsApiList: ['checkJsApi',
        'onMenuShareTimeline',
        'onMenuShareAppMessage',
        'onMenuShareQQ',
        'onMenuShareWeibo'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});
    //微信6.0以后版本
    wx.ready(function () {
        //在此输入各种API
        //分享到朋友圈
        wx.onMenuShareTimeline({
            title: window.shareData.tContent, // 分享标题
            link: window.shareData.timeLineLink, // 分享链接
            imgUrl: window.shareData.imgUrl, // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //分享给朋友
        wx.onMenuShareAppMessage({
            title: window.shareData.tTitle, // 分享标题
            desc: window.shareData.tContent, // 分享描述
            link: window.shareData.timeLineLink, // 分享链接
            imgUrl: window.shareData.imgUrl, // 分享图标
            type: '', // 分享类型,music、video或link，不填默认为link
            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //QQ
        wx.onMenuShareQQ({
            title: window.shareData.tTitle, // 分享标题
            desc: window.shareData.tContent, // 分享描述
            link: window.shareData.timeLineLink, // 分享链接
            imgUrl: window.shareData.imgUrl,// 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        //QQ微博
        wx.onMenuShareWeibo({
            title: window.shareData.tTitle, // 分享标题
            desc: window.shareData.tContent, // 分享描述
            link: window.shareData.timeLineLink, // 分享链接
            imgUrl: window.shareData.imgUrl, // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，
        //所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    });
    wx.error(function (res) {
        //alert(res);
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    });
