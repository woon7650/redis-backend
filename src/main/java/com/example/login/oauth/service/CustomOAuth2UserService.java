package com.example.login.oauth.service;

import com.example.login.user.repository.UserRepository;
import com.example.login.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String DEFAULT_STRING = "default";

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "userRepository")
    private UserRepository userRepository;


    /* OAuth2 로그인 요청에 필요한 정보(클라이언트 등록 정보, 사용자의 권한 부여 코드, 액세스 토큰)를 가지고 있는 객체 OAuth2UserRequest*/
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        /*공급자 정보인 registrationId(구글, 카카오, 네이버)*/
        String providerId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        /*사용자 정보 엔드포인트인 userInfoEndpoint*/
        ClientRegistration.ProviderDetails.UserInfoEndpoint userInfoEndpoint = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint();

        String password = DEFAULT_STRING;
        String email = DEFAULT_STRING;
        String picture = DEFAULT_STRING;
        String nickname = DEFAULT_STRING;
        String name = DEFAULT_STRING;

        if (Objects.equals(providerId, "google")) {
            password = (String) attributes.get("id");
            email = (String) attributes.get("email");
            picture = (String) attributes.get("picture");
            nickname = (String) attributes.get("name");
            name = (String) attributes.get("name");
        }

        if (Objects.equals(providerId, "kakao")) {
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

            password = String.valueOf(attributes.get("id"));
            email = (String) kakaoAccount.get("email");
            picture = (String) properties.get("profile_image");
            nickname = (String) properties.get("nickname");
            name = (String) properties.get("nickname");
        }
        //TODO: 회원 정보 검색 -> INSERT 혹은 UPDATE 로직 추가
        //TODO: return 값 생성
        return null;
    }


}
