package com.wyait.manage.web.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/basic")
public class ProcedureController {
    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);

    @RequestMapping("/procedure")
    public String toWorkStation(){
        return "/basic/procedure";
    }
}
