/**
 *
 */
package cn.mrcode.imooc.springsecurity.securityauthorize;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhailiang
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();

        boolean hasPermission = false;
        // 有可能 principal 是一个Anonymous
        // 所以只要是一个UserDetails那么就能标识是经过了我们自己的数据库查询的
        // 当前需要先配置UserDetailsServices
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            //读取用户所拥有权限的所有URL
            Set<String> urls = new HashSet<>();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }

        }

        return hasPermission;
    }


}
