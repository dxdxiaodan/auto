package com.auto.selenium.model;

import com.auto.selenium.utils.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author
 * @date
 * @desc
 */

@Data
public class AuthModel {
    private String testNum;
    private String testName;
    private String adminUser;
    private String adminPwd;
    private String testUser;
    private String testPwd;
    private String authId;
    private List<AuthTree> authList;
    private List<AuthTree> disauthList;
    private String authExpert;
    private String disauthExpert;
    public AuthModel(){}
    public AuthModel(String testNum,String testName,String adminUser,String adminPwd,String testUser,String testPwd,String authId,
              String authList,String disauthList,String authExpert,String disauthExpert){
        List<AuthTree> allowAuth = (List<AuthTree>) JsonUtil.parseList(authList,AuthTree.class);
        List<AuthTree> disallowAuth = (List<AuthTree>) JsonUtil.parseList(disauthList,AuthTree.class);
        this.testNum=testNum;
        this.testName=testName;
        this.adminUser=adminUser;
        this.adminPwd=adminPwd;
        this.testUser=testUser;
        this.testPwd=testPwd;
        this.authList=allowAuth;
        this.authId=authId;
        this.disauthList=disallowAuth;
        this.authExpert=authExpert;
        this.disauthExpert=disauthExpert;
    }

}
