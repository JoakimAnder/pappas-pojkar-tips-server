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
    public Response<String> login(@RequestBody Request<LoginRequest> query){

        User user = userRepo.findByEmail(query.getData().getEmail());

        if(user == null)
            return Response.createResponse(413, "Invalid login", false, null);

        Long now = LocalDateTime.now().toEpochSecond(Utility.SERVER_OFFSET);

        if(user.getLoginDeniedUntil() > now)
            return Response.createResponse(408, "Login is suspended", false, null);

        if(!Utility.MD5Encode(query.getData().getPassword()).equals(user.getPassword())) {
            int attempts = user.getAttemptedLogins();

            if(++attempts > 2) {
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
        userRepo.save(user);

        return Response.createSuccessfulResponse(token);

    }

    @PostMapping("/IsLoggedIn") //TODO remove
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
        if(user.getTokenLastValidDate() < checkTimeStamp){
            return "Not logged in";
        }

        user.setTokenLastValidDate(checkTimeStamp+Utility.SECONDS_UNTIL_AUTOMATIC_LOGOUT);
        userRepo.save(user);
        return "logged in";
    }

    @PostMapping("/addUser")
    public Response<User> addUser(@RequestBody Request<User> query){
        User user = query.getData();

        if(userRepo.existsByEmail(user.getEmail())) {
            return Response.createResponse(444, "Email is taken", false, user);
        }
        if(userRepo.existsByNickname(user.getNickname())) {
            return Response.createResponse(443, "Nickname is taken", false, user);
        }

        user = userRepo.save(
                new User(
                        user.getName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getPhone(),
                        user.getNickname()));

        return Response.createSuccessfulResponse(user);
    }

    @PostMapping("updateUser")
    public Response<User> updateUser(@RequestBody Request<User> query){
        int id = query.getHead().getUserId();
        Optional<User> oUser = userRepo.findById(id);

        if (!oUser.isPresent()) {
            return Response.createResponse(412, "User not found", false, null);
        }

        User newUser = query.getData();
        User user = oUser.get();

        if (isLoggedIn(user.getEmail(), query.getHead().getToken()).equals("Not logged in")) { //TODO fix isLoggedIn
            return Response.createResponse(401, "Not logged in", false, null);
        }

        if (userRepo.findByEmail(newUser.getEmail()) != null) {
            return Response.createResponse(444, "Email is taken", false, user);
        }
        if (userRepo.findByNickname(newUser.getNickname()) != null) {
            return Response.createResponse(443, "Nickname is taken", false, user);
        }
        if(newUser.getName() != null)
            user.setName(newUser.getName());
        if(newUser.getNickname() != null)
            user.setNickname(newUser.getNickname());
        if(newUser.getPhone() != null)
            user.setPhone(newUser.getPhone());
        if(newUser.getEmail() != null)
            user.setEmail(newUser.getEmail());


        user = userRepo.save(user);

        return Response.createSuccessfulResponse(user);
    }

    @PostMapping("deleteUser")
    public Response<Boolean> delete(@RequestBody Request<Integer> query){
        int id = query.getHead().getUserId();

        if(id != query.getData())
            return Response.createResponse(462, "Unauthorized to delete another's account", false, false);

        String token = query.getHead().getToken();
        Optional<User> oUser = userRepo.findById(id);


        if (!oUser.isPresent()) {
            return Response.createResponse(412, "User not found", false, false);
        }
        User user = oUser.get();

        if (isLoggedIn(user.getEmail(), token).equals("Not logged in")) {
            return Response.createResponse(401, "Not logged in", false, false);
        }


        userRepo.deleteById(id);
        return Response.createSuccessfulResponse(true);
    }


}
