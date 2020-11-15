package com.demo.kafka.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;
}
