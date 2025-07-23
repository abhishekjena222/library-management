package com.example.libraryManagement.repository;

import com.example.libraryManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    void deleteById(Long aLong);
}
