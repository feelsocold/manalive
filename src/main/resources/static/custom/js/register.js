$(document).ready(function() {

    var formObj = $("form");
    $('.reg-btn').on("click", function(e){
        e.preventDefault();
        var operation = $(this).data("oper");

        if(operation == 'social') {
            document.getElementById("oper").value = operation;
        }
        formObj.submit();

    })

// 이메일 중복 체크
    var bool = false;
    function duplicate_check() {
        $.ajax({
            type : 'post',
            url : '/duplicate_check',
            data : {"email" : email.val() },
            success : function(result, status, xhr){
                email_check = result;
                bool = result;
                console.log("result: " + result);
                console.log("bool: " + bool);
            },
            error : function(xhr, status, er) {
            }
        });
        return bool;
    }

// 이메일주소 정규화 체크
    var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식
    var email = $("#Email-Input");
    var caution_email = $(".caution-email");
    var email_authen = $(".email-authentication");

    var email_check = false;

    email.blur(function(){
        if(email.val().length > 0) {
            if ( !emailRule.test(email.val()) ) {
                if(caution_email.length == 0  || caution_email.data("oper") == 'nothing') {
                    $(".email-authentication").remove();
                    $(".caution-email").remove();
                    email.css({'border': 'solid 1px #FE0000'});
                    email.after("<span class='caution-email'>이메일 형태로 입력해주세요. </span>");
                    return false;
                }else{
                    return false;
                }
            } else {
                email.css({'border': 'solid 1px #e5e5e5', 'background-color': 'white'});
                $(".caution-email").remove();
                $(".email-authentication").remove();
                // 이메일 중복체크
                $.ajax({
                    type : 'post',
                    url : '/duplicate_check',
                    data : {"email" : email.val() },
                    success : function(result, status, xhr){
                        // 중복일 경우
                        if(result) {
                            email.css({'border': 'solid 1px #FE0000'});
                            email.after("<span class='caution-email'>가입이 불가능한 이메일 주소입니다. </span>");
                        // 최초 가입
                        }else{
                            // 이메일 인증절차 추가
                            if(!document.getElementById("Email-Authentication")){
                                email.after("<span id='Email-Authentication' class='email-authentication'>" +
                                    "<input type='button' id='Email-SendBtn' value='인증번호 전송하기 '>" +
                                    "<input type='number' id='Authentic-Number'>" +
                                    "<input type='button' id='Authentic-ConfirmBtn' value='확인'></span>");
                            }else {
                                return false;
                            }
                        }
                    },
                    error : function(xhr, status, er) {
                    }
                });
            }
        }else if(email.val().length == 0){
            $(".email-authentication").remove();
            $(".caution-email").remove();
            email.css({'border' : 'solid 1px #FE0000'});
            email.after("<span class='caution-email' data-oper='nothing'>필수 입력 항목입니다. </span>");
        }
    });

// 이메일 인증번호 전송
    var numberSix;
    $(document).on("click", "#Email-SendBtn", function(){

        $(this).attr("disabled", true);
        document.getElementById("Authentic-Number").focus();

        var to = email.val();

        console.log("이메일 주소 : " + to);

        $.ajax({
            type : 'post',
            url : '/authentication_send',
            data : {"to" : to },
            success : function(result, status, xhr){
                numberSix = result;
                console.log(numberSix);
            },
            error : function(xhr, status, er) {

            }
        })
    });
    $(document).on("click", "#Authentic-ConfirmBtn", function(){
        var inputSix = $("#Authentic-Number");
        if(numberSix == inputSix.val()) {
            $(this).attr("disabled", true);
            inputSix.attr("readonly", true);
            inputSix.css({'color': '#999999'});
            email.attr("readonly", true);
            email.css({'color': '#999999'});
            $("#Email-SendBtn").val("인증완료!");
            $("#Email-SendBtn").css({'color': '#4742DB'});
        }else{
            alert("일치하지 않습니다.");
        }


    });

// 비밀번호 정규화 체크
    var pwdRule = /^.*(?=^.{6,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    var password = $("#Password-Input");
    var caution_pwd = $(".caution-pwd");

    password.blur(function(){
        if(password.val().length > 0) {
            if ( !pwdRule.test(password.val()) ) {
                if(caution_pwd.length == 0  || caution_pwd.data("oper") == 'nothing') {
                    $(".caution-pwd").remove();
                    password.css({'border': 'solid 1px #FE0000'});
                    password.after("<span class='caution-pwd'>영문/숫자/특수문자 조합 8~16자 조합으로 입력해주세요. </span>");
                    return false;
                }else{
                    return false;
                }
            } else {
                password.css({'border': 'solid 1px #e5e5e5', 'background-color': 'white'});
                $(".caution-pwd").remove();
            }
        }else if(password.val().length == 0){
            $(".caution-pwd").remove();
            password.css({'border' : 'solid 1px #FE0000'});
            password.after("<span class='caution-pwd' data-oper='nothing'>필수 입력 항목입니다. </span>");
        }
    });

// 비밀번호 화인 체크
    var password2 = $("input[name=password2]");
    password2.blur(function(){
        if(password2.val().length > 0) {
            if(password.val() != password2.val()) {
                password2.css({'border': 'solid 1px #FE0000'});
                password2.after("<span class='caution-pwd2'>입력값이 일치하지 않습니다. </span>");
            }else{
                $(".caution-pwd2").remove();
                password2.css({'border': 'solid 1px #e5e5e5', 'background-color': 'white'});
            }
        }
    });

// 프로필 사진 업로드
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
    formData.append("category", "profilePhoto");
    console.log(files);

        $.ajax({
            url: '/s3Upload',
            processData: false,
            contentType : false,
            data: formData,
            type: 'POST',
            enctype: 'multipart/form-data'
         }).done(function (data) {
            if(value.files && value.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#profile-img').attr('src', e.target.result);
                }
                reader.readAsDataURL(value.files[0]);
            };
            $("#profileImg-btn").val("프로필사진 바꾸기");
            //$("#profileImg-btn").css({'color': '#999999'});


    });
}; // -- end asyncUpload function