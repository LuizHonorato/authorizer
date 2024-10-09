INSERT INTO merchants (id, uuid, name, preferred_balance_type, fallback_balance_type, created_at, updated_at, deleted_at)
VALUES (1, '4f6677d1-7d69-4e2b-b02c-012261296582', 'Mercado do Zé', 'FOOD', 'CASH', now(), now(), null);

ALTER TABLE accounts
    ADD CONSTRAINT fk_merchant
        FOREIGN KEY (merchant_id) REFERENCES merchants(id);

INSERT INTO accounts (id, uuid, merchant_id, cash_balance, food_balance, meal_balance, created_at, updated_at, deleted_at)
VALUES (1, '0b38b235-ec9c-4828-a25f-74a228d80845', 1, 200.00, 750.00, 100.00, now(), now(), null);

INSERT INTO merchants_category_codes (id, uuid, code, created_at, updated_at, deleted_at)
VALUES (1, 'd3979d5b-f18d-4a66-ad7c-976b31632498', '5411', now(), now(), null);

INSERT INTO merchants_category_codes (id, uuid, code, created_at, updated_at, deleted_at)
VALUES (2, 'c2c6b41d-dd59-405f-9d2f-208c57c4f3f5', '5412', now(), now(), null);

INSERT INTO merchants_category_codes (id, uuid, code, created_at, updated_at, deleted_at)
VALUES (3, '941f11a0-3289-4da9-beea-765293f51ce9', '5811', now(), now(), null);

INSERT INTO merchants_category_codes (id, uuid, code, created_at, updated_at, deleted_at)
VALUES (4, '776088cb-b5f4-4afc-9a5e-803d3d9c3e21', '5812', now(), now(), null);

INSERT INTO merchants_category_codes (id, uuid, code, created_at, updated_at, deleted_at)
VALUES (5, '70c2cbd7-c16d-4640-b705-86d4a8c67a4d', '7011', now(), now(), null);

INSERT INTO merchants_category_codes (id, uuid, code, created_at, updated_at, deleted_at)
VALUES (6, 'bdd115b6-bd62-44cf-93c4-3133d9b555f5', '7012', now(), now(), null);
