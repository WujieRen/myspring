package demo.pattern.responsibilitychain.demo3;

/**
 * @Author rwj
 * @Date 2022/11/4
 * @Description
 */
public interface Filter {
    boolean doFilter(Request request, Response response);
}
