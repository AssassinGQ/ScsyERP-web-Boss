$(document).ready(function () {
    $('#form-register').submit(function () {
        $("#text-message").html("");
        $.ajax({
            url: "/user/reg",
            type: "post",
            async: "false",
            data: $('#form-register').serialize(),
            success: function (data) {
                if(data != null) {
                    var dataObj = JSON.parse(data);
                    $("#text-message").html(dataObj.msg);
                    if (dataObj.status == 1) {
                        window.location.href="/home";
                    }
                }
            }
        });
        return false;
    });
});

function OnVcodeButtonClick() {
    var input = document.getElementById("input-phone");
    var messagetext = document.getElementById("text-message");
    var phone = input.value;
    if(!isPoneAvailable(phone)){
        messagetext.innerHTML = "请输入有效的手机号";
    }else{
        messagetext.innerHTML = "";
        $.ajax({
            url: "/user/getVcode",
            async: true,
            type: "get",
            data: {phone: phone},
            success: function (data) {
                if (data != null) {
                    var dataObj = JSON.parse(data);
                    if (dataObj.status == 1) {
                        var content = dataObj.data;
                        alert(content.vcode);
                    }
                }
            }
        });
    }
}

function isPoneAvailable(phone) {
    var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
    if (!myreg.test(phone)) {
        return false;
    } else {
        return true;
    }
}
