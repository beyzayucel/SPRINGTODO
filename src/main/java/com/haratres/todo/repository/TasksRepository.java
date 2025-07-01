package com.haratres.todo.repository;

import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {

    @Query("select t from Tasks t where t.users=:user order by t.title desc")
    List<Tasks> sortByTitle(@Param("user") Users users);


}
