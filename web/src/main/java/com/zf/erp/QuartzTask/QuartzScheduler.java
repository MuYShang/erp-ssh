package com.zf.erp.QuartzTask;

import com.zf.erp.domain.Storedetail;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;


public class QuartzScheduler {

    //环绕通知，用于监听每次库存变化
    public void aroundTransaction(ProceedingJoinPoint point) throws SchedulerException{

//        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
//        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class).withIdentity("myJob").build();
//        //创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
//        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
//        //创建schedule实例
//        StdSchedulerFactory factory = new StdSchedulerFactory();
//        Scheduler scheduler = factory.getScheduler();
//        scheduler.clear();
//        scheduler.start();
//        scheduler.scheduleJob(jobDetail,trigger);
            try {
                List<Storedetail> list = (List) point.proceed();
                //当仓库中某件商品库存小于100时开启任务
                if (null != list) {
                    for (Storedetail storedetail : list) {
                        if (storedetail.getNum() < 100) {
                            //开始发送邮件
                            Properties prop = new Properties();
                            prop.setProperty("mail.host", "smtp.qq.com");
                            prop.setProperty("mail.transport.protocol", "smtp");
                            prop.setProperty("mail.smtp.auth", "true");
                            //使用JavaMail发送邮件的5个步骤
                            //1、创建session
                            Session session = Session.getInstance(prop);
                            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
                            session.setDebug(true);
                            //2、通过session得到transport对象
                            Transport ts = session.getTransport();
                            //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
                            ts.connect("smtp.qq.com", "624006385", "fatohyhtqdsrbcib");
                            //4、创建邮件
                            Message message = createSimpleMail(session, storedetail.getGoodsName(), storedetail.getStoreName(), storedetail.getNum());
                            //5、发送邮件
                            ts.sendMessage(message, message.getAllRecipients());
                            ts.close();
                        }
                    }
                }
                } catch(Throwable throwable){
                    throwable.printStackTrace();
                }


    }

    public static MimeMessage createSimpleMail(Session session,String goodsname,String storename,int total)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("624006385@qq.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("2638627737@qq.com"));
        //邮件的标题
        message.setSubject("库存预警");
        //邮件的文本内容
        message.setContent("您【" + storename + "】中的【" + goodsname + "】库存已" +
                "达到预警值，当前库存为：" + total + ".请及时补足.", "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }


}
