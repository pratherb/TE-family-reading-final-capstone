package com.techelevator.controller;

import com.techelevator.dao.PrizeDao;
import com.techelevator.model.Prize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    public Prize create(@RequestBody Prize prize){
        return prizeDao.create(prize);
    }


}
