package com.example.wxhelper.share;

/**
 * @author Lucius
 * @version V1.0
 * @Title  分享内容
 * @Package com.hyd.huayingbao.tools.share
 * @Description
 * @date 2016/7/12 14:23
 */
public class ShareInfo {
    private static final long serialVersionUID = 1L;

    private String shareContent;
    private String shareUrl;
    private String shareTitle;
    private String shareImage;

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage;
    }
}