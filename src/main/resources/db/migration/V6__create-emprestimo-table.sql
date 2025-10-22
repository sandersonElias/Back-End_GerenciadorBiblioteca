CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_emprestimo (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE,
    renovacao INTEGER DEFAULT 0,
    status VARCHAR(25),
    aluno_id UUID,
    livro_id UUID,
    FOREIGN KEY (aluno_id) REFERENCES tb_aluno(id),
    FOREIGN KEY (livro_id) REFERENCES tb_livro(id)
);