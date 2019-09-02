/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pappaspojkar.tips;

import java.time.LocalDateTime;

import java.util.Optional;

import com.pappaspojkar.tips.Wrappers.*;


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
    public Response<Iterable<User>> getUsers(){
        return Response.createSuccessfulResponse(userRepo.findAll());
    }

    @PostMapping("/getUserById")
    public Response<Optional<User>> findByid(@RequestBody Request<Integer> query){
        Optional<User> user = userRepo.findById(query.getData());

        return (user.isPresent())
                ? Response.createSuccessfulResponse(user)
                : Response.createResponse(
                412,
                "User not found",
                false,
                            user);

    }

    @PostMapping(value = "/login")
    public Response<User> login(@RequestBody Request<LoginRequest> query){

        User user = userRepo.findByEmail(query.getData().getEmail());

        if(user == null) //User with given email not found
            return Response.createResponse(413, "Invalid login", false, null);

        Long now = LocalDateTime.now().toEpochSecond(Utility.SERVER_OFFSET);

        if(user.getLoginDeniedUntil() > now) //User has been suspended
            return Response.createResponse(408, "Login is suspended", false, null);

        if(!Utility.MD5Encode(query.getData().getPassword()).equals(user.getPassword())) { //Password doesn't match
            int attempts = user.getAttemptedLogins();

            if(++attempts > 2) {
                //If User has over 3 failed attempts, suspend account
                user.setAttemptedLogins(0);
                user.setLoginDeniedUntil(now+Utility.SECONDS_OF_LOGIN_DENIAL);
            } else {
                user.setAttemptedLogins(attempts);
            }
            userRepo.save(user);
            return Response.createResponse(413, "Invalid Login", false, null);
        }

        String token = Utility.generateToken();

        user.setToken(token);
        user.setLastLogin(now);
        user.setTokenLastValidDate(now+Utility.SECONDS_UNTIL_AUTOMATIC_LOGOUT);
        user.setAttemptedLogins(0);
        user = userRepo.save(user);

        return Response.createSuccessfulResponse(user);

    }

    @PostMapping("/addUser")
    public Response<User> addUser(@RequestBody Request<User> query){
        User user = query.getData();

        String name = user.getName();
        String email = user.getEmail();
        String phone = user.getPhone();
        String nickname = user.getNickname();
        String password = user.getPassword();

        // Null/Empty checks
        if(name == null || name.isEmpty())
            return Response.createResponse(430, "Name is required", false, user);
        if(email == null || email.isEmpty())
            return Response.createResponse(434, "Email is required", false, user);
        if(phone == null || phone.isEmpty())
            return Response.createResponse(431, "Phone is required", false, user);
        if(password == null || password.isEmpty())
            return Response.createResponse(432, "Password is required", false, user);

        // Nickname is optional, if empty, generate one.
        if(nickname == null || nickname.isEmpty()) {
            nickname = name;
            int i = 1;
            while(userRepo.existsByNickname(nickname+i)){
                i++;
            }
            nickname += i;
        }


        if(userRepo.existsByEmail(email)) {
            return Response.createResponse(444, "Email is taken", false, user);
        }
        if(userRepo.existsByNickname(nickname)) {
            return Response.createResponse(443, "Nickname is taken", false, user);
        }

        user = userRepo.save(
                new User(
                        name,
                        email,
                        password,
                        phone,
                       nickname));

        return Response.createSuccessfulResponse(user);

    }

}
