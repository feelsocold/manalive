var uploadedImgCnt = 0;
var hashTagCnt = 0;


// 이미지 클릭시 파일버튼 클릭
$(function () {
    $('#upload-btn').click(function (e) {
        e.preventDefault();
        $('#hdn-uploadBtn').click();
    });
});

function asyncUpload(obj){
    var file = $("input[name='uploadFile']");
    var formData = new FormData();
    var files = file[0].files;

    if(files.length < 7 && (uploadedImgCnt+files.length) < 7) {
        for(var i = 0; i < files.length; i++) {
            formData.append("multipartFile", files[i]);
        }
        formData.append("category", "marketPhoto");
        console.log(files);

        $.ajax({
            url: '/s3Upload',
            processData: false,
            contentType : false,
            data: formData,
            type: 'POST',
            enctype: 'multipart/form-data',
            success : function(result){
                uploadedImgCnt += files.length;
                console.log("upload result : " + result);
                showUploadedImg(result);
            },error: function (jqXHR, textStatus, errorThrown) {

            },beforeSend:function(){
                $('button').attr('disabled', true);
                $('button').css('opacity', '0.2');
                $('input').attr('disabled', 'disabled');
                for(var i = 0; i < files.length; i++) {
                    $("#thumbnail-wrap ul").append("<li style='width:190px; height:190px;' class='loading-img'><img class='loading-img' src='/custom/img/loading-img.gif' style='width:150px; height:150px;'></li>");
                }

            },complete:function(){
                $(".loading-img").remove();
                $('button').css('opacity', '1.0');
                $('button').attr('disabled', false);
                $('input').attr('disabled', false);

            }
        });
    }else {
        alert("6개 초과!");
       // uploadedImgCnt -= files.length;
        return false;
    }
}
function showUploadedImg(result) {
    for(var i = 0; i < result.length; i++) {
        $("#thumbnail-wrap ul").append("<li><img src='"+ result[i] +"' onclick='showImage(this)' class='thumnail-image'>" +
            "<br><button class='thumb-Btn' id='thumb-delBtn' onclick='imgDelete(this)'><img style='width:15px; height:15px;' src='/custom/img/icon/icon-trashbin.png'></button>" +
            "<input type='file' onchange='updateUpload(this)' class='re-uploadBtn' name='uploadFile' style='display: none;' multiple>" +
            "&nbsp;<button class='thumb-Btn' id='thumb-modBtn' onclick='imgUpdate(this)'><img style='width:15px; height:15px;' src='/custom/img/icon/icon-modify.png'></button></li>");
    }

    if(uploadedImgCnt< 6) {
        $("#upload-btn").html("사진 추가하기 (" + uploadedImgCnt + "/6)");
        $("#upload-btn").css('height', '40px');
    }else {
        $("#upload-btn").hide();
    }
}
function imgDelete(obj) {
    var oper = $(obj).closest("li").index();
    //alert("index -------" + oper);

    $(obj).closest("li").remove();

    var data = { oper : oper,
                category : "marketPhoto" };
        $.ajax({
            url: '/s3Delete',
            data: data,
            type: 'POST',
            success : function(result){
                uploadedImgCnt--;
                $("#upload-btn").html("사진 추가하기 (" + uploadedImgCnt + "/6)");
                $("#upload-btn").css('height', '40px');
                $("#upload-btn").show();
            },beforeSend:function(){
                $('button').css('opacity', '0.2');
                $('button').attr('disabled', true);
                $('input').attr('disabled', 'disabled');
            },complete:function(){
                $('button').css('opacity', '1.0');
                $('button').attr('disabled', false);
                $('input').attr('disabled', false);
            }
        });
}
function getIndex(selector) {
    var elem = document.querySelector(selector);
    for(var i = 0; i < elem.parentNode.childNodes.length; i++) {
        if (elem.parentNode.childNodes[i] === elem) {
            console.log('elemIndex = ' + i);
        }
    }
}

function imgUpdate(obj) {
    //$('#re-uploadBtn').click();
    //$(obj).closest("input").click();
    $(obj).siblings('.re-uploadBtn').click();
}

function updateUpload(obj){
    var oper = $(obj).closest("li").index();

    var file = $(obj);
    var formData = new FormData();
    var files = file[0].files;

    console.log(files);
    for(var i = 0; i < files.length; i++) {
        formData.append("multipartFile", files[i]);
    }
    formData.append("category", "marketPhoto");
    formData.append("oper", oper);

        $.ajax({
            url: '/s3Update',
            processData: false,
            contentType : false,
            data: formData,
            type: 'POST',
            enctype: 'multipart/form-data',
            success : function(result){
                console.log(result);
                $(obj).siblings('img').attr('src', result);

            },error: function (jqXHR, textStatus, errorThrown) {

            },beforeSend:function(){
                $('button').css('opacity', '0.2');
                $('button').attr('disabled', true);
                $('input').attr('disabled', 'disabled');
                $(obj).siblings('img').attr('src', '/custom/img/loading-img.gif');

            },complete:function(){
                $('button').css('opacity', '1.0');
                $('button').attr('disabled', false);
                $('input').attr('disabled', false);
            }
        });



}

