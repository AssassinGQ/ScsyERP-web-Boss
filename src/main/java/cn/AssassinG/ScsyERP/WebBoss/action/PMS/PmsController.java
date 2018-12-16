package cn.AssassinG.ScsyERP.WebBoss.action.PMS;

import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.User.facade.entity.User_Permission;
import cn.AssassinG.ScsyERP.User.facade.service.PermissionServiceFacade;
import cn.AssassinG.ScsyERP.User.facade.service.RoleServiceFacade;
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
    @Autowired
    private RoleServiceFacade roleServiceFacade;
    @Autowired
    private PermissionServiceFacade permissionServiceFacade;

    @Override
    protected BaseService<Role> getService() {
        return null;
    }

    @Override
    protected String getClassDesc() {
        return "角色";
    }

    @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addUserRole(Long user, Long role){
        try{
            userServiceFacade.addUserRole(user, role);
            return getResultJSON(RetStatusType.StatusSuccess, "用户角色添加成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeUserRole", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeUserRole(Long user, Long role){
        try{
            userServiceFacade.removeUserRole(user, role);
            return getResultJSON(RetStatusType.StatusSuccess, "用户角色移除成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/addRolePermission", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addRolePermission(Long role, Long permission){
        try{
            roleServiceFacade.addRolePermission(role, permission);
            return getResultJSON(RetStatusType.StatusSuccess, "角色权限添加成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeRolePermission", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeRolePermission(Long role, Long permission){
        try{
            roleServiceFacade.removeRolePermission(role, permission);
            return getResultJSON(RetStatusType.StatusSuccess, "角色权限移除成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/addUserPermission", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addUserPermission(Long user, Long permission, Integer type){
        try{
            userServiceFacade.addUserPermission(user, permission, type);
            return getResultJSON(RetStatusType.StatusSuccess, "用户权限添加成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeUserPermission", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeUserPermission(Long user, Long permission, Integer type){
        try{
            userServiceFacade.removeUserPermission(user, permission, type);
            return getResultJSON(RetStatusType.StatusSuccess, "用户权限移除成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value="/getAllRoles", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAllRoles(){
        List<Role> roles = roleServiceFacade.findAllRoles();
        JSONArray dataArray = new JSONArray();
        for(int i = 0; i < roles.size(); i++){
            JSONObject item = new JSONObject();
            Role role = roles.get(i);
            item.put("roleId", role.getId());
            item.put("name", role.getRoleName());
            item.put("desc", role.getRoleDesc());
            dataArray.add(item);
        }
        JSONObject contentObject = new JSONObject();
        contentObject.put("data", dataArray);
        contentObject.put("TotalCount", roles.size());
        return getResultJSON(RetStatusType.StatusSuccess, "查询成功", contentObject);
    }

    /*  User-Role 配置页面
    *   查询所有可见用户
    *   查询层次化角色，getAllRolesInherit
    *   用户这边是点选，角色用勾选，勾中父角色自动勾中相应子角色
    */

    @RequestMapping(value="/getRolesInherit", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getRolesInherit(){
        Role root_role = roleServiceFacade.findRoleByRoleName("superadmin");
        JSONArray dataArray = new JSONArray();
        if (root_role != null && !root_role.getIfDeleted()) {
            dataArray.add(buildChild(root_role));
        }
        JSONObject contentObject = new JSONObject();
        contentObject.put("TotalCount", 1);
        contentObject.put("data", dataArray);
        return getResultJSON(RetStatusType.StatusSuccess, "查询成功", contentObject);
    }

    private JSONObject buildChild(Role role){
        List<Role> children = roleServiceFacade.findChildrenRoles(role.getRoleName());
        if(children.size() == 0){
            JSONObject myselfObject = new JSONObject();
            myselfObject.put("id", role.getId());
            myselfObject.put("resourceId", role.getRoleName());
            myselfObject.put("resourceName", role.getRoleDesc());
            myselfObject.put("children", new JSONArray());
            return myselfObject;
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "group-"+role.getId());
            jsonObject.put("resourceId", "group-"+role.getRoleName());
            jsonObject.put("resourceName", role.getRoleDesc()+"组");
            JSONObject myselfObject = new JSONObject();
            myselfObject.put("id", role.getId());
            myselfObject.put("resourceId", role.getRoleName());
            myselfObject.put("resourceName", role.getRoleDesc());
            myselfObject.put("children", new JSONArray());
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(myselfObject);
            for(int i = 0; i < children.size(); i++){
                if(children.get(i) != null)
                    jsonArray.add(buildChild(children.get(i)));
            }
            jsonObject.put("children", jsonArray);
            return jsonObject;
        }
    }

    @RequestMapping(value="/getUserRoles", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserRoles(Long user){
        Set<Role> roles = userServiceFacade.findUserRoles(user);
        JSONObject contentObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Iterator<Role> iterator = roles.iterator();
        while(iterator.hasNext()){
            JSONObject item = new JSONObject();
            Role role = iterator.next();
//            String father_name = role.getSuperRoleName();
//            Role father_role = father_name == null ? null : roleServiceFacade.findRoleByRoleName(role.getSuperRoleName());
            item.put("id", role.getId());
//            item.put("pid", father_role == null ? 0 : father_role.getId());
            item.put("name", role.getRoleName());
            item.put("desc", role.getRoleDesc());
            jsonArray.add(item);
        }
        contentObject.put("data", jsonArray);
        contentObject.put("TotalCount", roles.size());
        return getResultJSON(RetStatusType.StatusSuccess, "查询成功", contentObject);
    }

    /*
    * User-Permission配置
    * 查询所有可见用户
    * 查询所有可见权限 getAllPermissions
    * 用户这边点选，权限勾选 */

    @RequestMapping(value="/getAllPermissions", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAllPermissions(){
        List<Permission> permissions = permissionServiceFacade.findAllPermission();
        JSONArray dataArray = new JSONArray();
        for(int i = 0; i < permissions.size(); i++){
            JSONObject item = new JSONObject();
            Permission permission = permissions.get(i);
            item.put("permissionId", permission.getId());
            item.put("name", permission.getPermissionName());
            item.put("desc", permission.getPermissionDesc());
            dataArray.add(item);
        }
        JSONObject contentObject = new JSONObject();
        contentObject.put("data", dataArray);
        contentObject.put("TotalCount", permissions.size());
        return getResultJSON(RetStatusType.StatusSuccess, "查询成功", contentObject);
    }

    /*
    * Role-Permission配置
    * 查询层次化角色，getAllRolesInherit
    * 查询所有可见权限 getAllPermissions
    * 点中角色以后右边自动勾选getRolePermission。自身的权限可以取消勾选，子角色的权限灰色不能取消勾选
    * 角色这边点选，权限那边勾选 */

    @RequestMapping(value="/getRolePermission", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getRolePermission(Long role){
        if(role == null){
            return getResultJSON("请上传角色主键");
        }
        Set<Permission> permissions = roleServiceFacade.findRolePermissions(role);
        JSONArray jsonArray = new JSONArray();
        for(Permission permission : permissions){
            JSONObject item = new JSONObject();
            item.put("id", permission.getId());
            item.put("name", permission.getPermissionName());
            item.put("desc", permission.getPermissionDesc());
            jsonArray.add(item);
        }
        JSONObject contentObject = new JSONObject();
        contentObject.put("data", jsonArray);
        contentObject.put("TotalCount", permissions.size());
        return getResultJSON(RetStatusType.StatusSuccess, "查询成功", contentObject);
    }

    //额外屏蔽权限表
    @RequestMapping(value="/getUserPermission", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserPermission(Long user){
        if(user == null){
            return getResultJSON("请上传用户主键");
        }
        List<User_Permission> userPermissions = userServiceFacade.findUserPermissions(user);
        JSONArray jsonArray = new JSONArray();
        for(User_Permission user_permission : userPermissions){
            JSONObject item = new JSONObject();
            item.put("userId", user_permission.getUserId());
            item.put("permissionId", user_permission.getPermissionId());
            item.put("type", user_permission.getType().getValue());
            jsonArray.add(item);
        }
        JSONObject contentObject = new JSONObject();
        contentObject.put("data", jsonArray);
        contentObject.put("TotalCount", userPermissions.size());
        return getResultJSON(RetStatusType.StatusSuccess, "查询成功", contentObject);
    }

    //最终权限
    @RequestMapping(value="/getUserFinalPermission", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserFinalPermission(Long user){
        if(user == null){
            return getResultJSON("请上传用户主键");
        }
        Set<Permission> permissions = userServiceFacade.findUserFinalPermissions(user);
        JSONArray jsonArray = new JSONArray();
        for(Permission permission : permissions){
            JSONObject item = new JSONObject();
            item.put("id", permission.getId());
            item.put("name", permission.getPermissionName());
            item.put("desc", permission.getPermissionDesc());
            jsonArray.add(item);
        }
        JSONObject contentObject = new JSONObject();
        contentObject.put("data", jsonArray);
        contentObject.put("TotalCount", permissions.size());
        return getResultJSON(RetStatusType.StatusSuccess, "查询成功", contentObject);
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
