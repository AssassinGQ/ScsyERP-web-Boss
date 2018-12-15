package cn.AssassinG.ScsyERP.WebBoss.action.PMS;

import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.service.UserServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.base.BaseController;
import cn.AssassinG.ScsyERP.common.core.service.BaseService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController<User> {
    private static final long serialVersionUID = -3606429045407221972L;
    private static Logger logger = Logger.getLogger(AuthController.class);
    @Autowired
    @Qualifier("userServiceFacade")
    private UserServiceFacade userServiceFacade;

    @RequestMapping(value = "/authconfig", method = RequestMethod.GET)
    public String toAuthConfig(ModelMap model){
        return "auth/AuthConfig";
    }

    //    @RequestMapping(value="/authconfig/getAllRoles", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject getAllRoles(){
//        List<Role> roles = userServiceFacade.findAllRoles();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("status", 1);
//        jsonObject.put("msg", "请求成功");
//        JSONArray jsonArray = new JSONArray();
//        for(int i = 0; i < roles.size(); i++){
//            JSONObject item = new JSONObject();
//            Role role = roles.get(i);
//            item.put("roleid", role.getId());
//            item.put("roledesc", role.getRoleDesc());
//            jsonArray.add(item);
//        }
//        jsonObject.put("data", jsonArray);
//        return jsonObject;
//    }

//    @RequestMapping(value="/authconfig/getChildRoles", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject getChildRoles(@RequestParam("roleid") Long roleid){
//        if(roleid == null)
//            roleid = 0L;
//        List<Role> roles = userServiceFacade.findChileRoles(roleid);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("status", 1);
//        jsonObject.put("msg", "请求成功");
//        JSONArray jsonArray = new JSONArray();
//        for(int i = 0; i < roles.size(); i++){
//            JSONObject item = new JSONObject();
//            Role role = roles.get(i);
//            item.put("roleid", role.getId());
//            item.put("roledesc", role.getRoleDesc());
//            jsonArray.add(item);
//        }
//        jsonObject.put("data", jsonArray);
//        return jsonObject;
//    }

    @RequestMapping(value="/authconfig/getAllRolesInherit", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAllRolesInherit(){
        List<Role> roles = userServiceFacade.findAllRoles();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 1);
        jsonObject.put("msg", "请求成功");
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < roles.size(); i++){
            JSONObject item = new JSONObject();
            Role role = roles.get(i);
            String father_name = role.getSuperRoleName();
            Role father_role = father_name == null ? null : userServiceFacade.findRoleByRoleName(role.getSuperRoleName());
            item.put("Id", role.getId());
            item.put("pid", father_role == null ? 0 : father_role.getId());
            item.put("roledesc", role.getRoleDesc());
            jsonArray.add(item);
        }
        jsonObject.put("data", jsonArray);
        return jsonObject;
    }

    @RequestMapping(value="/authconfig/getAllPermissions", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAllPermissions(){
        List<Permission> permissions = userServiceFacade.findAllPermission();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 1);
        jsonObject.put("msg", "请求成功");
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < permissions.size(); i++){
            JSONObject item = new JSONObject();
            Permission permission = permissions.get(i);
            item.put("permissionid", permission.getId());
            item.put("permissiondesc", permission.getPermissionDesc());
            jsonArray.add(item);
        }
        jsonObject.put("data", jsonArray);
        return jsonObject;
    }

    @RequestMapping(value="/authconfig/getRolePermission", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getRolePermission(@RequestParam("roleid") Long roleid){
        JSONObject jsonObject = new JSONObject();
        if(roleid == null){
            jsonObject.put("status", 0);
            jsonObject.put("msg", "请上传角色ID");
            jsonObject.put("data", null);
            return jsonObject;
        }
        Set<Permission> permissions = userServiceFacade.findRolePermissions(roleid);
        JSONArray jsonArray = new JSONArray();
        for(Permission permission : permissions){
            JSONObject item = new JSONObject();
            item.put("permissionid", permission.getId());
            item.put("permissiondesc", permission.getPermissionDesc());
            jsonArray.add(item);
        }
        jsonObject.put("status", 1);
        jsonObject.put("msg", "请求成功");
        jsonObject.put("data", jsonArray);
        return jsonObject;
    }

    @RequestMapping(value="/authconfig/getFatherRolePermission", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getFatherRolePermission(@RequestParam("roleid") Long roleid){
        JSONObject jsonObject = new JSONObject();
        if(roleid == null){
            jsonObject.put("status", 0);
            jsonObject.put("msg", "请上传角色ID");
            jsonObject.put("data", null);
            return jsonObject;
        }
        Set<Permission> permissions = userServiceFacade.findFatherRolePermissions(roleid);
        JSONArray jsonArray = new JSONArray();
        for(Permission permission : permissions){
            JSONObject item = new JSONObject();
            item.put("permissionid", permission.getId());
            item.put("permissiondesc", permission.getPermissionDesc());
            jsonArray.add(item);
        }
        jsonObject.put("status", 1);
        jsonObject.put("msg", "请求成功");
        jsonObject.put("data", jsonArray);
        return jsonObject;
    }

    @Override
    protected BaseService<User> getService() {
        return null;
    }

    @Override
    protected String getClassDesc() {
        return null;
    }

//    @RequestMapping(value="/authconfig/getRolesPermission", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject getRolesPermission(){
//        List<Role> roles = userServiceFacade.findAllRoles();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("status", 1);
//        jsonObject.put("msg", "请求成功");
//        JSONArray jsonArray = new JSONArray();
//        for(int i = 0; i < roles.size(); i++){
//            JSONObject itemObject = new JSONObject();
//            JSONArray itemArray = new JSONArray();
//            Role role = roles.get(i);
//            Set<Permission> permissions = userServiceFacade.findRolePermissions(role.getId());
//            itemObject.put("rolename", role.getRoleName());
//            for(Permission permission : permissions)
//                jsonArray.add(permission.getPermissionName());
//            itemObject.put("permissions", itemArray);
//            jsonArray.add(itemObject);
//        }
//        jsonObject.put("data", jsonArray);
//        return jsonObject;
//    }
}
