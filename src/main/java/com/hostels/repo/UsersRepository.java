package com.hostels.repo;

import com.hostels.beans.Users;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {
}