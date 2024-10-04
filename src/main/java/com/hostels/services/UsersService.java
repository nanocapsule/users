package com.hostels.services;

import com.hostels.beans.Users;
import com.hostels.repo.UsersRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    @Transactional
    public Users save(Users user) {
        return usersRepository.save(user);
    }
}
