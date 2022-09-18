docker run --net app -p 5432:5432 --restart=always  -d \
        --name postgres \
        -e POSTGRES_PASSWORD=123456 \
        -e PGDATA=/var/lib/postgresql/data/pgdata \
        -v /usr/local/postgres/postgresql.conf:/etc/postgresql/postgresql.conf \
        -v /usr/local/postgres/data:/var/lib/postgresql/data \
        postgres

#listen_addresses = '*'

# /usr/local/postgres/data/pgdata/pg_hba.conf
# host    all             all             0.0.0.0/0               trust