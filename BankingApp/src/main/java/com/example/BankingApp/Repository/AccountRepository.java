package com.example.BankingApp.Repository;

import com.example.BankingApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<User, Long> {
    User findById(long id);
}
