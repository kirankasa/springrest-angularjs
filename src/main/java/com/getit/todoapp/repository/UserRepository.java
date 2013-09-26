package com.getit.todoapp.repository;
import java.util.List;

import com.getit.todoapp.domain.Userinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RooJpaRepository(domainType = Userinfo.class)
public interface UserRepository extends JpaSpecificationExecutor<Userinfo>, JpaRepository<Userinfo, Long> {
	
	List<Userinfo> findByUserName(String userName);
}
