package com.getit.todoapp.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

import com.getit.todoapp.domain.Todo;

@Repository
@RooJpaRepository(domainType = Todo.class)
public interface TodoRepository extends JpaSpecificationExecutor<Todo>, JpaRepository<Todo, Long> {
	@Query("select t from Todo t where t.userName.userName = ?1")
	List<Todo> findTodosByUserName(String userName);
}
