CREATE TABLE IF NOT EXISTS public.accounts
(
    account_id         serial primary key NOT NULL,
    balance            double precision,
    closed             boolean,
    created_at         date,
    user_id            uuid               NOT NULL,
    user_first_name character varying(255),
    user_last_name character varying(255),
    account_type       character varying(255),
    bank_name          character varying(255),
    bik                character varying(255),
    correction_account character varying(255),
    inn                character varying(255),
    name               character varying(255),
    number             character varying(255),
    CONSTRAINT accounts_account_type_check CHECK (((account_type)::text = ANY
                                                   ((ARRAY ['SAVINGS':: character varying, 'CREDIT':: character varying, 'CHECKING':: character varying])::text[])
        ) )
);

CREATE TABLE IF NOT EXISTS public.cards
(
    card_id         serial primary key NOT NULL,
    account_id      serial             NOT NULL references accounts (account_id),
    blocked         boolean            NOT NULL,
    svv             integer,
    expiration_date timestamp(6) without time zone,
    number          character varying(255)
);

CREATE TABLE IF NOT EXISTS public.credit_accounts
(
    account_id    bigint NOT NULL references accounts (account_id),
    account_limit double precision,
    interest_rate double precision
);

CREATE TABLE IF NOT EXISTS public.savings_accounts
(
    rate       double precision,
    account_id bigint NOT NULL references accounts (account_id)
);

CREATE TABLE IF NOT EXISTS public.operation_categories
(
    category_id serial primary key NOT NULL,
    name        character varying(255)
);

CREATE TABLE IF NOT EXISTS public.operations
(
    operation_id serial primary key NOT NULL,
    amount       double precision,
    account_id   bigint             NOT NULL references accounts (account_id),
    category_id  bigint             NOT NULL references operation_categories (category_id),
    created_at   timestamp(6) without time zone,
    status       character varying(255),
    type         character varying(255),
    extra_fields jsonb,
    CONSTRAINT operations_status_check CHECK (((status)::text = ANY
                                               ((ARRAY ['SUCCESS':: character varying, 'FAILED':: character varying])::text[])
        ) ),
    CONSTRAINT operations_type_check CHECK (((type)::text = ANY
                                             ((ARRAY ['EXPENSE'::character varying, 'RECEIPT'::character varying])::text[])))
);

CREATE TABLE IF NOT EXISTS public.payment_infos
(
    payment_info_id    serial primary key NOT NULL,
    category_id        bigint unique      NOT NULL references operation_categories (category_id),
    fields             jsonb,
    bank_name          character varying(255),
    bik                character varying(255),
    correction_account character varying(255),
    inn                character varying(255),
    number             character varying(255),
    minAmount          double precision
);

