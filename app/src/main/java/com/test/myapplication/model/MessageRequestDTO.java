package com.test.myapplication.model;


import java.util.List;

public class MessageRequestDTO {
    private String name = "";
    private Object data;
    private List<?> dataList;

    public MessageRequestDTO(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    public MessageRequestDTO(String name, List<?> mList) {
        this.name = name;
        this.dataList = mList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
}
