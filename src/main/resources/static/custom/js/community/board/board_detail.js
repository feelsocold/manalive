var replyPageNumber = 0;
var seq = $("#seq").val();
var sessionEmail = $("#sessionUser-email").val();
var replyDelayCnt = 0;
var replyCnt = 0;
var likeCnt = 0;

$(document).ready(function() {
    var data = { "seq" : seq };

    $.ajax({
        type: 'POST',
        url: '/boardDetail',
        data: data,
        success : function(result){
            console.log(result);
            spreadBoardInfo(result);
            spreadReplyList(result.replyObj);
            discernLike();

            $("#likecnt-span").html(result.likeCnt);
            $("#replycnt-span").html(result.replyObj.replyCnt);
            replyCnt = Number(result.replyObj.replyCnt);
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){

        },complete:function(){
        }
    });
});
function discernLike() {
    var str = "";
    if(sessionEmail == null){
        console.log("세션X");
        str += "<img src='/custom/img/icon/icon-unlike.png' class='like-btns' id='dolike_btn'>";
        $("#likebtn-area").append(str);
    }else{
        console.log("세션O");
        var data = { "b_seq" : seq };
        $.ajax({
            type: 'POST',
            url: '/boardLikeDiscern',
            data: data,
            success : function(result){
                console.log("already? : " + result);
                if(result == false) {
                    str += "<img src='/custom/img/icon/icon-unlike.png' class='like-btns' id='dolike_btn'>";
                }else {
                    str += "<img src='/custom/img/icon/icon-like.png' class='like-btns' id='dounlike_btn'>";
                }
                $("#likebtn-area").append(str);
            },error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            }
        });
    }
}

function spreadBoardInfo(result) {
    var boardDto = result.boardDto;
    var attachList = result.attachList;

    $("#deleteConfirm-Btn").val(boardDto.seq);

    $("#title-div h2").html(boardDto.title);
    if(sessionEmail === boardDto.email) {
        $("#updbuttons-div span").append("<div style='float:left'>" +
            "<a href='/board/modify?b_seq="+boardDto.seq+"' id='board-modbtn'>수정</a>" +
            "&nbsp;&nbsp;<a href='"+boardDto.seq+"' id='board-delbtn'>삭제</a>");
    }
    // $("#date-div span").html(timeForToday(boardDto.createDate));

    for(var i=0; i < attachList.length; i++) {
        $("#photo-div").append("<div class='eachphoto-div'><img class='eachphoto-img' src='"+ attachList[i].url + "'></div>");
    }

    $("#content-div textarea").html(boardDto.content);
    autosize($("#content-div textarea"));

    // if(sessionEmail === boardDto.email) {
    //     $("#udt-buttons").append("<div><input type='button' id='board-modbtn' value='수정'>" +
    //         "&nbsp;&nbsp;<input type='button' data-oper='"+boardDto.seq+"' id='board-delbtn' value='삭제'></div>");
    // }

    $("#writer-profile").append("<img id='writer-photo' src='"+ boardDto.photo +"'>" +
        "<span id='writer-nick'>"+boardDto.nickname+"</span>");

    var hashtags = boardDto.hashtags.split(',');
    for ( var i in hashtags ) {
        //document.write( '<p>' + jbSplit[i] + '</p>' );
        if(hashtags[i] != ' ' && hashtags[i] != ''){
            console.log(hashtags);
            $("#hashtags-div").append("<span>#"+hashtags[i]+"</span>&nbsp;");
        }
    }

}
// 댓글리스트 뿌리기
function spreadReplyList(result) {
    var replyList = result.replyList;
    var replyCnt = result.replyCnt;
    if(replyList != null) {
        for(var i=0; i < replyList.length; i++) {
            var str = "";
            str += "<div class='reply-onerow'><div class='replyerPhoto-area' ><img class='replyerphoto-img' src='"+ replyList[i].photo +"'></div>";
            str += "<div class='replyerContent-area'><span class='replyer-nickname'>"+replyList[i].nickname+"</span>" + replyList[i].content;
            // str += "    <label>"+replyList[i].content+"</label>";
            str += "    <br><span class='reply-date'>"+timeForToday(replyList[i].createDate)+"</span>&nbsp;";
            if(sessionEmail === replyList[i].email){
                str += "<span class='delete-reply'><a href='"+replyList[i].r_seq+"' style='color: #ff5e52;'>삭제</a></span>"
            }
            str += "</div></div>";
            //str += "<div class='replylike-div'><img src='/custom/img/icon/icon-emptyheart.png' style='width: 20px; height: 20px;'></div></div>";
            $("#reply-getinside").append(str);
            //$("#reply-getinside").append("<div id='plusicon-div'><img src='/custom/img/icon/icon-plus.png'></div>");
        }
        if(replyCnt > (replyPageNumber+1)*10) {
            //$("#reply-area").("<div id='plusicon-div'><img src='/custom/img/icon/icon-plus.png'></div>");
            $("#reply-getinside").append("<div id='plusicon-div'><img id='morereply-btn' src='/custom/img/icon/icon-plus.png'></div>");
        }
    }else{
        return false;
    }
}

