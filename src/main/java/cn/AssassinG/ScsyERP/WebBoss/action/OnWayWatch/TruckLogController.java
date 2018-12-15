package cn.AssassinG.ScsyERP.WebBoss.action.OnWayWatch;

import cn.AssassinG.ScsyERP.OnWayWatch.facade.entity.TruckLog;
import cn.AssassinG.ScsyERP.OnWayWatch.facade.entity.Warn;
import cn.AssassinG.ScsyERP.OnWayWatch.facade.service.TruckLogServiceFacade;
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

import java.util.Map;

@Controller
@RequestMapping("/TruckLog")
public class TruckLogController extends BaseController<TruckLog> {
    private static final long serialVersionUID = 3946245307151897424L;
    @Autowired
    private TruckLogServiceFacade truckLogServiceFacade;
    @Override
    protected BaseService<TruckLog> getService() {
        return this.truckLogServiceFacade;
    }

    @Override
    protected String getClassDesc() {
        return "行车日志";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)//创建信息
    @ResponseBody
    public JSONObject create(TruckLog truckLog, Warn warn){
        try{
            truckLogServiceFacade.createWithWarn(truckLog, warn);
            JSONObject contentObject = new JSONObject();
            return getResultJSON(RetStatusType.StatusSuccess, "创建"+getClassDesc()+"信息成功", contentObject);
        }catch(DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

//    @RequestMapping(value = "/update", method = RequestMethod.POST)//更新信息
//    @ResponseBody
//    public JSONObject update(Long entityId, Map<String, Object> paramMap){
//        return super.updateImpl(entityId, paramMap);
//    }
//
//    @RequestMapping(value = "/delete", method = RequestMethod.POST)//删除信息
//    @ResponseBody
//    public JSONObject delete(Long entityId){
//        return super.deleteImpl(entityId);
//    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)//查询信息
    @ResponseBody
    public JSONObject query(Map<String, Object> paramMap){
        return super.queryImpl(paramMap);
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)//查询信息
    @ResponseBody
    public JSONObject getById(Long entityId){
        return super.getByIdImpl(entityId);
    }
}
