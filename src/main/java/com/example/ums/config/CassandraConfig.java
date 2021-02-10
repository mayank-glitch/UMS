package com.example.ums.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.List;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keySpace;

    @Value("${spring.data.cassandra.local-datacenter}")
    private String datacenter;

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Bean
    public Session createSession() {
        return createSession(contactPoints, port);
    }

    public static Session createSession(String ip, int port) {
        Cluster cluster;

        cluster = Cluster.builder().addContactPoint(ip).withoutJMXReporting().withPort(port).build();

        Session session = cluster.connect();

        session.execute(
                "CREATE KEYSPACE IF NOT EXISTS ums WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 }");
        session.execute(
                "CREATE TABLE IF NOT EXISTS ums.users(\n"
                        + "id uuid,\n"
                        + "user_name text,\n"
                        + "first_name text,\n"
                        + "last_name text,\n"
                        + "email text,\n"
                        + "password text,\n"
                        + "phone_no text,\n"
                        + "created_at bigint,\n"
                        + "is_latest boolean,\n"
                        + "version int,\n"
                        + "updated_at bigint,\n"
                        + "PRIMARY KEY(id, version))\n"
                        + "WITH CLUSTERING ORDER BY (version DESC);");
        return session;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keySpace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return com.sun.tools.javac.util.List.of(specification);
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getLocalDataCenter() {
        return datacenter;
    }
}
