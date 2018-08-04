package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <pre>
 *
 * </pre>
 * @author zhuqiang
 * @version 1.0.0
 * @date 2018/8/4 11:28
 * @since 1.0.0
 */
public interface ValidateCodeGenerate {
    ImageCode generate(HttpServletRequest request) throws IOException;
}
