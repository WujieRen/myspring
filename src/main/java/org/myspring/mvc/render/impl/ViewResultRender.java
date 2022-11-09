package org.myspring.mvc.render.impl;

import org.myspring.mvc.RequestProcessorChain;
import org.myspring.mvc.render.ResultRender;
import org.myspring.mvc.type.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 页面渲染器
 */
public class ViewResultRender implements ResultRender {

    public static final String VIEW_PATH = "/templates/";
    private ModelAndView modelAndView;

    /**
     * 对传入的参数进行处理，并赋值给ModelAndView成员变量
     * @param mv
     */
    public ViewResultRender(Object mv) {
        if(mv instanceof ModelAndView){
            //1.如果入参类型是ModelAndView，则直接赋值给成员变量
            this.modelAndView = (ModelAndView)mv;
        } else if(mv instanceof  String){
            //2.如果入参类型是String，则为视图，需要包装后才赋值给成员变量
            this.modelAndView = new ModelAndView().setView((String)mv);
        } else {
            //3.针对其他情况，则直接抛出异常
            throw new RuntimeException("Illegal request result type");
        }
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        HttpServletRequest request = requestProcessorChain.getRequest();
        HttpServletResponse response = requestProcessorChain.getResponse();
        String path = modelAndView.getView();
        modelAndView.getModel().forEach(request::setAttribute);
        //jsp页面
        request.getRequestDispatcher(VIEW_PATH + path).forward(request, response);
    }
}
