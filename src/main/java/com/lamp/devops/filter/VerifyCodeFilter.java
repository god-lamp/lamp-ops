package com.lamp.devops.filter;

/**
 * @author god-lamp
 * @since 2024-02-20
 * 验证码校验过滤器
 */
/*@Log4j2
@Component
public class VerifyCodeFilter extends OncePerRequestFilter {
    *//**
     * 验证码缓存前缀
     *//*
    public static final String CAPTCHA_CODE_PREFIX = "captcha_code:";
    public static final String CAPTCHA_CODE_PARAM_NAME = "captchaCode";
    public static final String CAPTCHA_KEY_PARAM_NAME = "captchaKey";
    @Resource
    private IFastMap<String, String> fastMap;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("servlet path: {}", request.getServletPath());
        log.info("path info: {}", request.getPathInfo());
        // 检验登录接口的验证码
        if (Constant.WHITES.stream().map(p -> new AntPathRequestMatcher(p.getValue(), p.getKey().toString())).anyMatch(matcher -> matcher.matches(request))) {
            // 请求中的验证码
            String verifyCode = request.getParameter(CAPTCHA_CODE_PARAM_NAME);

            if (StrUtil.isBlank(verifyCode)) {
                chain.doFilter(request, response);
                return;
            }
            // 缓存中的验证码
            String verifyCodeKey = request.getParameter(CAPTCHA_KEY_PARAM_NAME);
            String cacheVerifyCode = fastMap.get(CAPTCHA_CODE_PREFIX + verifyCodeKey);

            if (cacheVerifyCode == null) {
                throw new IllegalStateException("验证码已过期");
            } else {
                // 验证码比对
                MathGenerator mathGenerator = new MathGenerator();
                if (mathGenerator.verify(cacheVerifyCode, verifyCode)) {
                    chain.doFilter(request, response);
                } else {
                    throw new IllegalStateException("验证码输入错误，请重新输入");
                }
            }
        } else {
            // 非登录接口放行
            chain.doFilter(request, response);
        }
    }
}*/
