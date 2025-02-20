package com.codeit.team5.ican.repository;

import com.codeit.team5.ican.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByCodeitId(Long codeitId);
}
