CREATE TABLE specs (
   type VARCHAR(128),
   spec VARCHAR(128),
   num  integer NOT NULL,
   name VARCHAR(128) NOT NULL,
   CONSTRAINT specs_pkey PRIMARY KEY (type, spec)
);

INSERT INTO specs VALUES('106ЛБ1', 'бК0.347.082 ТУ1', 1, 'Микросхема');
INSERT INTO specs VALUES('106ЛБ1А', 'бК0.347.082 ТУ1', 1, 'Микросхема');