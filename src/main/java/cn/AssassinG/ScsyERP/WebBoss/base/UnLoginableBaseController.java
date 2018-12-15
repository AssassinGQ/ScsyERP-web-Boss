package cn.AssassinG.ScsyERP.WebBoss.base;

import cn.AssassinG.ScsyERP.common.core.service.UnLoginableService;
import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import cn.AssassinG.ScsyERP.common.core.service.BaseService;
import cn.AssassinG.ScsyERP.common.entity.UnLoginableEntity;
import cn.AssassinG.ScsyERP.common.exceptions.BizException;
import cn.AssassinG.ScsyERP.common.exceptions.DaoException;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public abstract class UnLoginableBaseController<T extends UnLoginableEntity> extends BaseController<T> {

    protected abstract UnLoginableService<T> getUnLoginableService();

    @Override
    protected BaseService getService() {
        return getUnLoginableService();
    }

    //todo 存在安全隐患，用户知道字段名后，可以在创建时制定一些受限制的字段的值
    protected JSONObject createImpl(T entity){
        try{
            getUnLoginableService().createWithNameCheck(entity);
            JSONObject contentObject = new JSONObject();
            return getResultJSON(RetStatusType.StatusSuccess, "创建"+getClassDesc()+"信息成功", contentObject);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    protected JSONObject updateImpl(Long entityId, Map<String, String> paramMap){
        try{
            getUnLoginableService().updateByMap(entityId, paramMap);
            return getResultJSON(RetStatusType.StatusSuccess, "修改"+getClassDesc()+"信息成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    protected JSONObject deleteImpl(Long entityId){
        try{
            getUnLoginableService().deleteById(entityId);
            return getResultJSON(RetStatusType.StatusSuccess, "删除"+getClassDesc()+"信息成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

//    protected JSONObject queryImpl(Map<String, Object> paramMap){
//        try{
//            Integer limit = (Integer) paramMap.get("limit");
//            Integer page = (Integer)paramMap.get("page");
//            if(limit != null || page != null){
//                if(page == null)
//                    page = 1;
//                if(limit == null)
//                    limit = 20;
//                PageParam pageParam = new PageParam(page, limit);
//                paramMap.remove("limit");
//                paramMap.remove("page");
//                return getResultJSON("查询"+getClassDesc()+"信息成功", getUnLoginableService().listPage(pageParam, paramMap));
//            }else{
//                return getResultJSON("查询"+getClassDesc()+"信息成功", getUnLoginableService().listBy(paramMap));
//            }
//        }catch (DaoException | BizException e){
//            return getResultJSON(e.getMessage());
//        }
//    }

}
