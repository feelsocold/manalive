var searchValue = "";
var uMarketTotal = 0;
var uMarketPageNumber = 0;
var sessionEmail = $("#sessionUser-email").val();

$.get("/market/list", function ( data ) {
    //alert(data);
    console.log(data);
    spreadMarketList(data);
    spreadPaging(data.pageMaker);
});

function spreadMarketList( data ) {
    var list = data.marketList;
    var attachList = data.attachList;

    //alert(list.length);

    $("#list-wrap").empty();

        // LIST 뿌리기
        for(var i = 0; i < list.length; i++) {
            var str = "";
            str += "<div class='item-wrapper' data-oper='"+list[i].seq +"'";
            if(list[i].soldout == true) {
                str += " style='opacity: 0.6;'";
            }
            str += ">";
            str += "<div class='item-image'><img style='' src='"+attachList[i]+"'  ></div>";
            str += "<div class='item-info'>";
            str += "<span class='item-title'>"+list[i].title+"</span><br>";
            if(list[i].soldout == false){
                str += "<span class='item-price'>"+numberFormat(list[i].price)+" 원</span><br>";
            }else if(list[i].soldout == true){
                str += "<span class='item-price'>판매완료</span><br>";
            }
            str += "<span class='item-regdate'>"+timeForToday(list[i].createDate)+"</span><hr>";
            str += "<span class='item-readcnt'>조회 <label>"+ list[i].readCount+"</label></span>";
            str += "<span class='item-wishcnt'>&nbsp;&sdot;&nbsp;찜 <label>"+list[i].marketWishList.length+"</label></span>";
            str += "</div>";
            $("#list-wrap").append(str);
        }
    //}
}

