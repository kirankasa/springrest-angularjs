// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.getit.todoapp.repository;

import com.getit.todoapp.domain.Todo;
import com.getit.todoapp.repository.TodoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

privileged aspect TodoRepository_Roo_Jpa_Repository {
    
    declare parents: TodoRepository extends JpaRepository<Todo, Long>;
    
    declare parents: TodoRepository extends JpaSpecificationExecutor<Todo>;
    
    declare @type: TodoRepository: @Repository;
    
}