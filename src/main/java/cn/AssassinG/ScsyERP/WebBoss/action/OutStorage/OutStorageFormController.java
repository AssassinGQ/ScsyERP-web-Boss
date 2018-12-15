package cn.AssassinG.ScsyERP.WebBoss.action.OutStorage;

import cn.AssassinG.ScsyERP.BasicInfo.facade.entity.Warehouse;
import cn.AssassinG.ScsyERP.BasicInfo.facade.service.WarehouseServiceFacade;
import cn.AssassinG.ScsyERP.OutStorage.facade.entity.OutStorageForm;
import cn.AssassinG.ScsyERP.OutStorage.facade.service.OutStorageFormServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.Intercepts.HttpRequestIntercepter;
import cn.AssassinG.ScsyERP.WebBoss.base.BaseController;
import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import cn.AssassinG.ScsyERP.common.core.service.BaseService;
import cn.AssassinG.ScsyERP.common.exceptions.BizException;
import cn.AssassinG.ScsyERP.common.exceptions.DaoException;
import com.alibaba.fastjson.JSON;
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
@RequestMapping("/OutStorageForm")
public class OutStorageFormController extends BaseController<OutStorageForm> {
    private static final long serialVersionUID = 2317417190265586252L;
    @Autowired
    private OutStorageFormServiceFacade outStorageFormServiceFacade;
    @Autowired
    private WarehouseServiceFacade warehouseServiceFacade;
    @Override
    protected BaseService<OutStorageForm> getService() {
        return this.outStorageFormServiceFacade;
    }

    @Override
    protected String getClassDesc() {
        return "出库单";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)//创建信息
    @ResponseBody
    public JSONObject create(OutStorageForm outStorageForm){
        return super.createImpl(outStorageForm);
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
    public JSONObject query(Map<String, Object> paramMap){
        return super.queryImpl(paramMap);
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)//查询信息
    @ResponseBody
    public JSONObject getById(Long entityId){
        try{
            OutStorageForm outStorageForm = outStorageFormServiceFacade.getById(entityId);
            if(outStorageForm == null || outStorageForm.getIfDeleted()){
                JSONArray dataArray = new JSONArray();
                JSONObject contentObject = new JSONObject();
                contentObject.put("data", dataArray);
                return getResultJSON(RetStatusType.StatusSuccess, "查询"+getClassDesc()+"成功", contentObject);
            }
            JSONObject itemObject = (JSONObject) JSON.toJSON(outStorageForm);
            Warehouse warehouse = warehouseServiceFacade.getById(outStorageForm.getWarehouse());
            if(warehouse != null){
                itemObject.put("driveWorkers", warehouse.getDriveWorkers());
                itemObject.put("liftWorkers", warehouse.getLiftWorkers());
            }
            JSONArray dataArray = new JSONArray();
            dataArray.add(itemObject);
            JSONObject contentObject = new JSONObject();
            contentObject.put("data", dataArray);
            return getResultJSON(RetStatusType.StatusSuccess, "查询"+getClassDesc()+"成功", contentObject);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/addDriveWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addDriveWorker(Long outStorageForm, Long driveWorker){
        try{
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(driveWorker);
            outStorageFormServiceFacade.addDriveWorkers(outStorageForm, jsonArray.toJSONString());
            return getResultJSON(RetStatusType.StatusSuccess, "出库单添加行车工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeDriveWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeDriveWorker(Long outStorageForm, Long driveWorker){
        try{
            outStorageFormServiceFacade.removeDriveWorker(outStorageForm, driveWorker);
            return getResultJSON(RetStatusType.StatusSuccess, "出库单移除行车工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/addLiftWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addLiftWorker(Long outStorageForm, Long liftWorker){
        try{
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(liftWorker);
            outStorageFormServiceFacade.addLiftWorkers(outStorageForm, jsonArray.toJSONString());
            return getResultJSON(RetStatusType.StatusSuccess, "出库单添加起重工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeLiftWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeLiftWorker(Long outStorageForm, Long liftWorker){
        try{
            outStorageFormServiceFacade.removeLiftWorker(outStorageForm, liftWorker);
            return getResultJSON(RetStatusType.StatusSuccess, "出库单移除起重工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addProduct(Long outStorageForm, Long product){
        try{
            outStorageFormServiceFacade.addProduct(outStorageForm, product);
            return getResultJSON(RetStatusType.StatusSuccess, "出库单添加货物信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeProduct", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeProduct(Long outStorageForm, Long product){
        try{
            outStorageFormServiceFacade.removeProduct(outStorageForm, product);
            return getResultJSON(RetStatusType.StatusSuccess, "出库单移除货物信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject complete(Long outStorageForm){
        try{
            outStorageFormServiceFacade.complete(outStorageForm);
            return getResultJSON(RetStatusType.StatusSuccess, "出库单以提交完成", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }
}
