CREATE TABLE USERS(
    ID NUMBER(5) PRIMARY KEY,
    USERNAME VARCHAR2(30),
    PASSWORD VARCHAR2(100),
    EMAIL VARCHAR2(30)
);



SELECT * FROM USERS;
SELECT * FROM POST;


// 카카오 api 요청 정보
// 앱키(client ID) : 1c2685d26cbbd907d5fb9764e52454af
// 도메인 : http://localhost:8080
// redirect uri : http://localhost:8080/oauth/kakao

// google api 정보
// 앱키(client ID) : 104242137738-fqqajh0r2jkrc76ein0fpe30dhbba9a5.apps.googleusercontent.com
// 클라이언트 비밀번호 : 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
// redirect uri : http://localhost:8080/oauth2/code/google