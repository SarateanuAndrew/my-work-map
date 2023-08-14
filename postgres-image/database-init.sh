set -o errexit

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    -- pam

    CREATE DATABASE pam;


    CREATE USER pam WITH PASSWORD 'myPasswordTest';

    GRANT ALL PRIVILEGES ON DATABASE pam TO pam;

    -- pam

    CREATE DATABASE pam;

    CREATE USER pam WITH PASSWORD 'myPasswordTest';

    GRANT ALL PRIVILEGES ON DATABASE pam TO pam;
EOSQL

#psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "pam" <<-EOSQL
#    CREATE EXTENSION pgcrypto;
#EOSQL
