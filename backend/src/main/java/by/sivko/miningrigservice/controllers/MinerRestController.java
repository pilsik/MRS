package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.models.miners.Miner;
import by.sivko.miningrigservice.services.miner.MinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/miners")
public class MinerRestController {

    private MinerService minerService;

    @Autowired
    public MinerRestController(MinerService minerService) {
        this.minerService = minerService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Miner>> getAllMiners() {
        List<Miner> minerList = this.minerService.getAllMiners();
        if (minerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(minerList, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/miner/{id}",method = RequestMethod.GET)
    public ResponseEntity<Miner> getMinerById(@PathVariable long id) {
        Miner miner = this.minerService.getMinerById(id);
        if (miner == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(miner, HttpStatus.OK);
        }
    }

}
