package com.ag04.clidemo.shell.table;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.TableModel;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeanTableModelBuilder {

    private ObjectMapper objectMapper;
    private Object bean;
    private LinkedHashMap<String, Object> labels;
    private String[] header;


    public BeanTableModelBuilder(Object bean, ObjectMapper objectMapper) {
        this.bean = bean;
        this.objectMapper = objectMapper;
    }

    public BeanTableModelBuilder withLabels(LinkedHashMap<String, Object> labels) {
        this.labels = labels;
        return this;
    }

    public BeanTableModelBuilder withHeader(String[] header) {
        this.header = header;
        return this;
    }

    public TableModel build() {
        Map<String, String> map = objectMapper.convertValue(bean, new TypeReference<Map<String, String>>() {});

        int targetSize = (header == null) ? map.size() : map.size() +1;
        Object[][] entityProperties = new Object[targetSize][2];
        int i = 0;
        if (header != null) {
            entityProperties[0][0] = header[0];
            entityProperties[0][1] = header[1];
            i = 1;
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            Object label = (labels != null) ? labels.get(entry.getKey()) : entry.getKey();
            entityProperties[i][0] = label + ":";
            entityProperties[i][1] = entry.getValue();
            i++;
        }
        return new ArrayTableModel(entityProperties);
    }
}
