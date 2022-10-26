package cn.rwj.fakeprj.entity.dto;

import lombok.Data;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description
 */
@Data
public class Result<T> {

    //本次请求结果的状态码，200表示成功
    private int code;
    //本次请求结果的详情
    private String msg;
    //本次请求返回的结果集
    private T data;

}
