package com.homoloa.dto;

import com.homoloa.domain.ParseEntity;


import java.util.ArrayList;
import java.util.List;


public class JsonWrapperDto {
    private List<ParseEntity> data = new ArrayList<>();

    public JsonWrapperDto(List<ParseEntity> data) {
        this.data = data;
    }

    public JsonWrapperDto() {
    }

    public List<ParseEntity> getData() {
        return data;
    }

    public void setData(List<ParseEntity> data) {
        this.data = data;
    }
}
