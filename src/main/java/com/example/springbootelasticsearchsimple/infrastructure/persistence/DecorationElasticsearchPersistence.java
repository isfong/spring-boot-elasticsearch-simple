package com.example.springbootelasticsearchsimple.infrastructure.persistence;

import com.example.springbootelasticsearchsimple.domain.models.entities.Decoration;
import com.example.springbootelasticsearchsimple.domain.repositories.DecorationRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface DecorationElasticsearchPersistence extends ElasticsearchRepository< Decoration, Long >,
        DecorationRepository {
    default SearchHits< Decoration > query( Query query, ElasticsearchOperations operations ) {
        return operations.search( query, Decoration.class );
    }
}
