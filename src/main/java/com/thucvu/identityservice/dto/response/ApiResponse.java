package com.thucvu.identityservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) // cac field null se khong duoc them vao json tra ve
public class ApiResponse<T> {

    @Builder.Default
    int code = 1000;
    String message;
    T result;
}
