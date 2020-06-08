var seq = $("#seq").val();
var uploadedImgCnt = 0;
var hashTagCnt = 0;

$(document).ready(function() {
    var data = { "seq" : seq };

    $.ajax({
        type: 'POST',
        url: '/boardDetail',
        data: data,
        success : function(result){
            console.log(result);
            spreadDto(result);
            uploadedImgCnt = result.attachList.length;
            loadUploadedImg(result.attachList);
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){

        },complete:function(){
        }
    });
});

function spreadDto(result) {
    var dto = result.boardDto;
    $("#title").val(dto.title);
    $("#content").val(dto.content);

    var hashtags = dto.hashtags.split(',');
    for ( var i in hashtags ) {
        if(hashtags[i] != ' ' && hashtags[i] != ''){
            hashTagCnt++;
            var str = "";
            str += "<div class=\"hashTag-div\" style=\"display: inline-block\"><span class=\"hashTag-span\">#";
            str += "<input type='text' maxlength='10' class='hashTag-text' name='hashtag' size='auto' readonly value='"+hashtags[i]+"'>";
            str += "<img class='x-icon' onclick='deleteHashtag(this)' src='/custom/img/icon/icon-x.png'></span></div>";
            $("#hashTag-wrap").append(str);
        }
    }
    if(hashTagCnt < 7) {
       // alert(hashTagCnt);
        var str2 = addHashTag();
        $("#hashTag-wrap").append(str2);
        $('.hashTag-text').focus();
    }
}

function controlImgCnt() {
    if(uploadedImgCnt< 6) {
        $("#upload-btn").html("사진 추가하기 (" + uploadedImgCnt + "/6)");
        $("#upload-btn").css('height', '40px');
    }else {
        $("#upload-btn").hide();
    }
}

