package demo.pattern.responsibilitychain.demo3;

/**
 * @Author rwj
 * @Date 2022/11/4
 * @Description
 */
public class HTMLFilter implements Filter{
    @Override
    public boolean doFilter(Request request, Response response) {
        request.str += "幸福值+500。";
        response.str += "--HTMLFilter()";
        return true;
    }
}
