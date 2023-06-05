package com.techelevator.controller;

import com.techelevator.dao.PrizeDao;
import com.techelevator.model.Prize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
public class PrizeController {

    private final String ENDPOINT = "/prize";
    private PrizeDao prizeDao;

    public PrizeController(PrizeDao prizeDao) {
        this.prizeDao = prizeDao;
    }

    //For quick testing
    @RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
    public List<Prize> findAll(){
        return prizeDao.findAll();
    }


}
