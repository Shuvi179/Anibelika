package com.orion.anibelika.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
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
