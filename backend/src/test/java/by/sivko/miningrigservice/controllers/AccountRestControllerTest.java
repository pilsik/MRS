package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.MiningRigServiceApplication;
import by.sivko.miningrigservice.dto.UserDto;
import by.sivko.miningrigservice.models.user.User;
import by.sivko.miningrigservice.services.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.security.Principal;
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
public class AccountRestControllerTest {

    private static final int SUCCESSES_CREATED_STATUS = 201;
    private static final int RESPONSE_CONFLICT_BAD_STATUS = 409;
    private static final String USER_NAME = "USER_NAME";
    private static final String WRONG_USER_NAME = "WRONG_USER_NAME";
    private static final String REQUEST_PARAM_USERNAME = "username";
    private static final String PATH_USER = "/api/account/register";
    private static final String REQUEST_PARAM_USERNAME_VALUE = "user";
    private static final String REQUEST_PARAM_EMAIL = "email";
    private static final String REQUEST_PARAM_EMAIL_VALUE = "user@user.us";
    private static final String REQUEST_PARAM_PASSWORD = "password";
    private static final String REQUEST_PARAM_PASSWORD_VALUE = "password";
    private static final String USER_EMAIL = "USER_EMAIL@USER.us";
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    WebApplicationContext webApplicationContext;

    @Mock
    UserService userService;

    @Mock
    Principal principal;

    @Autowired
    @InjectMocks
    AccountRestController accountRestController;

    MockMvc mockMvc;

    UserDto userDto = new UserDto();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        User userReturnMock = new User("userrr", "userrr", "userrr@user.us");
        Mockito
                .when(this.userService.saveUser(Mockito.any(User.class)))
                .thenReturn(userReturnMock);
        Mockito
                .when(this.userService.findUserByUsername(USER_NAME))
                .thenReturn(userReturnMock);
        Mockito
                .when(this.userService.findUserByEmail(USER_EMAIL))
                .thenReturn(userReturnMock);
        Mockito
                .when(this.userService.findUserByUsername(WRONG_USER_NAME))
                .thenReturn(null);
    }

    @Test
    public void createNewUser() throws Exception {
        userDto.setEmail(REQUEST_PARAM_EMAIL_VALUE);
        userDto.setPassword(REQUEST_PARAM_PASSWORD_VALUE);
        userDto.setUsername(REQUEST_PARAM_USERNAME_VALUE);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_USER)
                .contentType(APPLICATION_JSON_UTF8)
                .content(TestUtil.asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().is(SUCCESSES_CREATED_STATUS));
    }

    @Test
    public void createExistsUserByUsername() throws Exception {
        userDto.setEmail(REQUEST_PARAM_EMAIL_VALUE);
        userDto.setPassword(REQUEST_PARAM_PASSWORD_VALUE);
        userDto.setUsername(USER_NAME);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_USER)
                .contentType(APPLICATION_JSON_UTF8)
                .content(TestUtil.asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().is(RESPONSE_CONFLICT_BAD_STATUS));
    }

    @Test
    public void createExistsUserByEmail() throws Exception {
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword(REQUEST_PARAM_PASSWORD_VALUE);
        userDto.setUsername(REQUEST_PARAM_USERNAME_VALUE);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_USER)
                .contentType(APPLICATION_JSON_UTF8)
                .content(TestUtil.asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().is(RESPONSE_CONFLICT_BAD_STATUS));
    }


}
