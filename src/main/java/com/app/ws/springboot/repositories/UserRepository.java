package com.app.ws.springboot.repositories;

import com.app.ws.springboot.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findUserByEmail(String email);
    User findByUserId(String userId);
    
}
