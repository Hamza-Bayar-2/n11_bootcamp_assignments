package com.bootcamp.jwt_and_refresh_token_authentication.domain.entity;

import com.bootcamp.jwt_and_refresh_token_authentication.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {
    private Long user;
    private RoleType roleType;
}
