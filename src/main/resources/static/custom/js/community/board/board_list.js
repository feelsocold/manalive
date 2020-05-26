$.get("/board/list", function ( data ) {
    //alert(data);
    console.log(data);
    var boardList = data.boardList;
    var pageMaker = data.pageMaker;
    // console.log(pageMaker);
    // console.log(boardList);
    spreadBoardList( data );
    spreadPaging( pageMaker );

});

function spreadBoardList( data ) {
    var boardList = data.boardList;
    var userDetail = data.userDetail;

    $(".board-data").empty();
    // LIST 뿌리기
    for(var i=0; i < boardList.length; i++) {
        var str = "";
        str += "<tr><td>" + boardList[i].seq + "</td>";
        str += "<td style='text-align: left; padding-left: 25px; color: #337ab7;' class='board-title'><a href='/board/detail?seq="+boardList[i].seq+"'>" + boardList[i].title + "</a></td>";
        str += "<td style='text-align: left; padding-left: 10px'>" + "<img class='profile-photo' src='"+ userDetail[i].photo +"'>";
        str += userDetail[i].nickname + "</td>";
        str += "<td>" + timeForToday(boardList[i].createDate) + "</td>";
        str += "<td>" + "0" + "</td>";
        str += "<td>" + "0" + "</td></tr>";
        $(".board-data").append(str);
    }
}

function spreadPaging( pageMaker ){
    $("#paging").empty();
    // PAGING 뿌리기
    var str2 = "";
    if(pageMaker.prev){
        str2 += "<li class='page-item'><a class='page-link' href='#'>Previous</a></li>";
    }
    for(var i=pageMaker.startPage; i <= pageMaker.endPage; i++) {
        if(pageMaker.cri.pageNumber == i){
            str2 += "<li class='page-item active'><a class='page-link' href='"+ i +"'>" + i + "</a></li>";
        }else{
            str2 += "<li class='page-item'><a class='page-link' href='"+ i +"'>" + i + "</a></li>";
        }

    }
    if(pageMaker.next){
        str2 += "<li class='page-item'><a class='page-link' href='#'>Next</a></li>";
    }
    $('#paging').append(str2);
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
$(document).on('click','.page-item a',function(e){
    e.preventDefault();
    var pageNumber = $(this).attr("href");

    var data = { "pageNumber" : pageNumber };

    $.ajax({
        type: 'GET',
        url: '/board/list',
        //data: JSON.stringify(data),
        data: data,
        dataType : 'json',
        success : function(result){

            spreadBoardList(result);
            spreadPaging( result.pageMaker );

        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){
            //$(".board-data").html("<img class='loading-img' src='/custom/img/loading-img.gif' align='center' >");

        },complete:function(){
            $(".loading-img").remove();
        }
    });
});

// $(document).on('click','.board-title a',function(e){
//     e.preventDefault();
//     var seq = $(this).attr("href");
//     alert(seq);
//
// });


// 글쓰기 버튼 클릭
function goWrite() {
    var sessionVal = $("#sessionUser").val();
    if(sessionVal == "") {
        $('#Login-Btn').click();
    }else{
        location.href="/board/write";
    }
}