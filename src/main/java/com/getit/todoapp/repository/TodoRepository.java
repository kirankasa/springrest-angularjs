package com.getit.todoapp.repository;
import com.getit.todoapp.domain.Todo;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = Todo.class)
public interface TodoRepository {
}
