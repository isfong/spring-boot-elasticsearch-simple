package com.example.springbootelasticsearchsimple.domain.repositories;

import com.example.springbootelasticsearchsimple.domain.models.entities.Decoration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DecorationRepository {
    Decoration save( Decoration decoration );

    SearchHits< Decoration > query( Query query, ElasticsearchOperations operations );
}
