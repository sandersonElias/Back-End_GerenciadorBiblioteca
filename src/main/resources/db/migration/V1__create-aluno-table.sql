CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_aluno(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    ano INTEGER NOT NULL,
    turma VARCHAR(50),
    turmo VARCHAR (50)
);