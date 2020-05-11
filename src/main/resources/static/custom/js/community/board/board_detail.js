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
        $("#photo-div").append("<img src='"+ attachList[i].url + "'>");
    }


    $("#writer-profile").append("<img id='writer-photo' src='"+ boardDto.photo +"'>" +
        "<span id='writer-nick'>"+boardDto.nickname+"</span>");

    //alert(timeForToday(boardDto.createDate));
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