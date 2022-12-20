package com.springboot.FormatToJson;

public class ToJsonData<T> {
    T data;

    public ToJsonData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
