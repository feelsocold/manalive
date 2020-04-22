var uploadedImgCnt = 0;

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
                console.log(result);
                showUploadedImg(result);
            },error: function (jqXHR, textStatus, errorThrown) {

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
        $("#thumbnail-wrap ul").append("<li data-oper='" + (uploadedImgCnt-result.length+i) +"'><img src='"+ result[i] +"' onclick='showImage(this)' class='thumnail-image'>" +
            "<br><button class='thumb-Btn' id='thumb-delBtn' onclick='imgDelete(this)'><img style='width:15px; height:15px;' src='/custom/img/icon/icon-trashbin.png'></button>" +
            "<input type='file' data-oper='"+ i +"' id='re-uploadBtn' name='uploadFile' style='display: none;'>" +
            "&nbsp;<button class='thumb-Btn' id='thumb-modBtn' onclick='imgUpdate(this)'><img style='width:15px; height:15px;' src='/custom/img/icon/icon-modify.png'></button></li>");
    }

    if(uploadedImgCnt< 6) {
        $("#upload-btn").html("사진 추가하기 (" + uploadedImgCnt + "/6)");
        $("#upload-btn").css('height', '50px');
    }else {
        $("#upload-btn").hide();
    }
}
function imgDelete(obj) {
    $(obj).closest("li").remove();
    var oper = $(obj).closest("li").data("oper");
    alert(oper);

    var data = { oper : $(obj).closest("li").data("oper"),
                category : "boardPhoto" };

        $.ajax({
            url: '/s3Delete',
            data: data,
            type: 'POST',
            success : function(result){
                alert("DELETE");
                uploadedImgCnt--;
                $("#upload-btn").html("사진 추가하기 (" + uploadedImgCnt + "/6)");
                $("#upload-btn").css('height', '50px');
                $("#upload-btn").show();
            }
        });
}
function imgUpdate(obj) {

    $('#re-uploadBtn').click();
    alert($(obj).closest("li").data("oper"));
    //$(obj).closest("input").click();

    // var data = { oper : $(obj).closest("li").data("oper"),
    //     category : "boardPhoto" };
    //
    // $.ajax({
    //     url: '/s3Modify',
    //     data: data,
    //     type: 'POST',
    //     success : function(result){
    //         alert("DELETE");
    //         uploadedImgCnt--;
    //         $("#upload-btn").html("사진 추가하기 (" + uploadedImgCnt + "/6)");
    //     }
    // });
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


