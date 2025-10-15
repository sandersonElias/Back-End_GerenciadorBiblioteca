CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_catalogacao(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    catalogacao VARCHAR(50) NOT NULL
);