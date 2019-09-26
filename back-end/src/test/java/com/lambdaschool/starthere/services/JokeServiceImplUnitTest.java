package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.StartHereApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartHereApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JokeServiceImplUnitTest {
    @Autowired
    private JokeService jokeService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void C_findAll()
    {
        assertEquals(6, jokeService.findAll().size());
    }

    @Test
    public void C_findById()
    {
        assertEquals("joker2", jokeService.findById(6).getSetup());
    }

    @Test
    public void C_findPublicJokes()
    {
        assertEquals(4, jokeService.findPublicJokes().size());
    }

    @Test
    public void C_findJokesByOwner()
    {
        assertEquals(2, jokeService.findJokesByOwner(4).size());
    }

    @Test
    public void C_searchJokes()
    {
        assertEquals(1, jokeService.searchJokes("joker2").size());
    }

    @Test
    public void C_deleteJoke()
    {
        jokeService.delete(6, true);
        assertEquals(1, jokeService.findJokesByOwner(4).size());
    }


}
