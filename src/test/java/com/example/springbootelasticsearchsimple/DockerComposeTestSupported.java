package com.example.springbootelasticsearchsimple;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.palantir.docker.compose.DockerComposeExtension;
import com.palantir.docker.compose.configuration.DockerComposeFiles;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

@SpringBootTest
public class DockerComposeTestSupported {

    @SuppressWarnings( "unused" )
    @RegisterExtension
    public static final DockerComposeExtension dockerComposeExtension = DockerComposeExtension.builder( )
            .files( DockerComposeFiles.from( "src/test/resources/docker-compose.yml" ) )
            .waitingForService( "elasticsearch", HealthChecks.toHaveAllPortsOpen( ) )
            .build( );

    protected Faker faker = Faker.instance( Locale.CHINA );

    @SneakyThrows
    protected String toJsonString( Object o ) {
        return new ObjectMapper( ).writeValueAsString( o );
    }
}