function spreadPaging( pageMaker ){
    $("#paging-wrap").show();
    // console.log("page keyword : " + pageMaker.cri.keyword);
    // console.log("page category : " + pageMaker.cri.category);
    $("#paging-category").val(pageMaker.cri.category);
    $("#paging-keyword").val(pageMaker.cri.keyword);
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

// 검색
function searchMarket(obj) {
    var keyword = $("#marketsearch-input").val();
    searchValue = keyword;
    var first = keyword.substring(0,1);
    var data = { "keyword" : keyword };
    if(keyword != ""){

        if(first == "@"){
            searchUserMarketName(keyword);

        }else{
            data = { "keyword" : keyword,
                "category" : "TITLE"};

            $.ajax({
                type: 'GET',
                url: '/market/list',
                data: data,
                dataType : 'json',
                success : function(result){
                    console.log("search result : " + result);
                    spreadMarketList(result);
                    spreadPaging(result.pageMaker);
                    $("#categoryinfo-wrap h5").html("<label style='color:red'>"+keyword+"</label>" +
                        "의 검색결과&nbsp;<span style='color:#adb5bd'>"+result.pageMaker.total+"개</>");


                    if(result.marketList.length == 0){
                        var str = "";
                        str += "<div style='text-align: center; line-height: 30px; margin-top:120px;'><h3 style='color:red'>"+keyword+"</h3>";
                        str += "<label style='font-size: 18px;'>에 대한 검색결과가 없습니다.</label><hr style='width:50%;margin-top:30px; color: #e5e5e5; margin-bottom:30px;'>";
                        str +=  "<label style='font-size: 14px;'>- 단어의 철자가 정확한지 확인해보세요.<br>" +
                            "- 보다 일반적인 검색어로 다시 검색해보세요.</label></div>";
                        $("#list-wrap").append(str);
                    }
                },error: function (jqXHR, textStatus, errorThrown) {
                    alert("error");
                },beforeSend:function(){
                    //$(".board-data").html("<img class='loading-img' src='/custom/img/loading-img.gif' align='center' >");

                },complete:function(){
                    $(".loading-img").remove();
                }
            });
        }


    }else {
        return false;
    }


}
// @상점이름으로 검색
function searchUserMarketName(keyword){
    var marketName = keyword.substring(1);
    var data = { "searchValue" : marketName };

    $.ajax({
        type: 'GET',
        url: '/market/search/userMarketName',
        data: data,
        dataType : 'json',
        success : function(result){
            console.log("상점이름 검색 : " + result);

             $("#list-wrap").empty();
            uMarketTotal = result.userMarketCnt;
            if(uMarketTotal == 0) {
                var str = "";
                str += "<div style='text-align: center; line-height: 30px; margin-top:120px;'><h3 style='color:red'>" + keyword + "</h3>";
                str += "<label style='font-size: 18px;'>에 대한 검색결과가 없습니다.</label><hr style='width:50%;margin-top:30px; color: #e5e5e5; margin-bottom:30px;'>";
                str += "<label style='font-size: 14px;'>- 단어의 철자가 정확한지 확인해보세요.<br>" +
                    "- 보다 일반적인 검색어로 다시 검색해보세요.</label></div>";
                $("#list-wrap").append(str);
            } else{
                $("#paging-wrap").hide();
                spreadUserMarketInfo(result.userMarketList);
                $("#categoryinfo-wrap h5").html("<label style='color:red'>"+keyword+"</label>" +
                    "의 검색결과&nbsp;<span style='color:#adb5bd'>"+result.userMarketCnt+"개</>");

                // if(uMarketTotal > (uMarketPageNumber+1)*2) {
                //     $("#userMarketlist-wrap").append("<button>더보기</button>");
                // }

            }

        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){
        },complete:function(){
            $(".loading-img").remove();
        }
    });
}

function spreadUserMarketInfo(data){


    $("#list-wrap").append("<div id='userMarketlist-wrap'><li></li></div>");
    $("#userMarketlist-wrap").show();

    for(var i = 0; i < data.length; i++) {
        var str = "";
        str += "<ul><i><img class='userMarket-Photo' src='" + data[i].photoUrl + "'></i>";
        str += "<div class='usermarketinfo-div'><span class='usermarket-title'>" + data[i].marketName + "</span><br>";
        str += "    <span class='item-cnt'>상품수<label>" + "110" + "</label></span>&nbsp;";
        str += "    <span class='follower-cnt'>팔로워<label>" + "19" + "</label></span>";
        str += "</div>  <button type='button' class='umarket-followBtn'>";
        str += "팔로우";
        str += "</button></ul>";

        $("#userMarketlist-wrap li").append(str);


    }
}
// 좌측 카테고리 클릭 시
$(".category-list").on('click','ul',function(e){
    var oper = $(this).data("oper");

    $("#categoryinfo-wrap h5").html(oper);
    $("#paging-category").val("CATEGORY");
    $("#paging-keyword").val(oper);

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
            spreadPaging(result.pageMaker);
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){
            //$(".board-data").html("<img class='loading-img' src='/custom/img/loading-img.gif' align='center' >");

        },complete:function(){
            $(".loading-img").remove();
        }
    });

});
// 상품 디테일 이동
$(document).on('click','.item-wrapper',function(e){
    var seq = $(this).data("oper");
    location.href = "/market/detail/" + seq;

});
// 페이징 클릭시
$(document).on('click','.page-item a',function(e){
    e.preventDefault();
    var pageNumber = $(this).attr("href");

    var data = { "pageNumber" : pageNumber,
                 "category" : $("#paging-category").val(),
                 "keyword" : $("#paging-keyword").val() };

    console.log(data);

    $.ajax({
        type: 'GET',
        url: '/market/list',
        //data: JSON.stringify(data),
        data: data,
        dataType : 'json',
        success : function(result){
            console.log(result);
            spreadMarketList(result);
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

function getXicon( val ){
    if(val.length) {
        $("#searchemtpy-btn").css("display", "inline");
    }else{
        $("#searchemtpy-btn").css("display", "none");
    }
}

function emptysearchinput(obj){
    $("#marketsearch-input").val("");
    $(obj).css("display", "none");
}

function makeMarket() {
    var sessionVal = $("#sessionUser").val();
    if(sessionVal == "") {
        $('#Login-Btn').click();
    }else{
        location.href="/market/open";
    }
}

// $("#marketsearch-input").autocomplete({
//     source : function(request, response) {
//         $.ajax({
//             url : "/market/autoSearching"
//             , type : "GET"
//             , data : {keyWord : $("#testInput").val()} // 검색 키워드
//             , success : function(data){ // 성공
//                 response(
//                     $.map(data, function(item) {
//                         return {
//                             label : item.testNm    //목록에 표시되는 값
//                             , value : item.testNm    //선택 시 input창에 표시되는 값
//                             , idx : item.testIdx    // db 인덱스를 담을수 있음 (예제)
//                         };
//                     })
//                 );    //response
//             }
//             ,
//             error : function(){ //실패
//                 alert("통신에 실패했습니다.");
//             }
//         });
//     }
//     , minLength : 1
//     , autoFocus : false
//     , select : function(evt, ui) {
//         console.log("전체 data: " + JSON.stringify(ui));
//         console.log("db Index : " + ui.item.idx);
//         console.log("검색 데이터 : " + ui.item.value);
//     }
//     , focus : function(evt, ui) {
//         return false;
//     }
//     , close : function(evt) {
//     }
// });

$(function(){
    $( "#marketsearch-input" ).autocomplete({
        source : function( request, response ) {
            //많이 봤죠? jquery Ajax로 비동기 통신한 후
            //json객체를 서버에서 내려받아서 리스트 뽑는 작업
            $.ajax({
                //호출할 URL
                url : "/market/autoSearching",
                type : "GET",
                dataType: "json",
                data: {
                    searchValue: request.term
                    // searchValue: $(this).val()
                },
                success: function( result ) {
                    // console.log(result);
                    //return 된놈을 response() 함수내에 다음과 같이 정의해서 뽑아온다.
                    response(
                        $.each( result, function( item ) {
                            return {
                                //label : 화면에 보여지는 텍스트
                                //value : 실제 text태그에 들어갈 값
                                //본인은 둘다 똑같이 줬음
                                //화면에 보여지는 text가 즉, value가 되기때문
                                label: item.data,
                                value: item.data

                            }
                        })
                    );
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("error");
                }

            });
        },
        //최소 몇자 이상되면 통신을 시작하겠다라는 옵션
        delay: 500,
        minLength: 3,
        //자동완성 목록에서 특정 값 선택시 처리하는 동작 구현
        //구현없으면 단순 text태그내에 값이 들어간다.
        select: function( event, ui ) {
            console.log(ui.item);
        }
    }).autocomplete( "instance" )._renderItem = function( ul, item ) {    //요 부분이 UI를 마음대로 변경하는 부분
        return $( "<li class='autocomplete-li'>" )    //기본 tag가 li로 되어 있음
            .append( "<div>" + item.label + "</div>" )    //여기에다가 원하는 모양의 HTML을 만들면 UI가 원하는 모양으로 변함.
            .appendTo( ul );
    };
})

function goUserMarket() {
    $.ajax({
        type: 'POST',
        url: '/market/getUserMarketSeq',
        //data: JSON.stringify(data),
        dataType : 'json',
        success : function(result){
            location.href = "/market/userMarket/" + result;
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        }
    });
}