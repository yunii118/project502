package org.choongang.admin.config.controllers;

import lombok.Data;

@Data
public class BasicConfig {
    // null 오류 피하기 위해 기본값 설정
    private String siteTitle = "";
    private String siteDescription = "";
    private String siteKeywords = "";
    private int cssJsVersion = 1;
    private String thumbSize = "";
    private String joinTerms = "";

}
