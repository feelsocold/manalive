var csrfParameter = $('meta[name="_csrf_parameter"]').attr('content')
var csrfHeader = $('meta[name="_csrf_header"]').attr('content')
var csrfToken = $('meta[name="_csrf"]').attr('content')
var contextPath = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';

$(document).ready(function() {
 $(function(){
     $('#login-form').submit(function(e){
        e.preventDefault();
        var params = "login_email=" + $("#Login-Email").val() + "&login_password=" + $("#Login-Password").val();

        // $.ajaxSetup({
        //     beforeSend: function(xhr) {
        //         xhr.setRequestHeader(csrfHeader, csrfToken);
        //     }
        // });

        $.ajax({
            type : 'post',
            url : contextPath + "/user/login",
            dataType: "json",
            data : params,
            success : function(response){
                console.log(response);
                window.location.href = '/';
            },
            error : function(jqXHR, status, e) {
                console.error(status + " : " + e);
            }
        });
    });
});
});