package com.naidelii.chat.user.domain.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * @author naidelii
 */
@Data
public class IpInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 注册时的ip
     */
    private String createIp;

    /**
     * 注册时的ip详情
     */
    private IpDetail createIpDetail;

    /**
     * 最新登录的ip
     */
    private String updateIp;

    /**
     * 最新登录的ip详情
     */
    private IpDetail updateIpDetail;

    /**
     * 刷新ip
     *
     * @param ip ip
     */
    public void refreshIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return;
        }
        if (StringUtils.isEmpty(createIp)) {
            createIp = ip;
        }
        updateIp = ip;
    }

    /**
     * 获取要刷新的ip：这里判断更新ip就行，初始化的时候ip也是相同的
     * 如果最新登录的ip和最新登录的详情不一致，则刷新ip
     *
     * @return 要刷新的ip
     */
    public String needRefreshIp() {
        boolean notNeedRefresh = Optional.ofNullable(updateIpDetail)
                .map(IpDetail::getIp)
                .filter(ip -> Objects.equals(updateIp, ip))
                .isPresent();
        return notNeedRefresh ? null : updateIp;
    }

    public void refreshIpDetail(IpDetail ipDetail) {
        if (Objects.equals(createIp, ipDetail.getIp())) {
            createIpDetail = ipDetail;
        }
        if (Objects.equals(updateIp, ipDetail.getIp())) {
            updateIpDetail = ipDetail;
        }
    }

    /**
     * ip详情
     */
    @Data
    public static class IpDetail {

        /**
         * ip
         */
        private String ip;

        private String isp;
        private String ispId;
        private String city;
        private String cityId;
        private String country;
        private String countryId;
        private String region;
        private String regionId;
    }

}
