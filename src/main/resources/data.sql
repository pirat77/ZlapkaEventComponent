insert into location (id_string, name) values
                                                ('sdfsd-xfe', 'Bazar'),
                                                ('sdff3-23s', 'Kino');
insert into organization (id_string, name) values
                                                ('sdf3s-21s', 'Geeki'),
                                                ('sdfsd-12s', 'Transiarze');

insert into users (id_string, nick) values
                                                ('jkly7-8h6', 'Maciek'),
                                                ('fgr4g-bhu', 'Janek');
insert into event (id_string, name, description, max_participant, date, time, duration, public_event, archived,
                   category_id, location_id, organization_id, owner_id) values
                                                ('sdfsa', 'Festyn', 'fajna impra', '99', '2020-08-26', '12:12:12',
                                                 '3', 'true', 'false', 'OUTDOOR',
                                                 (SELECT id from location where name = 'Bazar'),
                                                 (SELECT id from organization where name = 'Geeki'),
                                                 (SELECT id from users where nick = 'Maciek'));


