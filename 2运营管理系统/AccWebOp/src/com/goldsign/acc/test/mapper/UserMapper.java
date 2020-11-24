/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.test.mapper;

import com.goldsign.acc.test.entity.User;
import java.util.List;

/**
 *
 * @author hejj
 */
public interface UserMapper {

    public User selectUserByID(String id);

    public List<User> selectUsers();

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUser(int id);

}
