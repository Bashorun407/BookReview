CREATE TABLE author
(
    id bigserial NOT NULL,
    genre character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    profile_picture character varying(255) COLLATE pg_default."default",
    first_name character varying(255) COLLATE pg_default."default",
    last_name character varying(255) COLLATE pg_default."default",
    date_of_birth character varying(255) COLLATE pg_default."default",
    phone_number character varying(255) COLLATE pg_default."default",
    username character varying(255) COLLATE pg_default."default",
    email character varying(255) COLLATE pg_default."default",
    password character varying(255) COLLATE pg_default."default",
        private UserType userType;
        private boolean isProfileComplete;
        private Boolean activeStatus;
    CONSTRAINT author_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE author
    OWNER to postgres;