package com.coder.rental.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class RouteVo {
    private String path;

    private String component;

    private String name;

    private Boolean alwaysShow;

    private Meta meta;

    private List<RouteVo> children;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Meta {
        private String title;

        private String icon;

        private Object[] roles;
    }
}
