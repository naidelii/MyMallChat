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
        obj.set("title", "test5");
//        obj.set("content", "<p style=\"text-indent: 2em;\">近日，重庆市荣昌区民营经济协会及协会党支部开展以“实干担当促发展”为主题的党建活动，并与铜鼓镇商会开展“万企兴万村”助力乡村振兴交流座谈会。</p>");
//        obj.set("content", "<p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; font-size: 16px;\">近日，重庆市荣昌区民营经济协会及协会党支部开展以“实干担当促发展”为主题的党建活动，并与铜鼓镇商会开展“万企兴万村”助力乡村振兴交流座谈会。</p><p style=\"margin-top: 0px; margin-bottom: 24px; text-wrap: wrap; padding: 0px; outline: 0px; max-width: 100%; clear: both; min-height: 1em; color: rgba(0, 0, 0, 0.9); font-family: system-ui, -apple-system, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei UI&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 17px; letter-spacing: 0.544px; background-color: rgb(255, 255, 255); text-align: center; box-sizing: border-box !important; overflow-wrap: break-word !important;\"><img class=\"rich_pages wxw-img js_insertlocalimg\" src=\"https://mmbiz.qpic.cn/sz_mmbiz_png/tpMNuFQbKQep3r3TspibD9Zuq8VibPQicxibwBib7a0AS41FNy0Sc8ArsPQBaGHoiaPhyfxLX4vVQVmQDMia9ULic0zbvg/640?wx_fmt=png&from=appmsg&wxfrom=13\" alt=\"图片\"/></p><p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; font-size: 16px;\">铜鼓镇副镇长杨媚，铜鼓镇商会常务副会长徐维丰、秘书长谢苹，荣昌区民营经济协会党支部书记施正云、会长陆龙海等近30人参加会议。</p><p style=\"margin-top: 0px; margin-bottom: 24px; text-wrap: wrap; padding: 0px; outline: 0px; max-width: 100%; clear: both; min-height: 1em; color: rgba(0, 0, 0, 0.9); font-family: system-ui, -apple-system, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei UI&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 17px; letter-spacing: 0.544px; background-color: rgb(255, 255, 255); text-align: center; box-sizing: border-box !important; overflow-wrap: break-word !important;\"><img class=\"rich_pages wxw-img js_insertlocalimg\" src=\"https://mmbiz.qpic.cn/sz_mmbiz_png/tpMNuFQbKQep3r3TspibD9Zuq8VibPQicxibZ0N6qFqdAetmpyZK4IKHqgH64V5ychfzMDOszn2EX20ccVuahbIU5A/640?wx_fmt=png&from=appmsg&wxfrom=13\" alt=\"图片\"/></p><p style=\"text-wrap: wrap;\"><br/></p><p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; font-size: 16px;\">杨媚介绍说，铜鼓镇打造了“两传统，三新兴”的发展模式，两传统是指蛋鸡养殖和花椒种植；三新兴是指千亩野菊花、千亩生态稻和千亩红高粱。目前两传统农业正在进行提档升级，三新兴产业在今年也取得不错的成绩。</p><p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; font-size: 16px;\">陆龙海表示，铜鼓镇的发展模式不仅盘活了农村荒地，还增加了农民就业机会、提高了农民收入、促进了农村经济发展，希望双方能够进一步加强沟通交流,共同探索合作模式,努力实现合作共赢。</p><p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; font-size: 16px;\">当天，全体党员参观了铜鼓山英烈园和铜鼓山革命纪念馆，重温了党的光辉历史和革命精神，接受了一次深刻的革命传统教育。</p><p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; font-size: 16px;\"><br/></p>");
        obj.set("content", "<p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;;\">近日，重庆市荣昌区民营经济协会及协会党支部开展以“实干担当促发展”为主题的党建活动，并与铜鼓镇商会开展“万企兴万村”助力乡村振兴交流座谈会。</p><p style=\"margin-top: 0px; margin-bottom: 24px; text-wrap: wrap; padding: 0px; outline: 0px; max-width: 100%; clear: both; min-height: 1em; color: rgba(0, 0, 0, 0.9); font-family: system-ui, -apple-system, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei UI&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 17px; letter-spacing: 0.544px; background-color: rgb(255, 255, 255); text-align: center; box-sizing: border-box !important; overflow-wrap: break-word !important;\"><img class=\"rich_pages wxw-img js_insertlocalimg\" src=\"https://mmbiz.qpic.cn/sz_mmbiz_png/tpMNuFQbKQep3r3TspibD9Zuq8VibPQicxibwBib7a0AS41FNy0Sc8ArsPQBaGHoiaPhyfxLX4vVQVmQDMia9ULic0zbvg/640?wx_fmt=png&from=appmsg&wxfrom=13\" alt=\"图片\"/></p><p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;;\">铜鼓镇副镇长杨媚，铜鼓镇商会常务副会长徐维丰、秘书长谢苹，荣昌区民营经济协会党支部书记施正云、会长陆龙海等近30人参加会议。</p><p style=\"margin-top: 0px; margin-bottom: 24px; text-wrap: wrap; padding: 0px; outline: 0px; max-width: 100%; clear: both; min-height: 1em; color: rgba(0, 0, 0, 0.9); font-family: system-ui, -apple-system, BlinkMacSystemFont, &quot;Helvetica Neue&quot;, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei UI&quot;, &quot;Microsoft YaHei&quot;, Arial, sans-serif; font-size: 17px; letter-spacing: 0.544px; background-color: rgb(255, 255, 255); text-align: center; box-sizing: border-box !important; overflow-wrap: break-word !important;\"><img class=\"rich_pages wxw-img js_insertlocalimg\" src=\"https://mmbiz.qpic.cn/sz_mmbiz_png/tpMNuFQbKQep3r3TspibD9Zuq8VibPQicxibZ0N6qFqdAetmpyZK4IKHqgH64V5ychfzMDOszn2EX20ccVuahbIU5A/640?wx_fmt=png&from=appmsg&wxfrom=13\" alt=\"图片\"/></p><p style=\"text-wrap: wrap;\"><br/></p><p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;;\">杨媚介绍说，铜鼓镇打造了“两传统，三新兴”的发展模式，两传统是指蛋鸡养殖和花椒种植；三新兴是指千亩野菊花、千亩生态稻和千亩红高粱。目前两传统农业正在进行提档升级，三新兴产业在今年也取得不错的成绩。</p><p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;;\">陆龙海表示，铜鼓镇的发展模式不仅盘活了农村荒地，还增加了农民就业机会、提高了农民收入、促进了农村经济发展，希望双方能够进一步加强沟通交流,共同探索合作模式,努力实现合作共赢。</p><p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;;\">当天，全体党员参观了铜鼓山英烈园和铜鼓山革命纪念馆，重温了党的光辉历史和革命精神，接受了一次深刻的革命传统教育。</p><p style=\"text-wrap: wrap; line-height: 1.75em; text-indent: 2em; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;;\"><br/></p><p><br/></p>");
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
        data.set("media_id", "WBuJmGjhUnnz1TjDWqPBUrqUbonfzELamFY0gxZLXh1vyNo8haRMYBxLeJNNZcI3");
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
