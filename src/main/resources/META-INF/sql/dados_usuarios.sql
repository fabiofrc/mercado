/*
delete from cliente;
delete from pessoa;
delete from endereco;
delete from usuario_grupo;
delete from grupo;
delete from usuario;
*/


insert into usuario (id, email, senha) values (1, 'admin@admin', '$2a$10$vBuzBdm8Pt2ZDGRFB8EVseCbi2KN70rotnYFnaIorEcY8YrvvEdhO');
insert into usuario (id, email, senha) values (2, 'fabio@gmail.com', '$2a$10$vBuzBdm8Pt2ZDGRFB8EVseCbi2KN70rotnYFnaIorEcY8YrvvEdhO');
insert into usuario (id, email, senha) values (3, 'empresa@gmail.com', '$2a$10$vBuzBdm8Pt2ZDGRFB8EVseCbi2KN70rotnYFnaIorEcY8YrvvEdhO');


insert into grupo (id, descricao, nome) values (1, 'Administradores', 'ADMINISTRADOR');
insert into grupo (id, descricao, nome) values (2, 'Participante', 'PARTICIPANTE');
insert into grupo (id, descricao, nome) values (3, 'PessoaJuridica', 'PESSOAJURIDICA');

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1);
insert into usuario_grupo (usuario_id, grupo_id) values (2, 2);
insert into usuario_grupo (usuario_id, grupo_id) values (3, 3);

insert into endereco(id, logradouro) values (1, 'meu endereco');
insert into pessoa(id, nome, usuario_id, endereco_id) values (1, 'administrador do sistema', 1, 1);
insert into cliente(cpf, cliente_id) values ('000.000.000-80', 1);



