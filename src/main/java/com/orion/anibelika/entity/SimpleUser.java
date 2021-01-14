package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "simple_user")
public class SimpleUser extends AuthUser {
    @Column
    private String password;

    @Override
    public String getPassword() {
        return this.password;
    }
}
