CREATE TABLE IF NOT EXISTS geode_security.users_roles (
  user_id INTEGER REFERENCES geode_security.users(id),
  role_id INTEGER REFERENCES geode_security.roles(id)
);
