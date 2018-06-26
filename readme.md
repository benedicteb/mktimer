# mktimer

This is a tiny web app you can use for timing things. For example for keeping
track of work hours.

## Building

```shell
$ mvn clean package docker:build
```

To also push the created tags to dockerhub (will create `:latest` and `:version`
every time) use:

```shell
$ mvn clean package docker:build -DpushImage
```

## Testing

First start the database instance.

```shell
$ docker-compose up -d
```

Then run the tests.

```shell
$ mvn test
```

## To do

-   Add flyway support
-   Add a UUID field to all acitivies and categories
-   Add endpoint for editing an activity
-   Add JWT authentication for all endpoints (not the start activity endpoint due
    to tasker)
-   Add ics-endpoint which generates a calendar with activities for a given
    category
-   Build a frontend
-   Fix the tests so they don't always fail when running maven since no postgres is up
