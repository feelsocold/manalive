var seq = $("#user_seq").val();
var sessionVal = $("#sessionUser").val();
var sessionEmail = $("#sessionUser-email").val();
var userMarketEmail = "";
var userMarketSeq = 0;
var followerPageNumber = 0;

$(document).ready(function() {
    var data = { "seq" : seq };
    $.ajax({
        type: 'POST',
        url: '/market/userMarket',
        data: data,
        success : function(result){
            console.log(result);
            spreadUserMarketInfo(result);
            spreadMarketList(result);
            spreadPaging(result.pageMaker);
            if(sessionVal != "") {
                checkUserMarketFollow();
            }
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){

        },complete:function(){
        }
    });
});
function checkUserMarketFollow(){

    var data = { "userMarketSeq" : userMarketSeq};
    $.ajax({
        type: 'POST',
        url: '/market/checkUserMarketFollow',
        data: data,
        dataType : 'json',
        //contentType : "application/json; charset=utf-8",
        success : function(data){
            if(data === true) {
                $("#usermarket-followBtn").css({'background-color':'white', color:'#35C5F0' });
                $("#usermarket-followBtn").html("팔로잉 ✔");
            }
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        }
    });
}

function spreadMarketList( data ) {
    var list = data.marketList;
    var attachList = data.attachList;
    $("#list-wrap").empty();
    // LIST 뿌리기
    for(var i = 0; i < list.length; i++) {
        var str = "";
        str += "<div class='item-wrapper' data-oper='"+list[i].seq+"'>";
        str += "<div class='item-image'><img src='"+attachList[i]+"'></div>";
        str += "<div class='item-info'>";
        str += "<span class='item-title'>"+list[i].title+"</span><br>";
        str += "<span class='item-price'>"+numberFormat(list[i].price)+" 원</span><br>";
        str += "<span class='item-regdate'>"+timeForToday(list[i].createDate)+"</span><hr>";
        str += "<span class='item-readcnt'>조회 <label>"+ list[i].readCount+"</label></span>";
        str += "<span class='item-wishcnt'>&nbsp;&sdot;&nbsp;찜 <label>"+list[i].marketWishList.length+"</label></span>";
        str += "</div>";
        $("#list-wrap").append(str);
    }
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
// 통화 변환
function numberFormat( value ) {
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
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
function spreadUserMarketInfo(data) {

    var dto = data.userMarketInfo;
    userMarketEmail = dto.email;
    userMarketSeq = dto.seq;
    $("#usermarket-title").html(dto.marketName);
    $("#usermarket-introduction").html(dto.introduction);
    $("#item-cnt").html(data.pageMaker.total);
    $("#usermarket-photo").attr('src', dto.photoUrl);
    $("#usermarket-createDate").html(timeForTodayForCreateDate(dto.createDate));
    $("#usermarket-visitedCnt").html(dto.visitedGuestCnt+"명");
    $("#follow-cnt").html(dto.followerCnt);

    if(sessionEmail == dto.email){
        $("#usermarket-followBtn").remove();
        $("#usermarket-manageBtn").show();
    }
}

function timeForTodayForCreateDate(value) {
    const today = new Date();
    const timeValue = new Date(value);
    const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
    const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
    if (betweenTimeDay == 0) {
        return '오늘';
    }else{
        return `${betweenTimeDay}일 전`;
    }

}
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
        "category" : "EMAIL",
        "keyword" : userMarketEmail };

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

// 팔로우 클릭1
$(document).on('click','#usermarket-followBtn',function(e){
    var sessionVal = $("#sessionUser").val();
    if(sessionVal == "") {
        $('#Login-Btn').click();
    }else {

        $.ajax({
            type: 'POST',
            url: '/market/checkHavingUserMarket',
            dataType: 'json',
            success: function (result) {
                if(!result){
                    alert("상점을 먼저 개설해야 합니다!");
                }else{
                    followUserMarket();
                }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            }
        });
    }
});
// 팔로우 클릭2
function followUserMarket() {
    var followerCnt = $("#follow-cnt").html();

    $.ajax({
        type: 'POST',
        url: '/market/follow/' + userMarketSeq,
        dataType: 'json',
        success: function (result) {
            if(result) {
                $("#usermarket-followBtn").css({'background-color':'white', color:'#35C5F0' });
                $("#usermarket-followBtn").html("팔로잉 ✔");
                $("#follow-cnt").html(Number(followerCnt) + 1);
            }else{
                $("#usermarket-followBtn").css({'background-color':'#35C5F0', color:'white' });
                $("#usermarket-followBtn").html("팔로우");
                $("#follow-cnt").html(Number(followerCnt) - 1);

            }
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        }
    });
}
// [상품] 클릭
$(document).on('click','.productlist-navBtn a ',function(e){
    e.preventDefault();
    $(".usermarket-nav ul li").css({"border": "none"});
    $(".usermarket-nav ul li a").css("color", "black");
    $(".content-wrap").hide();
    $("#productlist-wrap").show();
    $(this).closest("li").css("border-bottom", "4px solid #35C5F0");
    $(this).css("color", "#35C5F0");
});
// [팔로워] 클릭
$(document).on('click','.follower-btn a ',function(e){
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: '/market/follower/' + userMarketSeq,
            dataType: 'json',
            success: function (result) {
                console.log(result);
                $("#followlist-content").empty();
                $(".usermarket-nav ul li").css({"border": "none"});
                $(".usermarket-nav ul li a").css("color", "black");
                $(".follower-btn").css("border-bottom", "4px solid #35C5F0");
                $(".follower-btn a").css("color", "#35C5F0");
                $(".content-wrap").hide();
                spreadFollowerFollowingList(result, "follower");
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            }
        });
});
// [팔로잉] 클릭
$(document).on('click','.following-btn a ',function(e){
    e.preventDefault();

    $.ajax({
        type: 'POST',
        url: '/market/following/' + userMarketSeq,
        dataType: 'json',
        success: function (result) {
            console.log(result);
            $("#followlist-content").empty();
            $(".usermarket-nav ul li").css({"border": "none"});
            $(".usermarket-nav ul li a").css("color", "black");
            $(".following-btn").css("border-bottom", "4px solid #35C5F0");
            $(".following-btn a").css("color", "#35C5F0");
            $(".content-wrap").hide();
            spreadFollowerFollowingList(result, "following");
        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        }
    });
});
function spreadFollowerFollowingList(data, str){
    var list;
    if(str == 'follower'){
        list = data.followerList;
    }else if(str == 'following') {
        list = data.followingList;
    }
    for(var i = 0; i < list.length; i++) {
        var str = "";
        str += "<div class='follow-wrapper' data-oper='"+list[i].seq+"'>";
        str += "<div class='follower-image'><img src='"+list[i].photoUrl+"'></div>";
        str += "<div class='follower-info'>";
        str += "<span class='follower-marketname'>"+list[i].marketName+"</span><br>";
        str += "<span class='info-smallfont'>상품&nbsp;<label class='f-itemcnt'>"+list[i].productCnt+"</label>&nbsp;&nbsp;<label style='font-size: 10px;'>&#124;</label>&nbsp;&nbsp;</span>" +
            "<span class='info-smallfont'>팔로워&nbsp;<label class='f-followercnt'>"+list[i].followerCnt+"</label></span>"
        str += "</div>";
        $("#followlist-content").append(str);
    }
    $("#follow-wrap").show();
}
// 유저상점으로 이동
$(document).on('click','.follow-wrapper',function(e){
    var seq = $(this).data("oper");
    location.href = "/market/userMarket/" + seq;

});


