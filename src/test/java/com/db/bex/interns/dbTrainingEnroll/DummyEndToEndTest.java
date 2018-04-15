package com.db.bex.interns.dbTrainingEnroll;

import com.db.bex.interns.controller.DummyController;
import com.db.bex.interns.dao.DummyRepository;
import com.db.bex.interns.entity.Dummy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DummyEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DummyRepository dummyRepository;

    @Test
    @Rollback
    public void test_retrieveAllDummy() throws Exception {
        //Given
        insertTestData();

        //When
        mockMvc.perform(
                get("/dummy/")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user("user").password("password"))//dummy user & password for test context
            )
        //Them
            .andExpect(handler().handlerType(DummyController.class))
            .andExpect(handler().methodName("search"))
            .andExpect(status().isOk())
            .andExpect(content().string("[{\"id\":1,\"name\":\"dummy1\"},{\"id\":2,\"name\":\"dummy2\"}]"));
    }

    private void insertTestData() {
        Dummy dummy1 = new Dummy();
        dummy1.setName("dummy1");
        dummy1.setId(1L);
        dummyRepository.save(dummy1);

        Dummy dummy2 = new Dummy();
        dummy2.setName("dummy2");
        dummy2.setId(2L);
        dummyRepository.save(dummy2);
    }

}
