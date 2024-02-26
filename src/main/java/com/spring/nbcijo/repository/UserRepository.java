package com.spring.nbcijo.repository;

import com.spring.nbcijo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
