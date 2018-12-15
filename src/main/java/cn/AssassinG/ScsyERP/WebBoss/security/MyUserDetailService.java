package cn.AssassinG.ScsyERP.WebBoss.security;

import cn.AssassinG.ScsyERP.User.facade.entity.Permission;
import cn.AssassinG.ScsyERP.User.facade.entity.User;
import cn.AssassinG.ScsyERP.User.facade.service.UserServiceFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserServiceFacade userService;
    private static Logger logger = Logger.getLogger(MyUserDetailService.class);
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findUserByUname(s);
        if(user == null)
            throw new UsernameNotFoundException("");
        Set<Permission> permissions = userService.findUserPermissions(user.getId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for(Permission permission : permissions){
            logger.info(permission);
            authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassWord(), authorities);
    }
}
