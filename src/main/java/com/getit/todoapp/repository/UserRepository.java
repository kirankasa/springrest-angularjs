package com.getit.todoapp.repository;
import com.getit.todoapp.domain.Userinfo;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = Userinfo.class)
public interface UserRepository {
}
