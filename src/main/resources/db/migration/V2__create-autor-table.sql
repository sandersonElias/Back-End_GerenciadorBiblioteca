CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_autor(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);