package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.MiningRigServiceApplication;
import by.sivko.miningrigservice.models.miners.Miner;
import by.sivko.miningrigservice.services.miner.MinerService;
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

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@TestPropertySource("classpath:application-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MiningRigServiceApplication.class)
@WebAppConfiguration
public class MinerRestControllerTest {

    private static final String FIRST_MINER_NAME = "FIRST_MINER_NAME";
    private static final String FIRST_MINER_PATH_TO_EXE_FILE = "FIRST_MINER_PATH_TO_EXE_FILE";
    private static final String FIRST_MINER_DEFAULT_COMMAND_LINE_WITH_PARAMETERS = "FIRST_MINER_DEFAULT_COMMAND_LINE_WITH_PARAMETERS";
    private static final String FIRST_MINER_VERSION = "FIRST_MINER_VERSION";
    private static final String FIRST_MINER_DATA_REALISE = "FIRST_MINER_DATA_REALISE";
    private static final String SECOND_MINER_NAME = "SECOND_MINER_NAME";
    private static final String SECOND_MINER_PATH_TO_EXE_FILE = "SECOND_MINER_PATH_TO_EXE_FILE";
    private static final String SECOND_MINER_DEFAULT_COMMAND_LINE_WITH_PARAMETERS = "SECOND_MINER_DEFAULT_COMMAND_LINE_WITH_PARAMETERS";
    private static final String SECOND_MINER_VERSION = "SECOND_MINER_VERSION";
    private static final String SECOND_MINER_DATA_REALISE = "SECOND_MINER_DATA_REALISE";
    private static final String PATH_GET_ALL_MINERS = "/api/miners";
    private static final int HTTP_RESPONSE_SUCCESS_CODE = 200;
    private static final int HTTP_RESPONSE_NO_CONTENT_CODE = 204;
    private static final Long FIRST_MINER_ID = 1L;
    private static final String PATH_GET_FIRST_MINER = PATH_GET_ALL_MINERS + "/miner/" + FIRST_MINER_ID.toString();
    private static final Long NOT_EXIST_MINER_ID = 111L;
    private static final String PATH_GET_NOT_EXIST_MINER = PATH_GET_ALL_MINERS + "/miner/" + NOT_EXIST_MINER_ID.toString();

    @Autowired
    WebApplicationContext webApplicationContext;

    @Mock
    MinerService mockMinerService;

    MockMvc mockMvc;

    @Autowired
    @InjectMocks
    MinerRestController minerRestController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Miner firstMiner = new Miner(FIRST_MINER_NAME, FIRST_MINER_PATH_TO_EXE_FILE, FIRST_MINER_DEFAULT_COMMAND_LINE_WITH_PARAMETERS, FIRST_MINER_VERSION) {
        };
        Miner secondMiner = new Miner(SECOND_MINER_NAME, SECOND_MINER_PATH_TO_EXE_FILE, SECOND_MINER_DEFAULT_COMMAND_LINE_WITH_PARAMETERS, SECOND_MINER_VERSION) {
        };
        Mockito.when(mockMinerService.getAllMiners()).thenReturn(new ArrayList<>(Arrays.asList(firstMiner, secondMiner)));
        Mockito.when(mockMinerService.getMinerById(FIRST_MINER_ID)).thenReturn(firstMiner);
        Mockito.when(mockMinerService.getMinerById(NOT_EXIST_MINER_ID)).thenReturn(null);
    }


    @Test
    public void getAllMiners() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET_ALL_MINERS))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(FIRST_MINER_NAME)))
                .andExpect(jsonPath("$[1].name", is(SECOND_MINER_NAME)));
        verify(mockMinerService, times(1)).getAllMiners();
        verifyNoMoreInteractions(mockMinerService);
    }

    @Test
    public void getMinerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET_FIRST_MINER))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE))
                .andExpect(jsonPath("$.name", is(FIRST_MINER_NAME)))
                .andExpect(jsonPath("$.version", is(FIRST_MINER_VERSION)));
        verify(mockMinerService, times(1)).getMinerById(Mockito.anyLong());
        verifyNoMoreInteractions(mockMinerService);
    }

    @Test
    public void getNotExistMinerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET_NOT_EXIST_MINER))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_NO_CONTENT_CODE));
        verify(mockMinerService, times(1)).getMinerById(Mockito.anyLong());
        verifyNoMoreInteractions(mockMinerService);
    }
}