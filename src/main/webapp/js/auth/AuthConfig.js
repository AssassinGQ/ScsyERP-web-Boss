$(document).ready(function () {
    // var task1 = false;
    // var task2 = false;
    $.ajax({
        url: "/auth/authconfig/getAllPermissions",
        async: false,
        type: "get",
        success:function (data) {
            if(data != null){
                var dataObj = JSON.parse(data);
                if(dataObj.status == 1){
                    var dataarray = dataObj.data;
                    var obj = document.getElementById("ul-permissions");
                    obj.innerHTML = "";
                    for(x in dataarray){
                        var id = dataarray[x].permissionid;
                        var desc = dataarray[x].permissiondesc;
                        obj.innerHTML += "<li id='li-permission-"+id+"'><input type='checkbox' id='checkbox-"+id+"'><label for='checkbox"+id+"'>"+desc+"</label></li>\n";
                    }
                    document.getElementById("button-save").style.display="block";
                    // task1 = true;
                    // if(task2){
                    //     var ele_li_firstrole = document.getElementById("ul-roles").firstChild;
                    //     var roleid = (ele_li_firstrole.id.split("-"))[2];
                    //     // getRolePermissionFunction(roleid);
                    // }
                }
            }
        }
    });
    $.ajax({
        url: "/auth/authconfig/getAllRolesInherit",
        async: true,
        type: "get",
        success:function (data) {
            if(data != null){
                var dataObj = JSON.parse(data);
                if(dataObj.status == 1){
                    var dataarray = dataObj.data;
                    var obj = document.getElementById("roles-container");
                    const treeBuilder = new TreeBuilder(dataarray);
                    var content = treeBuilder.build().html();
                    obj.innerHTML = content;
                    // task2 = true;
                    // if(task2){
                    //     // getRolePermissionFunction(dataarray[0].roleid);
                    // }
                }
            }
        }
    });
});

function RoleItemClick(self){
    var itemid = self.id;
    var retarray = itemid.split("-");
    getRolePermissionFunction(retarray[2]);
}

function SaveButtonClick(){
    //寻找被选中的role
    var obj = document.getElementById("ul-roles");
    var radios = obj.getElementsByTagName("radio-role");
    var checked_role;
    for(x in radios){
        if(radios[x].checked){

            break;
        }
    }
    //寻找被选中的permissions
    var obj = document.getElementById("ul-permissions");
    var childs = obj.getElementsByTagName("li");
    var checked_permissions = new Array();
    for(x in childs){
        var child_childs = childs[x].childNodes;
        for(c in child_childs){
            if(child_childs[c].type == "checkbox"){
                if(child_childs[c].checked){
                    var permissionid = (child_childs[c].id.split("-"))[1];
                    checked_permissions.push(permissionid);
                }
            }
        }
    }
    alert(checked_permissions);
}

function getRolePermission(id) {
    $.ajax({
        url: "/auth/authconfig/getRolePermission",
        async: true,
        type: "get",
        data: {roleid: id},
        success: function (data) {
            if (data != null) {
                var dataObj = JSON.parse(data);
                if (dataObj.status == 1) {
                    var dataarray = dataObj.data;
                    for (x2 in dataarray) {
                        var obj = document.getElementById("li-permission-" + dataarray[x2].permissionid);
                        var child_childs = obj.childNodes;
                        for (c in child_childs) {
                            if (child_childs[c].type == "checkbox") {
                                child_childs[c].checked = true;
                            }
                        }
                    }
                }
            }
        }
    });
}

function getFatherRolePermission(id) {
    $.ajax({
        url: "/auth/authconfig/getFatherRolePermission",
        async: true,
        type: "get",
        data: {roleid: id},
        success: function (data) {
            if (data != null) {
                var dataObj = JSON.parse(data);
                if (dataObj.status == 1) {
                    var dataarray = dataObj.data;
                    for (x2 in dataarray) {
                        var obj = document.getElementById("li-permission-" + dataarray[x2].permissionid);
                        var child_childs = obj.childNodes;
                        for (c in child_childs) {
                            if (child_childs[c].type == "checkbox") {
                                child_childs[c].setAttribute("disabled", "disabled");
                                child_childs[c].checked = true;
                            }
                        }
                    }
                }
            }
        }
    });
}

