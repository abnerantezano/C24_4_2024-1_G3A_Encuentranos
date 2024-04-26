package com.ambrosio.html.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ambrosio.html.models.UserModel;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long> {
	
	

	
}
