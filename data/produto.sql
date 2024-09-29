

DROP TABLE IF EXISTS produto CASCADE;

CREATE TABLE produto (
                         id_produto BIGSERIAL PRIMARY KEY,
                         nome_produto VARCHAR(255) NOT NULL,
                         descricao_produto TEXT,
                         categoria VARCHAR(20) NOT NULL CHECK (categoria IN ('ANIMAIS',
                                                                             'ACESSORIOS_PARA_PET',
                                                                             'BONECAS',
                                                                             'CAMAS_DE_GATO',
                                                                             'INSETOS',
                                                                             'MINIATURAS',
                                                                             'NANINHAS',
                                                                             'NATALINOS',
                                                                             'PAINEIS'
                             )),
                         preco_produto DECIMAL(10, 2) NOT NULL,
                         quantidade_produto DECIMAL(10, 2),
                         porcentagem_desconto DECIMAL(5, 2),
                         imagem_produto1 VARCHAR(255),
                         imagem_produto2 VARCHAR(255),
                         imagem_produto3 VARCHAR(255),
                         imagem_produto4 VARCHAR(255),
                         disponivel BOOLEAN DEFAULT TRUE
);


select * from produto;