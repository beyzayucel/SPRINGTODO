package com.haratres.todo.repository;

import com.haratres.todo.entity.Tasks;
import com.haratres.todo.entity.Users;
import com.haratres.todo.enums.TasksStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {

    @Query("select t from Tasks t where t.status=:status and t.users=:user")
    List<Tasks> getStatus(@Param("status") TasksStatus tasks, @Param("user") Users users);

    @Query("select t from Tasks t where t.users=:user order by t.createdDate asc")
    List<Tasks> sortByDate(@Param("user") Users users);

    @Query("select t from Tasks t where t.users=:user and t.id=:id")
    Tasks getTasksById(@Param("id")int id, @Param("user") Users users);

    @Query("select t from Tasks t where t.users=:user and t.title=:title")
    Tasks getTasksByTitle(@Param("title")String title, @Param("user") Users users);
}
