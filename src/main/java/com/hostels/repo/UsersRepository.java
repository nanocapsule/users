package com.hostels.repo;

import com.hostels.beans.Users;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {
    @Query(
        "SELECT h FROM Users h WHERE " +
        "(:username IS NULL OR h.username LIKE CONCAT('%', :username, '%')) AND " +
        "(:email IS NULL OR h.email LIKE CONCAT('%', :email, '%'))"
    )
    List<Users> findUsersByOptionalFields(
        @Nullable String email,
        @Nullable String username
    );
}