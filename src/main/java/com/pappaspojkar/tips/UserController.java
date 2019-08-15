/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pappaspojkar.tips;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import com.pappaspojkar.tips.wrapper.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author mehtab
 */
@RestController
public class UserController {
    
    @Autowired
    UserRepo userRepo;
    
    @GetMapping("/user")
    public Iterable<User> getUsers(){
        return userRepo.findAll();
    }
    
    @GetMapping("/user/{id}")
    public Optional<User> findByid(@PathVariable Integer id){
        return userRepo.findById(id);
    }
    
    @GetMapping("/user/login")
    public String login(String email, String password){
        User user = userRepo.findByEmail(email);
        if(user == null || !password.equals(user.getPassword())){
            return "";
        }
        String token = Utility.generateToken();
        Long tokenLastUpdate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        
        user.setTokenLastDate(tokenLastUpdate);
        user.setToken(token);
        userRepo.save(user);
                
        
        
        return token;
            
    }
    
    @GetMapping("/login")
    public String isLoggedIn(String email, String token){
        if(token.isEmpty()){
            return "Not logged in";
        }
        User user = userRepo.findByEmail(email);
        if(user == null){
            return "Not logged in";
        }
        if(!token.equals(user.getToken())){
            return "Not logged in";
        }
        
       Long checkTimeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        if( user.getTokenLastDate() +900 < checkTimeStamp){
            return "Not logged in";
        }
        
        user.setTokenLastDate(checkTimeStamp);
        userRepo.save(user);
        return "logged in";
    }

    @PostMapping("/addUser")
    public void addUser2(@RequestBody Query query){

        System.out.println(query.getData());
        userRepo.save(query.getData().getUser());
        Query responseQuery = new Query();
        // responseQuery.setHead(

               // new ResponseHead(query.getHead()));
    }
    @PostMapping("/addUser3")
    public void addUser3(@RequestBody Query query){

        System.out.println(query.getData());
        userRepo.save(query.getData().getUser());
        Query responseQuery = new Query();
       // responseQuery.setHead(

            //    new ResponseHead(query.getHead()));
    }
    
    @PostMapping("/user/add")
    public User addUser(@RequestBody User user){
        if(userRepo.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("Email already exist");
        }
        if(userRepo.findByNickname(user.getNickname()) != null){
            throw new RuntimeException("Nickname already taken");
        }
        
       return userRepo.save(new User(user.getName(), user.getEmail(), user.getPassword(),user.getPhone(),user.getNickname()));
    }
    
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user){
        userRepo.findById(id);
        return userRepo.save(new User(user.getName(), user.getEmail(), user.getPassword(),user.getPhone(),user.getNickname()));
    }
    
    @DeleteMapping("user/{id}")
    public boolean delete(@PathVariable Integer id){
        userRepo.deleteById(id);
        return true;
    }
    
    
}
