package com.myblog.blogapp.payload;

import lombok.Data;

@Data
public class LoginDto {
   private String usernameorEmail;
    private String password;
}
