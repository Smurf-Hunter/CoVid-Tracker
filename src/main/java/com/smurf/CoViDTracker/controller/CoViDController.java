package com.smurf.CoViDTracker.controller;

import com.smurf.CoViDTracker.Service.CoronaDataService;
import com.smurf.CoViDTracker.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CoViDController {

    @Autowired
    private CoronaDataService coronaDataService;

    @GetMapping("/")
    public String mainFunction(Model model){

        List<Report> allCases = coronaDataService.getReportList();
        int total = allCases.stream().mapToInt(report -> report.getConfirms()).sum();
        int totalToday = allCases.stream().mapToInt(report -> report.getLastDay()).sum();
        model.addAttribute("coronas", coronaDataService.getReportList());
        model.addAttribute("total", total);
        model.addAttribute("totalToday", totalToday);
        return "coronaPage";

    }

    @GetMapping("/exit")
    public void exitApp(){
        System.exit(0);
    }

}
