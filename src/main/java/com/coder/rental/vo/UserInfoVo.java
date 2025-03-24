package com.coder.rental.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo implements Serializable {
    private Integer id;

    private String name;

    private String avatar;

    private String introduction;

    private Object[] roles;

    private Map<String,String> iconMap;
}
