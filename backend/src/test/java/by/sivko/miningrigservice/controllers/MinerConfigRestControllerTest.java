package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.MiningRigServiceApplication;
import by.sivko.miningrigservice.models.configs.MinerConfig;
import by.sivko.miningrigservice.models.miners.Miner;
import by.sivko.miningrigservice.models.user.User;
import by.sivko.miningrigservice.services.configs.MinerConfigService;
import by.sivko.miningrigservice.services.miner.MinerService;
import by.sivko.miningrigservice.services.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestPropertySource("classpath:application-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MiningRigServiceApplication.class)
@WebAppConfiguration
public class MinerConfigRestControllerTest {

    private static final String USER_USERNAME = "FIRST_USER_USERNAME";
    private static final String USER_PASSWORD = "FIRST_USER_PASSWORD";
    private static final String USER_EMAIL = "FIRST_USER_EMAIL";
    private static final String FIRST_MINER_CONFIG_NAME = "FIRST_MINER_CONFIG_NAME";
    private static final String SECOND_MINER_CONFIG_NAME = "SECOND_MINER_CONFIG_NAME";
    private static final String MOCK_GET_NAME = USER_USERNAME;
    private static final String MINER_NAME = "MINER_NAME";
    private static final String MINER_PATH_TO_EXE_FILE = "MINER_PATH_TO_EXE_FILE";
    private static final String MINER_DEFAULT_COMMAND_LINE_WITH_PARAMETERS = "MINER_DEFAULT_COMMAND_LINE_WITH_PARAMETERS";
    private static final String MINER_VERSION = "MINER_VERSION";
    private static final String MINER_DATA_REALISE = "MINER_DATA_REALISE";
    private static final Long MINER_VALUE_FIELD_ID = 1L;

    private static final int HTTP_RESPONSE_CREATED_CODE = 201;
    private static final int HTTP_RESPONSE_CONFLICT_CODE = 409;
    private static final int HTTP_RESPONSE_SUCCESS_CODE = 200;
    private static final int HTTP_RESPONSE_NO_CONTENT_CODE = 204;
    private static final int HTTP_RESPONSE_LOCKED_CODE = 423;

    private static final String USER_USERNAME_EMPTY_CONFIGS = "USER_USERNAME_EMPTY_CONFIGS";
    private static final String USER_PASSWORD_EMPTY_CONFIGS = "USER_PASSWORD_EMPTY_CONFIGS";
    private static final String USER_EMAIL_EMPTY_CONFIGS = "USER_EMAIL_EMPTY_CONFIGS";
    private static final String MOCK_GET_NAME_EMPTY_CONFIGS = USER_USERNAME_EMPTY_CONFIGS;
    private static final String MINER_CONFIG_FIELD_ID = "id";
    private static final Long FIRST_MINER_CONFIG_ID = 1L;
    private static final Long SECOND_MINER_CONFIG_ID = 2L;


    @Autowired
    WebApplicationContext webApplicationContext;

    @Mock
    Principal mockPrincipal;

    @Mock
    Principal mockPrincipalWithEmptyMinerConfigs;

    @Mock
    MinerConfigService mockMinerConfigService;

    @Mock
    UserService mockUserService;

    @Mock
    MinerService mockMinerService;

    MockMvc mockMvc;

    @Autowired
    @InjectMocks
    MinerConfigRestController minerConfigRestController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        User user = new User(USER_USERNAME, USER_PASSWORD, USER_EMAIL);
        User userWithEmptyConfigs = new User(USER_USERNAME_EMPTY_CONFIGS, USER_PASSWORD_EMPTY_CONFIGS, USER_EMAIL_EMPTY_CONFIGS);
        MinerConfig firstMinerConfig = new MinerConfig(FIRST_MINER_CONFIG_NAME, user);
        MinerConfig secondMinerConfig = new MinerConfig(SECOND_MINER_CONFIG_NAME, user);
        try {
            Field firstField = firstMinerConfig.getClass().getDeclaredField(MINER_CONFIG_FIELD_ID);
            firstField.setAccessible(true);
            firstField.set(firstMinerConfig, FIRST_MINER_CONFIG_ID);
            Field secondField = secondMinerConfig.getClass().getDeclaredField(MINER_CONFIG_FIELD_ID);
            secondField.setAccessible(true);
            secondField.set(secondMinerConfig, SECOND_MINER_CONFIG_ID);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        List<MinerConfig> userMinerConfigs = new ArrayList<>(Arrays.asList(firstMinerConfig, secondMinerConfig));
        user.setMinerConfigs(userMinerConfigs);
        Miner miner = new Miner(MINER_NAME, MINER_PATH_TO_EXE_FILE, MINER_DEFAULT_COMMAND_LINE_WITH_PARAMETERS, MINER_VERSION, MINER_DATA_REALISE) {
        };
        Mockito.when(mockPrincipal.getName()).thenReturn(MOCK_GET_NAME);
        Mockito.when(mockPrincipalWithEmptyMinerConfigs.getName()).thenReturn(MOCK_GET_NAME_EMPTY_CONFIGS);
        Mockito.when(mockUserService.findUserByUsername(MOCK_GET_NAME)).thenReturn(user);
        Mockito.when(mockUserService.findUserByUsername(MOCK_GET_NAME_EMPTY_CONFIGS)).thenReturn(userWithEmptyConfigs);
        Mockito.when(mockMinerService.getMinerById(MINER_VALUE_FIELD_ID)).thenReturn(miner);
        Mockito.when(mockUserService.getUserMinerConfigsByUsername(MOCK_GET_NAME)).thenReturn(userMinerConfigs);
        Mockito.when(mockUserService.getUserMinerConfigsByUsername(MOCK_GET_NAME_EMPTY_CONFIGS)).thenReturn(userWithEmptyConfigs.getMinerConfigs());
    }

    private static final String PATH_CREATE_MINER_CONFIG = "/api/configs";
    private static final String REQUEST_PARAM_MINER_CONFIG_NAME = "name";
    private static final String REQUEST_PARAM_MINER_CONFIG_NAME_VALUE = "REQUEST_PARAM_MINER_CONFIG_NAME_VALUE";
    private static final String REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE = "commandLine";
    private static final String REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE = "REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE";

    @Test
    public void createRigWithoutMinerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_MINER_CONFIG)
                .principal(mockPrincipal)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, REQUEST_PARAM_MINER_CONFIG_NAME_VALUE)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CREATED_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(1)).addMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    private static final String REQUEST_PARAM_MINER_ID = "minerId";
    private static final Long REQUEST_PARAM_NOT_EXISTS_MINER_ID_VALUE = 111L;

    @Test
    public void createRigWithNotExistsMinerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_MINER_CONFIG)
                .principal(mockPrincipal)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, REQUEST_PARAM_MINER_CONFIG_NAME_VALUE)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE)
                .param(REQUEST_PARAM_MINER_ID, REQUEST_PARAM_NOT_EXISTS_MINER_ID_VALUE.toString()))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(0)).addMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    private static final Long REQUEST_PARAM_EXISTS_MINER_ID_VALUE = MINER_VALUE_FIELD_ID;

    @Test
    public void createRigWithExistsMinerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_MINER_CONFIG)
                .principal(mockPrincipal)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, REQUEST_PARAM_MINER_CONFIG_NAME_VALUE)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE)
                .param(REQUEST_PARAM_MINER_ID, REQUEST_PARAM_EXISTS_MINER_ID_VALUE.toString()))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CREATED_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(1)).addMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    private static final String REQUEST_PARAM_EXIST_NAME_OF_MINER_CONFIG_NAME_VALUE = FIRST_MINER_CONFIG_NAME;

    @Test
    public void createRigWithExistsNameOfMinerConfig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_MINER_CONFIG)
                .principal(mockPrincipal)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, REQUEST_PARAM_EXIST_NAME_OF_MINER_CONFIG_NAME_VALUE)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(0)).addMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    private static final String PATH_GET_ALL_MINER_CONFIG = "/api/configs";

    @Test
    public void getAllMinerConfigs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET_ALL_MINER_CONFIG)
                .principal(mockPrincipal))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(FIRST_MINER_CONFIG_NAME)))
                .andExpect(jsonPath("$[1].name", is(SECOND_MINER_CONFIG_NAME)));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(0)).findUserByUsername(Mockito.any(String.class));
        verify(mockUserService, times(1)).getUserMinerConfigsByUsername(Mockito.any(String.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
    }

    @Test
    public void getAllMinerConfigsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET_ALL_MINER_CONFIG)
                .principal(mockPrincipalWithEmptyMinerConfigs))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_NO_CONTENT_CODE));
        verify(mockPrincipalWithEmptyMinerConfigs, times(2)).getName();
        verify(mockUserService, times(0)).findUserByUsername(Mockito.any(String.class));
        verify(mockUserService, times(1)).getUserMinerConfigsByUsername(Mockito.any(String.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
    }

    private static final String PATH_GET_MINER_CONFIG_BY_ID = "/api/configs/config/"+FIRST_MINER_CONFIG_ID;

    @Test
    public void getMinerConfigById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET_MINER_CONFIG_BY_ID)
                .principal(mockPrincipal))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE))
                .andExpect(jsonPath("$.name", is(FIRST_MINER_CONFIG_NAME)));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
    }

    private static final String PATH_GET_OTHERS_MINER_CONFIG_BY_ID = "/api/configs/config/"+REQUEST_PARAM_NOT_EXISTS_MINER_ID_VALUE.toString();

    @Test
    public void getOthersMinerConfigById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET_OTHERS_MINER_CONFIG_BY_ID)
                .principal(mockPrincipal))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_LOCKED_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
    }

    private static final String PATH_TO_REMOVE_MINER_CONFIG = "/api/configs/config/"+FIRST_MINER_CONFIG_ID;

    @Test
    public void removeMinerConfig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_TO_REMOVE_MINER_CONFIG)
                .principal(mockPrincipal))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(1)).removeMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    @Test
    public void removeOtherMinerConfig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_TO_REMOVE_MINER_CONFIG)
                .principal(mockPrincipalWithEmptyMinerConfigs))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_LOCKED_CODE));
        verify(mockPrincipalWithEmptyMinerConfigs, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(0)).removeMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    private static final String PATH_PUT_MINER_CONFIG = "/api/configs/config/"+FIRST_MINER_CONFIG_ID;

    @Test
    public void changeMinerConfig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_PUT_MINER_CONFIG)
                .principal(mockPrincipal)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, REQUEST_PARAM_MINER_CONFIG_NAME_VALUE)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(2)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(1)).addMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    @Test
    public void changeMinerConfigWithSameName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_PUT_MINER_CONFIG)
                .principal(mockPrincipal)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, FIRST_MINER_CONFIG_NAME)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(2)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(1)).addMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    @Test
    public void changeMinerConfigWithExistName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_PUT_MINER_CONFIG)
                .principal(mockPrincipal)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, SECOND_MINER_CONFIG_NAME)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(2)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(0)).addMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    private static final String MINER_CONFIG_NAME_LESS_THREE_CHARACTERS = "a";

    @Test
    public void changeMinerConfigWithNameLessThreeCharacters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_PUT_MINER_CONFIG)
                .principal(mockPrincipal)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, MINER_CONFIG_NAME_LESS_THREE_CHARACTERS)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipal, times(1)).getName();
        verify(mockUserService, times(0)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(0)).addMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    @Test
    public void changeOthersMinerConfig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_PUT_MINER_CONFIG)
                .principal(mockPrincipalWithEmptyMinerConfigs)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, REQUEST_PARAM_MINER_CONFIG_NAME_VALUE)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_LOCKED_CODE));
        verify(mockPrincipalWithEmptyMinerConfigs, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(0)).addMinerConfig(Mockito.any(MinerConfig.class));
        verifyNoMoreInteractions(mockPrincipalWithEmptyMinerConfigs);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
    }

    @Test
    public void changeMinerConfigWithExistsMinerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_PUT_MINER_CONFIG)
                .principal(mockPrincipal)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, REQUEST_PARAM_MINER_CONFIG_NAME_VALUE)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE)
                .param(REQUEST_PARAM_MINER_ID, REQUEST_PARAM_EXISTS_MINER_ID_VALUE.toString()))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(2)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(1)).addMinerConfig(Mockito.any(MinerConfig.class));
        verify(mockMinerService, times(1)).getMinerById(Mockito.any(Long.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
        verifyNoMoreInteractions(mockMinerService);
    }

    @Test
    public void changeMinerConfigWithNotExistsMinerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_PUT_MINER_CONFIG)
                .principal(mockPrincipal)
                .param(REQUEST_PARAM_MINER_CONFIG_NAME, REQUEST_PARAM_MINER_CONFIG_NAME_VALUE)
                .param(REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE, REQUEST_PARAM_MINER_CONFIG_COMMAND_LINE_VALUE)
                .param(REQUEST_PARAM_MINER_ID, REQUEST_PARAM_NOT_EXISTS_MINER_ID_VALUE.toString()))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipal, times(2)).getName();
        verify(mockUserService, times(2)).findUserByUsername(Mockito.any(String.class));
        verify(mockMinerConfigService, times(0)).addMinerConfig(Mockito.any(MinerConfig.class));
        verify(mockMinerService, times(1)).getMinerById(Mockito.any(Long.class));
        verifyNoMoreInteractions(mockPrincipal);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockMinerConfigService);
        verifyNoMoreInteractions(mockMinerService);
    }
}