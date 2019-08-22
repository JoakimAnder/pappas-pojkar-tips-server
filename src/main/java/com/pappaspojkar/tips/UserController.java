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
