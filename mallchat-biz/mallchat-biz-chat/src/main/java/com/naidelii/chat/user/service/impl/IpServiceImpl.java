package com.naidelii.chat.user.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.naidelii.base.domain.vo.response.Result;
import com.naidelii.chat.user.dao.SysUserDao;
import com.naidelii.chat.user.domain.entity.IpInfo;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.IIpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IpServiceImpl implements IIpService {

    /**
     * 处理ip的线程池
     */
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1, 1, 0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(500),
            new NamedThreadFactory("refresh-ipDetail", false));

    private final SysUserDao userDao;

    @Override
    public void asyncParseByUserId(String userId) {
        EXECUTOR.execute(() -> {
            // 获取用户信息
            SysUser user = userDao.getById(userId);
            IpInfo ipInfo = user.getIpInfo();
            if (ipInfo == null) {
                return;
            }
            // 获取要刷新的ip
            String ip = ipInfo.needRefreshIp();
            if (StringUtils.isBlank(ip)) {
                return;
            }
            // 解析ip
            IpInfo.IpDetail ipDetail = tryGetIpDetailOrNullTreeTimes(ip);
            // 如果ip解析失败，打印日志
            if (Objects.isNull(ipDetail)) {
                log.error("get ip detail fail ip:{},userId:{}", ip, userId);
                return;
            }
            // 更新ip详情
            ipInfo.refreshIpDetail(ipDetail);
            // 更新数据库
            SysUser updateUser = new SysUser();
            updateUser.setId(userId);
            updateUser.setIpInfo(ipInfo);
            userDao.updateById(updateUser);
        });
    }

    private IpInfo.IpDetail tryGetIpDetailOrNullTreeTimes(String ip) {
        // 尝试三次
        for (int i = 0; i < 3; i++) {
            IpInfo.IpDetail ipDetail = getIpDetailOrNull(ip);
            // 如果解析成功了则直接返回
            if (ipDetail != null) {
                return ipDetail;
            }
            // 否则等待3s后，再次尝试解析
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.error("tryGetIpDetailOrNullTreeTimes parse fail", e);
            }
        }
        // 如果3次都没有解析成功，直接返回null
        return null;
    }

    public IpInfo.IpDetail getIpDetailOrNull(String ip) {
        // 淘宝解析ip的接口
        String url = "https://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc";
        String body = HttpUtil.get(url);
        // 转为实体类
        Result<IpInfo.IpDetail> result = JSONUtil.toBean(body, new TypeReference<Result<IpInfo.IpDetail>>() {
        }, false);
        return result.getData();
    }
}
