package com.lambdaschool.starthere.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.services.JokeService;
import com.lambdaschool.starthere.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = JokeController.class, secure = false)
public class JokeControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JokeService jokeService;

    @MockBean
    private UserService userService;

    private List<Joke> jokeList = new ArrayList<>();
    private List<Joke> myJokes = new ArrayList<>();
    private List<Joke> searchJokes = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        Joke joke1 = new Joke(null, "joker2", "hahaha", false, returnEmptyJokeLikes());
        Joke joke2 = new Joke(null, "public", "not private", false, returnEmptyJokeLikes());

        jokeList.add(joke1);
        jokeList.add(joke2);

        myJokes.add(joke1);

        searchJokes.add(joke1);
    }

    @Test
    public void C_findPublicJokes() throws Exception {
        String apiUrl = "/jokes/public";

        Mockito.when(jokeService.findPublicJokes()).thenReturn(jokeList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(jokeList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void C_getMyJokes() throws Exception {
        String apiUrl = "/jokes/auth/mine";

        Mockito.when(jokeService.findJokesByOwner(4)).thenReturn(myJokes);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(myJokes);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void C_searchJokes() throws Exception {
        String apiUrl = "/jokes/search/joker2";

        Mockito.when(jokeService.searchJokes("joker2")).thenReturn(searchJokes);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(searchJokes);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    public List<Joke> returnEmptyJoke() {
        return new ArrayList<>();
    }
    public List<User> returnEmptyUser() {
        return new ArrayList<>();
    }
    public List<UserJokeLikes> returnEmptyJokeLikes() { return new ArrayList<>(); }
}
