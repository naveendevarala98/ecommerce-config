package com.ecommerce.ProductService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

    private MockMvc mockMvc;

//    @Test
//    public void testWelcome() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/").param("name", "Geeks"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attribute("welcome",
//                        "Welcome , Geeks to the world of programming!!!"))
//                .andExpect(MockMvcResultMatchers.view().name("welcome-page"))
//                .andDo(MockMvcResultHandlers.print());
//    }

}
