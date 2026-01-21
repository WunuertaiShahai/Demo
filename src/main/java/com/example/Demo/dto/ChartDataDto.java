package com.example.Demo.dto;

import java.util.List;
import java.util.Map;

public class ChartDataDto {
    private List<String> labels;
    private List<Number> data;
    private Map<String, Object> additionalData;

    // 构造方法
    public ChartDataDto() {}

    public ChartDataDto(List<String> labels, List<Number> data) {
        this.labels = labels;
        this.data = data;
    }

    // Getter和Setter方法
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }

    public List<Number> getData() { return data; }
    public void setData(List<Number> data) { this.data = data; }

    public Map<String, Object> getAdditionalData() { return additionalData; }
    public void setAdditionalData(Map<String, Object> additionalData) { this.additionalData = additionalData; }
}