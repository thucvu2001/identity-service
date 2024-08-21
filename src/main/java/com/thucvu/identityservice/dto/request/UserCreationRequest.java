package com.thucvu.identityservice.dto.request;

import com.thucvu.identityservice.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE) // field of class default is private
public class UserCreationRequest {
    @Size(min = 3, max = 20, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, max = 20, message = "INVALID_PASSWORD")
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 2, message = "INVALID_DOB")
    LocalDate dob;
}
