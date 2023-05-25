package com.boots.controller;

import com.boots.entity.User;
import com.boots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TestController {
    @Autowired
    UserRepository userRepository;
    List<TestClient> clients  =new ArrayList<>();
    TestClient testClient1 = new TestClient(1,"egor","dsada");

    @PostMapping("/test")
    public ResponseEntity<?> create(@RequestBody TestClient testClient){

        testClient1 = testClient;
        System.out.println(testClient1.getNumber_phone());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/test")
    public ResponseEntity<Date> write(){
        User user = userRepository.findByUsername("user");
        return new ResponseEntity<>(user.getActiveDateTime(),HttpStatus.OK);
    }
}
