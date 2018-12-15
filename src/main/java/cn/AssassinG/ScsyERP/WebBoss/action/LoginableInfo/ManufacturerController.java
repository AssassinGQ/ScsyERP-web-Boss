package cn.AssassinG.ScsyERP.WebBoss.action.LoginableInfo;

import cn.AssassinG.ScsyERP.User.facade.entity.Manufacturer;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.LoginableService;
import cn.AssassinG.ScsyERP.User.facade.service.ManufacturerServiceFacade;
import cn.AssassinG.ScsyERP.User.facade.service.UserServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.Intercepts.HttpRequestIntercepter;
import cn.AssassinG.ScsyERP.WebBoss.base.LoginableBaseController;
import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import cn.AssassinG.ScsyERP.common.exceptions.BizException;
import cn.AssassinG.ScsyERP.common.exceptions.DaoException;
import cn.AssassinG.ScsyERP.common.page.PageBean;
import cn.AssassinG.ScsyERP.common.page.PageParam;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/BasicInfo/Manufacturer")
public class ManufacturerController extends LoginableBaseController<Manufacturer> {
    private static final long serialVersionUID = -8008104586300778029L;
    @Autowired
    private ManufacturerServiceFacade manufacturerServiceFacade;
    @Autowired
    private UserServiceFacade userServiceFacade;

    @Override
    protected LoginableService<Manufacturer> getLoginableService() {
        return this.manufacturerServiceFacade;
    }

    @Override
    protected String getClassDesc() {
        return "生产厂家";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)//创建信息
    @ResponseBody
    public JSONObject create(Manufacturer manufacturer, User user){
        return super.createImpl(manufacturer, user);
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
                PageBean<Manufacturer> pageBean = manufacturerServiceFacade.listPage(pageParam, paramMap);
                List<Manufacturer> manufacturers = pageBean.getRecordList();
                JSONArray dataArray = new JSONArray();
                for(int i = 0; i < manufacturers.size(); i++){
                    Map<String, Object> queryMap = new HashMap<>();
                    queryMap.put("IfDeleted", false);
                    queryMap.put("UserInfo", manufacturers.get(i).getId());
                    queryMap.put("UserType", UserType.Manufacturer);
                    List<User> users = userServiceFacade.listBy(queryMap);
                    if(users.size() > 1){
                        return getResultJSON("未知错误，"+getClassDesc()+"信息（id=" + manufacturers.get(i).getId() + "）被多个登录信息关联");
                    }else if(users.size() == 0){
                        return getResultJSON("未知错误，"+getClassDesc()+"信息（id=" + manufacturers.get(i).getId() + "）没有关联的登录信息");
                    }else{
                        JSONObject itemObject = (JSONObject) JSONObject.toJSON(manufacturers.get(i));
                        itemObject.put("userName", users.get(0).getUserName());
                        itemObject.put("passWord", users.get(0).getPassWord());
                        itemObject.put("phone", users.get(0).getPhone());
                        itemObject.put("userId", users.get(0).getId());
                        dataArray.add(itemObject);
                    }
                }
                JSONObject contentObject = new JSONObject();
                contentObject.put("TotalPage", pageBean.getPageCount());//总页数
                contentObject.put("CurrentPage", pageBean.getCurrentPage());//当前是第几页
                contentObject.put("TotalCount", pageBean.getTotalCount());//当前页有多少条记录
                contentObject.put("BeginPageIndex", pageBean.getBeginPageIndex());//当前页从哪一条记录开始
                contentObject.put("EndPageIndex", pageBean.getEndPageIndex());//当前页从哪一条记录结束
                contentObject.put("data", dataArray);
                return getResultJSON(RetStatusType.StatusSuccess, "查询"+getClassDesc()+"信息成功", contentObject);
            }else{
                List<Manufacturer> admins = manufacturerServiceFacade.listBy(paramMap);
                JSONArray dataArray = new JSONArray();
                for(int i = 0; i < admins.size(); i++){
                    Map<String, Object> queryMap = new HashMap<>();
                    queryMap.put("IfDeleted", false);
                    queryMap.put("UserInfo", admins.get(i).getId());
                    queryMap.put("UserType", UserType.Manufacturer);
                    List<User> users = userServiceFacade.listBy(queryMap);
                    if(users.size() > 1){
                        return getResultJSON("未知错误，"+getClassDesc()+"信息（id=" + admins.get(i).getId() + "）被多个登录信息关联");
                    }else if(users.size() == 0){
                        return getResultJSON("未知错误，"+getClassDesc()+"信息（id=" + admins.get(i).getId() + "）没有关联的登录信息");
                    }else{
                        JSONObject itemObject = (JSONObject) JSONObject.toJSON(admins.get(i));
                        itemObject.put("userName", users.get(0).getUserName());
                        itemObject.put("passWord", users.get(0).getPassWord());
                        itemObject.put("phone", users.get(0).getPhone());
                        itemObject.put("userId", users.get(0).getId());
                        dataArray.add(itemObject);
                    }
                }
                JSONObject contentObject = new JSONObject();
                contentObject.put("TotalCount", admins.size());//总共有多少条记录
                contentObject.put("data", dataArray);
                return getResultJSON(RetStatusType.StatusSuccess, "查询"+getClassDesc()+"信息成功", contentObject);
            }
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)//查询信息
    @ResponseBody
    public JSONObject getById(Long entityId){
        return super.getByIdImpl(entityId);
    }

    @RequestMapping(value = "/addWorkshop", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addWorkshop(Long manufacturer, Long workshop){
        try{
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(workshop);
            manufacturerServiceFacade.addWorkshops(manufacturer, jsonArray.toJSONString());
            return getResultJSON(RetStatusType.StatusSuccess, "生产厂家添加生产车间信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeWorkshop", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject removeWorkshop(Long manufacturer, Long workshop){
        try{
            manufacturerServiceFacade.removeWorkshop(manufacturer, workshop);
            return getResultJSON(RetStatusType.StatusSuccess, "生产厂家移除生产车间信息成功", null);
        }catch (DaoException | BizException e){
            return getResultJSON(e.getMessage());
        }
    }
}
