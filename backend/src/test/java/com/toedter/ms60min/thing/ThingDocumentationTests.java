package com.toedter.ms60min.thing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.RequestDispatcher;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ThingDocumentationTests {
    private RestDocumentationResultHandler documentationHandler;

    private MockMvc mockMvc;

    private ThingRepository thingRepository;

    private ObjectMapper objectMapper;

    @Autowired
    ThingDocumentationTests(ThingRepository thingRepository, ObjectMapper objectMapper) {
        this.thingRepository = thingRepository;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.documentationHandler = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation).uris()
                        .withScheme("https")
                        .withHost("ms60min.com")
                        .withPort(443))
                .alwaysDo(this.documentationHandler)
                .build();
    }

    @Test
    @DisplayName("should get content-type header")
    public void headersExample() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api")
                .header("Accept", MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        responseHeaders(
                                headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`"))));
    }

    @Test
    @DisplayName("should return error messages")
    public void errorExample() throws Exception {
        this.mockMvc
                .perform(RestDocumentationRequestBuilders.get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                        .requestAttr(RequestDispatcher.ERROR_REQUEST_URI,
                                "/things/123")
                        .requestAttr(RequestDispatcher.ERROR_MESSAGE,
                                "The tag 'http://localhost:8080/things/123' does not exist"))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue())))
                .andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue())))
                .andDo(this.documentationHandler.document(
                        responseFields(
                                fieldWithPath("error").description("The HTTP error that occurred, e.g. `Bad Request`"),
                                fieldWithPath("message").description("A description of the cause of the error"),
                                fieldWithPath("path").description("The path to which the request was made"),
                                fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
                                fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))));
    }

    @Test
    @DisplayName("should return all links in root API")
    public void indexExample() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api")
                .header("Accept", MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        links(
                                linkWithRel("ms60min:things").description("The <<resources-things,Things resource>>"),
                                linkWithRel("curies").description("The Curies for documentation"),
                                linkWithRel("profile").description("The profiles of the REST resources")
                        ),
                        responseFields(
                                subsectionWithPath("_links").description("<<resources-index-links,Links>> to other resources"))));
    }

    @Test
    @DisplayName("should return all things")
    public void listThings() throws Exception {
        this.thingRepository.deleteAll();
        this.createThing("Hammer", "Black");
        this.createThing("Bike", "Red");

        this.mockMvc.perform(get("/api/things").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        links(
                                linkWithRel("self").description("The <<resources-things,Things resource>>"),
                                linkWithRel("profile").description("The profile describes the data structure of this resource"),
                                linkWithRel("curies").description("Curies are used for online documentation")
                        ),
                        responseFields(
                                subsectionWithPath("_embedded.ms60min:things").description("An array of <<resources-thing, Thing resources>>"),
                                subsectionWithPath("_links").description("<<resources-things-list_links,Links>> to other resources"),
                                subsectionWithPath("page").description("The pagination information")
                        )));
    }

    @Test
    @DisplayName("should return one thing")
    public void getThing() throws Exception {
        this.thingRepository.deleteAll();
        Thing thing = this.createThing("Hammer", "Black");

        this.mockMvc.perform(get("/api/things/" + thing.getId()).accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andDo(this.documentationHandler.document(
                        links(
                                linkWithRel("self").description("The <<resources-thing,Thing resource>>, not templated"),
                                linkWithRel("ms60min:thing").description("The <<resources-thing,Thing resource>>, may be templated"),
                                linkWithRel("curies").description("Curies are used for online documentation")
                        ),
                        responseFields(
                                fieldWithPath("name").description("The thins's name"),
                                fieldWithPath("color").description("The thing's color"),
                                subsectionWithPath("_links").description("<<resources-thing-get_links,Links>> to other resources")
                        )));
    }

    @Test
    @DisplayName("should create a thing")
    public void createThing() throws Exception {
        Map<String, String> thing = new HashMap<String, String>();
        thing.put("name", "Violin");
        thing.put("color", "Brown");

        this.mockMvc.perform(
                post("/api/things").contentType(MediaTypes.HAL_JSON).content(
                        this.objectMapper.writeValueAsString(thing))).andExpect(
                status().isCreated())
                .andDo(this.documentationHandler.document(
                        requestFields(
                                fieldWithPath("name").description("The thins's name"),
                                fieldWithPath("color").description("The thing's color")
                        )
                ));
    }

    private Thing createThing(String name, String color) {
        Thing thing = new Thing(name, color);
        this.thingRepository.save(thing);
        return thing;
    }
}