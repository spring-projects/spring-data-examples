CREATE EXTENSION IF NOT EXISTS vector;

DROP TABLE IF EXISTS jpa_comment;

DROP SEQUENCE IF EXISTS jpa_comment_seq;

CREATE TABLE IF NOT EXISTS jpa_comment (id bigserial PRIMARY KEY, country varchar(10), description varchar(20), embedding vector(5));

CREATE SEQUENCE jpa_comment_seq INCREMENT 50;

CREATE INDEX ON jpa_comment USING hnsw (embedding vector_l2_ops);
