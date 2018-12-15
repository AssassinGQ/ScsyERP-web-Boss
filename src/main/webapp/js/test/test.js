$(document).ready(function () {
    /////////////////////////////////////
    // const marray = {};
    // marray[2] = 1;
    // alert(marray);
    // alert(marray[0]);
    // alert(marray[1]);
    // alert(marray[2]);
    //////////////////////////////////////////
    // $("#checkbox4").attr("disabled", "true");
    // document.getElementById("checkbox4").disabled = true;
    // document.getElementById("checkbox4").onclick(this, function () {
    //     alert(this.id);
    // });
})

function checkboxclick(self) {
    // if($('#checkbox4').attr("disabled") && $('#checkbox4').attr("disabled") == "disabled"){
    //     $('#checkbox4').removeAttr("disabled");
    // }else{
    //     $('#checkbox4').attr("disabled", "disabled");
    // }
    var checkbox4 = document.getElementById("checkbox4");
    if(checkbox4.getAttribute("disabled") && checkbox4.getAttribute("disabled") == "disabled")
        checkbox4.removeAttribute("disabled");
    else
        checkbox4.setAttribute("disabled", "disabled");
}
