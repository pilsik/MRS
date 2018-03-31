package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.controllers.exceptions.AlreadyExistsException;
import by.sivko.miningrigservice.controllers.exceptions.NotExistException;
import by.sivko.miningrigservice.dto.MinerConfigDto;
import by.sivko.miningrigservice.dto.MinerDto;
import by.sivko.miningrigservice.models.configs.MinerConfig;
import by.sivko.miningrigservice.models.miners.Miner;
import by.sivko.miningrigservice.models.user.User;
import by.sivko.miningrigservice.services.configs.MinerConfigService;
import by.sivko.miningrigservice.services.miner.MinerService;
import by.sivko.miningrigservice.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/configs")
public class MinerConfigRestController {

    private MinerConfigService minerConfigService;

    private UserService userService;

    private MinerService minerService;

    @Autowired
    public MinerConfigRestController(MinerConfigService minerConfigService, UserService userService, MinerService minerService) {
        this.minerConfigService = minerConfigService;
        this.userService = userService;
        this.minerService = minerService;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> createRig(@RequestBody @Valid MinerConfigDto minerConfigDto, Principal principal) {
        String username = principal.getName();
        User user = this.userService.findUserByUsername(username);
        if (checkExistConfigByName(user.getMinerConfigs(), minerConfigDto.getName(), 0)) {
            throw new AlreadyExistsException(String.format("A config with name [%s] already exist", minerConfigDto.getName()));
        } else {
            MinerConfig minerConfig = new MinerConfig(minerConfigDto.getName(), user);
            if (minerConfigDto.getMinerId() != null) {
                Miner miner = this.minerService.getMinerById(minerConfigDto.getMinerId());
                if (miner != null)
                    minerConfig.setMiner(miner);
                else throw new NotExistException(String.format("A miner with id [%s] NOT exist", minerConfigDto.getMinerId()));
            }
            this.minerConfigService.addMinerConfig(minerConfig);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    private boolean checkExistConfigByName(List<MinerConfig> minerConfigs, String rigName, long id) {
        boolean isExist = false;
        for (MinerConfig minerConfig : minerConfigs) {
            if (rigName.equalsIgnoreCase(minerConfig.getName()) && minerConfig.getId() != id) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<MinerConfig>> getAllMinerConfigs(Principal principal) {
        List<MinerConfig> rigSet = this.userService.getUserMinerConfigsByUsername(principal.getName());
        if (rigSet.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rigSet, HttpStatus.OK);
    }

    @RequestMapping(value = "/config/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<MinerConfig> getMinerConfigById(@PathVariable long id, Principal principal) {
        MinerConfig minerConfig = checkOwnerConfig(principal.getName(), id);
        if (minerConfig != null) {
            return new ResponseEntity<>(minerConfig, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/config/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> changeMinerConfig(@PathVariable long id,@RequestBody @Valid MinerConfigDto minerConfigDto, Principal principal) {
        String name = principal.getName();
        MinerConfig minerConfig = checkOwnerConfig(name, id);
        if (minerConfig != null) {
            User user = this.userService.findUserByUsername(name);
            if (checkExistConfigByName(user.getMinerConfigs(), minerConfigDto.getName(), id))
                throw new AlreadyExistsException(String.format("Config with name %s already exists", minerConfig.getName()));
            minerConfig.setName(minerConfigDto.getName());
            minerConfig.setCommandLine(minerConfigDto.getCommandLine());
            if (minerConfigDto.getMinerId() != null) {
                Miner miner = this.minerService.getMinerById(minerConfigDto.getMinerId());
                if (miner == null)
                    throw new NotExistException(String.format("Miner with id %s NOT exists", minerConfigDto.getMinerId().toString()));
                minerConfig.setMiner(miner);
            }
            this.minerConfigService.addMinerConfig(minerConfig);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/config/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MinerConfig> removeMinerConfig(@PathVariable long id, Principal principal) {
        MinerConfig minerConfig = checkOwnerConfig(principal.getName(), id);
        if (minerConfig != null) {
            minerConfig.getUser().getMinerConfigs().remove(minerConfig);
            this.minerConfigService.removeMinerConfig(minerConfig);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.LOCKED);
        }
    }

    private MinerConfig checkOwnerConfig(String username, long id) {
        User user = this.userService.findUserByUsername(username);
        for (MinerConfig minerConfig : user.getMinerConfigs()) {
            if (minerConfig.getId() == id) {
                return minerConfig;
            }
        }
        return null;
    }


}
