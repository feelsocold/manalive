var replyPageNumber = 0;
var seq = $("#seq").val();
var sessionVal = $("#sessionUser").val();
var sessionEmail = $("#sessionUser-email").val();
var sellingMarketSeq;
var sessionUserMarketSeq;

var inquiryDelayCnt = 0;
var inquiryPageNumber = 0;
var inquiryListSize = 0;

$(document).ready(function() {
    //var data = { "seq" : seq };

    $.ajax({
        type: 'POST',
        url: '/market/detail/' + seq,
        success : function(result){
            console.log(result);
            sellingMarketSeq = result.marketDto.seq;
            inquiryListSize = result.marketDto.marketInquiryList.length;
            sessionUserMarketSeq = result.sessionUserMarketSeq;
            spreadItemInfo(result.marketDto);
            spreadPhotolist(result.attachList);
            spreadMarketInquiryList(result.inquiryList);
            spreadUserMarket(result.userMarketDto);
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){

        },complete:function(){
        }
    });

    if(sessionVal != "") {
        var data = { "marketSeq" : seq};
        $.ajax({
            type: 'POST',
            url: '/market/wishCheck',
            data: JSON.stringify(data),
            dataType : 'json',
            contentType : "application/json; charset=utf-8",
            success : function(data){
                if(data.result){
                    $("#wish-btn").css({'background-color':'#35C5F0', color:'white' });
                    $("#wish-btn").html("찜 ✔");
                }
            },error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            }
        });
    }



});
function spreadItemInfo(data) {
    var productStatus = data.productStatus;
    var delivery = data.delivery;
    var price = data.price;
    $("#item-title").html(data.title);
    $("#item-cate").html(data.category);
    $("#item-price").html(numberFormat(price + "원"));

    if(data.email == sessionEmail){
        var str = "<div class='update-div'><button id='modify-btn'>수정</button><button id='delete-btn'>삭제</button></div>";
        $("#wish-btn").after(str);
    }

    if(productStatus == 'used'){
        $("#item-state").html("중고");
    }else if(productStatus == 'new'){
        $("#item-state").html("새상품");

    }
    if(delivery == 'free'){
        $("#item-delivery").html("무료배송");
    }else{
        $("#item-delivery").html("배송비 별도");

    }

    $("#product-content").html(data.content);
    autosize($("#product-content"));
}

function spreadPhotolist(data) {
    for(var i=0; i < data.length; i++) {
        $("#photolist-area ul").append("<li><img src='"+ data[i].url+"' onmouseover='changeMainPhoto(this)'></li>");
        if(i == 0){
            $("#main-photo").attr('src', data[i].url);
        }
    }
}

function spreadUserMarket(obj) {
    var len = obj.marketList.length;
    $("#alink-userMarket").attr("href", "/market/userMarket/" + obj.seq);

    $("#umarket-photo").attr("src", obj.marketPhoto);
    $("#umarket-name").html(obj.marketName);

    if(len == 1){
        $("#otherItmes-table").remove();
    }else{
        var end = (len>5)?5:len;
        for(var i=0; i < end; i++) {
            var other_seq = obj.marketList[i].seq;
            if(seq != other_seq){
                console.log(other_seq);
                getMainPhoto(other_seq);
                 end++;
            }
        }
    }

    if(len > 5){
        $("#other-product").append("<div class='other-eachProduct'><button id='other-moreBtn'>더보기</button></div>");
    }
}
// 상품 문의 리스트 뿌리기
function spreadMarketInquiryList(data) {
    console.log(data);

    for(var i=0; i < data.length; i++) {
        var str = "";
        str += "<div class='each-inquiry'><div class='inquiry-marketphotowrap'>";
        str += "<img src='" + data[i].userMarketPhoto + "'></div>";
        str += "<div class='inquiry-textwrap'><div class='eachinquiry-marketnameanddate'>";
        str += "<span class='eachinquiry-marketname'>" + data[i].userMarketName +"</span>" ;
        str += "<span class='eachinquiry-regdate'>" + timeForToday(data[i].createDate) +"</span></div>" ;
        str += "<textarea class='eachinquiry-content' readonly>" + data[i].content + "</textarea>";

        if(sessionUserMarketSeq == data[i].userMarketSeq){
            str += "<button class='inquiry-delbtn' value='"+data[i].inquirySeq+"'>삭제하기</button>";
        }

        if(sessionUserMarketSeq == sellingMarketSeq){
            str += "<button class='inquiry-answerbtn' value='"+data[i].inquirySeq+"'>답변하기</button>";
        }

        str += "</div></div>";
        $("#inquirycontent-wrap").append(str);
    }

    // alert(sessionUserMarketSeq);

    if(inquiryListSize> 5*(inquiryPageNumber+1) ){
        var str = "<button id='moreinquiry-btn\'><img src=\'/custom/img/icon/icon-plus-gray.png\'>상품문의 더보기\</button>";
        $("#inquirycontent-wrap").append(str);
    }
}
function changeMainPhoto(obj) {
    var url = $(obj).attr("src");
    $("#main-photo").attr('src', url);
}
function numberFormat( value ) {
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
function getMainPhoto(seq) {
    var data = { "seq" : seq,
        "category" :  "MARKET"
    };
    var str = "";
    var arr = "";
    arr = arr + seq + ",";
        $.ajax({
            type: 'POST',
            url: '/attach/getMainPhoto',
            data: data,
            success : function(result){
                str += ("<div class='other-eachProduct'><a href='"+seq+"'><img src='"+ result+"'></div></a>");
                //$("#other-product").append("<div class='other-eachProduct'><img src='"+ result+"'></div>");
            },error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            },beforeSend:function(){

            },complete:function(){

                $("#other-product").append(str);
                console.log(arr);

            }
        });
}

