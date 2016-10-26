package com.bestomb.common.listener;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.util.LunarUtils;
import com.bestomb.common.util.SMSUtils;
import com.bestomb.common.util.YamlForMapHandleUtil;
import com.bestomb.common.util.socket.ServerSocketByCemetery;
import com.bestomb.common.util.yamlMapper.ClientConf;
import com.bestomb.dao.IMemberAccountDao;
import com.bestomb.dao.ISpecialHolidayDao;
import com.bestomb.entity.MemberAccount;
import com.bestomb.entity.SpecialHoliday;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 特殊节日短信推送监听类
 * Created by jason on 2016-08-01.
 */
public class SpecialHolidayListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(this.getClass());

    private ISpecialHolidayDao specialHolidayDao;

    private IMemberAccountDao memberAccountDao;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebApplicationContext appctx = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        specialHolidayDao = (ISpecialHolidayDao) appctx.getBean("ISpecialHolidayDao");
        memberAccountDao = (IMemberAccountDao) appctx.getBean("IMemberAccountDao");

        Yaml yaml = new Yaml();
        /**
         * 如果配置文件在jar包中，则需要使用流加载方式读取
         */
        Map<String, Object> map = yaml.loadAs(ServerSocketByCemetery.class.getResourceAsStream("/client-conf.yaml"), Map.class);
        //获取配置时间（短信推送时间节点）
        String[] executeNode = String.valueOf(YamlForMapHandleUtil.getMapByKey(map, ClientConf.SpecialHolidayByMsg.execute_node.toString())).split("-");
        final int beginTime = Integer.parseInt(executeNode[0]);
        final int endTime = Integer.parseInt(executeNode[1]);

        //开启线程，定时轮询
        new Thread(new Runnable() {
            public void run() {
                logger.debug("特殊节日短信推送程序开始执行 ...");
                /**
                 * 如果当前线程满足执行网关的条件，则在执行完业务后，
                 * 将当前系统时间（天）记录到day中，下一次线程运行，
                 * 优先判断day中是否有值，且值是否为当天，如果是，则无需继续业务处理
                 */
                int day = 0;
                //死循环
                while (true) {
                    //线程休眠，休眠时长：1小时
                    try {
                        Thread.sleep(1 * 1000 * 60 * 60);
                    } catch (InterruptedException e) {
                        logger.error("特殊节日短信推送程序线程休眠失败", e);
                    }

                    //获取当前系统时间-时
                    Calendar c = Calendar.getInstance();
                    int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                    int hour = c.get(Calendar.HOUR_OF_DAY);

                    if (dayOfMonth == day) {
                        continue;
                    }

                    //判断当前系统时间（小时）是否在配置的推送时间节点范围内，如果在，则出发短息网关
                    if (hour < beginTime || hour > endTime) {
                        continue;
                    }

                    //查询特殊节日数据集合
                    List<SpecialHoliday> specialHolidays = specialHolidayDao.selectAll();

                    //如果没有节日数据，则无需继续业务
                    if (CollectionUtils.isEmpty(specialHolidays)) {
                        continue;
                    }

                    //查询全部会员手机号和昵称
                    List<MemberAccount> memberAccounts = memberAccountDao.selectAll();
                    //如果没有会员数据，则无需继续业务
                    if (CollectionUtils.isEmpty(memberAccounts)) {
                        continue;
                    }

                    //获取系统日期下一天的公历日期
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    int monthByGL = c.get(Calendar.MONTH) + 1;
                    int dayByGL = c.get(Calendar.DAY_OF_MONTH);

                    //获取系统日期下一天的农历日期
                    LunarUtils lunar = new LunarUtils(c);
                    int monthByNL = Integer.parseInt(lunar.numeric_md().substring(0, 2));
                    int dayByNL = Integer.parseInt(lunar.numeric_md().substring(2));

                    //遍历节日输入
                    for (SpecialHoliday specialHoliday : specialHolidays) {
                        //根据节日类型，比较节日月-日是否为系统当前天的下一天
                        if (specialHoliday.getType().intValue() == 1) {
                            if (specialHoliday.getDateMonth().intValue() != monthByNL) {
                                continue;
                            }
                            if (specialHoliday.getDateDay().intValue() != dayByNL) {
                                continue;
                            }

                            //农历比较
                            sendByMembers(memberAccounts, specialHoliday);
                        } else {
                            if (specialHoliday.getDateMonth().intValue() != monthByGL) {
                                continue;
                            }
                            if (specialHoliday.getDateDay().intValue() != dayByGL) {
                                continue;
                            }

                            //公历比较
                            sendByMembers(memberAccounts, specialHoliday);
                        }
                    }

                    day = dayOfMonth;
                }
            }
        }).start();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    /**
     * 给会员发送短信
     *
     * @param memberAccounts
     */
    private void sendByMembers(List<MemberAccount> memberAccounts, SpecialHoliday specialHoliday) {
        for (MemberAccount memberAccount : memberAccounts) {
            if (ObjectUtils.isEmpty(memberAccount.getMobileNumber())) {
                continue;
            }
//            if (StringUtils.isEmpty(memberAccount.getNickName())) {
//                continue;
//            }

            try {
                SMSUtils.batchSend2(String.valueOf(memberAccount.getMobileNumber()), specialHoliday.getMessage());
            } catch (EqianyuanException e) {
                logger.warn("特殊节日短信发送失败", e);
            } catch (IOException e) {
                logger.warn("特殊节日短信发送失败", e);
            }
        }
    }
}
