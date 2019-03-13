package cn.AssassinG.ScsyERP.WebBoss.action.UnLoginableInfo;

import cn.AssassinG.ScsyERP.BasicInfo.facade.entity.Warehouse;
import cn.AssassinG.ScsyERP.BasicInfo.facade.service.WarehouseServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.Intercepts.HttpRequestIntercepter;
import cn.AssassinG.ScsyERP.WebBoss.base.UnLoginableBaseController;
import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import cn.AssassinG.ScsyERP.common.core.service.UnLoginableService;
import cn.AssassinG.ScsyERP.common.exceptions.BizException;
import cn.AssassinG.ScsyERP.common.exceptions.DaoException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/BasicInfo/Warehouse")
public class WarehouseController extends UnLoginableBaseController<Warehouse> {
    private static final long serialVersionUID = 1254677080170300122L;
    @Autowired
    private WarehouseServiceFacade warehouseServiceFacade;

    @Override
    protected UnLoginableService<Warehouse> getUnLoginableService() {
        return this.warehouseServiceFacade;
    }

    @Override
    protected String getClassDesc() {
        return "仓库";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)//创建信息
    @ResponseBody
    public JSONObject create(Warehouse warehouse){
        return super.createImpl(warehouse);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)//更新信息
    @ResponseBody
    public JSONObject update(Long entityId, HttpServletRequest request){
        Map<String, String> paramMap = (Map<String, String>) request.getAttribute(HttpRequestIntercepter.MAPKEY);
        return super.updateImpl(entityId, paramMap);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)//删除信息
    @ResponseBody
    public JSONObject delete(Long entityId){
        return super.deleteImpl(entityId);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)//查询信息
    @ResponseBody
    public JSONObject query(String limit, String page, Warehouse warehouse){
        return super.queryImpl(limit, page, warehouse);
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)//查询信息
    @ResponseBody
    public JSONObject getById(Long entityId){
        return super.getByIdImpl(entityId);
    }

    @RequestMapping(value = "/addDriveWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addDriveWorkers(Long warehouse, Long driveWorker){
        try{
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(driveWorker);
            warehouseServiceFacade.addDriveWorkers(warehouse, jsonArray.toJSONString());
            return getResultJSON(RetStatusType.StatusSuccess, "仓库添加行车工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeDriveWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeDriveWorker(Long warehouse, Long driveWorker){
        try{
            warehouseServiceFacade.removeDriveWorker(warehouse, driveWorker);
            return getResultJSON(RetStatusType.StatusSuccess, "仓库移除行车工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/addLiftWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addLiftWorkers(Long warehouse, Long liftWorker){
        try{
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(liftWorker);
            warehouseServiceFacade.addLiftWorkers(warehouse, jsonArray.toJSONString());
            return getResultJSON(RetStatusType.StatusSuccess, "仓库添加起重工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeLiftWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeLiftWorker(Long warehouse, Long liftWorker){
        try{
            warehouseServiceFacade.removeLiftWorker(warehouse, liftWorker);
            return getResultJSON(RetStatusType.StatusSuccess, "仓库移除起重工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }
}
