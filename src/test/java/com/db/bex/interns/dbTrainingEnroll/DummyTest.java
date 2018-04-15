package com.db.bex.interns.dbTrainingEnroll;

import com.db.bex.interns.controller.DummyController;
import com.db.bex.interns.dto.DummyDto;
import com.db.bex.interns.service.DummyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DummyTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DummyService dummyService;

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    @Spy
    DummyController dummyController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        /*mockMvc  = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();*/
    }

    @Test
    public void test_retrieveAllDummy() throws Exception {
        //Then
        mockMvc.perform(
                get("/dummy/")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user("user").password("password"))//dummy user & password for test context
            )
            .andExpect(handler().handlerType(DummyController.class))
            .andExpect(handler().methodName("search"))
            .andExpect(status().isOk())
            .andExpect(content().string("[{\"id\":1,\"name\":\"dummy1\"},{\"id\":2,\"name\":\"dummy2\"}]"));
    }

}
