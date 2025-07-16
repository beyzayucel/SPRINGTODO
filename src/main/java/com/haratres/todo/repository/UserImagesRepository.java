package com.haratres.todo.repository;

import com.haratres.todo.entity.UserImage;
import com.haratres.todo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserImagesRepository extends JpaRepository<UserImage,Integer> {
    List<UserImage> findByUsers(Users users);
}
