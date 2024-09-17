## Made with Spring Boot and GraphQL initializr
Followed tutorial: https://spring.io/guides/gs/graphql-server

## Connect to local h2 database - with these properties
```
spring.datasource.url=jdbc:h2:./target/h2db/db/'TEST';AUTO_SERVER=TRUE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=hottentott
```

## Accessing the GraphQL console 
`http://localhost:8080/graphiql`

## Testing the GraphQL API with curl
`bash curl -u user:hottentott -X POST "http://localhost:8080/graphql" -H "Content-Type: application/json" -d '{"query":"{ todos{ id } }"}'`

