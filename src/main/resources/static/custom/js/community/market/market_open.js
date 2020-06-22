$(document).ready(function() {
    var phone = $('[name="phone"]');
    phone.blur(function(){

        if(phone.val().length != 11){
            $(".phone-authentication").remove();
            $(".caution-div").remove();
            phone.css({'border': 'solid 1px #FE0000'});
            phone.after("<br><div class='caution-div' style='border: 0px solid greenyellow; display: inline-block;'><label class='caution-phone'>올바른 휴대폰 번호를 입력해주세요.</label></div>");
        }else {
            $.ajax({
                type: 'post',
                url: '/duplicatePhoneCheck',
                data: {"phone": phone.val()},
                success: function (result, status, xhr) {

                    // 중복일 경우
                    if(result) {
                        $(".caution-div").remove();
                        phone.css({'border': 'solid 1px #FE0000'});
                        phone.after("<div class='caution-div' style='border: 0px solid greenyellow; display: inline-block;'><label class='caution-phone'>인증이 불가능한 번호입니다. 입력하신 번호를 확인해주세요.</label></div>");
                        $(".phone-authentication").remove();
                        // 최초 가입
                    }else{
                        if(!document.getElementById("Phone-Authentication")){
                            $(".caution-div").remove();
                            phone.css({'border': 'solid 1px #e5e5e5'});
                            phone.after("<div id='Phone-Authentication' class='phone-authentication'>" +
                                "<input type='button' id='sms-sendBtn' value='인증번호 전송하기 '>" +
                                "<input type='number' id='authentic-number' placeholder='인증번호를 입력해주세요.'>" +
                                "<input type='button' id='authentic-confirmBtn' value='확인'></div>");
                            $("#info-wrap").css({'margin-top': '30px'});

                        }else {
                            return false;
                        }
                    }
                },
                error: function (xhr, status, er) {
                }
            });
        }
    });

    var numberSix;
    $(document).on("click", "#sms-sendBtn", function(){

        $(this).attr("disabled", true);
        document.getElementById("authentic-number").focus();

        var to = phone.val();

        console.log("이메일 주소 : " + to);

        $.ajax({
            type : 'post',
            url : '/smsAuthentication_send',
            data : {"to" : to },
            success : function(result, status, xhr){
                numberSix = result;
                console.log(numberSix);
            },
            error : function(xhr, status, er) {

            }
        })
    });

    $(document).on("click", "#authentic-confirmBtn", function(){
        var inputSix = $("#authentic-number");
        if(numberSix == inputSix.val()) {
            $(this).attr("disabled", true);
            inputSix.attr("readonly", true);
            inputSix.css({'color': '#999999'});
            phone.attr("readonly", true);
            phone.css({'color': '#999999'});
            $("#sms-sendBtn").val("인증완료");
            $("#sms-sendBtn").css({'color': '#4742DB'});
            $('[name="name"]').attr('disabled', false);
            $("#profileImg-btn").attr('disabled', false);
            $("#profile-img").css({'background': 'none'});
            $('[name="name"]').focus();
            // $("#info-wrap").show();

        }else{
            alert("일치하지 않습니다.");
        }
    });
// 버튼 클릭시 파일업로드 클릭
    $(function () {
        $('#profileImg-btn').click(function (e) {
            e.preventDefault();
            $('#hdn-uploadBtn').click();
        });
    });
});

// ajax 파일업로드
function asyncUpload(value){
    //var file = $('#hdn-uploadBtn')[0].files[0];
    var file = $("input[name='uploadFile']");
    var formData = new FormData();
    var files = file[0].files;

    //formData.append('data', file);
    for(var i = 0; i < files.length; i++) {
        formData.append("multipartFile", files[i]);
    }
    formData.append("category", "marketProfile_Photo");
    console.log(files);

    $.ajax({
        url: '/s3Upload',
        processData: false,
        contentType : false,
        data: formData,
        type: 'POST',
        enctype: 'multipart/form-data',
        success : function(result){
            console.log(result);
            $("#market-photo").val(result[0]);
        }
    }).done(function (data) {
        if(value.files && value.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#profile-img').attr('src', e.target.result);
            }
            reader.readAsDataURL(value.files[0]);
        };
        $("#profileImg-btn").val("프로필사진 바꾸기");
        $("#profile-img").css({'padding': '2%'});
        //$("#profileImg-btn").css({'color': '#999999'});


    });
}; // -- end asyncUpload function

// 게시물 등록
$('#openMarket-submitBtn').click(function (e) {
    e.preventDefault();

    //$("#boardForm").submit();

    var data = { "phone" : document.getElementById("input-phone").value,
        "marketName" : document.getElementById("market-name").value,
        "marketPhoto" : document.getElementById("market-photo").value
    };

    console.log(data);

    //let data = $("#boardForm").serialize();

    $.ajax({
        type: 'POST',
        url: '/market/open',
        contentType : "application/json; charset=utf-8",
        data: JSON.stringify(data),
        //data: data,
        dataType : 'json',
        success : function(result){
            console.log(result);
            alert(result.result);

            if(result.result != null){
                location.href="/market";
            }

        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){


        },complete:function(){
            //alert("complete");
        }
    });

});