package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.models.rigs.Rig;
import by.sivko.miningrigservice.models.status.Status;
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
    public ResponseEntity<Void> setStatsForRig(@PathVariable long rigId, String password, @RequestBody String stats) {
        Rig rig = this.rigService.getRigById(rigId);
        if (!rig.getPassword().equals(password)) return new ResponseEntity<>(HttpStatus.LOCKED);
        if (rig.getStatus() == null) rig.setStatus(new Status(rig, stats));
        else rig.getStatus().setStats(stats);
        rig.getStatus().setOnline(true);
        this.rigService.updateRig(rig);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
