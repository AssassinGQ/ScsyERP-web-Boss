package cn.AssassinG.ScsyERP.WebBoss.action.UnLoginableInfo;

import cn.AssassinG.ScsyERP.BasicInfo.facade.entity.Workshop;
import cn.AssassinG.ScsyERP.BasicInfo.facade.service.WorkshopServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.Intercepts.HttpRequestIntercepter;
import cn.AssassinG.ScsyERP.WebBoss.base.UnLoginableBaseController;
import cn.AssassinG.ScsyERP.common.core.service.UnLoginableService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/BasicInfo/Workshop")
public class WorkshopController extends UnLoginableBaseController<Workshop> {
    private static final long serialVersionUID = -1361759819532327832L;
    @Autowired
    private WorkshopServiceFacade workshopServiceFacade;

    @Override
    protected UnLoginableService<Workshop> getUnLoginableService() {
        return this.workshopServiceFacade;
    }

    @Override
    protected String getClassDesc() {
        return "起重工";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)//创建信息
    @ResponseBody
    public JSONObject create(Workshop workshop){
        return super.createImpl(workshop);
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
        return super.getByIdImpl(entityId);
    }
}
