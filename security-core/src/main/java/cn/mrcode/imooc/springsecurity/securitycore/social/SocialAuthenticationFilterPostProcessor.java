/**
 * 
 */
package cn.mrcode.imooc.springsecurity.securitycore.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author zhailiang
 *
 */
public interface SocialAuthenticationFilterPostProcessor {
	
	void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
