package com.chirchir.rm.api;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error<T> {

    private String message;

    private T model;
}
