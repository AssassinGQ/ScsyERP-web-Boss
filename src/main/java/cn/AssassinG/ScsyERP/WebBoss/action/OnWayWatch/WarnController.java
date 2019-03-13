package cn.AssassinG.ScsyERP.WebBoss.action.OnWayWatch;

import cn.AssassinG.ScsyERP.OnWayWatch.facade.entity.Warn;
import cn.AssassinG.ScsyERP.OnWayWatch.facade.service.WarnServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.Intercepts.HttpRequestIntercepter;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/Warn")
public class WarnController extends BaseController<Warn> {
    private static final long serialVersionUID = -8700906070481756657L;
    @Autowired
    private WarnServiceFacade warnServiceFacade;
    @Override
    protected BaseService<Warn> getService() {
        return this.warnServiceFacade;
    }

    @Override
    protected String getClassDesc() {
        return "异常";
    }

//    @RequestMapping(value = "/create", method = RequestMethod.POST)//创建信息
//    @ResponseBody
//    public JSONObject create(Warn warn){
//        return super.createImpl(warn);
//    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)//更新信息
    @ResponseBody
    public JSONObject update(Long entityId, HttpServletRequest request){
        Map<String, String> paramMap = (Map<String, String>) request.getAttribute(HttpRequestIntercepter.MAPKEY);
        return super.updateImpl(entityId, paramMap);
    }

//    @RequestMapping(value = "/delete", method = RequestMethod.POST)//删除信息
//    @ResponseBody
//    public JSONObject delete(Long entityId){
//        return super.deleteImpl(entityId);
//    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)//查询信息
    @ResponseBody
    public JSONObject query(String limit, String page, Warn warn){
        return super.queryImpl(limit, page, warn);
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)//查询信息
    @ResponseBody
    public JSONObject getById(Long entityId){
        return super.getByIdImpl(entityId);
    }

    @RequestMapping(value = "/addPicture", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addPicture(Long onTruckForm, Long picture){
        try{
//            warnServiceFacade.addPicture(onTruckForm, picture);
            return getResultJSON(RetStatusType.StatusSuccess, "异常信息添加图片信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removePicture", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removePicture(Long onTruckForm, Long picture){
        try{
//            warnServiceFacade.removePicture(onTruckForm, picture);
            return getResultJSON(RetStatusType.StatusSuccess, "异常信息移除图片信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }
}
