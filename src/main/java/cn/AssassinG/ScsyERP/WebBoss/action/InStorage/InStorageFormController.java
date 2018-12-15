package cn.AssassinG.ScsyERP.WebBoss.action.InStorage;

import cn.AssassinG.ScsyERP.BasicInfo.facade.entity.Warehouse;
import cn.AssassinG.ScsyERP.BasicInfo.facade.service.WarehouseServiceFacade;
import cn.AssassinG.ScsyERP.InStorage.facade.entity.InStorageForm;
import cn.AssassinG.ScsyERP.InStorage.facade.service.InStorageFormServiceFacade;
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
@RequestMapping("/InStorageForm")
public class InStorageFormController extends BaseController<InStorageForm> {
    private static final long serialVersionUID = -6911275633006355328L;
    @Autowired
    private InStorageFormServiceFacade inStorageFormServiceFacade;
    @Autowired
    private WarehouseServiceFacade warehouseServiceFacade;

    @Override
    protected BaseService<InStorageForm> getService() {
        return this.inStorageFormServiceFacade;
    }

    @Override
    protected String getClassDesc() {
        return "入库单";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)//创建信息
    @ResponseBody
    public JSONObject create(InStorageForm inStorageForm){
        return super.createImpl(inStorageForm);
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
            InStorageForm inStorageForm = inStorageFormServiceFacade.getById(entityId);
            if(inStorageForm == null || inStorageForm.getIfDeleted()){
                JSONArray dataArray = new JSONArray();
                JSONObject contentObject = new JSONObject();
                contentObject.put("data", dataArray);
                return getResultJSON(RetStatusType.StatusSuccess, "查询"+getClassDesc()+"成功", contentObject);
            }
            JSONObject itemObject = (JSONObject) JSON.toJSON(inStorageForm);
            Warehouse warehouse = warehouseServiceFacade.getById(inStorageForm.getWarehouse());
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
    public JSONObject addDriveWorker(Long inStorageForm, Long driveWorker){
        try{
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(driveWorker);
            inStorageFormServiceFacade.addDriveWorkers(inStorageForm, jsonArray.toJSONString());
            return getResultJSON(RetStatusType.StatusSuccess, "入库单添加行车工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeDriveWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeDriveWorker(Long inStorageForm, Long driveWorker){
        try{
            inStorageFormServiceFacade.removeDriveWorker(inStorageForm, driveWorker);
            return getResultJSON(RetStatusType.StatusSuccess, "入库单移除行车工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/addLiftWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addLiftWorker(Long inStorageForm, Long liftWorker){
        try{
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(liftWorker);
            inStorageFormServiceFacade.addLiftWorkers(inStorageForm, jsonArray.toJSONString());
            return getResultJSON(RetStatusType.StatusSuccess, "入库单添加起重工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeLiftWorker", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeLiftWorker(Long inStorageForm, Long liftWorker){
        try{
            inStorageFormServiceFacade.removeLiftWorker(inStorageForm, liftWorker);
            return getResultJSON(RetStatusType.StatusSuccess, "入库单移除起重工信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addProduct(Long inStorageForm, Long product){
        try{
            inStorageFormServiceFacade.addProduct(inStorageForm, product);
            return getResultJSON(RetStatusType.StatusSuccess, "入库单添加货物信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeProduct", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeProduct(Long inStorageForm, Long product){
        try{
            inStorageFormServiceFacade.removeProduct(inStorageForm, product);
            return getResultJSON(RetStatusType.StatusSuccess, "入库单移除货物信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/upload_location", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadLocation(Long warehouse, String location){
        try{
            inStorageFormServiceFacade.updateProductLocation(warehouse, location);
            return getResultJSON(RetStatusType.StatusSuccess, "设置货物信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject complete(Long inStorageForm){
        try{
            inStorageFormServiceFacade.complete(inStorageForm);
            return getResultJSON(RetStatusType.StatusSuccess, "入库单已提交完成", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }
}
