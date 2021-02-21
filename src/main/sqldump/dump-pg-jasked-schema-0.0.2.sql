--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.25
-- Dumped by pg_dump version 11.2

-- Started on 2021-02-20 21:59:06

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 8 (class 2615 OID 56371)
-- Name: jasked; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA jasked;


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 174 (class 1259 OID 56372)
-- Name: faq_site; Type: TABLE; Schema: jasked; Owner: -
--

CREATE TABLE jasked.faq_site (
    id integer NOT NULL,
    name character varying NOT NULL,
    path character varying NOT NULL,
    status_id integer NOT NULL
);


--
-- TOC entry 2072 (class 0 OID 0)
-- Dependencies: 174
-- Name: TABLE faq_site; Type: COMMENT; Schema: jasked; Owner: -
--

COMMENT ON TABLE jasked.faq_site IS 'Site or administrative unit to which the FAQ belongs';


--
-- TOC entry 2073 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN faq_site.path; Type: COMMENT; Schema: jasked; Owner: -
--

COMMENT ON COLUMN jasked.faq_site.path IS 'Part of the URL identifying the site FAQ';


--
-- TOC entry 175 (class 1259 OID 56375)
-- Name: faq_site_id_seq; Type: SEQUENCE; Schema: jasked; Owner: -
--

CREATE SEQUENCE jasked.faq_site_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2074 (class 0 OID 0)
-- Dependencies: 175
-- Name: faq_site_id_seq; Type: SEQUENCE OWNED BY; Schema: jasked; Owner: -
--

ALTER SEQUENCE jasked.faq_site_id_seq OWNED BY jasked.faq_site.id;


--
-- TOC entry 176 (class 1259 OID 56384)
-- Name: object_status; Type: TABLE; Schema: jasked; Owner: -
--

CREATE TABLE jasked.object_status (
    id integer NOT NULL,
    name character varying NOT NULL
);


--
-- TOC entry 180 (class 1259 OID 56424)
-- Name: question; Type: TABLE; Schema: jasked; Owner: -
--

CREATE TABLE jasked.question (
    id integer NOT NULL,
    wording character varying NOT NULL,
    answer character varying NOT NULL,
    status_id integer NOT NULL,
    category_id integer NOT NULL
);


--
-- TOC entry 2075 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN question.wording; Type: COMMENT; Schema: jasked; Owner: -
--

COMMENT ON COLUMN jasked.question.wording IS 'the question itself';


--
-- TOC entry 2076 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN question.answer; Type: COMMENT; Schema: jasked; Owner: -
--

COMMENT ON COLUMN jasked.question.answer IS 'question answer';


--
-- TOC entry 178 (class 1259 OID 56404)
-- Name: question_category; Type: TABLE; Schema: jasked; Owner: -
--

CREATE TABLE jasked.question_category (
    id integer NOT NULL,
    name character varying NOT NULL,
    description character varying,
    site_id integer NOT NULL
);


--
-- TOC entry 177 (class 1259 OID 56402)
-- Name: question_category_id_seq; Type: SEQUENCE; Schema: jasked; Owner: -
--

CREATE SEQUENCE jasked.question_category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2077 (class 0 OID 0)
-- Dependencies: 177
-- Name: question_category_id_seq; Type: SEQUENCE OWNED BY; Schema: jasked; Owner: -
--

ALTER SEQUENCE jasked.question_category_id_seq OWNED BY jasked.question_category.id;


--
-- TOC entry 179 (class 1259 OID 56422)
-- Name: question_id_seq; Type: SEQUENCE; Schema: jasked; Owner: -
--

CREATE SEQUENCE jasked.question_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2078 (class 0 OID 0)
-- Dependencies: 179
-- Name: question_id_seq; Type: SEQUENCE OWNED BY; Schema: jasked; Owner: -
--

ALTER SEQUENCE jasked.question_id_seq OWNED BY jasked.question.id;


--
-- TOC entry 184 (class 1259 OID 56511)
-- Name: role; Type: TABLE; Schema: jasked; Owner: -
--

CREATE TABLE jasked.role (
    id integer NOT NULL,
    name character varying NOT NULL,
    description character varying
);


