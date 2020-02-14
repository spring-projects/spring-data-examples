CREATE TABLE IF NOT EXISTS geode_security.roles_permissions (
  role_id INTEGER REFERENCES geode_security.roles(id),
  resource VARCHAR(32) NOT NUll,
  operation VARCHAR(32) NOT NULL,
  region_name VARCHAR(255),
  key_name VARCHAR(255)
);