function loadUploadedImg(result) {
    console.log(result);

    for(var i = 0; i < result.length; i++) {
        $("#thumbnail-wrap ul").append("<li><img src='"+ result[i].url +"' onclick='showImage(this)' class='thumnail-image'>" +
            "<br><button class='thumb-Btn' id='thumb-delBtn' onclick='imgDelete(this)' value='"+result[i].att_no+"'><img style='width:15px; height:15px;' src='/custom/img/icon/icon-trashbin.png'></button>" +
            "<input type='file' onchange='updateUpload(this)' class='re-uploadBtn' name='uploadFile' style='display: none;' multiple>" +
            "&nbsp;<button class='thumb-Btn' id='thumb-modBtn' onclick='imgUpdate(this)' value='"+result[i].att_no+"'><img style='width:15px; height:15px;' src='/custom/img/icon/icon-modify.png'></button></li>");
    }2
    controlImgCnt();

}
function showUploadedImg(result) {
    for(var i = 0; i < result.length; i++) {
        $("#thumbnail-wrap ul").append("<li><img src='"+ result[i] +"' onclick='showImage(this)' class='thumnail-image'>" +
            "<br><button class='thumb-Btn' id='thumb-delBtn' onclick='imgDelete(this)'><img style='width:15px; height:15px;' src='/custom/img/icon/icon-trashbin.png'></button>" +
            "<input type='file' onchange='updateUpload(this)' class='re-uploadBtn' name='uploadFile' style='display: none;' multiple>" +
            "&nbsp;<button class='thumb-Btn' id='thumb-modBtn' onclick='imgUpdate(this)'><img style='width:15px; height:15px;' src='/custom/img/icon/icon-modify.png'></button></li>");
    }
    controlImgCnt();
}



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
    //alert(files.length);
    //alert(uploadedImgCnt);

    if(files.length < 7 && (uploadedImgCnt+files.length) < 7) {
        for(var i = 0; i < files.length; i++) {
            formData.append("multipartFile", files[i]);
        }
        formData.append("category", "boardPhoto");
        formData.append("b_seq", seq);
        console.log(files);

        $.ajax({
            url: '/modifyBoardAttachUpload',
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
                    $("#thumbnail-wrap ul").append("<li style='width:151px; height:181px;' class='loading-img'><img class='loading-img' src='/custom/img/loading-img.gif' style='width:150px; height:150px;'></li>");
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

function imgDelete(obj) {
    var oper = $(obj).closest("li").index();
    //alert("index -------" + oper);

    $(obj).closest("li").remove();

    var data = { oper : oper,
                category : "boardPhoto",
                att_no : $(obj).val()
    };
        $.ajax({
            url: '/modifyBoardAttachDelete',
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
    var att_no = $(obj).siblings("#thumb-modBtn").val();
    //alert(att_no);

    console.log(files);
    for(var i = 0; i < files.length; i++) {
        formData.append("multipartFile", files[i]);
    }
    formData.append("category", "boardPhoto");
    formData.append("oper", oper);
    formData.append("b_seq", seq);
    formData.append("att_no", att_no);


        $.ajax({
            url: '/modifyBoardAttachUpdate',
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
$('#thumb-delBtn').on( 'click',  function(){ alert('click'); } );


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

function keydownHash(obj) {
    var value = $(obj).val();
    $('.size-span').text(value);

    var len = $('.size-span').outerWidth();
    var inputSize = $(obj).width();

    var inputLen = value.length;
    //console.log(size);

    if(len > inputSize){
        //$(obj).width(len+8);
        $(obj).width(len+3);
    }else {
        if(inputSize > 57 ){
            $(obj).width(len+10);
        }
    }
    var imgobj = $(obj).siblings('img');
    console.log(imgobj.length);
    // 엔터키 눌렀을 때 동작
    if(event.keyCode == 13) {
        if($(obj).val().length > 0 && imgobj.length == 0){
            hashTagCnt++;
            $(obj).attr('readonly', 'readonly');
            $(obj).after("<img class='x-icon' onclick='deleteHashtag(this)' src='/custom/img/icon/icon-x.png'>");
            alert(hashTagCnt);
            if(hashTagCnt < 7) {
                var str = addHashTag();
                $(obj).closest('.hashTag-div').after(str);
                $('.hashTag-text').focus();

            }
        }
    }
}

function addHashTag() {
    var str = "";
    str += "<div class='hashTag-div' style='display: inline-block'>";
    str += "<span class='size-span' style='display:none'></span><span class='hasTag-span'>#";
    str += "<input type='text' maxlength='13' name='hashtag' class='hashTag-text' onkeyup='keyUpHash(this)' onkeydown='keydownHash(this)' placeholder='키워드' size='5' ></span></div>";
    return str;

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
    alert(hashTagCnt);
    if(hashTagCnt < 7 ) {
        var str = addHashTag();
        $("#hashTag-wrap").append(str);
        $('.hashTag-text').focus();
    }
    $(obj).closest('.hashTag-div').remove();
}

// 게시물 수정
$('#board-submitBtn').click(function (e) {
    e.preventDefault();
    var hashtags = "";
    $("input[name='hashtag']").each(function (i) {
        if( $("input[name='hashtag']").eq(i).val() != '' && $("input[name='hashtag']").eq(i).val() != ' ') {
            hashtags += $("input[name='hashtag']").eq(i).val() + ",";
        }
    });
    console.log(hashtags);
    $("input[name='hashtags']").val(hashtags);

    //$("#boardForm").submit();

        var data = { "seq" : seq,
                      "title" : document.getElementById("title").value,
                     "content" : document.getElementById("content").value,
                     "hashtags" : hashtags
        };

        //let data = $("#boardForm").serialize();

        $.ajax({
            type: 'POST',
            url: '/board/update',
            contentType : "application/json; charset=utf-8",
            data: JSON.stringify(data),
            //data: data,
            dataType : 'json',
            success : function(result){
                console.log(result.boardSeq);
                //document.getElementById('confirm-Modal').show();

                location.href="/board/detail/" + seq;

            },error: function (jqXHR, textStatus, errorThrown) {
                alert("error");
            },beforeSend:function(){


            },complete:function(){
                //alert("complete");
            }
        });

});

// Get the modal
var confirm_modal = document.getElementById('confirm-Modal');



