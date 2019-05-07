
package com.rmit.gateway.config;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.rmit.gateway.utils.SecurityUtils;
import com.rmit.main.library.gateway.api.ApiRequestUtils;


@Component
public class AuthorizationFilter extends ZuulFilter {

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String userId = SecurityUtils.getCurrentLogin();
        if (userId == null) {
            ctx.setResponseStatusCode(401);
            ctx.setSendZuulResponse(false);
        } else {
            ctx.addZuulRequestHeader(ApiRequestUtils.USER_ID, userId);
        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public String filterType() {
        return "pre";
    }

}
