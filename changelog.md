# Changelog

## Backend

### v1.1.0 (2018-07-01)

-   Added before and after query parameters to activity
-   Added a create activity endpoint
-   Optimized activity tests

### v1.0.1 (2018-06-27)

-   Change JWT expiration to be taken from environment

### v1.0.0 (2018-06-26)

-   Add JWT authentication (in addition to HTTP Basic for `activity/start` and
    `activity/stop`).
-   Add integration tests
-   Add `docker-compose.yml` for running PostgreSQL test instance
