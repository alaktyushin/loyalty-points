package com.laktyushin.loyaltypoints.utils;

import com.laktyushin.loyaltypoints.enums.Roles;
import org.springframework.http.RequestEntity;

import java.util.Base64;

public final class TokenUtils {

    public static Roles getRoleFromRequest(RequestEntity<String> requestEntity) {
        if (!requestEntity.toString().contains("authorization:\"Bearer ")) {
            return Roles.UNREGISTERED;
        }
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] strings = requestEntity.toString().split("authorization:\"Bearer ");
        String[] chunks = strings[1].split("\"")[0].split("\\.");
        String payload = new String(decoder.decode(chunks[1]));
        if (payload.contains("\"role\":\"admin\"")) {
            return Roles.ADMIN;
        } else if (payload.contains("\"role\":\"REGISTERED_BUSINESS\"")) {
            return Roles.REGISTERED_BUSINESS;
        } else if (payload.contains("\"role\":\"COMMON_BUSINESS\"")) {
            return Roles.COMMON_BUSINESS;
        }
        return Roles.UNREGISTERED;
    }
}
