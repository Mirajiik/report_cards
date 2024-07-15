package com.mirajiik.report_cards.controller;


import com.mirajiik.report_cards.service.ReportCardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reportCards")
public class RESTfullController {
    private ReportCardService service;

}