--
-- TOC entry 183 (class 1259 OID 56509)
-- Name: role_id_seq; Type: SEQUENCE; Schema: jasked; Owner: -
--

CREATE SEQUENCE jasked.role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2079 (class 0 OID 0)
-- Dependencies: 183
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: jasked; Owner: -
--

ALTER SEQUENCE jasked.role_id_seq OWNED BY jasked.role.id;


--
-- TOC entry 182 (class 1259 OID 56498)
-- Name: user; Type: TABLE; Schema: jasked; Owner: -
--

CREATE TABLE jasked."user" (
    id integer NOT NULL,
    user_name character varying NOT NULL,
    email character varying,
    first_name character varying NOT NULL,
    last_name character varying,
    password character varying NOT NULL,
    super_user boolean DEFAULT false NOT NULL
);


--
-- TOC entry 181 (class 1259 OID 56496)
-- Name: user_id_seq; Type: SEQUENCE; Schema: jasked; Owner: -
--

CREATE SEQUENCE jasked.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2080 (class 0 OID 0)
-- Dependencies: 181
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: jasked; Owner: -
--

ALTER SEQUENCE jasked.user_id_seq OWNED BY jasked."user".id;


--
-- TOC entry 185 (class 1259 OID 56522)
-- Name: user_role_faq; Type: TABLE; Schema: jasked; Owner: -
--

CREATE TABLE jasked.user_role_faq (
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    faq_site_id integer NOT NULL
);


--
-- TOC entry 1920 (class 2604 OID 56377)
-- Name: faq_site id; Type: DEFAULT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.faq_site ALTER COLUMN id SET DEFAULT nextval('jasked.faq_site_id_seq'::regclass);


--
-- TOC entry 1922 (class 2604 OID 56427)
-- Name: question id; Type: DEFAULT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question ALTER COLUMN id SET DEFAULT nextval('jasked.question_id_seq'::regclass);


--
-- TOC entry 1921 (class 2604 OID 56407)
-- Name: question_category id; Type: DEFAULT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question_category ALTER COLUMN id SET DEFAULT nextval('jasked.question_category_id_seq'::regclass);


--
-- TOC entry 1925 (class 2604 OID 56514)
-- Name: role id; Type: DEFAULT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.role ALTER COLUMN id SET DEFAULT nextval('jasked.role_id_seq'::regclass);


--
-- TOC entry 1923 (class 2604 OID 56501)
-- Name: user id; Type: DEFAULT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked."user" ALTER COLUMN id SET DEFAULT nextval('jasked.user_id_seq'::regclass);


--
-- TOC entry 2055 (class 0 OID 56372)
-- Dependencies: 174
-- Data for Name: faq_site; Type: TABLE DATA; Schema: jasked; Owner: -
--




--
-- TOC entry 2057 (class 0 OID 56384)
-- Dependencies: 176
-- Data for Name: object_status; Type: TABLE DATA; Schema: jasked; Owner: -
--

INSERT INTO jasked.object_status VALUES (1, 'private');
INSERT INTO jasked.object_status VALUES (100, 'published');


--
-- TOC entry 2061 (class 0 OID 56424)
-- Dependencies: 180
-- Data for Name: question; Type: TABLE DATA; Schema: jasked; Owner: -
--




--
-- TOC entry 2059 (class 0 OID 56404)
-- Dependencies: 178
-- Data for Name: question_category; Type: TABLE DATA; Schema: jasked; Owner: -
--




--
-- TOC entry 2065 (class 0 OID 56511)
-- Dependencies: 184
-- Data for Name: role; Type: TABLE DATA; Schema: jasked; Owner: -
--



--
-- TOC entry 2063 (class 0 OID 56498)
-- Dependencies: 182
-- Data for Name: user; Type: TABLE DATA; Schema: jasked; Owner: -
--

INSERT INTO jasked."user" VALUES (10, 'admin', 'admin@admin.jasked.com', 'Administrator', 'System', 'admin', true);


--
-- TOC entry 2066 (class 0 OID 56522)
-- Dependencies: 185
-- Data for Name: user_role_faq; Type: TABLE DATA; Schema: jasked; Owner: -
--



