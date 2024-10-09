CREATE SEQUENCE transactions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE transactions (
  id BIGSERIAL PRIMARY KEY,
  uuid UUID NOT NULL,
  account_id BIGINT NOT NULL,
  amount NUMERIC(19, 2) NOT NULL,
  balance_type VARCHAR(50) NOT NULL,
  response_code VARCHAR(50) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);

ALTER SEQUENCE transactions_id_seq OWNED BY transactions.id;

CREATE UNIQUE INDEX idx_transactions_uuid ON transactions (uuid);
