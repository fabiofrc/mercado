insert into categoria (id, nome) values (1, 'Mat\Finais');
insert into categoria (id, nome) values (2, 'Alimentos Básicos');
insert into categoria (id, nome) values (3, 'Frescos e refrigerantes');
insert into categoria (id, nome) values (4, 'Açougue e peixaria');
insert into categoria (id, nome) values (5, 'Bebidas');
insert into categoria (id, nome) values (6, 'Limpeza');
insert into categoria (id, nome) values (7, 'Perfumaria');
insert into categoria (id, nome) values (8, 'Saudaveis');
insert into categoria (id, nome) values (9, 'Pets');
insert into categoria (id, nome) values (10, 'Infantial');

-- mat/finais
insert into subcategoria (id, nome, categoria_id) values (1, 'Pães, torradas e bolos', 1);
insert into subcategoria (id, nome, categoria_id) values (2, 'Iogurtes e laticínios', 1);
insert into subcategoria (id, nome, categoria_id) values (3, 'Achocolatados', 1);
insert into subcategoria (id, nome, categoria_id) values (4, 'Café e chá', 1);
insert into subcategoria (id, nome, categoria_id) values (5, 'Farinhas, aveia e mingau', 1);
insert into subcategoria (id, nome, categoria_id) values (6, 'Leite', 1);
insert into subcategoria (id, nome, categoria_id) values (7, 'Biscoitos e cereais', 1);

--alimentos básicos
insert into subcategoria (id, nome, categoria_id) values (8, 'Arroz e grãos', 2);
insert into subcategoria (id, nome, categoria_id) values (9, 'Doces, sobremesas e confeitaria', 2);
insert into subcategoria (id, nome, categoria_id) values (10, 'Massas e molhos', 2);
insert into subcategoria (id, nome, categoria_id) values (11, 'Azeites, óleos e vinagres', 2);
insert into subcategoria (id, nome, categoria_id) values (12, 'Temperos e condimentos', 2);

--Frescos e refrigerantes
insert into subcategoria (id, nome, categoria_id) values (13, 'Frutas', 3);
insert into subcategoria (id, nome, categoria_id) values (14, 'Carnes, aves e peixes', 3);
insert into subcategoria (id, nome, categoria_id) values (15, 'Verduras e Legumes', 3);
insert into subcategoria (id, nome, categoria_id) values (16, 'Queijos e frios', 3);
insert into subcategoria (id, nome, categoria_id) values (17, 'Congelados', 3);
insert into subcategoria (id, nome, categoria_id) values (18, 'Pratos prontos', 3);
insert into subcategoria (id, nome, categoria_id) values (19, 'Ovos', 3);

--Açougue e peixaria
insert into subcategoria (id, nome, categoria_id) values (20, 'Carne bovina', 4);
insert into subcategoria (id, nome, categoria_id) values (21, 'Carne de porco', 4);
insert into subcategoria (id, nome, categoria_id) values (22, 'Frango', 4);
insert into subcategoria (id, nome, categoria_id) values (23, 'Aves especiais', 4);
insert into subcategoria (id, nome, categoria_id) values (24, 'Peixaria', 4);
insert into subcategoria (id, nome, categoria_id) values (25, 'Frutos do mar', 4);


--Bebidas
insert into subcategoria (id, nome, categoria_id) values (26, 'Água e água de coco', 5);
insert into subcategoria (id, nome, categoria_id) values (27, 'Sucos e refrescos', 5);
insert into subcategoria (id, nome, categoria_id) values (28, 'Refrigerantes', 5);
insert into subcategoria (id, nome, categoria_id) values (29, 'Cervejas', 5);
insert into subcategoria (id, nome, categoria_id) values (30, 'Cervejas especiais', 5);
insert into subcategoria (id, nome, categoria_id) values (31, 'Vinhos e espumantes', 5);
insert into subcategoria (id, nome, categoria_id) values (32, 'Whisky', 5);
insert into subcategoria (id, nome, categoria_id) values (33, 'Destilados', 5);

--Limpeza
insert into subcategoria (id, nome, categoria_id) values (34, 'Limpeza da casa', 6);
insert into subcategoria (id, nome, categoria_id) values (35, 'Cuidados com as roupas', 6);
insert into subcategoria (id, nome, categoria_id) values (36, 'Descatáveis', 6);


--Perfumaria
insert into subcategoria (id, nome, categoria_id) values (37, 'Cuidados pessoais', 7);
insert into subcategoria (id, nome, categoria_id) values (38, 'Banho', 7);
insert into subcategoria (id, nome, categoria_id) values (39, 'Higiene bucal', 7);
insert into subcategoria (id, nome, categoria_id) values (40, 'Papel higiênico', 7);


--Saudaveis 
insert into subcategoria (id, nome, categoria_id) values (41, 'Integrais', 8);
insert into subcategoria (id, nome, categoria_id) values (42, 'Orgânicos', 8);
insert into subcategoria (id, nome, categoria_id) values (43, 'sem Antibiótico', 8);
insert into subcategoria (id, nome, categoria_id) values (44, 'sem Glúten', 8);
insert into subcategoria (id, nome, categoria_id) values (45, 'sem Lactose', 8);
insert into subcategoria (id, nome, categoria_id) values (46, 'Zero', 8);
insert into subcategoria (id, nome, categoria_id) values (47, 'Sementes', 8);


--Pets
insert into subcategoria (id, nome, categoria_id) values (48, 'Cães', 9);
insert into subcategoria (id, nome, categoria_id) values (49, 'Gatos', 9);


--Infantil
insert into subcategoria (id, nome, categoria_id) values (50, 'Balas, chicletes e pirulitos', 10);
insert into subcategoria (id, nome, categoria_id) values (51, 'Itens para festa', 10);
insert into subcategoria (id, nome, categoria_id) values (52, 'Leite fermentado', 10);
insert into subcategoria (id, nome, categoria_id) values (53, 'Biscoitos, salgadinhos e cereais', 10);
insert into subcategoria (id, nome, categoria_id) values (54, 'Cereais', 10);
insert into subcategoria (id, nome, categoria_id) values (55, 'Leites e fórmulas infantis', 10);
insert into subcategoria (id, nome, categoria_id) values (56, 'Bebidas', 10);
insert into subcategoria (id, nome, categoria_id) values (57, 'Fraldas', 10);


--delete from subcategoria;
--delete from categoria;
