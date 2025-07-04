package com.haratres.todo.repository;

import com.haratres.todo.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Integer> {
    Otp findByEmail(String email);
    Otp findTopByEmailOrderByIdDesc(String email);
}
