package com.bestomb.controller;

import com.bestomb.service.IDashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 后台工作台
 * Created by jason on 2017-03-04.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Resource
    private IDashboardService dashboardService;

    @RequestMapping("/getStatistics")
    public ModelAndView getStatistics(@RequestParam(value = "beginTime", required = false) String beginTime, @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView view = new ModelAndView("dashboard");
        view.addObject("beginTime", beginTime);
        view.addObject("endTime", endTime);
        view.addAllObjects(dashboardService.getStatistics(beginTime, endTime));
        return view;
    }
}
