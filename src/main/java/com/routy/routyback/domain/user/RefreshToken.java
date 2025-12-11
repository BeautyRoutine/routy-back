package com.routy.routyback.domain.user;

import lombok.Data;
import java.util.Date;

@Data
public class RefreshToken {
    private String userId;
    private String refreshToken;
    private Date expiresAt;
}
