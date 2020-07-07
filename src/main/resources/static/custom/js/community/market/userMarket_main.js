var replyPageNumber = 0;
var seq = $("#seq").val();
var sessionVal = $("#sessionUser").val();
var sessionEmail = $("#sessionUser-email").val();
var replyDelayCnt = 0;
var replyCnt = 0;
var likeCnt = 0;

$(document).ready(function() {
    //var data = { "seq" : seq };
    $.ajax({
        type: 'POST',
        url: '/market/userMarket',
        dataType : 'json',
        success : function(result){
            console.log(result);
            spreadMarketList(result);
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){

        },complete:function(){
        }
    });
});

function spreadMarketList( data ) {
    var list = data.marketList;
    var attachList = data.attachList;

    //alert(list.length);

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
    //}
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