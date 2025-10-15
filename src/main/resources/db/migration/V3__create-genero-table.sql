CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_genero(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    genero VARCHAR(50) NOT NULL
);