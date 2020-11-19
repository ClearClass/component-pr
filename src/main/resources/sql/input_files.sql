CREATE DOMAIN dirs AS VARCHAR(7) CHECK (VALUE IN ('Control', 'Units'));
CREATE DOMAIN exts AS VARCHAR(3) CHECK (VALUE IN ('sch', 'pcb'));

CREATE TABLE files (
   dir      dirs,
   ext      exts,
   filename VARCHAR(64),
   modified DATETIME NOT NULL,
   verified boolean  NOT NULL DEFAULT false,
   CONSTRAINT files_pkey PRIMARY KEY (dir, ext, filename)
);