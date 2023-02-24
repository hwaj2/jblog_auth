let replyObject = {

    init: function() {
        $("#btn-save-reply").on("click", () => {
            this.insertReply();
        });
    },

    // 댓글 등록
    insertReply : function() {
        alert("댓글 등록 요청됨");

        let id = $("#postId").val();
        let reply = {
            content : $("#reply-content").val()
        }

        $.ajax({
            type: "POST",
            url: "/reply/" + id,
            data: JSON.stringify(reply),
            contentType: "application/json; charset=utf-8"
        }).done(function(response) {
            let message = response["data"];
            alert(message);
            location = "/post/" + id;
        });
    },


    // 댓글 삭제
    deleteReply : function(postId, replyId) {
        alert("댓글 삭제 요청됨");

        $.ajax({
            type: "DELETE",
            url: "/reply/" + replyId
        }).done(function(response) {
            let message = response["data"];
            alert(message);
            location = "/post/" + postId; //postId를 통해 해당 상세화면으로 돌아가기 위해서
        });
    },

}

replyObject.init();

