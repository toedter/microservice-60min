package com.toedter.ms60min.thing;

import com.toedter.test.category.UnitTest;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkDiscoverer;
import org.springframework.hateoas.LinkDiscoverers;
import org.springframework.hateoas.MediaTypes;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Category(UnitTest.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestApiIndexTest {

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected LinkDiscoverers links;

    protected MockMvc mvc;

    @Before
    public void before() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).
                addFilter(new ShallowEtagHeaderFilter()).
                build();
    }

    @Test
    public void shouldGetThingsLink() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/api")).
                andDo(MockMvcResultHandlers.print()).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/hal+json;charset=UTF-8")).
                andExpect(jsonPath("_links.things.href", CoreMatchers.notNullValue())).
                andReturn().
                getResponse();

        LinkDiscoverer discoverer = links.getLinkDiscovererFor(response.getContentType());
        Link link = discoverer.findLinkWithRel("things", response.getContentAsString());
        String href = link.getHref();

        mvc.perform(get(href)).
                andDo(MockMvcResultHandlers.print()).
                andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON)).
                andExpect(jsonPath("_embedded.things", CoreMatchers.notNullValue()));
    }
}
