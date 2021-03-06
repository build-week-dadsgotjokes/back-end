package com.lambdaschool.starthere;

import com.lambdaschool.starthere.models.*;
import com.lambdaschool.starthere.services.JokeService;
import com.lambdaschool.starthere.services.RoleService;
import com.lambdaschool.starthere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    JokeService jokeService;


    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        roleService.save(r1);
        roleService.save(r2);
        roleService.save(r3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();

        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));
        admins.add(new UserRoles(new User(), r3));



        User u1 = new User("admin", "password", admins, returnEmptyJokeLikes());
        Joke j2 = new Joke(u1, "joker2", "hahahaha", false, returnEmptyJokeLikes());



        u1.getJokes()
          .add(new Joke(u1, "JokeHeadline1", "Because no molk", true, new ArrayList<>()));
        u1.getJokes()
          .add(j2);

        u1 = userService.save(u1);



        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();

        datas.add(new UserRoles(new User(), r3));
        datas.add(new UserRoles(new User(), r2));

        ArrayList<UserJokeLikes> jokeLikes = new ArrayList<>();

        jokeLikes.add(new UserJokeLikes(new User(), j2));

        User u2 = new User("cinnamon", "1234567", datas, jokeLikes);
        u2.getJokes()
          .add(new Joke(u2, "wow anticipation", "lmaoo", false, returnEmptyJokeLikes()));
        u2.getJokes()
          .add(new Joke(u2, "epic setup", "terrible execution", false, returnEmptyJokeLikes()));
        u2.getJokes()
          .add(new Joke(u2, "i like private", "why have personal jokes weirdo?", true, returnEmptyJokeLikes()));



        userService.save(u2);



        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u3 = new User("barnbarn", "ILuvM4th!", users, returnEmptyJokeLikes());
        Joke j1 = new Joke(u3, "epicness 3.0", "killer joke", false, returnEmptyJokeLikes());

        u3.getJokes()
          .add(j1);
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("Bob", "password", users, returnEmptyJokeLikes());
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("Jane", "password", users, returnEmptyJokeLikes());
        userService.save(u5);



    }

    public List<Joke> returnEmptyJoke() {
        return new ArrayList<>();
    }
    public List<User> returnEmptyUser() {
        return new ArrayList<>();
    }
    public List<UserJokeLikes> returnEmptyJokeLikes() { return new ArrayList<>(); }
}