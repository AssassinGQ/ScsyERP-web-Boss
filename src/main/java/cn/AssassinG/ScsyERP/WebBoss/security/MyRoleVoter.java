package cn.AssassinG.ScsyERP.WebBoss.security;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

public class MyRoleVoter implements AccessDecisionVoter<Object> {
    private String rolePrefix = "RES_";
    private static Logger logger = Logger.getLogger(MyRoleVoter.class);

    public MyRoleVoter() {
    }

    public String getRolePrefix() {
        return this.rolePrefix;
    }

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    public boolean supports(ConfigAttribute attribute) {
        return attribute.getAttribute() != null && attribute.getAttribute().startsWith(this.getRolePrefix());
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        logger.info(attributes);
        if (authentication == null) {
            return -1;
        } else {
            int result = 0;
            Collection<? extends GrantedAuthority> authorities = this.extractAuthorities(authentication);
            Iterator var6 = attributes.iterator();

            while(true) {
                ConfigAttribute attribute;
                do {
                    if (!var6.hasNext()) {
                        return result;
                    }

                    attribute = (ConfigAttribute)var6.next();
                } while(!this.supports(attribute));

                result = -1;
                Iterator var8 = authorities.iterator();

                while(var8.hasNext()) {
                    GrantedAuthority authority = (GrantedAuthority)var8.next();
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return 1;
                    }
                }
            }
        }
    }

    Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities();
    }
}
