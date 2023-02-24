// userObject 객체 선언
let userObject = {

    // 파일이 로딩되는 순간 init() 함수 자동으로 호출
    init: function() {

        let _this = this;

        // "#btn-save" 버튼에 "click" 이벤트가 발생되면 insertUser() 함수를 호출한다.
        $("#btn-save").on("click", () => {
            _this.insertUser();
        });

        $("#btn-update").on("click", () => {
            _this.updateUser();
        });

    },

    insertUser: function() {
        alert("회원 가입 요청됨");

        // 사용자가 입력한 값들(username, password, email)을 추출한다.
        let user = {
            username : $("#username").val(),
            password : $("#password").val(),
            email : $("#email").val()
        }

        console.log(user);

        // Ajax를 이용한 비동기 호출
        $.ajax({
            type: "POST", // 요청 방식
            url: "/auth/insertUser",
            data: JSON.stringify(user),                     //user Object를 JSON으로 변환
            contentType: "application/json; charset=utf-8"  // HTTP 바디에 설정되는 데이터의 마임타입설정

        }).done(function(response) {
            let status = response["status"];
            if(status == 200) {
                let message = response["data"];
                alert(message);
                console.log(message);
                location = "/";
            } else {
                let warn = "";
                let errors = response["data"];
                if(errors.username != null) warn = warn + errors.username + "\n";
                if(errors.password != null) warn = warn + errors.password + "\n";
                if(errors.email != null) warn = warn + errors.email;
                alert(warn);
            }
        }).fail(function(error) {
            alert("에러 발생 : " + error);
        });
    },


    updateUser: function() {
        alert("회원정보 수정 요청");
        let user = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }
        $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(user),
            contentType: "application/json; charset=utf-8",
        }).done(function(response) {
            let message = response["data"];
            alert(message);
            location = "/";
        }).fail(function(error) {
            let message = error["data"];
            alert("문제 발생 : " + message);
        });
    },


}

// userObject 객체의 init 함수 호출.
userObject.init();
