INSERT into role (name) VALUES ('ADMIN');
INSERT into role (name) VALUES ('MANAGER');
INSERT into role (name) VALUES ('USER');

-- passwort is uer
insert into person (email, password) values ('admin@admin.com', '$2a$10$CYJEjGAgzuHLdIX85.dN2uNORL084WgUZ3mC75.eBhuE.kGmN3j9i');
insert into person (email, password) values ('manager@manager.com', '$2a$10$CYJEjGAgzuHLdIX85.dN2uNORL084WgUZ3mC75.eBhuE.kGmN3j9i');
insert into person (email, password) values ('user@user.com', '$2a$10$CYJEjGAgzuHLdIX85.dN2uNORL084WgUZ3mC75.eBhuE.kGmN3j9i');

INSERT into person_role (person_id, role_id) VALUES (1, 1);
INSERT into person_role (person_id, role_id) VALUES (2, 2);
INSERT into person_role (person_id, role_id) VALUES (3, 3);

insert into event (name, date, owner_id, description) values ('Event 1', '2021-01-01', 1, "description");
insert into event (name, date, owner_id, description) values ('Event 1', '2021-01-01', 2, "description");
insert into event (name, date, owner_id, description) values ('Event 1', '2021-01-01', 3, "description");

insert into ticket (name, description, amount, event_id) values ('ticket', 'description', 100, 1);
insert into ticket (name, description, amount, event_id) values ('ticket', 'description', 100, 2);
insert into ticket (name, description, amount, event_id) values ('ticket', 'description', 100, 3);