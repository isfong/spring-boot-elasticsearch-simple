package com.example.springbootelasticsearchsimple.application.services;

import com.example.springbootelasticsearchsimple.domain.models.entities.Decoration;
import com.example.springbootelasticsearchsimple.domain.repositories.DecorationRepository;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class DecorationApplicationService {
    private final DecorationRepository decorationRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public DecorationApplicationService( DecorationRepository decorationRepository, ElasticsearchOperations elasticsearchOperations ) {
        this.decorationRepository = decorationRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public SearchPage< Decoration > query( int page, int size, String sort, String customerName,
                                           String customerPhoneNumber, String workerName, String share ) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder( );
        BoolQueryBuilder customerQueryBuilder = QueryBuilders.boolQuery( );
        BoolQueryBuilder workersQueryBuilder = QueryBuilders.boolQuery( );
        BoolQueryBuilder aggregateQueryBuilder = QueryBuilders.boolQuery( );

        if ( StringUtils.isNotBlank( customerName ) ) {
            customerQueryBuilder.must( QueryBuilders.queryStringQuery( customerName ).field( "customer.name" ) );
        }
        if ( StringUtils.isNotBlank( customerPhoneNumber ) ) {
            customerQueryBuilder.must( QueryBuilders.queryStringQuery( customerPhoneNumber ).field( "customer.phoneNumber" ) );
        }
        if ( StringUtils.isNotBlank( workerName ) ) {
            workersQueryBuilder.must( QueryBuilders.queryStringQuery( workerName ).field( "workers.name" ) );
        }
        if ( customerQueryBuilder.hasClauses( ) ) {
            aggregateQueryBuilder.must( QueryBuilders.nestedQuery( "customer", customerQueryBuilder, ScoreMode.None ) );
        }
        if ( workersQueryBuilder.hasClauses( ) ) {
            aggregateQueryBuilder.must( QueryBuilders.nestedQuery( "workers", workersQueryBuilder, ScoreMode.None ) );
        }
        if ( StringUtils.isNotBlank( share ) ) {
            aggregateQueryBuilder.must( QueryBuilders.queryStringQuery( share ).field( "shares" ) );
        }
        if ( aggregateQueryBuilder.hasClauses( ) ) {
            nativeSearchQueryBuilder.withQuery( aggregateQueryBuilder );
        }
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build( );
        Pageable pageable = PageRequest.of( page, size, Sort.by( sort ).descending( ) );
        nativeSearchQuery.setPageable( pageable );
        SearchHits< Decoration > searchHits = decorationRepository.query( nativeSearchQuery, elasticsearchOperations );
        return SearchHitSupport.searchPageFor( searchHits, pageable );
    }
}
