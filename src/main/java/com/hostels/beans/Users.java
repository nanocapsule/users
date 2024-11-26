package com.hostels.beans;

import io.micronaut.configuration.hibernate.jpa.proxy.GenerateProxy;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@GenerateProxy
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;

    public String toString() {
        return "Users(userId=" + this.getUserId() + ", username=" + this.getUsername() + ", email=" + this.getEmail() + ")";
    }

}