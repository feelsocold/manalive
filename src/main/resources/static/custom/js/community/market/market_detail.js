var replyPageNumber = 0;
var seq = $("#seq").val();
var sessionEmail = $("#sessionUser-email").val();
var replyDelayCnt = 0;
var replyCnt = 0;
var likeCnt = 0;

$(document).ready(function() {
    //var data = { "seq" : seq };

    $.ajax({
        type: 'POST',
        url: '/market/detail/' + seq,
        // data: data,
        success : function(result){
            console.log(result);
            spreadItemInfo(result.marketDto);
            spreadPhotolist(result.attachList);
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){

        },complete:function(){
        }
    });
});
function spreadItemInfo(data) {
    var state = data.state;
    var delivery = data.delivery;
    var price = data.price;
    $("#item-title").html(data.title);
    $("#item-cate").html(data.category);
    $("#item-price").html(numberFormat(price + "원"));

    if(state == 'used'){
        $("#item-state").html("중고");
    }else if(state == 'new'){
        $("#item-state").html("새상품");

    }
    if(delivery == 'free'){
        $("#item-delivery").html("무료배송");
    }else{
        $("#item-delivery").html("배송비 별도");

    }
}

function spreadPhotolist(data) {
    for(var i=0; i < data.length; i++) {
        $("#photolist-area ul").append("<li><img src='"+ data[i].url+"' onmouseover='changeMainPhoto(this)'></li>");
        if(i == 0){
            $("#main-photo").attr('src', data[i].url);
        }
    }
}
function changeMainPhoto(obj) {
    var url = $(obj).attr("src");
    $("#main-photo").attr('src', url);
}
function numberFormat( value ) {
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


