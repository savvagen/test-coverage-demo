package com.example.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    @Getter @Setter public int code;
    @Getter @Setter public String type;
    @Getter @Setter public String message;
}
