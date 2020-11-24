package com.goldsign.acc.frame.emali;

import com.goldsign.acc.app.system.entity.BaseMessage;
import com.goldsign.acc.frame.constant.FrameDBConstant;
import com.goldsign.acc.frame.entity.EmailContent;
import com.goldsign.acc.frame.entity.EmailErrorLog;
import com.goldsign.acc.frame.mapper.EmailContentMapper;
import com.goldsign.acc.frame.mapper.EmailErrorLogMapper;
import com.goldsign.acc.frame.mapper.EmailRecipientsMapper;
import com.goldsign.acc.systemmonitor.util.DateHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * Description:
 *
 * @author: zhongziqi
 * @Date: 2019-04-25
 * @Time: 14:43
 */
@Service
public class EmailService {


    public static final String PROTOCOL_SMTP = "smtp";
    public static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=UTF-8";
    private static Logger logger = Logger.getLogger(EmailService.class);
    @Autowired
    private EmailRecipientsMapper emailRecipientsMapper;
    @Autowired
    private EmailContentMapper emailContentMapper;
    @Autowired
    private EmailErrorLogMapper emailErrorLogMapper;
    @Autowired
    protected DataSourceTransactionManager txMgr;
    @Autowired
    private DefaultTransactionDefinition def;

    public ArrayList getTOList() {
        return emailRecipientsMapper.getTOList();
    }

    public ArrayList getCCList() {
        return emailRecipientsMapper.getCCList();
    }

    public ArrayList<EmailContent> getContents() {
        return emailContentMapper.getContents();
    }

    public int updateForSend(EmailContent record) {
        TransactionStatus status = null;
        int result = 0;
        try {
            status = txMgr.getTransaction(this.def);
            result = emailContentMapper.updateForSend(record);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            e.getStackTrace();
        }
        return result;
    }

    public Address[] getAddresses(ArrayList list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        InternetAddress[] addresses = new InternetAddress[list.size()];
        for (int i = 0; i < list.size(); i++) {
            try {
                addresses[i] = new InternetAddress((String) list.get(i));
            } catch (AddressException e) {
                logger.error("数据异常：" + list.get(i) + e.getMessage());
                return null;
            }

        }
        return addresses;
    }

    public void sendEmail(Address[] toAddresses, Address[] ccAddresses, ArrayList<EmailContent> contentList) throws Exception {
        if (contentList == null || contentList.size() == 0 || toAddresses == null || toAddresses.length == 0) {
            logger.error("send Error=====缺少收件人或无发送内容");
            return;
        }
        //1、连接邮件服务器的参数配置
        Properties props = getEmailProperties();
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        MimeMessage msg = getMimeMessage(toAddresses, ccAddresses, contentList, session);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(FrameDBConstant.EMAIL_ACCOUNT, FrameDBConstant.EMAIL_PASSWORD);
        //发送邮件，并发送到所有收件人地址， 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg, msg.getAllRecipients());
        //5、关闭邮件连接
        transport.close();
    }

