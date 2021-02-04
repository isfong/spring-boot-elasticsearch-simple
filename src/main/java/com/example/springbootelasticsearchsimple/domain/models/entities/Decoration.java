package com.example.springbootelasticsearchsimple.domain.models.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Document( indexName = "decoration" )
public class Decoration {

    @Id
    private Long id;
    @Field( type = FieldType.Nested )
    private Customer customer;
    @Field( type = FieldType.Nested )
    private List< DecorationWorker > workers;
    private List<String> shares;
}
