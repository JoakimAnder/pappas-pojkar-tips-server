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

    private <E> boolean checkRequest(Request<E> query, boolean head, boolean data) {
        if(query == null)
            return false;
        if(head) {
            if(query.getHead() == null)
                return false;
        }

        if(data){
            if(query.getData() == null)
                return false;
        }
        return true;
    }


    @PostMapping("/getUsers")
    public Response<Iterable<User>> getUsers(){
        return Response.createSuccessfulResponse(userRepo.findAll());
    }

    @PostMapping("/getUserById")
    public Response<Optional<User>> findByid(@RequestBody Request<Integer> query){
        if(!checkRequest(query, false, true))
            return Response.createResponse(400, "Request not found, try {data:int}", false, null);

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
        if(!checkRequest(query, false, true))
            return Response.createResponse(400, "Request not found, try {data:User}", false, null);


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
        if(!checkRequest(query, false, true))
            return Response.createResponse(400, "Request not found, try {data:User}", false, null);

        User user = query.getData();

        String name = user.getName();
        String email = user.getEmail();
        String phone = user.getPhone();
        String nickname = user.getNickname();
        String password = user.getPassword();

        // Null/Empty checks
        if(name == null || name.isEmpty())
            return Response.createResponse(430, "Name is required", false, user);
        if(phone == null || phone.isEmpty())
            return Response.createResponse(431, "Phone is required", false, user);
        if(password == null || password.isEmpty())
            return Response.createResponse(432, "Password is required", false, user);
        if(email == null || email.isEmpty())
            return Response.createResponse(434, "Email is required", false, user);

        // Validate email/name/phone
        if(!Utility.isValidFullName(name))
            return Response.createResponse(450, "Name isn't valid, try "+Utility.VALID_FULL_NAME_REGEX.pattern(), false, user);
        if(!Utility.isValidPhone(phone))
            return Response.createResponse(451, "Phone isn't valid, try "+Utility.VALID_PHONE_REGEX.pattern(), false, user);
        if(!Utility.isValidEmail(email))
            return Response.createResponse(454, "Email isn't valid, try "+Utility.VALID_EMAIL_ADDRESS_REGEX.pattern(), false, user);


        // Nickname is optional, if empty, generate one.
        if(nickname == null || nickname.isEmpty()) {
            nickname = name;
            int i = 1;
            while(userRepo.existsByNickname(nickname+i)){
                i++;
            }
            nickname += i;
        } else if (userRepo.existsByNickname(nickname)) {
            return Response.createResponse(443, "Nickname is taken", false, user);
        }


        if(userRepo.existsByEmail(email)) {
            return Response.createResponse(444, "Email is taken", false, user);
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
