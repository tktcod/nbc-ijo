package com.spring.nbcijo.repository;

import com.spring.nbcijo.entity.PasswordHistory;
import com.spring.nbcijo.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

    List<PasswordHistory> findTop3ByUserIdOrderByIdDesc(User user);
}