--
-- TOC entry 2081 (class 0 OID 0)
-- Dependencies: 175
-- Name: faq_site_id_seq; Type: SEQUENCE SET; Schema: jasked; Owner: -
--

SELECT pg_catalog.setval('jasked.faq_site_id_seq', 5, true);


--
-- TOC entry 2082 (class 0 OID 0)
-- Dependencies: 177
-- Name: question_category_id_seq; Type: SEQUENCE SET; Schema: jasked; Owner: -
--

SELECT pg_catalog.setval('jasked.question_category_id_seq', 8, true);


--
-- TOC entry 2083 (class 0 OID 0)
-- Dependencies: 179
-- Name: question_id_seq; Type: SEQUENCE SET; Schema: jasked; Owner: -
--

SELECT pg_catalog.setval('jasked.question_id_seq', 5, true);


--
-- TOC entry 2084 (class 0 OID 0)
-- Dependencies: 183
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: jasked; Owner: -
--

SELECT pg_catalog.setval('jasked.role_id_seq', 1, false);


--
-- TOC entry 2085 (class 0 OID 0)
-- Dependencies: 181
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: jasked; Owner: -
--

SELECT pg_catalog.setval('jasked.user_id_seq', 10, true);


--
-- TOC entry 1927 (class 2606 OID 56414)
-- Name: faq_site faq_site_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.faq_site
    ADD CONSTRAINT faq_site_pk PRIMARY KEY (id);


--
-- TOC entry 1929 (class 2606 OID 56391)
-- Name: object_status object_status_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.object_status
    ADD CONSTRAINT object_status_pk PRIMARY KEY (id);


--
-- TOC entry 1931 (class 2606 OID 56416)
-- Name: question_category question_category_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question_category
    ADD CONSTRAINT question_category_pk PRIMARY KEY (id);


--
-- TOC entry 1933 (class 2606 OID 56432)
-- Name: question question_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question
    ADD CONSTRAINT question_pk PRIMARY KEY (id);


--
-- TOC entry 1938 (class 2606 OID 56519)
-- Name: role role_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.role
    ADD CONSTRAINT role_pk PRIMARY KEY (id);


--
-- TOC entry 1935 (class 2606 OID 56506)
-- Name: user user_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (id);


--
-- TOC entry 1941 (class 2606 OID 56526)
-- Name: user_role_faq user_role_faq_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.user_role_faq
    ADD CONSTRAINT user_role_faq_pk PRIMARY KEY (user_id, role_id, faq_site_id);


--
-- TOC entry 1939 (class 1259 OID 64652)
-- Name: role_un_case_insensitive; Type: INDEX; Schema: jasked; Owner: -
--

CREATE UNIQUE INDEX role_un_case_insensitive ON jasked.role USING btree (upper((name)::text));


--
-- TOC entry 1936 (class 1259 OID 64651)
-- Name: user_un_case_insensitive; Type: INDEX; Schema: jasked; Owner: -
--

CREATE UNIQUE INDEX user_un_case_insensitive ON jasked."user" USING btree (upper((user_name)::text));


--
-- TOC entry 1942 (class 2606 OID 56397)
-- Name: faq_site faq_site_fk; Type: FK CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.faq_site
    ADD CONSTRAINT faq_site_fk FOREIGN KEY (status_id) REFERENCES jasked.object_status(id);


--
-- TOC entry 1943 (class 2606 OID 56438)
-- Name: question_category question_category_fk; Type: FK CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question_category
    ADD CONSTRAINT question_category_fk FOREIGN KEY (site_id) REFERENCES jasked.faq_site(id);


--
-- TOC entry 1944 (class 2606 OID 56443)
-- Name: question question_fk; Type: FK CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question
    ADD CONSTRAINT question_fk FOREIGN KEY (status_id) REFERENCES jasked.object_status(id);


--
-- TOC entry 1945 (class 2606 OID 56448)
-- Name: question question_fk_1; Type: FK CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question
    ADD CONSTRAINT question_fk_1 FOREIGN KEY (category_id) REFERENCES jasked.question_category(id);


-- Completed on 2021-02-20 21:59:06

--
-- PostgreSQL database dump complete
--

