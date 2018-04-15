package com.db.bex.interns.dbTrainingEnroll;

import com.db.bex.interns.controller.DummyController;
import com.db.bex.interns.dto.DummyDto;
import com.db.bex.interns.service.DummyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class DummyTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DummyService dummyService;

    @InjectMocks
    @Spy
    DummyController dummyController;

    @Test
    public void test_retrieveAllDummy() throws Exception {
        List<DummyDto> dummyDtoList = new ArrayList<>();
        DummyDto dummy1 = new DummyDto();
        dummy1.setId(1l);
        dummy1.setName("dummy1");
        DummyDto dummy2 = new DummyDto();
        dummy2.setId(2l);
        dummy2.setName("dummy2");
        when(dummyService.search())
                .thenReturn(dummyDtoList);

        mockMvc.perform(
                get("/dummy/")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user("user").password("password"))//dummy user & password for test context
            )
            .andExpect(status().isOk())
            .andExpect(content().string("[{\"id\":1,\"name\":\"dummy1\"},{\"id\":2,\"name\":\"dummy2\"}]"));

        verify(dummyService, times(1)).search();
    }

}
