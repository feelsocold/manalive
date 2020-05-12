var replyPageNumber = 0;

$(document).ready(function() {
    var seq = $("#seq").val();
    var data = { "seq" : seq };

    $.ajax({
        type: 'POST',
        url: '/boardDetail',
        data: data,
        // data: seq,
        //data: {"seq" : seq },
        // dataType : 'json',
        success : function(result){
            console.log(result);
            spreadBoardInfo(result);
            spreadReplyList(result);
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){

        },complete:function(){
        }
    });


});

function spreadBoardInfo(result) {
    var boardDto = result.boardDto;
    var attachList = result.attachList;

    $("#title-div h2").html(boardDto.title);
    $("#date-div span").html(timeForToday(boardDto.createDate));

    for(var i=0; i < attachList.length; i++) {
        $("#photo-div").append("<div class='eachphoto-div'><img class='eachphoto-img' src='"+ attachList[i].url + "'></div>");
    }

    $("#content-div textarea").html(boardDto.content);
    autosize($("#content-div textarea"));


    $("#writer-profile").append("<img id='writer-photo' src='"+ boardDto.photo +"'>" +
        "<span id='writer-nick'>"+boardDto.nickname+"</span>");
}
// 댓글리스트 뿌리기
function spreadReplyList(result) {
    var replyList = result.replyObj.replyList;
    var replyCnt = result.replyObj.replyCnt;
    for(var i=0; i < replyList.length; i++) {
        var str = "";
        str += "<div class='replyerPhoto-area' ><img class='replyerphoto-img' src='"+ replyList[i].photo +"'></div>";
        str += "<div class='replyerContent-area'><span class='replyer-nickname'>"+replyList[i].nickname+"</span>" + replyList[i].content;
        // str += "    <label>"+replyList[i].content+"</label>";
        str += "    <br><span class='reply-date'>"+timeForToday(replyList[i].createDate)+"</span>";
        str += "</div><br>";
        $("#reply-getinside").append(str);
        //$("#reply-getinside").append("<div id='plusicon-div'><img src='/custom/img/icon/icon-plus.png'></div>");
    }
    if(replyCnt > (replyPageNumber+1)*10) {
        //$("#reply-area").("<div id='plusicon-div'><img src='/custom/img/icon/icon-plus.png'></div>");
        $("#reply-getinside").append("<div id='plusicon-div'><img src='/custom/img/icon/icon-plus.png'></div>");
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

$("#replyWrite-area textarea").keypress(function(event) {

    if (event.keyCode == 13) {
        event.preventDefault();
    }
});


$(document).on('click','#replypost-btn',function(e){
    var data = { "b_seq" : $("#seq").val(),
                 "content" :  $("#reply-content").val()
                 // "replyer" : $('#sessionUser-email')
    }

        $.ajax({
            type: 'POST',
            url: '/reply/board',
            contentType : "application/json; charset=utf-8",
            data: JSON.stringify(data),
            //data: data,
            dataType : 'json',
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

