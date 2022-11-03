package demo.pattern.responsibilitychain.demo3;

/**
 * @Author rwj
 * @Date 2022/11/4
 * @Description
 */
public class SensitiveFilter implements Filter{
    @Override
    public boolean doFilter(Request request, Response response) {
        request.str += ("能力值+1000");
        response.str += "--SensitiveFilter()";
        return true;
    }
}
