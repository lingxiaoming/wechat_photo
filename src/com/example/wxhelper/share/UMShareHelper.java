package com.example.wxhelper.share;

import android.app.Activity;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucius
 * @version V1.0
 * @Title 分享通用类
 * @Description
 * @date 2016/7/12 9:22
 */
public class UMShareHelper {

    private static final String WX_APPID = "wx5c414c10965d2893"; // 本地测试
    //    private static final String WX_APPID = "wx5c414c10965d2893";// 正式
    private static final String WX_APPKEY = "e2513588556c7b690a820acc2851e30e";// 本地测试
//    private static final String WX_APPKEY = "49a6858fedb184ad01c07fcfd6a140a4";// 正式

    public static final String WEIXIN = "微信";
    public static final String WEIXIN_CIRCLE = "微信朋友圈";

    public static final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private Activity mActivity;
    private List<UMSsoHandler> umSsoHandlers;
    public String shareContent = "";
    public String shareUrl = "";
    public String shareTitle = "";
    public String shareImage = "";

    public UMShareHelper(Activity activity, ShareInfo info) {
        mActivity = activity;
        umSsoHandlers = new ArrayList<UMSsoHandler>();
        if (info != null) {
            shareContent = info.getShareContent();
            shareUrl = info.getShareUrl();
            shareTitle = info.getShareTitle();
            shareImage = info.getShareImage();
        }
        initSocialSDK();
        initShare();
    }

    /**
     * 初始化SDK，添加一些平台
     */
    private void initSocialSDK() {

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(mActivity, WX_APPID, WX_APPKEY);
        wxHandler.addToSocialSDK();
        wxHandler.showCompressToast(false);
        umSsoHandlers.add(wxHandler);

        // 添加微信朋友圈平台
        UMWXHandler wxCircleHandler = new UMWXHandler(mActivity, WX_APPID,
                WX_APPKEY);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        wxCircleHandler.showCompressToast(false);
        umSsoHandlers.add(wxCircleHandler);

    }

    /**
     * 初始化分享平台内容
     */
    private void initShare() {
        // 设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(shareContent);
        weixinContent.setTitle(shareTitle);
        weixinContent.setTargetUrl(shareUrl);
        weixinContent.setShareImage(new UMImage(mActivity,
                shareImage));
        mController.setShareMedia(weixinContent);

        // 设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(shareContent);
        circleMedia.setTitle(shareTitle);
        circleMedia.setShareImage(new UMImage(mActivity,
                shareImage));
        circleMedia.setTargetUrl(shareUrl);
        mController.setShareMedia(circleMedia);

    }

    public void share(String type, SnsPostListener mShareListener) {
        if (WEIXIN.equalsIgnoreCase(type)) {
            shareToMedia(SHARE_MEDIA.WEIXIN, mShareListener);
        } else if (WEIXIN_CIRCLE.equalsIgnoreCase(type)) {
            shareToMedia(SHARE_MEDIA.WEIXIN_CIRCLE, mShareListener);
        }
    }

    private void shareToMedia(SHARE_MEDIA share_MEDIA, SnsPostListener listener) {
        mController.getConfig().closeToast();
        mController.directShare(mActivity, share_MEDIA, listener);
    }
}