package com.auto.selenium.model;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @date
 * @desc
 */
@Data
public class AuthTree {
    private String menuName;
    private String menuCode;
    private String level;
    private String link;
    private String title;
    List<AuthTree> child;
}
