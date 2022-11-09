package org.myspring.mvc.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author rwj
 * @Date 2022/11/10
 * @Description 用来存储 请求路径 和 请求方法
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPathInfo {

    private String httpPath;
    private String httpMethod;

}
