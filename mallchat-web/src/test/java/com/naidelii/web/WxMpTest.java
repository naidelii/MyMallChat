package com.naidelii.web;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WxMpTest {

    @Autowired
    private WxMpService wxMpService;

    @Test
    public void getAQRCode() throws WxErrorException {
        WxMpQrcodeService qrcodeService = wxMpService.getQrcodeService();
        // 场景值
        String code = UUID.randomUUID().toString();
        // 过期时间（单位s）
        int expireTime = 10 * 60;
        WxMpQrCodeTicket wxMpQrCodeTicket = qrcodeService.qrCodeCreateTmpTicket(code, expireTime);
        String url = wxMpQrCodeTicket.getUrl();
        System.out.println(url);
    }

    /**
     * 新增草稿箱
     *
     * @throws WxErrorException e
     */
    @Test
    public void draft() throws WxErrorException {
        // media_id：WBuJmGjhUnnz1TjDWqPBUuol5LFr4P0P0m5piQbGPGaVzPRpwoS8h-8vD-o93ouT
        String accessToken = wxMpService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/draft/add?access_token=" + accessToken;
        JSONObject data = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.set("title", "如何有效利用番茄工作法提高工作效率");
        obj.set("content", "正文测试群发");
        obj.set("thumb_media_id", "WBuJmGjhUnnz1TjDWqPBUqqEMblwYRzjXIQzcUixq1xO-K9Iowf1RusqS421N_FO");
        array.add(obj);
        data.set("articles", array);
        String result = HttpUtil.post(url, data.toString());
        JSONObject entries = JSONUtil.parseObj(result);
        System.out.println(entries);
    }


    /**
     * 发布文章
     *
     * @throws WxErrorException e
     */
    @Test
    public void postArticle() throws WxErrorException {
        String accessToken = wxMpService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/freepublish/submit?access_token=" + accessToken;
        JSONObject data = new JSONObject();
        // WBuJmGjhUnnz1TjDWqPBUnOwiaAX9sv3cduDU4XfsZGtEnIvNY8zaSGqGT4FU0qC
        // WBuJmGjhUnnz1TjDWqPBUnOwiaAX9sv3cduDU4XfsZGtEnIvNY8zaSGqGT4FU0qC
        data.set("media_id", "WBuJmGjhUnnz1TjDWqPBUnOwiaAX9sv3cduDU4XfsZGtEnIvNY8zaSGqGT4FU0qC");
        String result = HttpUtil.post(url, data.toString());
        JSONObject entries = JSONUtil.parseObj(result);
        System.out.println(entries);
    }

    /**
     * 发布文章（群发）
     *
     * @throws WxErrorException e
     */
    @Test
    public void bulkSending() throws WxErrorException {
        String accessToken = wxMpService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + accessToken;
        JSONObject data = new JSONObject();
        // filter
        JSONObject filter = new JSONObject();
        filter.set("is_to_all", true);
//        filter.set("tag_id", 2);
        // mpNews
        JSONObject mpNews = new JSONObject();
        mpNews.set("media_id", "WBuJmGjhUnnz1TjDWqPBUnuuI89ZmHUTeYLJBE5I6eTyXiSaOwX89k1BIHUTGDKx");
        data.set("filter", filter);
        data.set("mpnews", mpNews);
        data.set("msgtype", "mpnews");
        data.set("send_ignore_reprint", 0);
        String result = HttpUtil.post(url, data.toString());
        JSONObject entries = JSONUtil.parseObj(result);
        System.out.println(entries);
    }


    /**
     * 获取素材列表
     *
     * @throws WxErrorException e
     */
    @Test
    public void getMaterialList() throws WxErrorException {
        String accessToken = wxMpService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + accessToken;
        JSONObject data = new JSONObject();
        data.set("type", "image");
        data.set("offset", 0);
        data.set("count", 20);
        String result = HttpUtil.post(url, data.toString());
        JSONObject entries = JSONUtil.parseObj(result);
        System.out.println(entries);
    }

    /**
     * 获取草稿箱列表
     *
     * @throws WxErrorException e
     */
    @Test
    public void getDraftBoxList() throws WxErrorException {
        String accessToken = wxMpService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/draft/batchget?access_token=" + accessToken;
        JSONObject data = new JSONObject();
        data.set("no_content", 0);
        data.set("offset", 0);
        data.set("count", 20);
        String result = HttpUtil.post(url, data.toString());
        JSONObject entries = JSONUtil.parseObj(result);
        System.out.println(entries);
    }
}
