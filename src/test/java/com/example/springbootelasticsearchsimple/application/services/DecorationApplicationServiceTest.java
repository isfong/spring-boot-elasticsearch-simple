package com.example.springbootelasticsearchsimple.application.services;

import com.example.springbootelasticsearchsimple.DockerComposeTestSupported;
import com.example.springbootelasticsearchsimple.domain.models.entities.Customer;
import com.example.springbootelasticsearchsimple.domain.models.entities.Decoration;
import com.example.springbootelasticsearchsimple.domain.models.entities.DecorationWorker;
import com.example.springbootelasticsearchsimple.domain.repositories.DecorationRepository;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DecorationApplicationServiceTest extends DockerComposeTestSupported {
    @Autowired
    DecorationRepository decorationRepository;
    @Autowired
    DecorationApplicationService decorationApplicationService;

    List< Decoration > cachedDecorations = new ArrayList<>( );

    void initData(  ) {
        for ( int i = 1; i <= 50; i++ ) {
            Decoration decoration = new Decoration( );
            decoration.setId( RandomUtils.nextLong( ) );
            DecorationWorker worker = new DecorationWorker( );
            worker.setId( 9090L );
            worker.setName( "5632" );
            decoration.setWorkers( List.of( worker ) );
            decoration.setShares( List.of( "Java", "Python", "Golang" ) );
            Customer customer = new Customer( );
            customer.setId( RandomUtils.nextLong( ) );
//            customer.setName( faker.name( ).name( ) );
            customer.setName( "同一个名字" );
            customer.setPhoneNumber( faker.phoneNumber( ).cellPhone( ) );
//            customer.setPhoneNumber( "15622150796" );
            decoration.setCustomer( customer );
            Decoration save = decorationRepository.save( decoration );
            cachedDecorations.add( save );
        }
    }

    @Test
    void query( ) {
        // Given
        this.initData( );

        // When
        SearchPage< Decoration > decorationSearchPage = decorationApplicationService.query( 0, 3, "id", "*字*", null, "*56*", "*av*" );
        System.err.println( toJsonString( decorationSearchPage ) );
        List< Decoration > decorations = decorationSearchPage.getSearchHits( ).stream( ).map( SearchHit::getContent ).collect( Collectors.toList( ) );

        // Then
        System.err.println( toJsonString( decorations ) );
        assertThat( decorations ).isNotEmpty( );
    }
}