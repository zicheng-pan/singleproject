package org.example.forUser;

import org.example.App;
import org.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestUserService {

    @Autowired
    UserService userService;

    /*
        Integration test for Nas mysql connection
     */
    @Test
    public void testqueryUsernameIsExist() {
//        boolean imooc = userService.queryUsernameIsExist("imooc");
//        System.out.println(imooc);
//        assert true;
    }
}
