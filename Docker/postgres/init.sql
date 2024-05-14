-- Table: public.images

-- DROP TABLE IF EXISTS public.images;

CREATE TABLE IF NOT EXISTS public.images
(
    key integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    "processInstanceKey" bigint NOT NULL,
    image bytea NOT NULL,
    CONSTRAINT images_pkey PRIMARY KEY (key)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.images
    OWNER to postgres;