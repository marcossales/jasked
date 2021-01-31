--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.25
-- Dumped by pg_dump version 11.2

-- Started on 2021-01-31 11:00:12

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
-- TOC entry 2038 (class 0 OID 0)
-- Dependencies: 174
-- Name: TABLE faq_site; Type: COMMENT; Schema: jasked; Owner: -
--

COMMENT ON TABLE jasked.faq_site IS 'Site or administrative unit to which the FAQ belongs';


--
-- TOC entry 2039 (class 0 OID 0)
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
-- TOC entry 2040 (class 0 OID 0)
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
-- TOC entry 2041 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN question.wording; Type: COMMENT; Schema: jasked; Owner: -
--

COMMENT ON COLUMN jasked.question.wording IS 'the question itself';


--
-- TOC entry 2042 (class 0 OID 0)
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
-- TOC entry 2043 (class 0 OID 0)
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
-- TOC entry 2044 (class 0 OID 0)
-- Dependencies: 179
-- Name: question_id_seq; Type: SEQUENCE OWNED BY; Schema: jasked; Owner: -
--

ALTER SEQUENCE jasked.question_id_seq OWNED BY jasked.question.id;


--
-- TOC entry 1902 (class 2604 OID 56377)
-- Name: faq_site id; Type: DEFAULT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.faq_site ALTER COLUMN id SET DEFAULT nextval('jasked.faq_site_id_seq'::regclass);


--
-- TOC entry 1904 (class 2604 OID 56427)
-- Name: question id; Type: DEFAULT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question ALTER COLUMN id SET DEFAULT nextval('jasked.question_id_seq'::regclass);


--
-- TOC entry 1903 (class 2604 OID 56407)
-- Name: question_category id; Type: DEFAULT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question_category ALTER COLUMN id SET DEFAULT nextval('jasked.question_category_id_seq'::regclass);


--
-- TOC entry 2026 (class 0 OID 56372)
-- Dependencies: 174
-- Data for Name: faq_site; Type: TABLE DATA; Schema: jasked; Owner: -
--



--
-- TOC entry 2028 (class 0 OID 56384)
-- Dependencies: 176
-- Data for Name: object_status; Type: TABLE DATA; Schema: jasked; Owner: -
--

INSERT INTO jasked.object_status VALUES (1, 'private');
INSERT INTO jasked.object_status VALUES (100, 'published');


--
-- TOC entry 2032 (class 0 OID 56424)
-- Dependencies: 180
-- Data for Name: question; Type: TABLE DATA; Schema: jasked; Owner: -
--



--
-- TOC entry 2030 (class 0 OID 56404)
-- Dependencies: 178
-- Data for Name: question_category; Type: TABLE DATA; Schema: jasked; Owner: -
--



--
-- TOC entry 2045 (class 0 OID 0)
-- Dependencies: 175
-- Name: faq_site_id_seq; Type: SEQUENCE SET; Schema: jasked; Owner: -
--

SELECT pg_catalog.setval('jasked.faq_site_id_seq', 1, false);


--
-- TOC entry 2046 (class 0 OID 0)
-- Dependencies: 177
-- Name: question_category_id_seq; Type: SEQUENCE SET; Schema: jasked; Owner: -
--

SELECT pg_catalog.setval('jasked.question_category_id_seq', 1, false);


--
-- TOC entry 2047 (class 0 OID 0)
-- Dependencies: 179
-- Name: question_id_seq; Type: SEQUENCE SET; Schema: jasked; Owner: -
--

SELECT pg_catalog.setval('jasked.question_id_seq', 1, false);


--
-- TOC entry 1906 (class 2606 OID 56414)
-- Name: faq_site faq_site_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.faq_site
    ADD CONSTRAINT faq_site_pk PRIMARY KEY (id);


--
-- TOC entry 1908 (class 2606 OID 56391)
-- Name: object_status object_status_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.object_status
    ADD CONSTRAINT object_status_pk PRIMARY KEY (id);


--
-- TOC entry 1910 (class 2606 OID 56416)
-- Name: question_category question_category_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question_category
    ADD CONSTRAINT question_category_pk PRIMARY KEY (id);


--
-- TOC entry 1912 (class 2606 OID 56432)
-- Name: question question_pk; Type: CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question
    ADD CONSTRAINT question_pk PRIMARY KEY (id);


--
-- TOC entry 1913 (class 2606 OID 56397)
-- Name: faq_site faq_site_fk; Type: FK CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.faq_site
    ADD CONSTRAINT faq_site_fk FOREIGN KEY (status_id) REFERENCES jasked.object_status(id);


--
-- TOC entry 1914 (class 2606 OID 56438)
-- Name: question_category question_category_fk; Type: FK CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question_category
    ADD CONSTRAINT question_category_fk FOREIGN KEY (site_id) REFERENCES jasked.faq_site(id);


--
-- TOC entry 1915 (class 2606 OID 56443)
-- Name: question question_fk; Type: FK CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question
    ADD CONSTRAINT question_fk FOREIGN KEY (status_id) REFERENCES jasked.object_status(id);


--
-- TOC entry 1916 (class 2606 OID 56448)
-- Name: question question_fk_1; Type: FK CONSTRAINT; Schema: jasked; Owner: -
--

ALTER TABLE ONLY jasked.question
    ADD CONSTRAINT question_fk_1 FOREIGN KEY (category_id) REFERENCES jasked.question_category(id);


-- Completed on 2021-01-31 11:00:21

--
-- PostgreSQL database dump complete
--


-- Create the app user of the schema
-- Change password in production!!!!!
CREATE ROLE jasked_app NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN PASSWORD 'jasked_app'; 
GRANT USAGE ON SCHEMA jasked TO jasked_app;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA jasked TO jasked_app;
GRANT SELECT,USAGE ON ALL SEQUENCES IN SCHEMA jasked TO jasked_app;
