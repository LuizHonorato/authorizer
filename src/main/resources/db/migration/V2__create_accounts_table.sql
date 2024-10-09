CREATE SEQUENCE accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE accounts (
      id BIGSERIAL PRIMARY KEY,
      uuid UUID NOT NULL,
      merchant_id BIGINT NOT NULL,
      food_balance NUMERIC(19, 2) NOT NULL,
      meal_balance NUMERIC(19, 2) NOT NULL,
      cash_balance NUMERIC(19, 2) NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP,
      deleted_at TIMESTAMP
);

ALTER SEQUENCE accounts_id_seq OWNED BY accounts.id;

CREATE UNIQUE INDEX idx_accounts_uuid ON accounts (uuid);