$(document).on('click','.other-eachProduct a',function(e){
    e.preventDefault();
    var market_seq = $(this).attr("href");
    location.href = "/market/detail/" + market_seq;
});

// 찜하기 or 찜해제
function saveOrdeleteWish(obj){
    var data = { "marketSeq" : seq};
    if(sessionVal != "") {
        $.ajax({
            type: 'POST',
            url: '/market/wish',
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.result == 'SAVE') {
                    $("#wish-btn").css({'background-color': '#35C5F0', color: 'white'});
                    $("#wish-btn").html("찜  ✔");
                } else if (data.result == 'DELETE') {
                    $("#wish-btn").css({'background-color': 'white', color: '#35C5F0'});
                    $("#wish-btn").html("찜하기");
                }
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            }, beforeSend: function () {
                $(obj).attr('disabled', true);
            }, complete: function () {
                $(obj).attr('disabled', false);
            }
        });
    }else{
        $('#Login-Btn').click();
    }
}
// 상풍문의 글자수 체크
function keydownInquiry(obj) {
    var value = $(obj).val();
    if(value.length < 102) {
        $("#inquiry-textCnt").html(value.length); }
}
// 상품문의 등록
function saveInquiry() {

    $("#inquirycontent-wrap").empty();
    var data = { "content" : $("#inquiry-content").val(),
                "marketProductSeq" : seq};

    if(sessionVal != "") {
        $.ajax({
            type: 'POST',
            url: '/market/saveMarketInquiry',
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                inquiryListSize++;
                inquiryPageNumber = 0;
                inquiryDelayCnt =0;
                getInquiryList();
                $("#inquiry-content").val('');
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            }, beforeSend: function () {

            }, complete: function () {

            }
        });
    }else{
        $('#Login-Btn').click();
    }
}

function getInquiryList() {

        $.ajax({
            type: 'POST',
            url: '/market/inquiry/list/' + seq + "/" + inquiryPageNumber + "/" + inquiryDelayCnt,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                spreadMarketInquiryList(data);
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("!error!");
            }
        });
}
// 상품문의 더보기 클릭
$(document).on('click','#moreinquiry-btn',function(e) {
    $(this).remove();
    inquiryPageNumber++;
    getInquiryList();
});
// 상품문의 삭제하기
$(document).on('click','.inquiry-delbtn',function(e){
    var obj = $(this);
    var inquirySeq = obj.val();

    inquiryDelayCnt++;
    $.ajax({
        type: 'POST',
        url: '/market/inquiry/delete/' + inquirySeq,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function (data) {

        }, error: function (jqXHR, textStatus, errorThrown) {
            alert("!error!");
        },complete:function(){
            obj.closest('.each-inquiry').remove();
        }
    });

});

$(document).on('click','.inquiry-answerbtn',function(e){
    var inquirySeq = $(this).val();

    $('.answer-wrap').remove();
    $('.inquiry-answerbtn').show();
    $(this).after("<div class='answer-wrap'><textarea class='answer-textarea'></textarea><button class='inquiryanswer-regBtn' value="+inquirySeq+">답변 등록</button></div>");
    $(this).hide();

});

$(document).on('click','.inquiryanswer-regBtn',function(e){
    var obj = $(this);
    var inquirySeq = obj.val();
    var content = obj.siblings('.answer-textarea');
    var data = { "inquirySeq" : inquirySeq,
                "content" : content.val() };



    if(content != "") {
        $.ajax({
            type: 'POST',
            url: '/market/saveMarketInquiryAnswer',
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                console.log(data);
            }, error: function (jqXHR, textStatus, errorThrown) {
                alert("error~~");
            },complete:function(){
                var src = $("#umarket-photo").attr('src');
                var marketname = $("#umarket-name").html();
                var str = "";
                str += "<div style='display: inline-block'><img src='"+src+"' style='width: 45px; height: 45px; border-radius: 50%'>&nbsp;";
                str += "<span>"+marketname+"</span>"
                str += "</div>";
                obj.closest('.answer-wrap').before(str);
                content.attr('readonly','readonly');
                content.css("border", "none");
                obj.remove();
            }
        });
    }else{
        alert("답변을 입력해주세요.");
    }




});


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