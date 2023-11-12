package com.artineer.artineersemina232.repository;


import com.artineer.artineersemina232.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username); //메소드 오버라이딩 함.

}
