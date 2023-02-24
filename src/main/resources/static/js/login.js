// loginObject 객체 선언
let loginObject = {

    init: function() {
        $("#btn-login").on("click", () => {
            this.login();
        });

    },

    login : function() {
        alert("로그인 요청");

        let data = {
            username : $("#username").val(),
            password : $("#password").val()
        }

        $.ajax({
            type: "POST",
            url: "/auth/login",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8"
        }).done(function(response) {
            let status = response["status"];
            if(status == 200){
                let message = response["data"];
                alert(message);
                console.log(message);
                location = "/";
            }else{
                let errors = response["data"];
                alert(errors);
            }
        }).fail(function(error) {
            let message = error["data"];
            alert("에러 발생 : " + message);
        });
    },

}

loginObject.init();
