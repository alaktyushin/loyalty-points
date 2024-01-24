

# OpenAPI:
```
http://localhost:8088/swagger-ui-custom.html
```

```
http://localhost:8088/v3/api-docs
```
---

# Tokens:
## Tokens should include "role" parameter:
    
    {
        "sub": "9853cdb6-a2b6-4909-aa12-c3310ddc9cb2",
        "role": "REGISTERED_BUSINESS",
        "name": "John Doe",
        "iat": 1516239022
    }

## Tokens can be generated at:<br>
```
https://jwt.io/
```

## Tokens can be used for testing purposes:

#### ADMIN:
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI5ODUzY2RiNi1hMmI2LTQ5MDktYWExMi1jMzMxMGRkYzljYjIiLCJyb2xlIjoiYWRtaW4iLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.JGytX6AwSB9HSd-EXpqmKvlW4zEYtYKbZz28236S1p8


#### REGISTERED_BUSINESS:
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI5ODUzY2RiNi1hMmI2LTQ5MDktYWExMi1jMzMxMGRkYzljYjIiLCJyb2xlIjoiUkVHSVNURVJFRF9CVVNJTkVTUyIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.gxsTeg_FcW-1TNFYl--pukti9x8cnZ4mkLHvxCbHvG8


#### COMMON_BUSINESS:
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI5ODUzY2RiNi1hMmI2LTQ5MDktYWExMi1jMzMxMGRkYzljYjIiLCJyb2xlIjoiQ09NTU9OX0JVU0lORVNTIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.W0mIc8PQBvM8jQoRV0_v0j53DpuMRo2GTjO7B8TDw-s
