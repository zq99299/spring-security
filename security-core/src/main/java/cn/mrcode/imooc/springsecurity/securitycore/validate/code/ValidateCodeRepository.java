package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * <pre>
 * 验证码存储仓库接口
 * </pre>
 * @author zhuqiang
 * @version 1.0.0
 * @date 2018/8/8 13:58
 * @since 1.0.0
 */
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     * @param request
     * @param validateCodeType
     */
    void remove(ServletWebRequest request, ValidateCodeType validateCodeType);
}
