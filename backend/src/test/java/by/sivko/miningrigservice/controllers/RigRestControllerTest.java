package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.MiningRigServiceApplication;
import by.sivko.miningrigservice.models.rigs.Rig;
import by.sivko.miningrigservice.models.user.User;
import by.sivko.miningrigservice.services.rig.RigService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestPropertySource("classpath:application-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MiningRigServiceApplication.class)
@WebAppConfiguration
public class RigRestControllerTest {

    private static final String MOCK_PRINCIPAL_NAME_FOR_FIRST_USER = "MOCK_PRINCIPAL_NAME_FOR_FIRST_USER";
    private static final String MOCK_PRINCIPAL_NAME_FOR_SECOND_USER = "MOCK_PRINCIPAL_NAME_FOR_SECOND_USER";
    private static final String FIRST_USER_USERNAME = "FIRST_USER_USERNAME";
    private static final String FIRST_USER_PASSWORD = "FIRST_USER_PASSWORD";
    private static final String FIRST_USER_EMAIL = "FIRST_USER_EMAIL";
    private static final String SECOND_USER_USERNAME = "SECOND_USER_USERNAME";
    private static final String SECOND_USER_PASSWORD = "SECOND_USER_PASSWORD";
    private static final String SECOND_USER_EMAIL = "SECOND_USER_EMAIL";
    private static final String FIRST_RIG_NAME = "FIRST_RIG_NAME";
    private static final String FIRST_RIG_PASSWORD = "FIRST_RIG_PASSWORD";
    private static final String SECOND_RIG_NAME = "SECOND_RIG_NAME";
    private static final String SECOND_RIG_PASSWORD = "SECOND_RIG_PASSWORD";
    private static final String NAME_FIELD_ID = "id";

    private static final int HTTP_RESPONSE_CONFLICT_CODE = 409;
    private static final int HTTP_RESPONSE_SUCCESS_CODE = 200;
    private static final int HTTP_RESPONSE_CREATED_CODE = 201;
    private static final int HTTP_RESPONSE_NO_CONTENT = 204;
    private static final int HTTP_RESPONSE_LOCKED_CODE = 423;

    private static final String PATH_RIGS_USER = "/api/rigs";
    private static final Long VALUE_FIELD_ID_FOR_FIRST_USER = 1L;
    private static final Long VALUE_FIELD_ID_FOR_SECOND_USER = 2L;


    @Autowired
    WebApplicationContext webApplicationContext;

    @Mock
    UserService mockUserService;

    @Mock
    RigService mockRigService;

    @Mock
    Principal mockPrincipalForFirstUser;

    @Mock
    Principal mockPrincipalForSecondUser;

    @Autowired
    @InjectMocks
    RigRestController rigRestController;

    private MockMvc mockMvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        User firstUser = new User(FIRST_USER_USERNAME, FIRST_USER_PASSWORD, FIRST_USER_EMAIL);
        User secondUser = new User(SECOND_USER_USERNAME, SECOND_USER_PASSWORD, SECOND_USER_EMAIL);
        Rig firstRig = new Rig(FIRST_RIG_NAME, FIRST_RIG_PASSWORD, firstUser);
        Rig secondRig = new Rig(SECOND_RIG_NAME, SECOND_RIG_PASSWORD, firstUser);
        try {
            Field firstField = firstRig.getClass().getDeclaredField(NAME_FIELD_ID);
            firstField.setAccessible(true);
            firstField.set(firstRig, VALUE_FIELD_ID_FOR_FIRST_USER);
            Field secondField = firstRig.getClass().getDeclaredField(NAME_FIELD_ID);
            secondField.setAccessible(true);
            secondField.set(secondRig, VALUE_FIELD_ID_FOR_SECOND_USER);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        List<Rig> rigArrayList = new ArrayList<>(Arrays.asList(firstRig, secondRig));
        firstUser.setUserRigList(rigArrayList);
        Mockito
                .when(mockPrincipalForFirstUser.getName())
                .thenReturn(MOCK_PRINCIPAL_NAME_FOR_FIRST_USER);
        Mockito
                .when(mockPrincipalForSecondUser.getName())
                .thenReturn(MOCK_PRINCIPAL_NAME_FOR_SECOND_USER);
        Mockito
                .when(mockUserService.getUserRigsByUsername(MOCK_PRINCIPAL_NAME_FOR_FIRST_USER))
                .thenReturn(firstUser.getUserRigList());
        Mockito
                .when(mockUserService.getUserRigsByUsername(MOCK_PRINCIPAL_NAME_FOR_SECOND_USER))
                .thenReturn(secondUser.getUserRigList());
        Mockito.when(mockUserService.findUserByUsername(MOCK_PRINCIPAL_NAME_FOR_FIRST_USER))
                .thenReturn(firstUser);
    }

    @Test
    public void getRigsOfUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_RIGS_USER)
                .principal(mockPrincipalForFirstUser))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(FIRST_RIG_NAME)))
                .andExpect(jsonPath("$[1].name", is(SECOND_RIG_NAME)));
        verify(mockPrincipalForFirstUser, times(2)).getName();
        verifyNoMoreInteractions(mockPrincipalForFirstUser);
        verify(mockUserService, times(1)).getUserRigsByUsername(Mockito.any(String.class));
        verifyNoMoreInteractions(mockUserService);
    }

    @Test
    public void getRigsOfUserEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_RIGS_USER)
                .principal(mockPrincipalForSecondUser))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_NO_CONTENT));
        verify(mockPrincipalForSecondUser, times(2)).getName();
        verify(mockUserService, times(1)).getUserRigsByUsername(Mockito.any(String.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
    }

    private static final String PATH_CREATE_RIG = "/api/rigs";
    private static final String REQUEST_PARAM_RIG_DTO_NAME = "name";
    private static final String REQUEST_PARAM_RIG_DTO_NAME_VALUE = "test1";
    private static final String REQUEST_PARAM_RIG_DTO_PASSWORD = "password";
    private static final String REQUEST_PARAM_RIG_DTO_PASSWORD_VALUE = "test1";

    @Test
    public void createRig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_RIG)
                .principal(mockPrincipalForFirstUser)
                .param(REQUEST_PARAM_RIG_DTO_NAME, REQUEST_PARAM_RIG_DTO_NAME_VALUE)
                .param(REQUEST_PARAM_RIG_DTO_PASSWORD, REQUEST_PARAM_RIG_DTO_PASSWORD_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CREATED_CODE));
        verify(mockPrincipalForFirstUser, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verify(mockRigService, times(1)).addRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockRigService);
    }

    private static final String REQUEST_PARAM_RIG_DTO_NAME_VALUE_LESS_THREE_CHARACTER = "a";

    @Test
    public void createRigNameLessThreeCharacter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_RIG)
                .principal(mockPrincipalForFirstUser)
                .param(REQUEST_PARAM_RIG_DTO_NAME, REQUEST_PARAM_RIG_DTO_NAME_VALUE_LESS_THREE_CHARACTER)
                .param(REQUEST_PARAM_RIG_DTO_PASSWORD, REQUEST_PARAM_RIG_DTO_PASSWORD_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipalForFirstUser, times(1)).getName();
        verify(mockUserService, times(0)).findUserByUsername(Mockito.any(String.class));
        verify(mockRigService, times(0)).addRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockRigService);
    }

    private static final String REQUEST_PARAM_RIG_DTO_NAME_PASSWORD_LESS_THREE_CHARACTER = "aa";

    @Test
    public void createRigPasswordLessThreeCharacter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_RIG)
                .principal(mockPrincipalForFirstUser)
                .param(REQUEST_PARAM_RIG_DTO_NAME, REQUEST_PARAM_RIG_DTO_NAME_VALUE)
                .param(REQUEST_PARAM_RIG_DTO_PASSWORD, REQUEST_PARAM_RIG_DTO_NAME_PASSWORD_LESS_THREE_CHARACTER))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipalForFirstUser, times(1)).getName();
        verify(mockUserService, times(0)).findUserByUsername(Mockito.any(String.class));
        verify(mockRigService, times(0)).addRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockRigService);
    }

    @Test
    public void createRigEmptyDTO() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_RIG)
                .principal(mockPrincipalForFirstUser)
                .param(REQUEST_PARAM_RIG_DTO_NAME, REQUEST_PARAM_RIG_DTO_NAME_VALUE)
                .param(REQUEST_PARAM_RIG_DTO_PASSWORD, REQUEST_PARAM_RIG_DTO_NAME_PASSWORD_LESS_THREE_CHARACTER))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipalForFirstUser, times(1)).getName();
        verify(mockUserService, times(0)).findUserByUsername(Mockito.any(String.class));
        verify(mockRigService, times(0)).addRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockRigService);
    }

    @Test
    public void createRigExistsNameOfRig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_CREATE_RIG)
                .principal(mockPrincipalForFirstUser)
                .param(REQUEST_PARAM_RIG_DTO_NAME, FIRST_RIG_NAME)
                .param(REQUEST_PARAM_RIG_DTO_PASSWORD, REQUEST_PARAM_RIG_DTO_PASSWORD_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipalForFirstUser, times(2)).getName();
        verify(mockUserService, times(1)).findUserByUsername(Mockito.any(String.class));
        verify(mockRigService, times(0)).addRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockRigService);
    }

    private static final String PATH_GET_RIG_ID = "/api/rigs/rig/" + VALUE_FIELD_ID_FOR_SECOND_USER.intValue();

    @Test
    public void getRig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET_RIG_ID)
                .principal(mockPrincipalForFirstUser))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE))
                .andExpect(jsonPath("$.id", is(VALUE_FIELD_ID_FOR_SECOND_USER.intValue())))
                .andExpect(jsonPath("$.name", is(SECOND_RIG_NAME)));
        verify(mockPrincipalForFirstUser, times(2)).getName();
        verify(mockUserService, times(1)).getUserRigsByUsername(Mockito.any(String.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
    }

    @Test
    public void removeRig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_GET_RIG_ID)
                .principal(mockPrincipalForFirstUser))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE));
        verify(mockPrincipalForFirstUser, times(2)).getName();
        verify(mockUserService, times(1)).getUserRigsByUsername(Mockito.any(String.class));
        verify(mockRigService, times(1)).removeRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
    }

    private static final int ANOTHER_RIG_ID = 333;
    private static final String PATH_ANOTHER_GET_RIG_ID = "/api/rigs/rig/" + ANOTHER_RIG_ID;

    @Test
    public void removeAnotherRig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_ANOTHER_GET_RIG_ID)
                .principal(mockPrincipalForFirstUser))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_LOCKED_CODE));
        verify(mockPrincipalForFirstUser, times(2)).getName();
        verify(mockUserService, times(1)).getUserRigsByUsername(Mockito.any(String.class));
        verify(mockRigService, times(0)).removeRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockRigService);

    }

    private static final String CHANGE_RIG_NAME = "CHANGE_RIG_NAME";
    private static final String CHANGE_RIG_PASSWORD = "CHANGE_RIG_PASSWORD";

    @Test
    public void changeRigPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_GET_RIG_ID)
                .principal(mockPrincipalForFirstUser)
                .param(REQUEST_PARAM_RIG_DTO_NAME, CHANGE_RIG_NAME)
                .param(REQUEST_PARAM_RIG_DTO_PASSWORD, CHANGE_RIG_PASSWORD))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_SUCCESS_CODE));
        verify(mockPrincipalForFirstUser, times(2)).getName();
        verify(mockUserService, times(1)).getUserRigsByUsername(Mockito.any(String.class));
        verify(mockRigService, times(1)).addRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockRigService);
    }

    private static final String CHANGE_RIG_NAME_LESS_THREE = "a";
    private static final String CHANGE_RIG_PASSWORD_LESS_THREE = "a";

    @Test
    public void changeRigPasswordLessThreeCharacters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_GET_RIG_ID)
                .principal(mockPrincipalForFirstUser)
                .param(REQUEST_PARAM_RIG_DTO_NAME, CHANGE_RIG_NAME_LESS_THREE)
                .param(REQUEST_PARAM_RIG_DTO_PASSWORD, CHANGE_RIG_PASSWORD_LESS_THREE))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipalForFirstUser, times(1)).getName();
        verify(mockUserService, times(0)).getUserRigsByUsername(Mockito.any(String.class));
        verify(mockRigService, times(0)).addRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockRigService);
    }

    @Test
    public void changeRigNameExistsName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_GET_RIG_ID)
                .principal(mockPrincipalForFirstUser)
                .param(REQUEST_PARAM_RIG_DTO_NAME, SECOND_RIG_NAME)
                .param(REQUEST_PARAM_RIG_DTO_PASSWORD, FIRST_RIG_PASSWORD))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_CONFLICT_CODE));
        verify(mockPrincipalForFirstUser, times(2)).getName();
        verify(mockUserService, times(1)).getUserRigsByUsername(Mockito.any(String.class));
        verify(mockRigService, times(0)).addRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockRigService);
    }

    @Test
    public void changeOtherRig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(PATH_ANOTHER_GET_RIG_ID)
                .principal(mockPrincipalForFirstUser)
                .param(REQUEST_PARAM_RIG_DTO_NAME, SECOND_RIG_NAME)
                .param(REQUEST_PARAM_RIG_DTO_PASSWORD, FIRST_RIG_PASSWORD))
                .andExpect(MockMvcResultMatchers.status().is(HTTP_RESPONSE_LOCKED_CODE));
        verify(mockPrincipalForFirstUser, times(2)).getName();
        verify(mockUserService, times(1)).getUserRigsByUsername(Mockito.any(String.class));
        verify(mockRigService, times(0)).addRig(Mockito.any(Rig.class));
        verifyNoMoreInteractions(mockPrincipalForSecondUser);
        verifyNoMoreInteractions(mockUserService);
        verifyNoMoreInteractions(mockRigService);
    }

}