    public MimeMessage getMimeMessage(Address[] toAddresses, Address[] ccAddresses, ArrayList<EmailContent> contentList, Session session) throws MessagingException {
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(FrameDBConstant.EMAIL_SENDER_ADDRESS));
        msg.setRecipients(MimeMessage.RecipientType.TO, toAddresses);
        if (ccAddresses != null && ccAddresses.length > 0) {
            msg.setRecipients(MimeMessage.RecipientType.CC, ccAddresses);
        }
        //设置邮件主题
        msg.setSubject(FrameDBConstant.EMAIL_TITLE, "UTF-8");
        StringBuffer contentBuffer = getEmailContent(contentList);
        //设置邮件正文
        msg.setContent(contentBuffer.toString(), TEXT_HTML_CHARSET_UTF_8);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        return msg;
    }

    public StringBuffer getEmailContent(ArrayList<EmailContent> contentList) {
        StringBuffer contentBuffer = new StringBuffer();
        contentBuffer.append("<body>");
        contentBuffer.append("<table>");
        contentBuffer.append("<thead>");
        contentBuffer.append("<tr>");
        contentBuffer.append("<th style=\"width: 100px;text-align:center\">模块</th>");
        contentBuffer.append("<th style=\"width: 200px;text-align:center\">服务器名</th>");
        contentBuffer.append("<th style=\"width: 100px;text-align:center\">ip</th>");
        contentBuffer.append("<th style=\"width: 100px;text-align:center\">状态</th>");
        contentBuffer.append("<th style=\"width: 200px;text-align:center\">解析时间</th>");
        contentBuffer.append("<th style=\"width: 300px;text-align:center\">其他信息</th>");
        contentBuffer.append("</tr>");
        contentBuffer.append("</thead>");
        contentBuffer.append("<tbody>");
//        contentBuffer.append("模块-服务器名-ip-状态-解析时间-其他信息<br/>");
        for (int i = 0; i < contentList.size(); i++) {
            EmailContent vo = contentList.get(i);
            contentBuffer.append("<tr>");
            contentBuffer.append("<td style=\"width: 100px;text-align:center\">");
            contentBuffer.append(vo.getModule());
            contentBuffer.append("</td>");
            contentBuffer.append("<td style=\"width: 200px;text-align:center\">");
            contentBuffer.append(vo.getName());
            contentBuffer.append("</td>");
//            contentBuffer.append("-");
            contentBuffer.append("<td style=\"width: 100px;text-align:center\">");
            contentBuffer.append(vo.getIp());
            contentBuffer.append("</td>");
//            contentBuffer.append("-");
            contentBuffer.append("<td style=\"width: 100px;text-align:center\">");
            contentBuffer.append(vo.getStatus());
            contentBuffer.append("</td>");
//            contentBuffer.append("-");
            contentBuffer.append("<td style=\"width: 200px;text-align:center\">");
            contentBuffer.append(vo.getPasreTime());
            contentBuffer.append("</td>");
            contentBuffer.append("<td style=\"width: 300px;text-align:center\"> ");
            if (vo.getMessage() != null) {
//                contentBuffer.append("-");
                contentBuffer.append(vo.getMessage());
            }
            contentBuffer.append("</td>");
            contentBuffer.append("</tr>");
//            contentBuffer.append("</br>");
        }
        contentBuffer.append("</tbody>");
        contentBuffer.append("</table>");
        contentBuffer.append("<br/>");
        contentBuffer.append("<span style=\"font-size:17px\">");
        contentBuffer.append(FrameDBConstant.EMAIL_SENDER_ADDRESS);
        contentBuffer.append("<span style=\"font-size:17px\"> </span></body>");
        return contentBuffer;
    }

    public Properties getEmailProperties() {
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", FrameDBConstant.MAIL_SMTP_AUTH_USE);
        //设置传输协议
        props.setProperty("mail.transport.protocol", PROTOCOL_SMTP);
        props.setProperty("mail.smtp.ssl.enable", FrameDBConstant.MAIL_SMTP_SSL_ENABLE_USE);

        props.setProperty("mail.smtp.port", FrameDBConstant.MAIL_SMTP_PORT);
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", FrameDBConstant.MAIL_SMTP_HOST);
        return props;
    }

    public int checkOldAbnormal(EmailContent record) {
        return emailContentMapper.checkOldAbnormal(record);
    }

    public int insertSelective(EmailContent record) {
        TransactionStatus status = null;
        int result = 0;
        try {
            status = txMgr.getTransaction(this.def);
            result = emailContentMapper.insertSelective(record);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            logger.error("EmailService.insertSelective Error===" + e.getMessage());
        }
        return result;
    }


    public int checkNeedUpdate(BaseMessage vo) {
        return emailContentMapper.checkNeedUpdate(vo);
    }

    public int updateHandleAbnormal(BaseMessage vo) {
        TransactionStatus status = null;
        int result = 0;
        try {
            status = txMgr.getTransaction(this.def);
            result = emailContentMapper.updateHandleAbnormal(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            logger.error("EmailService.updateHandleAbnormal Error===" + e.getMessage());
        }
        return result;
    }

    public int recordEmailSendError(EmailErrorLog vo) {
        TransactionStatus status = null;
        int result = 0;
        try {
            status = txMgr.getTransaction(this.def);
            result = emailErrorLogMapper.insert(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            logger.error("EmailService.recordEmailSendError Error===" + e.getMessage());
        }
        return result;
    }
}
