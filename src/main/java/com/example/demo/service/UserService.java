package com.example.demo.service;

import com.example.demo.entity.Prescription;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    private UserRepository userRepository;

    public UserService()
    {
    }

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers()
    {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public List<User> getAllPatients()
    {
        String userRole = "patient";
        List<User> userList = userRepository.findByRole(userRole);
        return userList;
    }

    public void save(User user)
    {
        userRepository.save(user);
    }

    public User findById(int id)
    {
        User newUser =null;
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
        {
            newUser = user.get();
        }
        return newUser;
    }


    public void deleteById(int id)
    {
        userRepository.deleteById(id);
    }

}
