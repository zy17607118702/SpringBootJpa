

/**
 * Copyright &copy; 2012-2013
 * <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights
 * reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.cn.springbootjpa.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

/**
 * SpringMVC框架的基础Controller类。
 * 
 */
@Slf4j
public abstract class BaseController implements MessageSourceAware {

    private static final String ERROR_MESSAGE = "error";
    private static final String INFO_MESSAGE = "info";
    private static final String SUCCESS_MESSAGE = "success";
    private static final String WARN_MESSAGE = "warning";
    protected static final String METHOD_ADD = "adding";
    protected static final String METHOD_EDIT = "editing";
    protected static final String METHOD_VIEW = "viewing";
    protected MessageSource messageSource;
    /**
     * 视图前缀
     */
    protected String requestMapping;

    protected BaseController() {
        requestMapping = fromAnnotation();
    }

    /**
     * 往Model中追加错误信息显示于用户界面。
     * 
     * @param model    Model
     * @param messages 错误信息内容
     */
    protected void addErrorMessage(Model model, String... messages) {
        model.addAttribute(ERROR_MESSAGE, catMessages(messages));
    }

    /**
     * @param redirectAttributes
     * @param messages
     */
    protected void addErrorMessage(RedirectAttributes redirectAttributes, String... messages) {
        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, catMessages(messages));
    }

    /**
     * 往Model中添加提示消息显示于用户界面中。
     * 
     * @param model    Model
     * @param messages 消息
     */
    protected void addInfoMessage(Model model, String... messages) {
        model.addAttribute(INFO_MESSAGE, catMessages(messages));
    }

    /**
     * 添加Flash消息
     * 
     * @param messages 消息
     */
    protected void addInfoMessage(RedirectAttributes redirectAttributes, String... messages) {
        redirectAttributes.addFlashAttribute(INFO_MESSAGE, catMessages(messages));
    }

    /**
     * 添加Model消息
     * 
     * @param messages 消息
     */
    protected void addSuccessMessage(Model model, String... messages) {
        model.addAttribute(SUCCESS_MESSAGE, catMessages(messages));
    }

    /**
     * 添加Flash消息
     * 
     * @param messages 消息
     */
    protected void addSuccessMessage(RedirectAttributes redirectAttributes, String... messages) {
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, catMessages(messages));
    }

    protected void addWarnMessage(Model model, String... messages) {
        model.addAttribute(WARN_MESSAGE, catMessages(messages));
    }

    protected void addWarnMessage(RedirectAttributes redirectAttributes, String... messages) {
        redirectAttributes.addFlashAttribute(WARN_MESSAGE, catMessages(messages));
    }

    protected String catMessages(String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            if (message.indexOf("\"message\" : ") > 0) {
                message = message.substring(message.indexOf("\"message\" : ") + 12, message.length() - 2);
            }
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        return sb.toString();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(Model model, Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getLocalizedMessage(), e);
        // model.addAttribute("theme", theme);
        return "error/error";
    }

    /**
     * RequestMapping的路径默认为View JSP的前缀
     * 
     * @return
     */
    protected String fromAnnotation() {
        String prefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            prefix += requestMapping.value()[0];
        }
        return prefix;
    }

    /**
     * 返回功能后缀字符串
     * 
     * @return
     */
    protected String getPrefix() {
        return "";
    }

    /**
     * return request mapping this class annotated with @RequestMapping
     * 
     * @return
     */
    @ModelAttribute("baseUrl")
    protected String getRequestMapping() {
        return requestMapping;
    }

    /**
     * 返回重定向用的字符串。
     * 
     * @param relativeUrl 模块相对URL
     * @return 返回"redirect:"开头的重定向字符串
     */
    protected String redirect(String relativeUrl) {
        if (!StringUtils.isBlank(relativeUrl) && !relativeUrl.startsWith("/")) {
            relativeUrl = "/" + relativeUrl;
        }
        return "redirect:" + getRequestMapping() + relativeUrl;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected void setMethod(Model model, String method) {
        model.addAttribute("method", method);
    }

    /**
     * 获取视图名称：即prefixViewName + "/" + suffixName
     * <p>
     * 例如：前缀返回为user,则user list的视图可通过view("List")返回为userList.jsp
     * </p>
     * 
     * @return
     */
    protected String view(String suffixName) {
        if (!StringUtils.isBlank(suffixName) && !suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }
        String view = getRequestMapping() + suffixName;
        if (view.startsWith("/")) {
            view = view.substring(1);
        }
        log.info(" ****** view is: " + view);
        return view.replaceAll("//", "/");
    }
}