function timeForToday(value) {
    const today = new Date();
    const timeValue = new Date(value);

    const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
    if (betweenTime < 1) return '방금 전';
    if (betweenTime < 60) {
        return `${betweenTime}분 전`;
    }

    const betweenTimeHour = Math.floor(betweenTime / 60);
    if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간 전`;
    }

    const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
    if (betweenTimeDay < 365) {
        return `${betweenTimeDay}일 전`;
    }

    return `${Math.floor(betweenTimeDay / 365)}년전`;
}

// 댓글 입력창에서 enter 비활성화
$("#replyWrite-area textarea").keypress(function(event) {
    if (event.keyCode == 13) {
        event.preventDefault();
    }
});


$(document).on('click','#replypost-btn',function(e){
    //alert(replyPageNumber);
    replyCnt++;
    var data = { "b_seq" : $("#seq").val(),
                 "content" :  $("#reply-content").val()
    }
        $.ajax({
            type: 'POST',
            url: '/reply/board/save',
            contentType : "application/json; charset=utf-8",
            data: JSON.stringify(data),
            //data: data,
            dataType : 'json',
            success : function(result){
                console.log(result);
                replyPageNumber = 0;
                replyDelayCnt = 0;
                //alert(replyPageNumber);
                $("#reply-getinside").empty();
                $("#reply-content").val('');

                $("#replycnt-span").html(replyCnt);
                spreadReplyList(result);

            },error: function (jqXHR, textStatus, errorThrown) {
                //alert("error");
            },beforeSend:function(){


            },complete:function(){
                //alert("complete");
            }
        });
});
$("#replyWrite-area textarea").keypress(function(event) {
    if (event.keyCode == 13) {
        event.preventDefault();
        $('#replypost-btn').click();
    }
});

$(document).on('click','#morereply-btn',function(e){
    $(this).closest('div').remove();
    replyPageNumber++;
    //alert(replyPageNumber);
    var data = { "b_seq" : $("#seq").val(),
                "pageNumber" :  replyPageNumber,
                "delayCnt" : replyDelayCnt
    }

    $.ajax({
        type: 'POST',
        url: '/reply/board/list',
        data: data,
        success : function(result){
            console.log(result);
            spreadReplyList(result);

        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){


        },complete:function(){
            //alert("complete");
        }
    });
});

$(document).on('click','.delete-reply a',function(e) {
    e.preventDefault();
    var r_seq = $(this).attr("href");
    $(this).closest('.reply-onerow').remove();
    var data = { "r_seq" : r_seq };
        $.ajax({
            type: 'POST',
            url: '/reply/board/delete',
            data: data,
            success : function(result){
                console.log("삭제완료");
                replyDelayCnt++;
            },error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            },beforeSend:function(){

            },complete:function(){
                //alert("complete");
            }
        });
});
$(document).on('click','#board-delbtn',function(e) {
    e.preventDefault();
    $("#confirm-modal").show();


});

$(document).on('click','#cancelDelete-Btn',function(e) {
    $("#confirm-modal").hide();
});

$(document).on('click','#deleteConfirm-Btn',function(e) {
    var seq = $(this).val();
    var data = { "b_seq" : seq };

    $.ajax({
        type: 'POST',
        url: '/boardDelete',
        data: data,
        success : function(result){
            console.log("삭제완료");
            location.href="/board";

        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){


        },complete:function(){
            //alert("complete");
        }
    });
});

$(document).on('click','#dolike_btn',function(e) {
    if(sessionEmail == null){
        alert("로그인 후 이용해주세요");
        return false;
    }else{
        var data = { "b_seq" : seq };
        $.ajax({
            type: 'POST',
            url: '/boardLike',
            data: data,
            success : function(result){
                var str = "";
                str += "<img src='/custom/img/icon/icon-like.png' class='like-btns' id='dounlike_btn'>";
                $("#likebtn-area").empty();
                $("#likebtn-area").append(str);
                var cnt = $("#likecnt-span").html();
                $("#likecnt-span").html(Number(cnt)+1);
            },error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            }
        });
    }
});

$(document).on('click','#dounlike_btn',function(e) {
    if(sessionEmail == null){
        alert("로그인 후 이용해주세요");
        return false;
    }else{
        var data = { "b_seq" : seq };
        $.ajax({
            type: 'POST',
            url: '/boardUnLike',
            data: data,
            success : function(result){
                var str = "";
                str += "<img src='/custom/img/icon/icon-unlike.png' class='like-btns' id='dolike_btn'>";
                $("#likebtn-area").empty();
                $("#likebtn-area").append(str);
                var cnt = $("#likecnt-span").html();
                $("#likecnt-span").html(Number(cnt)-1);
            },error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            }
        });
    }
});
s