package cn.AssassinG.ScsyERP.WebBoss.action.LoginableInfo;

import cn.AssassinG.ScsyERP.User.facade.entity.Corporation;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.CorporationServiceFacade;
import cn.AssassinG.ScsyERP.User.facade.service.LoginableService;
import cn.AssassinG.ScsyERP.User.facade.service.UserServiceFacade;
import cn.AssassinG.ScsyERP.WebBoss.Intercepts.HttpRequestIntercepter;
import cn.AssassinG.ScsyERP.WebBoss.base.LoginableBaseController;
import cn.AssassinG.ScsyERP.WebBoss.enums.RetStatusType;
import cn.AssassinG.ScsyERP.WebBoss.utils.JavaBeanUtils;
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
@RequestMapping("/BasicInfo/Corporation")
public class CorporationController extends LoginableBaseController<Corporation> {
    private static final long serialVersionUID = -9099447540085997292L;
    @Autowired
    private CorporationServiceFacade corporationServiceFacade;
    @Autowired
    private UserServiceFacade userServiceFacade;

    @Override
    protected LoginableService<Corporation> getLoginableService() {
        return this.corporationServiceFacade;
    }

    @Override
    protected String getClassDesc() {
        return "承运方";
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

    //TODO 这个不应该写在这里 应该写入Biz,这还需要定义新的联合bean
    @RequestMapping(value = "/query", method = RequestMethod.GET)//查询信息
    @ResponseBody
    public JSONObject query(String limit, String page, Corporation corporation){
        try{
            if(limit != null || page != null){
                Integer limitInt, pageInt;
                if(limit == null){
                    limitInt = 20;
                    try{
                        pageInt = Integer.parseInt(page);
                    }catch(Exception e){
                        pageInt = 1;
                    }
                }else if(page == null){
                    try{
                        limitInt = Integer.parseInt(limit);
                    }catch(Exception e){
                        limitInt = 20;
                    }
                    pageInt = 1;
                }else{
                    limitInt = Integer.parseInt(limit);
                    pageInt = Integer.parseInt(page);
                }
                PageParam pageParam = new PageParam(pageInt, limitInt);
                Map<String, Object> paramMap = JavaBeanUtils.Bean2Map(corporation);
                PageBean<Corporation> pageBean = corporationServiceFacade.listPage(pageParam, paramMap);
                List<Corporation> corporations = pageBean.getRecordList();
                JSONArray dataArray = new JSONArray();
                for(int i = 0; i < corporations.size(); i++){
                    Map<String, Object> queryMap = new HashMap<>();
                    queryMap.put("IfDeleted", false);
                    queryMap.put("UserInfo", corporations.get(i).getId());
                    queryMap.put("UserType", UserType.Corporation);
                    List<User> users = userServiceFacade.listBy(queryMap);
                    if(users.size() > 1){
                        return getResultJSON("未知错误，"+getClassDesc()+"信息（id=" + corporations.get(i).getId() + "）被多个登录信息关联");
                    }else if(users.size() == 0){
                        return getResultJSON("未知错误，"+getClassDesc()+"信息（id=" + corporations.get(i).getId() + "）没有关联的登录信息");
                    }else{
                        JSONObject itemObject = (JSONObject) JSONObject.toJSON(corporations.get(i));
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
                Map<String, Object> paramMap = JavaBeanUtils.Bean2Map(corporation);
                List<Corporation> admins = corporationServiceFacade.listBy(paramMap);
                JSONArray dataArray = new JSONArray();
                for(int i = 0; i < admins.size(); i++){
                    Map<String, Object> queryMap = new HashMap<>();
                    queryMap.put("IfDeleted", false);
                    queryMap.put("UserInfo", admins.get(i).getId());
                    queryMap.put("UserType", UserType.Corporation);
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
}
