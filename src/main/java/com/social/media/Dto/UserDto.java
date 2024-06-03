package com.social.media.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "First Name cannot be blank")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;
    @NotBlank(message = "Last Name cannot be blank")
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName ;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email ;
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    private String userName;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String passwords;
}
