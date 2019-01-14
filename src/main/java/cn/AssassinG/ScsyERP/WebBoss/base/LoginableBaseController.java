package cn.AssassinG.ScsyERP.WebBoss.base;

import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.LoginableService;
import cn.AssassinG.ScsyERP.User.facade.service.UserServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import cn.AssassinG.ScsyERP.common.core.service.BaseService;
import cn.AssassinG.ScsyERP.common.entity.LoginableEntity;
import cn.AssassinG.ScsyERP.common.exceptions.BizException;
import cn.AssassinG.ScsyERP.common.exceptions.DaoException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class LoginableBaseController<T extends LoginableEntity> extends BaseController<T> {

    @Autowired
    private UserServiceFacade userServiceFacade;//todo pom.xml中能够不引入service工程（在idea中，如果不引入，会报错：spring无法autowired。测试一下是不是是idea的问题，实际运行没有问题）
    protected abstract LoginableService<T> getLoginableService();

    @Override
    protected BaseService<T> getService() {
        return getLoginableService();
    }

    @Override
    protected JSONObject createImpl(T entity) {
        return getResultJSON("可登录基本信息需要提供登录信息才能创建");
    }

    //todo 存在安全隐患，用户知道字段名后，可以在创建时制定一些受限制的字段的值
    protected JSONObject createImpl(T entity, User user){//todo 测试Converter能够对entity中的某个属性生效
        try{
            getLoginableService().createWithUser(entity, user);
            JSONObject contentObject = new JSONObject();
            return getResultJSON(RetStatusType.StatusSuccess, "创建"+getClassDesc()+"信息成功", contentObject);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    protected JSONObject updateImpl(Long entityId, Map<String, String> paramMap){
        try{
            getLoginableService().updateByMap(entityId, paramMap);
            return getResultJSON(RetStatusType.StatusSuccess, "修改"+getClassDesc()+"信息成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    protected JSONObject deleteImpl(Long userId){
        try{
            getLoginableService().deleteByUserId(userId);
            return getResultJSON(RetStatusType.StatusSuccess, "删除"+getClassDesc()+"信息成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    //TODO 这个不应该写在这里 应该写入LoginBiz,但那样做还需要定义新的联合bean
    protected JSONObject getByIdImpl(Long entityId){
        try{
            LoginableEntity loginableEntity = getLoginableService().getById(entityId);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("IfDeleted", false);
            paramMap.put("UserType", UserType.getEnumByClassName(loginableEntity.getClass().getName()));
            paramMap.put("UserInfo", loginableEntity.getId());
            List<User> users = userServiceFacade.listBy(paramMap);
            if(users.size() > 1){
                return getResultJSON(RetStatusType.StatusFailure, "一个"+getClassDesc()+"信息被多个登录信息关联，entityId："+entityId, null);
            }else if(users.size() == 0){
                return getResultJSON(RetStatusType.StatusFailure, getClassDesc()+"信息没有关联的登录信息", null);
            }else{
                JSONObject itemObject = (JSONObject) JSON.toJSON(loginableEntity);
                User user = users.get(0);
                itemObject.put("userName", user.getUserName());
                itemObject.put("passWord", user.getPassWord());
                itemObject.put("phone", user.getPhone());
                JSONArray dataArray = new JSONArray();
                dataArray.add(itemObject);
                JSONObject contentObject = new JSONObject();
                contentObject.put("data", dataArray);
                return getResultJSON(RetStatusType.StatusSuccess, "查询成功", contentObject);
            }
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

}
