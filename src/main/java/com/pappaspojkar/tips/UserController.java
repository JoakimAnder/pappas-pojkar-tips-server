/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pappaspojkar.tips;

import java.time.LocalDateTime;
import java.util.Optional;

import com.pappaspojkar.tips.Wrappers.Query;
import com.pappaspojkar.tips.Wrappers.LoginRequest;
import com.pappaspojkar.tips.Wrappers.RequestHead;
import com.pappaspojkar.tips.Wrappers.ResponseHead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mehtab
 * @author joakim
 */
@RestController
public class UserController {
    
    @Autowired
    UserRepo userRepo;
    
    @PostMapping("/getUsers")
    public Query<Iterable<User>> getUsers(@RequestBody Query query){
        return new Query<>(
            new ResponseHead()
                .setStatusCode(200)
                .setToken(query.getHead().getToken()), 
            userRepo.findAll());
    }
    
    @PostMapping("/getUserById")
    public Query<Optional<User>> findByid(@RequestBody Query<Integer> query){
        Optional<User> user = userRepo.findById(query.getData());

        String message = "";
        Integer statusCode = 200;

        if(!user.isPresent()) {
            message = "No User Found";
            statusCode = 412;
        }

        return new Query<>(
            new ResponseHead(
                    statusCode,
                    message,
                    query.getHead().getToken()
            ),
            user);
    }
    
    @PostMapping("/login")
    public Query login(@RequestBody Query<LoginRequest> query){
        User user = userRepo.findByEmail(query.getData().getEmail());
        String token = query.getHead().getToken();
        
        if(user == null) 
            return new Query(new ResponseHead(412, "Invalid Login", token));;

        Long now = LocalDateTime.now().toEpochSecond(Utility.SERVER_OFFSET);

        if(user.getLoginDeniedUntil() > now) {
            return new Query(new ResponseHead(408, "Login is suspended", token));
        }
        if(!Utility.MD5Encode(query.getData().getPassword()).equals(user.getPassword())) {
            int attempts = user.getAttemptedLogins();

            if(++attempts > 2) {
                user.setAttemptedLogins(0);
                user.setLoginDeniedUntil(now+Utility.SECONDS_OF_LOGIN_DENIAL);
            } else {
                user.setAttemptedLogins(attempts);
            }
            userRepo.save(user);
            return new Query(new ResponseHead(412, "Invalid Login", token));
        }

        token = Utility.generateToken();
        Long lastValidDate = LocalDateTime.now()
            .plusSeconds(Utility.SECONDS_UNTIL_AUTOMATIC_LOGOUT)
            .toEpochSecond(Utility.SERVER_OFFSET);
        
        user.setTokenLastValidDate(lastValidDate);
        user.setToken(token);
        user.setAttemptedLogins(0);
        userRepo.save(user);
        
        return new Query(
            new ResponseHead()
                .setToken(token)
                .setStatusCode(200)
        );
    }
    
    @PostMapping("/IsLoggedIn")
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
        
       Long checkTimeStamp = LocalDateTime.now().toEpochSecond(Utility.SERVER_OFFSET);
        if( user.getTokenLastValidDate() < checkTimeStamp){
            return "Not logged in";
        }
        
        user.setTokenLastValidDate(checkTimeStamp);
        userRepo.save(user);
        return "logged in";
    }
    
    @PostMapping("/addUser")
    public Query<User> addUser(@RequestBody Query<User> query){
        User user = query.getData();

        if(userRepo.existsByEmail(user.getEmail())) {
            return new Query<>(
                    new ResponseHead(
                            444,
                            "Email is taken",
                            query.getHead().getToken()
                    ),
                    user
            );
        }
        if(userRepo.existsByNickname(user.getNickname())) {
            return new Query<> (
                    new ResponseHead(
                            443,
                            "Nickname is taken",
                            query.getHead().getToken()
                    ),
                    user
            );
        }

        user = userRepo.save(
            new User(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getNickname()));

       return new Query<>(
            new ResponseHead(
                200,
                "",
                query.getHead().getToken()),
            user
        );
    }
    
    @PostMapping("updateUser")
    public Query<User> updateUser(@RequestBody Query<User> query){
        int id = ((RequestHead) query.getHead()).getUserId();
        Optional<User> oUser = userRepo.findById(id);
        String token = query.getHead().getToken();

        if (!oUser.isPresent()) {
            return new Query<>(
                new ResponseHead(
                    412,
                    "User not found",
                    token
                ),
                null
            );
        }
        User user = oUser.get();
        User newUser = query.getData();

        if (isLoggedIn(user.getEmail(), token).equals("Not logged in")) {
            return new Query<>(
                new ResponseHead(
                    401,
                    "Unauthorized",
                    token
                ),
                null
            );
        }

        if (userRepo.findByEmail(newUser.getEmail()) != null) {
            return new Query<>(
                new ResponseHead(
                    444,
                    "Email is taken",
                    token
                ),
                user
            );
        }
        if (userRepo.findByNickname(newUser.getNickname()) != null) {
            return new Query<>(
                new ResponseHead(
                    443,
                    "Nickname is taken",
                    token
                ),
                user
            );
        }
        if(newUser.getName() != null)
            user.setName(newUser.getName());
        if(newUser.getNickname() != null)
            user.setNickname(newUser.getNickname());
        if(newUser.getPhone() != null)
            user.setPhone(newUser.getPhone());
        if(newUser.getEmail() != null)
            user.setEmail(newUser.getEmail());


        userRepo.save(user);

        return new Query<>(
            new ResponseHead(
                200,
                "",
                token
            ),
            user
        );
    }
    
    @PostMapping("deleteUser")
    public Query<Boolean> delete(@RequestBody Query<Integer> query){
        int id = ((RequestHead) query.getHead()).getUserId();

        if(id != query.getData())
            return new Query<>(
                    new ResponseHead(
                            401,
                            "Unauthorized to delete another's account",
                            query.getHead().getToken()
                    ),
                    false
            );

        String token = query.getHead().getToken();
        Optional<User> oUser = userRepo.findById(id);


        if (!oUser.isPresent()) {
            return new Query<>(
                new ResponseHead(
                    412,
                    "User not found",
                    token
                ),
                null
            );
        }
        User user = oUser.get();

        if (isLoggedIn(user.getEmail(), token).equals("Not logged in")) {
            return new Query<>(
                new ResponseHead(
                    401,
                    "Unauthorized",
                    token
                ),
                null
            );
        }
        

        userRepo.deleteById(id);
        return new Query<>(
            new ResponseHead(
                200,
                "",
                token
            ),
            true
        );
    }
    
    
}
