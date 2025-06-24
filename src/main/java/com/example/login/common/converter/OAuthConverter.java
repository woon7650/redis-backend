package com.example.login.common.converter;

import com.example.login.common.enumType.OAuthType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class OAuthConverter implements Converter<String, OAuthType> {
    @Override
    public OAuthType convert(String oauthType) {
        return OAuthType.valueOf(oauthType.toUpperCase());
    }
}
