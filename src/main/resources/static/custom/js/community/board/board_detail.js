$(document).ready(function() {
    var seq = $("#seq").val();
    var data = { "seq" : seq };

    $.ajax({
        type: 'POST',
        url: '/boardDetail',
        data: data,
        // data: seq,
        //data: {"seq" : seq },
        // dataType : 'json',
        success : function(result){
            console.log(result);
        },error: function (jqXHR, textStatus, errorThrown) {
            alert("error");
        },beforeSend:function(){

        },complete:function(){
        }
    });

    // $.post("/boardDetail", function ( data ) {
    //     console.log(data);
});