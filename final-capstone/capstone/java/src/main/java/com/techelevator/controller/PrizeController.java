package com.techelevator.controller;

import com.techelevator.dao.FamilyDao;
import com.techelevator.dao.PrizeDao;
import com.techelevator.model.Prize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
public class PrizeController {

    private final String ENDPOINT = "/prize";
    private PrizeDao prizeDao;
    private FamilyDao familyDao;

    public PrizeController(PrizeDao prizeDao, FamilyDao familyDao) {
        this.prizeDao = prizeDao;
        this.familyDao = familyDao;
    }

    //All of these should be restricted to only show for Principal (family_id of logged in user)?

    //For quick testing
    @RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
    public List<Prize> findAll() {
        return prizeDao.findAll();
    }

    //groupType should be "parent/child/both";
    //Get all prizes associated with a family
    @RequestMapping(value = ENDPOINT + "/{groupType}", method = RequestMethod.GET)
    public List<Prize> findAllForFamily(@PathVariable String groupType, Principal principal) {
        groupType = groupType.toLowerCase();
        return prizeDao.getPrizesByUserGroup(groupType, principal);
    }

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    public Prize create(@RequestBody Prize prize) {
        return prizeDao.create(prize);
    }

    //Find a prize by its name
    @RequestMapping(value = ENDPOINT + "/name/{prizeName}", method = RequestMethod.GET)
    public Prize findByName(@PathVariable String prizeName) {
        return prizeDao.getByName(prizeName);
    }

    @RequestMapping(value = ENDPOINT + "/name/{prizeName}", method = RequestMethod.PUT)
    public Prize updatePrize(Prize prize){
        return prizeDao.update(prize);
    }

    @RequestMapping(value = ENDPOINT + "/name/{prizeName}", method = RequestMethod.DELETE)
    public void deleteByName(@PathVariable String prizeName){
    }

    //Add a prize to the user's list of won prizes
    @RequestMapping(value = ENDPOINT + "/award/{prizeName}", method = RequestMethod.POST)
    public void awardPrizeToUserByPrizeName(@PathVariable String prizeName, @RequestParam String username){
        prizeDao.awardPrizeByUser(prizeName, username);
    }

    @RequestMapping(value = ENDPOINT + "/award/{username}", method = RequestMethod.GET)
    public List<Prize> findPrizesWonByUser(@PathVariable String username){
        return prizeDao.getPrizesWonByUser(username);
    }




}
