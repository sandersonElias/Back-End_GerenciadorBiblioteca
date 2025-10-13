INSERT INTO tb_aluno (id, ano, nome, turma, turno) VALUES
(1, 1, 'Ana Silva', '2A', 'Manhã'),
(2, 2, 'Bruno Souza', '2A', 'Manhã'),
(3, 3, 'Carla Pereira', '2B', 'Tarde'),
(4, 3, 'Diego Oliveira', '2B', 'Tarde'),
(5, 1, 'Eduarda Lima', '2C', 'Noite');

INSERT INTO tb_autor (id, nome) VALUES
(1,'Machado de Assis'),
(2,'José de Alencar'),
(3, 'Monteiro Lobato'),
(4, 'Clarice Lispector'),
(5, 'Graciliano Ramos'),
(6, 'Cecília Meireles'),
(7, 'Carlos Drummond de Andrade'),
(8, 'Jorge Amado'),
(9, 'Érico Veríssimo'),
(10,'Manuel Bandeira');

INSERT INTO tb_catalogacao (id, catalogacao) VALUES
(1, 'Literatura Brasileira'),
(2, 'Literatura Estrangeira'),
(3, 'Ciência e Tecnologia'),
(4, 'História'),
(5, 'Filosofia');

INSERT INTO tb_genero (id, genero) VALUES
(1, 'Romance'),
(2, 'Poesia'),
(3, 'Infantil'),
(4, 'Drama'),
(5, 'Conto');

INSERT INTO tb_livro (id,cdd, editora, localizacao, quant_disponivel, quant_total, titulo, catalogacao_id, genero_id) VALUES
(1, '869.3', 'Companhia das Letras', 'A1 PT1', 5, 5, 'Dom Casmurro', 1, 1),
(2, '869.3', 'Companhia das Letras', 'A1 PT2', 4, 4, 'Memórias Póstumas de Brás Cubas', 1, 1),
(3, '869.3', 'Record', 'A2 PT1', 3, 3, 'Iracema', 1, 1),
(4, '869.3', 'Moderna', 'A2 PT2', 2, 2, 'O Guarani', 1, 1),
(5, '869.3', 'Brasiliense', 'A3 PT1', 6, 6, 'Sítio do Picapau Amarelo', 1, 3),
(6, '869.3', 'Editora 34', 'A3 PT2', 5, 5, 'A Hora da Estrela', 1, 1),
(7, '869.3', 'Record', 'A4 PT1', 4, 4, 'Vidas Secas', 1, 1),
(8, '869.3', 'Nova Fronteira', 'A4 PT2', 3, 3, 'Infância', 1, 1),
(9, '869.3', 'Global', 'A5 PT1', 6, 6, 'Ou Isto ou Aquilo', 1, 3),
(10, '869.3', 'Global', 'A5 PT2', 6, 6, 'Romanceiro da Inconfidência', 1, 1),
(11, '869.3', 'Record', 'A6 PT1', 4, 4, 'Alguma Poesia', 1, 2),
(12, '869.3', 'Record', 'A6 PT2', 4, 4, 'Sentimento do Mundo', 1, 2),
(13, '869.3', 'Record', 'A7 PT1', 5, 5, 'Gabriela, Cravo e Canela', 1, 1),
(14, '869.3', 'Record', 'A7 PT2', 5, 5, 'Dona Flor e Seus Dois Maridos', 1, 1),
(15, '869.3', 'Globo', 'A8 PT1', 4, 4, 'O Tempo e o Vento', 1, 1),
(16, '869.3', 'Globo', 'A8 PT2', 4, 4, 'Incidente em Antares', 1, 1),
(17, '869.3', 'José Olympio', 'A9 PT1', 3, 3, 'Estrela da Vida Inteira', 1, 2),
(18, '869.3', 'José Olympio', 'A9 PT2', 3, 3, 'Libertinagem', 1, 2),
(19, '869.3', 'Companhia das Letras', 'A10 PT1', 5, 5, 'O Cortiço', 1, 1),
(20, '869.2', 'Companhia das Letras', 'A10 PT2', 5, 5, 'Senhora', 1, 1),
(21, '869.2', 'Moderna', 'A11 PT1', 4, 4, 'Quincas Borba', 1, 1),
(22, '869.2', 'Record', 'A11 PT2', 4, 4, 'Mar Morto', 1, 1),
(23, '869.2', 'Globo', 'A12 PT1', 3, 3, 'São Bernardo', 1, 1),
(24, '869.3', 'Globo', 'A12 PT2', 3, 3, 'Clarissa', 1, 1),
(25, '869.3', 'Global', 'A13 PT1', 6, 6, 'Lira dos Vinte Anos', 1, 2),
(26, '869.3', 'Global', 'A13 PT2', 6, 6, 'Poemas Escolhidos', 1, 2),
(27, '869.3', 'Moderna', 'A14 PT1', 4, 4, 'Casa de Pensão', 1, 1),
(28, '869.3', 'Record', 'A14 PT2', 4, 4, 'O Mulato', 1, 1),
(29, '869.3', 'Nova Fronteira', 'A15 PT1', 5, 5, 'Fogo Morto', 1, 1),
(30, '869.3', 'Nova Fronteira', 'A15 PT2', 5, 5, 'Menino de Engenho', 1, 1);

INSERT INTO livro_autor (livro_id, autor_id) VALUES
(1, 1), -- Dom Casmurro -> Machado de Assis
(2, 1), -- Memórias Póstumas -> Machado
(3, 2), -- Iracema -> José de Alencar
(4, 2), -- O Guarani -> José de Alencar
(5, 3), -- Sítio do Picapau -> Monteiro Lobato
(6, 4), -- A Hora da Estrela -> Clarice Lispector
(7, 5), -- Vidas Secas -> Graciliano Ramos
(8, 5), -- Infância -> Graciliano Ramos
(9, 6), -- Ou Isto ou Aquilo -> Cecília Meireles
(11, 7), -- Alguma Poesia -> Carlos Drummond
(12, 7), -- Sentimento do Mundo -> Carlos Drummond
(13, 8), -- Gabriela -> Jorge Amado
(14, 8), -- Dona Flor -> Jorge Amado
(15, 9), -- O Tempo e o Vento -> Érico Veríssimo
(16, 9), -- Incidente em Antares -> Érico Veríssimo
(17, 10), -- Estrela da Vida Inteira -> Manuel Bandeira
(18, 10); -- Libertinagem -> Manuel Bandeira

INSERT INTO tb_emprestimo (id, data_emprestimo, data_prevista, aluno_id, livro_id) VALUES
(1, '2025-08-01', '2025-08-08', 1, 1),
(2, '2025-08-02', '2025-08-09', 2, 3),
(3, '2025-08-03', '2025-08-10', 3, 5),
(4, '2025-08-04', '2025-08-11', 4, 7),
(5, '2025-08-05', '2025-08-12', 5, 9);