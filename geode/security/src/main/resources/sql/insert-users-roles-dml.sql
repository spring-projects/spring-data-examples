INSERT INTO geode_security.users_roles (user_id, role_id) VALUES ((SELECT id FROM geode_security.users WHERE name = 'root'), (SELECT id FROM geode_security.roles WHERE name = 'ADMIN'));
INSERT INTO geode_security.users_roles (user_id, role_id) VALUES ((SELECT id FROM geode_security.users WHERE name = 'root'), (SELECT id FROM geode_security.roles WHERE name = 'DBA'));
INSERT INTO geode_security.users_roles (user_id, role_id) VALUES ((SELECT id FROM geode_security.users WHERE name = 'scientist'), (SELECT id FROM geode_security.roles WHERE name = 'DATA_SCIENTIST'));
INSERT INTO geode_security.users_roles (user_id, role_id) VALUES ((SELECT id FROM geode_security.users WHERE name = 'analyst'), (SELECT id FROM geode_security.roles WHERE name = 'DATA_ANALYST'));
INSERT INTO geode_security.users_roles (user_id, role_id) VALUES ((SELECT id FROM geode_security.users WHERE name = 'guest'), (SELECT id FROM geode_security.roles WHERE name = 'GUEST'));
