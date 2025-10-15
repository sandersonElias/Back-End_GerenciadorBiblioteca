CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_livro (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    titulo VARCHAR(50) NOT NULL,
    editora VARCHAR(50) NOT NULL,
    cdd VARCHAR(5) NOT NULL,
    localizacao VARCHAR(10) NOT NULL,
    exemplares INTEGER NOT NULL,
    disponibilidade BOOLEAN NOT NULL,
    genero_id UUID,
    catalogacao_id UUID,
    autor_id UUID,
    FOREIGN KEY (genero_id) REFERENCES tb_genero(id),
    FOREIGN KEY (catalogacao_id) REFERENCES tb_catalogacao(id),
    FOREIGN KEY (autor_id) REFERENCES tb_autor(id)
);