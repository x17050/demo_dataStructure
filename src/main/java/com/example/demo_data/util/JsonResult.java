package com.example.demo_data.util;

import lombok.Data;

import java.io.Serializable;
@Data
//因为所有的响应的结果都采用Json格式的数据进行响应,所以需要实现Serializable接口
public class JsonResult<E> implements Serializable {
    private Integer state;
    private String message;
    private E data;


    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public JsonResult(Throwable e) {
        this.message=e.getMessage();
    }

}
