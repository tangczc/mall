package com.rootchen.mall.service;

import javax.mail.MessagingException;

/**
 * @description: 发送信息类(邮箱，手机等发送消息)
 * @author: LiChen
 * @create: 2019-05-07 13:46
 */
public interface ISendMsgService {
    /**
     * 发送文本邮件
     * @param to        发送人邮箱
     * @param subject   发送主题
     * @param content   发送内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件
     * @param to        发送人邮箱
     * @param subject   发送主题
     * @param content   发送内容
     * @throws MessagingException
     */
    void sendHtmlMail(String to, String subject, String content) throws MessagingException;

}