// $('.thumnail-image').on({
//     mouseenter: function(){ alert('mouse in'); },
//     mouseleave: function(){ alert('mouse out'); }
// },'span');
// $('#thumb-delBtn').on( 'click',  function(){ alert('click'); } );


// Get the modal
var modal = document.getElementById('img-Modal');
var span = document.getElementsByClassName("imgModal-close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
    // document.getElementById("Login-Email").value = "";
    // document.getElementById("Login-Password").value = "";
    document.getElementById("showImage-area").innerHTML = "";

}
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        document.getElementById("showImage-area").innerHTML = "";
    }
}

function showImage(obj) {
    var src = $(obj).attr("src");
    $("#showImage-area").append("<img style='width:90%; height:90%;' src='" + src + "'>");
    modal.style.display = "block";
}

// $(".hashTag-text").on('keydown', function(e){
//     var value = $(".hashTag-text").val();
//     $(".hasTag-span").append('<div id="virtual_dom">' + value + '</div>');
//     //실제 코드에는 상위돔에 집어넣어주세요.
//
//     var inputWidth =  $('#virtual_dom').width() + 10; // 글자 하나의 대략적인 크기
//
//     $(".hashTag-text").css('width', inputWidth);
//     $('#virtual_dom').remove();
// });

function keydownTitle(obj) {
    var value = $(obj).val();
    if(value.length < 31) {
    $("#title-textCnt").html(value.length); }
}
function keydownContent(obj) {
    var value = $(obj).val();
    if(value.length < 2001) {
        $("#title-ContentCnt").html(value.length);
    }
}
function numberFormat(inputNumber) {
    console.log(inputNumber);
    //return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    console.log(inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));

    var regNumber = /^[0-9]*$/;

    if(!regNumber.test(inputNumber))
    {
        console.log('숫자만 입력하세요');
        $("#price-input").val("");
        $("#price").val("");
    }else {
        $("#price-input").val(inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
        $("#price").val(inputNumber);
    }
}

function addHashTag(obj) {
    var str = "";
    str += "<div class='hashTag-div' style='display: inline-block'>";
    str += "<span class='size-span' style='display:none'></span><span class='hashTag-span'>#";
    str += "<input type='text' maxlength='13' name='hashtag' class='hashTag-text' onkeyup='keyUpHash(this)' onkeydown='keydownHash(this)' placeholder='키워드' size='5' ></span></div>";
    $(obj).closest('.hashTag-div').after(str);
    $('.hashTag-text').focus();
}

function keyUpHash(obj) {
    // 스페이스바 금지
    $(obj).val( $(obj).val().replace(/ /gi, '') );
    // 특수문자 금지
    $(obj).val( $(obj).val().replace(/[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi, '') );
    // 문자가 없을 때 input width 초기화
    if($(obj).val().length == 0){
        $(obj).width(46);
    }
}
function deleteHashtag(obj) {
    hashTagCnt--;
    if(hashTagCnt == 6) {addHashTag(obj);}
    $(obj).closest('.hashTag-div').remove();
}

// 게시물 등록
$('#sell-submitBtn').click(function (e) {
    e.preventDefault();

        var data = { "title" : document.getElementById("title").value,
                     "content" : document.getElementById("content").value,
                     "category" : document.getElementById("cate-selectbox").value,
                     "price" : document.getElementById("price").value,
                     "quantity" : document.getElementById("quantity").value,
                     "state" : $("input[name='state']:checked").val(),
                     "delivery" : $("input[name='delivery']:checked").val()
        };

        console.log(data);

        // let data = $("#boardForm").serialize();

        $.ajax({
            type: 'POST',
            url: '/market/post',
            contentType : "application/json; charset=utf-8",
            data: JSON.stringify(data),
            //data: data,
            dataType : 'json',
            success : function(result){
                console.log(result.boardSeq);
                //document.getElementById('confirm-Modal').show();

                $("#goDetail-Btn").attr("onclick", "location.href='/board/detail/"+result.seq +"'");

                $("#confirm-modal").show();

            },error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            },beforeSend:function(){


            },complete:function(){
                //alert("complete");
            }
        });

});

// select box
$(".dropdown img.flag").addClass("flagvisibility");

$(".dropdown dt a").click(function() {
    $(".dropdown dd ul").toggle();
});

$(".dropdown dd ul li a").click(function() {
    var text = $(this).html();
    $(".dropdown dt a span").html(text);
    $(".dropdown dd ul").hide();
    /* $("#result").html("Selected value is: " + getSelectedValue("sample"));*/
});

function getSelectedValue(id) {
    return $("#" + id).find("dt a span.value").html();
}

$(document).bind('click', function(e) {
    var $clicked = $(e.target);
    if (!$clicked.parents().hasClass("dropdown"))
        $(".dropdown dd ul").hide();
});

$(".dropdown img.flag").toggleClass("flagvisibility");