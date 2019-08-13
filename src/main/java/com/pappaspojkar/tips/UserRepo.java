/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pappaspojkar.tips;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mehtab
 */
@Repository
public interface UserRepo extends CrudRepository<User, Integer>{
    
    public User findByEmail(String email);
    
}
