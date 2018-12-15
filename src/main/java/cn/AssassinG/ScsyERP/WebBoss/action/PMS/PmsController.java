package cn.AssassinG.ScsyERP.WebBoss.action.PMS;

import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.User.facade.service.UserServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.base.BaseController;
import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import cn.AssassinG.ScsyERP.common.core.service.BaseService;
import cn.AssassinG.ScsyERP.common.exceptions.BizException;
import cn.AssassinG.ScsyERP.common.exceptions.DaoException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/Pms")
public class PmsController extends BaseController<Role> {
    private static final long serialVersionUID = 2143900832034488834L;
    @Autowired
    private UserServiceFacade userServiceFacade;

    @Override
    protected BaseService<Role> getService() {
        return null;
    }

    @Override
    protected String getClassDesc() {
        return "角色";
    }

    @RequestMapping(value = "/addUserRole", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject addUserRole(Long user, Long role){
        try{
            userServiceFacade.addUserRole(user, role);
            return getResultJSON(RetStatusType.StatusSuccess, "用户名修改成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeUserRole", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject removeUserRole(Long user, Long role){
        try{
            userServiceFacade.removeUserRole(user, role);
            return getResultJSON(RetStatusType.StatusSuccess, "用户名修改成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/addRolePermission", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject addRolePermission(Long role, Long permission){
        try{
            userServiceFacade.addRolePermission(role, permission);
            return getResultJSON(RetStatusType.StatusSuccess, "用户名修改成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeRolePermission", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject removeRolePermission(Long role, Long permission){
        try{
            userServiceFacade.removeRolePermission(role, permission);
            return getResultJSON(RetStatusType.StatusSuccess, "用户名修改成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/addUserPermission", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject addUserPermission(Long user, Long permission){
        try{
            userServiceFacade.addUserPermission(user, permission);
            return getResultJSON(RetStatusType.StatusSuccess, "用户名修改成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeUserPermission", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject removeUserPermission(Long user, Long permission){
        try{
            userServiceFacade.removeUserPermission(user, permission);
            return getResultJSON(RetStatusType.StatusSuccess, "用户名修改成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    /*  User-Role 配置页面
    *   查询所有可见用户
    *   查询层次化角色，getAllRolesInherit
    *   用户这边是点选，角色用勾选，勾中父角色自动勾中相应子角色
    */

    @RequestMapping(value="/getAllRolesInherit", method = RequestMethod.GET)
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

    @RequestMapping(value="/getUserRoles", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserRoles(Long user){
        Set<Role> roles = userServiceFacade.findUserRoles(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 1);
        jsonObject.put("msg", "请求成功");
        JSONArray jsonArray = new JSONArray();
        Iterator<Role> iterator = roles.iterator();
        while(iterator.hasNext()){
            JSONObject item = new JSONObject();
            Role role = iterator.next();
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

    /*
    * User-Permission配置
    * 查询所有可见用户
    * 查询所有可见权限 getAllPermissions
    * 用户这边点选，权限勾选 */

    @RequestMapping(value="/getAllPermissions", method = RequestMethod.GET)
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

    /*
    * Role-Permission配置
    * 查询层次化角色，getAllRolesInherit
    * 查询所有可见权限 getAllPermissions
    * 点中角色以后右边自动勾选getRolePermission。自身的权限可以取消勾选，子角色的权限灰色不能取消勾选
    * 角色这边点选，权限那边勾选 */

    @RequestMapping(value="/getRolePermission", method = RequestMethod.GET)
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

//    @RequestMapping(value="/getFatherRolePermission", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject getFatherRolePermission(@RequestParam("roleid") Long roleid){
//        JSONObject jsonObject = new JSONObject();
//        if(roleid == null){
//            jsonObject.put("status", 0);
//            jsonObject.put("msg", "请上传角色ID");
//            jsonObject.put("data", null);
//            return jsonObject;
//        }
//        Set<Permission> permissions = userServiceFacade.findFatherRolePermissions(roleid);
//        JSONArray jsonArray = new JSONArray();
//        for(Permission permission : permissions){
//            JSONObject item = new JSONObject();
//            item.put("permissionid", permission.getId());
//            item.put("permissiondesc", permission.getPermissionDesc());
//            jsonArray.add(item);
//        }
//        jsonObject.put("status", 1);
//        jsonObject.put("msg", "请求成功");
//        jsonObject.put("data", jsonArray);
//        return jsonObject;
//    }

}
