package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.models.rigs.Rig;
import by.sivko.miningrigservice.services.rig.RigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/status")
public class StatusRestController {

    @Autowired
    RigService rigService;

    @CrossOrigin
    @RequestMapping("/{rigId}")
    public ResponseEntity<Void> setStatsForRig(@PathVariable long rigId, String password,@RequestBody String stats) {
        Rig rig = this.rigService.getRigById(rigId);
        if (!rig.getPassword().equals(password)) return new ResponseEntity<>(HttpStatus.LOCKED);
        rig.getStatus().setStats(stats);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
