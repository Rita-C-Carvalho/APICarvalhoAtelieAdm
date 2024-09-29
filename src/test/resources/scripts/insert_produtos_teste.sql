DELETE FROM produto;

INSERT INTO produto (id_produto, nome_produto, descricao_produto, categoria, preco_produto, porcentagem_desconto, imagem_produto1, imagem_produto2, imagem_produto3, imagem_produto4, disponivel)
VALUES
    (1, 'Produto 1', 'Descrição do Produto 1', 'INSETOS', 100.00, 10.00, 'imagem1.jpg', 'imagem4.jpg', 'imagem5.jpg', 'imagem6.jpg', true),
    (2, 'Produto 2', 'Descrição do Produto 2', 'BONECAS', 200.00, 20.00, 'imagem2.jpg', 'imagem7.jpg', 'imagem8.jpg', 'imagem9.jpg', true),
    (3, 'Produto 3', 'Descrição do Produto 3', 'PAINEIS', 300.00, 0.00, 'imagem3.jpg', 'imagem10.jpg', 'imagem11.jpg', 'imagem12.jpg', false);