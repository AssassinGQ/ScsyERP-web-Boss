package cn.AssassinG.ScsyERP.WebBoss.base;

import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import cn.AssassinG.ScsyERP.common.core.service.BaseService;
import cn.AssassinG.ScsyERP.common.entity.BaseEntity;
import cn.AssassinG.ScsyERP.common.exceptions.BizException;
import cn.AssassinG.ScsyERP.common.exceptions.DaoException;
import cn.AssassinG.ScsyERP.common.page.PageBean;
import cn.AssassinG.ScsyERP.common.page.PageParam;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class BaseController<T extends BaseEntity> implements Serializable {

    protected abstract BaseService<T> getService();
    protected abstract String getClassDesc();
    protected static final String RET_STATUS = "status";
    protected static final String RET_MSG = "msg";
    protected static final String RET_CONTENT = "content";

    protected JSONObject getResultJSON(String msg){
        return getResultJSON(RetStatusType.StatusFailure, msg, null);
    }

    protected JSONObject getResultJSON(RetStatusType status, String msg){
        return getResultJSON(status, msg, null);
    }

    protected JSONObject getResultJSON(RetStatusType status, String msg, JSONObject contentObject){
        JSONObject retObject = new JSONObject();
        retObject.put(RET_STATUS, status.getStatus());
        retObject.put(RET_MSG, msg);
        retObject.put(RET_CONTENT, contentObject);
        return retObject;
    }

    protected JSONObject getResultJSON(String msg, PageBean pageBean){
        JSONObject contentObject = new JSONObject();
        contentObject.put("TotalPage", pageBean.getPageCount());//总页数
        contentObject.put("CurrentPage", pageBean.getCurrentPage());//当前是第几页
        contentObject.put("TotalCount", pageBean.getTotalCount());//当前页有多少条记录
        contentObject.put("BeginPageIndex", pageBean.getBeginPageIndex());//当前页从哪一条记录开始
        contentObject.put("EndPageIndex", pageBean.getEndPageIndex());//当前页从哪一条记录结束
        contentObject.put("data", new JSONArray(pageBean.getRecordList()));
        return getResultJSON(RetStatusType.StatusSuccess, msg, contentObject);
    }

    protected JSONObject getResultJSON(String msg, List list){
        JSONObject contentObject = new JSONObject();
        contentObject.put("TotalCount", list.size());//总共有多少条记录
        contentObject.put("data", new JSONArray(list));
        return getResultJSON(RetStatusType.StatusSuccess, msg, contentObject);
    }

    protected JSONObject createImpl(T entity){
        try{
            getService().create(entity);
            JSONObject contentObject = new JSONObject();
            return getResultJSON(RetStatusType.StatusSuccess, "创建"+getClassDesc()+"信息成功", contentObject);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    protected JSONObject updateImpl(Long entityId, Map<String, String> paramMap){
        try{
            getService().updateByMap(entityId, paramMap);
            return getResultJSON(RetStatusType.StatusSuccess, "修改"+getClassDesc()+"信息成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    protected JSONObject deleteImpl(Long entityId){
        try{
            getService().deleteById(entityId);
            return getResultJSON(RetStatusType.StatusSuccess, "删除"+getClassDesc()+"信息成功", null);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    protected JSONObject queryImpl(Map<String, Object> paramMap){
        try{
            Integer limit = (Integer) paramMap.get("limit");
            Integer page = (Integer)paramMap.get("page");
            if(limit != null || page != null){
                if(page == null)
                    page = 1;
                if(limit == null)
                    limit = 20;
                PageParam pageParam = new PageParam(page, limit);
                paramMap.remove("limit");
                paramMap.remove("page");
                return getResultJSON("查询"+getClassDesc()+"信息成功", getService().listPage(pageParam, paramMap));
            }else{
                return getResultJSON("查询"+getClassDesc()+"信息成功", getService().listBy(paramMap));
            }
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    protected JSONObject getByIdImpl(Long entityId){
        try{
            BaseEntity baseEntity = getService().getById(entityId);
            JSONArray dataArray = new JSONArray();
            dataArray.add((JSONObject) JSON.toJSON(baseEntity));
            JSONObject contentObject = new JSONObject();
            contentObject.put("data", dataArray);
            return getResultJSON(RetStatusType.StatusSuccess, "查询成功", contentObject);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }
}
