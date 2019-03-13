package cn.AssassinG.ScsyERP.WebBoss.action.LoginableInfo;

import cn.AssassinG.ScsyERP.User.facade.entity.Admin;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.enums.UserType;
import cn.AssassinG.ScsyERP.User.facade.service.AdminServiceFacade;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/BasicInfo/Admin")
public class AdminController extends LoginableBaseController<Admin> {
    private static final long serialVersionUID = 963447180373283728L;
    @Autowired
    private AdminServiceFacade adminServiceFacade;
    @Autowired
    private UserServiceFacade userServiceFacade;

    @Override
    protected LoginableService<Admin> getLoginableService() {
        return this.adminServiceFacade;
    }

    @Override
    protected String getClassDesc() {
        return "管理员";
    }

    //todo 添加try catch或者用advice
    @RequestMapping(value = "/create", method = RequestMethod.POST)//创建信息
    @ResponseBody
    public JSONObject create(Admin admin, User user){
        try{
            SecurityContext sctx = SecurityContextHolder.getContext();
            Authentication auth = sctx.getAuthentication();
            if(auth == null){
                System.out.println("Authentication null");
            }else{
//                Object object = auth.getDetails();
                Object object = auth.getPrincipal();
                if(object instanceof UserDetails){
                    UserDetails userDetails = (UserDetails)object;
                    if(userDetails != null)
                        System.out.println("username : " + userDetails.getUsername());
                    else
                        System.out.println("user detail null");
                }else if(object instanceof Principal){
                    Principal principal = (Principal) object;
                    if(principal != null)
                        System.out.println("name : " + principal.getName());
                    else
                        System.out.println("principal null");
                }else if(object == null){
                    System.out.println("object null");
                }else{
                    System.out.println("not a userdetail or principal");
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return super.createImpl(admin, user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)//更新信息
    @ResponseBody
    public JSONObject update(Long entityId, HttpServletRequest request){
        Map<String, String> paramMap = (Map<String, String>) request.getAttribute(HttpRequestIntercepter.MAPKEY);
        return super.updateImpl(entityId, paramMap);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)//删除信息
    @ResponseBody
    public JSONObject delete(Long userId){
        return super.deleteImpl(userId);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)//查询信息
    @ResponseBody
    public JSONObject query(String limit, String page, Admin admin){
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
                Map<String, Object> paramMap = JavaBeanUtils.Bean2Map(admin);
                PageBean<Admin> pageBean = adminServiceFacade.listPage(pageParam, paramMap);
                List<Admin> admins = pageBean.getRecordList();
                JSONArray dataArray = new JSONArray();
                for(int i = 0; i < admins.size(); i++){
                    Map<String, Object> queryMap = new HashMap<>();
                    queryMap.put("IfDeleted", false);
                    queryMap.put("UserInfo", admins.get(i).getId());
                    queryMap.put("UserType", UserType.Admin);
                    List<User> users = userServiceFacade.listBy(queryMap);
                    if(users.size() > 1){
                        return getResultJSON("未知错误，管理员信息（id=" + admins.get(i).getId() + "）被多个登录信息关联");
                    }else if(users.size() == 0){
                        return getResultJSON("未知错误，管理员信息（id=" + admins.get(i).getId() + "）没有关联的登录信息");
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
                contentObject.put("TotalPage", pageBean.getPageCount());//总页数
                contentObject.put("CurrentPage", pageBean.getCurrentPage());//当前是第几页
                contentObject.put("TotalCount", pageBean.getTotalCount());//当前页有多少条记录
                contentObject.put("BeginPageIndex", pageBean.getBeginPageIndex());//当前页从哪一条记录开始
                contentObject.put("EndPageIndex", pageBean.getEndPageIndex());//当前页从哪一条记录结束
                contentObject.put("data", dataArray);
                return getResultJSON(RetStatusType.StatusSuccess, "查询"+getClassDesc()+"信息成功", contentObject);
            }else{
                Map<String, Object> paramMap = JavaBeanUtils.Bean2Map(admin);
                List<Admin> admins = adminServiceFacade.listBy(paramMap);
                JSONArray dataArray = new JSONArray();
                for(int i = 0; i < admins.size(); i++){
                    Map<String, Object> queryMap = new HashMap<>();
                    queryMap.put("IfDeleted", false);
                    queryMap.put("UserInfo", admins.get(i).getId());
                    queryMap.put("UserType", UserType.Admin);
                    List<User> users = userServiceFacade.listBy(queryMap);
                    if(users.size() > 1){
                        return getResultJSON("未知错误，管理员信息（id=" + admins.get(i).getId() + "）被多个登录信息关联");
                    }else if(users.size() == 0){
                        return getResultJSON("未知错误，管理员信息（id=" + admins.get(i).getId() + "）没有关联的登录信息");
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
