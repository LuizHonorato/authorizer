CREATE SEQUENCE merchants_category_codes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE merchants_category_codes (
  id BIGSERIAL PRIMARY KEY,
  uuid UUID NOT NULL,
  code VARCHAR(50) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);

ALTER SEQUENCE merchants_category_codes_id_seq OWNED BY merchants_category_codes.id;

CREATE UNIQUE INDEX idx_merchants_category_codes_uuid ON merchants_category_codes (uuid);

CREATE UNIQUE INDEX idx_merchants_category_codes_code ON merchants_category_codes (code);