// function spreadItemInfo(data) {
//     var state = data.state;
//     var delivery = data.delivery;
//     var price = data.price;
//     $("#item-title").html(data.title);
//     $("#item-cate").html(data.category);
//     $("#item-price").html(numberFormat(price + "원"));
//
//     if(data.email == sessionEmail){
//         var str = "<div class='update-div'><button id='modify-btn'>수정</button><button id='delete-btn'>삭제</button></div>";
//         $("#wish-btn").after(str);
//     }
//
//     if(state == 'used'){
//         $("#item-state").html("중고");
//     }else if(state == 'new'){
//         $("#item-state").html("새상품");
//
//     }
//     if(delivery == 'free'){
//         $("#item-delivery").html("무료배송");
//     }else{
//         $("#item-delivery").html("배송비 별도");
//
//     }
//
//     $("#product-content").html(data.content);
//     autosize($("#product-content"));
// }
//
// function spreadPhotolist(data) {
//     for(var i=0; i < data.length; i++) {
//         $("#photolist-area ul").append("<li><img src='"+ data[i].url+"' onmouseover='changeMainPhoto(this)'></li>");
//         if(i == 0){
//             $("#main-photo").attr('src', data[i].url);
//         }
//     }
// }
//
// function spreadUserMarket(obj) {
//     var len = obj.marketList.length;
//
//     $("#umarket-photo").attr("src", obj.marketPhoto);
//     $("#umarket-name").html(obj.marketName);
//
//     if(len == 1){
//         $("#otherItmes-table").remove();
//     }else{
//         var end = (len>5)?5:len;
//         for(var i=0; i < end; i++) {
//             var other_seq = obj.marketList[i].seq;
//             if(seq != other_seq){
//                 console.log(other_seq);
//                 getMainPhoto(other_seq);
//                  end++;
//             }
//         }
//     }
//
//     if(len > 5){
//         $("#other-product").append("<div class='other-eachProduct'><button id='other-moreBtn'>더보기</button></div>");
//     }
// }
//
// function changeMainPhoto(obj) {
//     var url = $(obj).attr("src");
//     $("#main-photo").attr('src', url);
// }
// function numberFormat( value ) {
//     return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
// }
// function getMainPhoto(seq) {
//     var data = { "seq" : seq,
//         "category" :  "MARKET"
//     };
//     var str = "";
//     var arr = "";
//     arr = arr + seq + ",";
//         $.ajax({
//             type: 'POST',
//             url: '/attach/getMainPhoto',
//             data: data,
//             success : function(result){
//                 str += ("<div class='other-eachProduct'><a href='"+seq+"'><img src='"+ result+"'></div></a>");
//                 //$("#other-product").append("<div class='other-eachProduct'><img src='"+ result+"'></div>");
//             },error: function (jqXHR, textStatus, errorThrown) {
//                 alert("error");
//             },beforeSend:function(){
//
//             },complete:function(){
//
//                 $("#other-product").append(str);
//                 console.log(arr);
//
//             }
//         });
// }
//
// $(document).on('click','.other-eachProduct a',function(e){
//     e.preventDefault();
//     var market_seq = $(this).attr("href");
//     location.href = "/market/detail/" + market_seq;
// });
//
// function saveOrdeleteWish(obj){
//     var data = { "marketSeq" : seq};
//     if(sessionVal != "") {
//         $.ajax({
//             type: 'POST',
//             url: '/market/wish',
//             data: JSON.stringify(data),
//             dataType: 'json',
//             contentType: "application/json; charset=utf-8",
//             success: function (data) {
//                 if (data.result == 'SAVE') {
//                     $("#wish-btn").css({'background-color': '#35C5F0', color: 'white'});
//                     $("#wish-btn").html("찜  ✔");
//                 } else if (data.result == 'DELETE') {
//                     $("#wish-btn").css({'background-color': 'white', color: '#35C5F0'});
//                     $("#wish-btn").html("찜하기");
//                 }
//             }, error: function (jqXHR, textStatus, errorThrown) {
//                 alert("error");
//             }, beforeSend: function () {
//                 $(obj).attr('disabled', true);
//             }, complete: function () {
//                 $(obj).attr('disabled', false);
//             }
//         });
//     }else{
//         $('#Login-Btn').click();
//     }
//
// }