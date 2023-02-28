package com.exe.study.jblog.service.kakao;

import com.exe.study.jblog.constant.OAuthType;
import com.exe.study.jblog.constant.RoleType;
import com.exe.study.jblog.domain.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


// 카카오 api 요청 정보
// REST API : 494d815f5517043247cb1f2773b09196
// 도메인 : http://localhost:8080
// redirect uri : http://localhost:8080/oauth/kakao
@Service
public class KakaoLoginService {

    private final String ACCESS_TOKEN_URI = "https://kauth.kakao.com/oauth/token"; // 토큰 발급
    private final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";     // 사용자 정보 가져오기


    // 카카오 비밀번호 설정 : 카카오 인증서버로 부터 전달받은 정보를 기반으로 회원가입 처리를 위해서 비밀번호 생성
    @Value("${kakao.default.password}")
    private String kakaoPassword;


    // 1. 엑세스 토큰 발급받기
    public String getAccessToken(String code) {

        // HttpHeader 헤더생성(MIME Type)
        HttpHeaders header = new HttpHeaders();
        header.add("Content-type",
                "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 바디생성(4개의 필수 파라미터 설정)
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "1c2685d26cbbd907d5fb9764e52454af");
        body.add("redirect_uri", "http://localhost:8080/oauth/kakao");
        body.add("code", code); //발급받은 인가코드담아서 요청

        // HttpHeader와 HttpBody가 설정된 HttpEntity 객체 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(body, header);

        // RestTemplate을 이용하면 브라우저 없이 HTTP 요청을 처리할수 있음
        RestTemplate restTemplate = new RestTemplate();


        // HTTP 요청 및 응답 받기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                ACCESS_TOKEN_URI,	        // AccessToken 요청 주소
                HttpMethod.POST,			// 요청 방식
                requestEntity,				// 요청 헤더와 바디
                String.class				// 응답 받을 타입
        );

        // HTTP 응답 BODY 정보 리턴
        String jsonData = responseEntity.getBody();

        // JSON 데이터에서 Access Token 정보만 추출
        Gson gsonObj = new Gson();
        Map<?, ?> data = gsonObj.fromJson(jsonData, Map.class);
        return (String) data.get("access_token");

    }


    // 2. 사용자 정보 가져오기
    public User getUserInfo(String accessToken) {

        // HttpHeader 생성
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);
        header.add("Content-type",
                "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기(body 정보는 생략 가능)
        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(header);

        // RestTemplate을 이용하면 브라우저 없이 HTTP 요청을 처리할 수 있다.
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 요청을 POST(GET) 방식으로 실행하기 -> 그러면 문자열로 응답이 들어온다.
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                USER_INFO_URI,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 카카오 인증 서버가 리턴한 사용자 정보
        String userInfo = responseEntity.getBody();


        // JSON 데이터에서 추출한 정보를 바탕으로 User 객체 설정
        Gson gsonObj = new Gson();
        Map<?, ?> data = gsonObj.fromJson(userInfo, Map.class);

//		Double id = (Double) (data.get("id"));
//		String nickname = (String) ((Map<?, ?>) (data.get("properties"))).get("nickname");

        String myEmail = "my@kakao.com";
        String email = (String) ((Map<?, ?>) (data.get("kakao_account"))).get("email");

        User user = new User();
        user.setUsername(myEmail);
        user.setPassword(kakaoPassword);
        user.setEmail(myEmail);
        user.setOauth(OAuthType.KAKAO);
        user.setRole(RoleType.USER);
        return user;


    }
}
