package com.ImyEye.info.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ImyEye.info.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{

      Optional<User> findByEmail(String email);

}
