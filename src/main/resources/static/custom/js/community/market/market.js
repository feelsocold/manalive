$.get("/market/list", function ( data ) {
    //alert(data);
    console.log(data);
    spreadMarketList(data);
});

function spreadMarketList( data ) {
    var list = data.marketList;

    $("#list-wrap").empty();

    // LIST 뿌리기
    for(var i = 0; i < list.length; i++) {
        var str = "";
        str += "<div class='item-wrapper'>";
        str += "<div class='item-image'><img src='"+list[i].attachList[0].url+"'></div>";
        str += "<div class='item-info'>";
        str += "<span class='item-title'>"+list[i].title+"</span><br>";
        str += "<span class='item-price'>"+numberFormat(list[i].price)+"원</span><br>";
        str += "<span class='item-regdate'>"+timeForToday(list[i].createDate)+"</span><hr>";
        str += "<span class='item-readcnt'>조회 <label>"+"0"+"</label></span>&nbsp;";
        str += "<span class='item-wishcnt'>&sdot;&nbsp;찜 <label>"+"0"+"</label></span>";
        str += "</div>";
        $("#list-wrap").append(str);
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

// 좌측 카테고리 클릭 시
$(".category-list").on('click','ul',function(e){
    var oper = $(this).data("oper");

    $("#categoryinfo-wrap h5").html(oper);
    var data;
    if( oper !== "") {
         data = { "category" : "CATEGORY",
                    "keyword" : $(this).html() };

    }else if(oper == "") {
        $("#categoryinfo-wrap h5").html("전체 상품");
        data = null;
    }

    $.ajax({
        type: 'GET',
        url: '/market/list',
        data: data,
        dataType : 'json',
        success : function(result){
            console.log(result);
            spreadMarketList(result);
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){
            //$(".board-data").html("<img class='loading-img' src='/custom/img/loading-img.gif' align='center' >");

        },complete:function(){
            $(".loading-img").remove();
        }
    });

});

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


// 글쓰기 버튼 클릭
function goWrite() {
    var sessionVal = $("#sessionUser").val();
    if(sessionVal == "") {
        $('#Login-Btn').click();
    }else{
        location.href="/board/write";
    }
}
// 시간 환산
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
// 통화 변환
function numberFormat( value ) {
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}