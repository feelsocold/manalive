// 이미지 클릭시 파일버튼 클릭
$(function () {
    $('#upload-btn').click(function (e) {
        e.preventDefault();
        $('#hdn-uploadBtn').click();
    });
});

// ajax 파일업로드
$(document).ready(function(){
    $("#write-finish").on("click", function(e){
       var formData = new FormData();
       var inputFile = $("input[name='uploadFile']");
       var files = inputFile[0].files;
       console.log(files);
    });
});

function asyncUpload(obj){

    var formData = new FormData();
    var inputFile = $("input[name='uploadFile']");
    var files = inputFile[0].files;
    console.log(files);

    // //add filedate to formdata
    // for(var i = 0; i < files.length; i++) {
    //     formData.append("uploadFile", files[i]);
    // }
    //
    // $.ajax({
    //     url: '/profilePhotoUpload',
    //     proccessData : false,
    //     contentType : false,
    //     data: formData,
    //     type: 'POST',
    //     success: function(result){
    //         alert("UPLOAD!");
    //     }
    // });



}







