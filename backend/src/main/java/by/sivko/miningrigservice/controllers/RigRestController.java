package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.controllers.exceptions.AlreadyExistsException;
import by.sivko.miningrigservice.dto.RigDto;
import by.sivko.miningrigservice.models.rigs.Rig;
import by.sivko.miningrigservice.models.user.User;
import by.sivko.miningrigservice.services.rig.RigService;
import by.sivko.miningrigservice.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/rigs")
public class RigRestController {

    private RigService rigService;

    private UserService userService;

    @Autowired
    public RigRestController(RigService rigService, UserService userService) {
        this.rigService = rigService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Rig>> getRigsOfUser(Principal principal) {
        List<Rig> rigList = this.userService.getUserRigsByUsername(principal.getName());
        if (rigList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rigList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createRig(@Valid RigDto rigDto, Principal principal, UriComponentsBuilder ucBuilder) {
        String username = principal.getName();
        User user = this.userService.findUserByUsername(username);
        if (checkExistRigByName(user.getUserRigList(), rigDto.getName())) {
            throw new AlreadyExistsException(String.format("A rig with name [%s] already exist", rigDto.getName()));
        } else {
            Rig newRig = new Rig(rigDto.getName(), rigDto.getPassword(), user);
            rigService.addRig(newRig);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/rigs").buildAndExpand().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    private boolean checkExistRigByName(List<Rig> rigList, String rigName) {
        boolean isExist = false;
        for (Rig rig : rigList) {
            if (rigName.equalsIgnoreCase(rig.getName())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    @RequestMapping(value = "/rig/{id}", method = RequestMethod.GET)
    public ResponseEntity<Rig> getRig(@PathVariable long id, Principal principal) {
        List<Rig> rigs = this.userService.getUserRigsByUsername(principal.getName());
        Rig rig = checkUserOwnerRigAndGetRig(rigs, id);
        if (rig != null) {
            return new ResponseEntity<>(rig, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
    }

    @RequestMapping(value = "/rig/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeRig(@PathVariable long id, Principal principal) {
        List<Rig> rigs = this.userService.getUserRigsByUsername(principal.getName());
        Rig rig = checkUserOwnerRigAndGetRig(rigs, id);
        if (rig != null) {
            rigService.removeRig(rig);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
    }

    @RequestMapping(value = "/rig/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> changeRigPasswordOrName(@PathVariable long id, @Valid RigDto rigDto, Principal principal) {
        List<Rig> rigs = this.userService.getUserRigsByUsername(principal.getName());
        Rig rig = checkUserOwnerRigAndGetRig(rigs, id);
        if (rig != null) {
            if (rigDto.getPassword() != null && !rigDto.getPassword().isEmpty()) {
                rig.setPassword(rigDto.getPassword());
            }
            if (rigDto.getName() != null && !rigDto.getName().isEmpty()) {
                if (checkExistRigByName(rigs, rigDto.getName()))
                    throw new AlreadyExistsException(String.format("A rig with name [%s] already exist", rigDto.getName()));
                rig.setName(rigDto.getName());
            }
            this.rigService.addRig(rig);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
    }


    private Rig checkUserOwnerRigAndGetRig(List<Rig> rigList, long id) {
        for (Rig rig : rigList) {
            if (rig.getId() == id) {
                return rig;
            }
        }
        return null;
    }
}
