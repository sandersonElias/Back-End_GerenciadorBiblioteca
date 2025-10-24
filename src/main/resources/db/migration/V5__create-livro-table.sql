CREATE TABLE tb_livro (
    id INTEGER PRIMARY KEY,
    titulo VARCHAR(50) NOT NULL,
    editora VARCHAR(50) NOT NULL,
    cdd VARCHAR(5) NOT NULL,
    localizacao VARCHAR(10) NOT NULL,
    exemplares INTEGER NOT NULL,
    disponibilidade BOOLEAN NOT NULL,
    descrica VARCHAR(255),
    quant_total INTEGER,
    url_img VARCHAR(100),
    genero_id INTEGER,
    catalogacao_id INTEGER,
    autor_id INTEGER,
    FOREIGN KEY (genero_id) REFERENCES tb_genero(id),
    FOREIGN KEY (catalogacao_id) REFERENCES tb_catalogacao(id),
    FOREIGN KEY (autor_id) REFERENCES tb_autor(id)
);