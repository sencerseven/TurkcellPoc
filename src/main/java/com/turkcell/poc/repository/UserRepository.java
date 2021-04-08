package com.turkcell.poc.repository;

import com.turkcell.poc.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

  Optional<Users> findByUserName(String userName);

}
