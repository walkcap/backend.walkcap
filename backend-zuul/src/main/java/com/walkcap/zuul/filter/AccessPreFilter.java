package com.walkcap.zuul.filter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.walkcap.common.Result;
import com.walkcap.common.StringZipUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessPreFilter extends ZuulFilter {
    @Autowired
    @Qualifier("accessRestTemplate")
    private RestTemplate restTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }
    
    @Override
    public boolean shouldFilter() {
        return Boolean.TRUE;
    }

	/**
	 * 将请求做鉴权校验
	 */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        
        // （1）路由至注册中心："/uaa"路由至auth-center-quick服务
        //     "/uaa","/zcmes","/manage" 路由至配置文件指定路径
        //     e.g.,http://localhost:5555/uaa/form/pcToken
        //          http://localhost:5555/uaa/menu/treeOnLogins
        log.info("request {}:{}",request.getMethod(),request.getRequestURI());
        if (StringUtils.startsWithAny(request.getRequestURI(), new String[]{"/uaa","/zcmes","/manage"})) {
            ctx.setSendZuulResponse(Boolean.TRUE);
            return null;
        }
        
        // （2）鉴权处理：URI+Token
        //     e.g.,http://localhost:5555/ge/advanceSche/ganttList
        //     ge: 微服务名称
        ResponseEntity<Result> stringResponseEntity;
        
        try {
             // (3.1) 鉴权：通过Token进行鉴权，成功后返回用户信息
            stringResponseEntity = restTemplate.getForEntity(
            		               "http://auth-center-quick/user/isAccessed?requestURI={1}&Authorization={2}",
                                   Result.class, 
                                   request.getRequestURI(),                 // 请求URI：/ge/advanceSche/ganttList
                                   request.getHeader("Authorization"));     // bearer+JWTToken
            
            // （3.2）鉴权通过后访问原来请求URI
            // 反馈代码
            if(stringResponseEntity.getBody().getCode() != 0)
            {
                throw new HttpClientErrorException(HttpStatus.resolve(stringResponseEntity.getBody().getCode()),stringResponseEntity.getBody().getMsg());
            }
            
            // 用户信息
            Map<String, String[]> parameterMap = request.getParameterMap();
            String userInfo = JSON.toJSONString(stringResponseEntity.getBody().getData());
            parameterMap.put("userInfo", new String[]{StringZipUtil.compressData(userInfo)});
            
            // 请求参数
            Map<String, List<String>> params = new HashMap<>(10);
            parameterMap.forEach((key, value) -> params.put(key, Arrays.asList(value)));
            
            // 发送请求: key==userInfo; value==StringZipUtil.compressData(userInfo)
            ctx.setRequestQueryParams(params);
        } 
        catch (HttpClientErrorException e) {
            ctx.setSendZuulResponse(Boolean.FALSE);
            //ctx.set("error.status_code", e.getRawStatusCode());
            throw new ZuulException(e,e.getMessage(),e.getRawStatusCode(),"");
        }
        
        return null;
    }
}

