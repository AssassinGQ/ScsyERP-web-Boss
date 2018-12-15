package cn.AssassinG.ScsyERP.WebBoss.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MyAffirmativeBased extends AbstractAccessDecisionManager {
    public MyAffirmativeBased(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
        super(decisionVoters);
    }

    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException {
        this.logger.info(configAttributes);
        int deny = 0;
        Iterator var5 = this.getDecisionVoters().iterator();

        while(var5.hasNext()) {
            AccessDecisionVoter voter = (AccessDecisionVoter)var5.next();
            int result = voter.vote(authentication, object, configAttributes);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Voter: " + voter + ", returned: " + result);
            }

            switch(result) {
                case -1:
                    ++deny;
                    break;
                case 1:
                    return;
            }
        }

        if (deny > 0) {
            throw new AccessDeniedException(this.messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
        } else {
            this.checkAllowIfAllAbstainDecisions();
        }
    }
}
