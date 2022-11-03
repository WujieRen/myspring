package demo.pattern.responsibilitychain.demo3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author rwj
 * @Date 2022/11/4
 * @Description
 */
public class FilterChain implements Filter {

//    private List<Filter> filterList = new ArrayList<>();
    private List<Filter> filterList = Arrays.asList(new HTMLFilter(), new SensitiveFilter());

    public FilterChain add(Filter filter) {
        filterList.add(filter);
        return this;
    }

    @Override
    public boolean doFilter(Request request, Response response) {
        for(Filter f : filterList ){
            f.doFilter(request, response);
        }
        return true;
    }
}
