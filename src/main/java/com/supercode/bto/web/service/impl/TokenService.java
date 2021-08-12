package com.supercode.bto.web.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.supercode.bto.web.entity.RzRyxxb;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO token 下发
 * @date 2021/7/8 21:17
 */
@Service("TokenService")
public class TokenService {

    public String getToken(RzRyxxb ryxx) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(ryxx.getRy_bh()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(ryxx.getRy_mm()));
        return token;
    }
}
