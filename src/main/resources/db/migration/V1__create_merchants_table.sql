CREATE SEQUENCE merchants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE merchants (
   id BIGSERIAL PRIMARY KEY,
   uuid UUID NOT NULL,
   name VARCHAR(255) NOT NULL,
   preferred_balance_type VARCHAR(50) NOT NULL,
   fallback_balance_type VARCHAR(50) NOT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP,
   deleted_at TIMESTAMP
);

ALTER SEQUENCE merchants_id_seq OWNED BY merchants.id;

CREATE UNIQUE INDEX idx_merchants_uuid ON merchants (uuid);