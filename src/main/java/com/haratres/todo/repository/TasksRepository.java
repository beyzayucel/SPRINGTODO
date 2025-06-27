package com.haratres.todo.repository;

import com.haratres.todo.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {

    List<Tasks> findByTitle(String title);


}
