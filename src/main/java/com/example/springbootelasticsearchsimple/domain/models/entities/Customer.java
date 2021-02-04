package com.example.springbootelasticsearchsimple.domain.models.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
public class Customer {
    @Id
    private Long id;
    private String name;
    private String description;
    private String phoneNumber;
}
