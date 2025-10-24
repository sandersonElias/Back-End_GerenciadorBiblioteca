CREATE TABLE tb_emprestimo (
    id INTEGER PRIMARY KEY,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,
    renovacao INTEGER DEFAULT 0,
    status VARCHAR(25),
    aluno_id INTEGER,
    livro_id INTEGER,
    FOREIGN KEY (aluno_id) REFERENCES tb_aluno(id),
    FOREIGN KEY (livro_id) REFERENCES tb_livro(id)
);