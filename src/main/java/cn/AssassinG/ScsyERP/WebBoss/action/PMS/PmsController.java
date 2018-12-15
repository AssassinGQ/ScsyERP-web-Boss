package cn.AssassinG.ScsyERP.WebBoss.action.PMS;

import cn.AssassinG.ScsyERP.User.facade.entity.Role;
import cn.AssassinG.ScsyERP.User.facade.service.UserServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.base.BaseController;
import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import cn.AssassinG.ScsyERP.common.core.service.BaseService;
import cn.AssassinG.ScsyERP.common.exceptions.BizException;
import cn.AssassinG.ScsyERP.common.exceptions.DaoException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
