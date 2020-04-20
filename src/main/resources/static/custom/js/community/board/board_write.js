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

    for(var i = 0; i < files.length; i++) {
        formData.append("multipartFile", files[i]);
    }
    formData.append("category", "boardPhoto");
    console.log(files);

    $.ajax({
        url: '/s3Upload',
        processData: false,
        contentType : false,
        data: formData,
        type: 'POST',
        enctype: 'multipart/form-data'
    });



}







