-- ТАБЛИЦА ИЗДЕЛИЙ
CREATE TABLE items (
   item  VARCHAR(8) PRIMARY KEY,
   date  DATETIME          -- дата создания каталога изделия
);

-- ТАБЛИЦА ПРИБОРОВ И ПЛАТ
CREATE TABLE units (
   item      VARCHAR(8),
   unit      VARCHAR(16),  -- децимальный номер
   title     VARCHAR(128), -- название
   iscontr   boolean     NOT NULL,
   CONSTRAINT units_pkey PRIMARY KEY (item, unit),
   CONSTRAINT units_fkey FOREIGN KEY (item) REFERENCES items(item) ON DELETE CASCADE
);

-- ТАБЛИЦЫ ТИПОНОМИНАЛОВ
CREATE DOMAIN dimensions AS VARCHAR(4) CHECK (VALUE IN ('п', 'н', 'мк', 'м', '', 'к', 'М'));

CREATE TABLE dim_values (
   dim  dimensions PRIMARY KEY,
   e    real       UNIQUE NOT NULL
);
INSERT INTO dim_values VALUES('п', 1E-12);
INSERT INTO dim_values VALUES('н', 1E-9);
INSERT INTO dim_values VALUES('мк',1E-6);
INSERT INTO dim_values VALUES('м', 1E-3);
INSERT INTO dim_values VALUES('',  1E0);
INSERT INTO dim_values VALUES('к', 1E3);
INSERT INTO dim_values VALUES('М', 1E6);

CREATE DOMAIN tolerance AS integer CHECK (VALUE IN (1, 5, 10, 20, 8020));

-- типономиналы устройств 
CREATE TABLE a_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(32)  NOT NULL,
   value   VARCHAR(32)  NOT NULL,
   spec    VARCHAR(32),
   CONSTRAINT a_uniq UNIQUE (type, value)
);

-- типономиналы конденсаторов
CREATE DOMAIN voltage AS integer CHECK (VALUE IN (10, 16, 20, 25, 32, 35, 50, 100));
CREATE DOMAIN dielectric AS VARCHAR(3) CHECK (VALUE IN ('МП0', 'Н30', 'Н90', 'ЭЛТ', 'NP0', 'C0G', 'X7R', 'X5R', 'Z5U', 'Y5V'));

CREATE DOMAIN cases AS VARCHAR(5) CHECK (VALUE IN ('2012М', '3216М', '5750М', 'A', 'B', 'C', 'D', 'E', 'X', '0603', '0805', '1206', '10x20'));

CREATE TABLE c_spec (
   type    VARCHAR(32)  PRIMARY KEY,
   spec    VARCHAR(32)  UNIQUE 
);

CREATE TABLE c_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(16)  NOT NULL,
   diel    dielectric   NOT NULL,
   volt    voltage      NOT NULL,
   size    cases,
   value   numeric(6,2) NOT NULL,
   dim     dimensions   NOT NULL,
   toler   tolerance,
   metal   boolean      NOT NULL,
   CONSTRAINT c_uniq UNIQUE (type, diel, volt, size, value, dim, toler, metal),
   CONSTRAINT c_fkey FOREIGN KEY (type) REFERENCES c_spec(type)
);

-- типономиналы микросхем
CREATE TABLE d_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(32)  UNIQUE NOT NULL,
   spec    VARCHAR(32)
);

-- типономиналы предохранителей
CREATE TABLE f_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(16)  UNIQUE NOT NULL,
   spec    VARCHAR(32)
);

-- типономиналы генераторов
CREATE TABLE g_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(16)  NOT NULL,
   freqs   VARCHAR(4)   NOT NULL,
   spec    VARCHAR(32),
   CONSTRAINT g_uniq UNIQUE (type, freqs)
);

-- типономиналы индикаторов
CREATE TABLE h_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(16)  UNIQUE NOT NULL,
   spec    VARCHAR(32)  
);

-- типономиналы индуктивностей
CREATE TABLE l_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(16)  NOT NULL,
   value   numeric(6,2),
   dim     dimensions,
   toler   tolerance,
   spec    VARCHAR(32),
   CONSTRAINT l_uniq UNIQUE (type, value, dim, toler)
);

-- типономиналы резисторов
CREATE TABLE r_spec (
   type    VARCHAR(16)  PRIMARY KEY,
   spec    VARCHAR(32)  UNIQUE 
);

CREATE TABLE r_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(16)  NOT NULL,
   power   numeric(4,3), -- опциональный параметр (для импортных не определен)
   size    cases,        -- опциональный параметр
   value   numeric(6,2) NOT NULL,
   dim     dimensions   NOT NULL,
   toler   tolerance    NOT NULL,
   CONSTRAINT r_uniq UNIQUE (type, power, value, dim, toler),
   CONSTRAINT r_fkey FOREIGN KEY (type) REFERENCES r_spec(type)
);

-- типономиналы кнопок
CREATE TABLE s_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(16)  UNIQUE NOT NULL,
   spec    VARCHAR(32)
);

-- типономиналы трансформаторов
CREATE TABLE t_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(16)  UNIQUE NOT NULL,
   spec    VARCHAR(32) 
);

-- типономиналы модулей питания 
CREATE TABLE u_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(16)  UNIQUE NOT NULL,
   spec    VARCHAR(32)  
);

-- типономиналы полупроводниковых приборов
CREATE TABLE v_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(16)  UNIQUE NOT NULL,
   spec    VARCHAR(32) 
);

-- типономиналы соединителей
CREATE DOMAIN xtypes4 AS VARCHAR(1) CHECK (VALUE IN ('P', 'S', 'K', 'G'));
CREATE TABLE x_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(32)  NOT NULL,
   xtype   xtypes4      NOT NULL,
   spec    VARCHAR(32),
   CONSTRAINT x_uniq UNIQUE (type, xtype)
);

-- типономиналы фильтров
CREATE TABLE z_types (
   id      serial       PRIMARY KEY,
   type    VARCHAR(32)  UNIQUE NOT NULL,
   spec    VARCHAR(32) 
);

-- ТАБЛИЦЫ ПРИМЕНЯЕМОСТИ
CREATE TABLE a (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT a_pkey  PRIMARY KEY (item, unit, refdes),
   CONSTRAINT a_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT a_fkey2 FOREIGN KEY (type_id) REFERENCES a_types(id)
);

CREATE TABLE c (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT c_pkey  PRIMARY KEY (item, unit, refdes),
   CONSTRAINT c_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT c_fkey2 FOREIGN KEY (type_id) REFERENCES c_types(id)
);

CREATE DOMAIN dtypes AS VARCHAR(1) CHECK (VALUE IN ('A', 'D'));
CREATE TABLE d (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   dtype   dtypes     NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT d_pkey  PRIMARY KEY (item, unit, dtype, refdes),
   CONSTRAINT d_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT d_fkey2 FOREIGN KEY (type_id) REFERENCES d_types(id)
);

CREATE TABLE f (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT f_pkey  PRIMARY KEY (item, unit, refdes),
   CONSTRAINT f_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT f_fkey2 FOREIGN KEY (type_id) REFERENCES f_types(id)
);

CREATE TABLE g (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT g_pkey  PRIMARY KEY (item, unit, refdes),
   CONSTRAINT g_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT g_fkey2 FOREIGN KEY (type_id) REFERENCES g_types(id)
);

CREATE DOMAIN htypes AS VARCHAR(1) CHECK (VALUE IN ('L'));
CREATE TABLE h (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   htype   htypes     NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT h_pkey  PRIMARY KEY (item, unit, htype, refdes),
   CONSTRAINT h_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT h_fkey2 FOREIGN KEY (type_id) REFERENCES h_types(id)
);

CREATE TABLE l (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT l_pkey  PRIMARY KEY (item, unit, refdes),
   CONSTRAINT l_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT l_fkey2 FOREIGN KEY (type_id) REFERENCES l_types(id)
);

CREATE TABLE r (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT r_pkey  PRIMARY KEY (item, unit, refdes),
   CONSTRAINT r_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT r_fkey2 FOREIGN KEY (type_id) REFERENCES r_types(id)
);

CREATE TABLE s (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT s_pkey  PRIMARY KEY (item, unit, refdes),
   CONSTRAINT s_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT s_fkey2 FOREIGN KEY (type_id) REFERENCES s_types(id)
);

CREATE TABLE t (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT t_pkey  PRIMARY KEY (item, unit, refdes),
   CONSTRAINT t_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT t_fkey2 FOREIGN KEY (type_id) REFERENCES t_types(id)
);

CREATE TABLE u (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT u_pkey  PRIMARY KEY (item, unit, refdes),
   CONSTRAINT u_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT u_fkey2 FOREIGN KEY (type_id) REFERENCES u_types(id)
);

CREATE DOMAIN vtypes AS VARCHAR(1) CHECK (VALUE IN ('D', 'T'));
CREATE TABLE v (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   vtype   vtypes     NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT v_pkey  PRIMARY KEY (item, unit, vtype, refdes),
   CONSTRAINT v_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT v_fkey2 FOREIGN KEY (type_id) REFERENCES v_types(id)
);

CREATE DOMAIN xtypes AS VARCHAR(1) CHECK (VALUE IN ('P', 'S'));
CREATE TABLE x (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   xtype   xtypes     NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT x_pkey  PRIMARY KEY (item, unit, xtype, refdes),
   CONSTRAINT x_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT x_fkey2 FOREIGN KEY (type_id) REFERENCES x_types(id)
);

CREATE TABLE z (
   item    VARCHAR(8) NOT NULL,
   unit    VARCHAR(16) NOT NULL,
   refdes  integer    NOT NULL,
   type_id integer    NOT NULL,
   CONSTRAINT z_pkey  PRIMARY KEY (item, unit, refdes),
   CONSTRAINT z_fkey1 FOREIGN KEY (item, unit) REFERENCES units(item, unit) ON DELETE CASCADE,
   CONSTRAINT z_fkey2 FOREIGN KEY (type_id) REFERENCES z_types(id)
);

CREATE VIEW allcomp AS
SELECT item, unit, concat('A',  refdes) AS refdes, type_id FROM a
UNION ALL
SELECT item, unit, concat('C',  refdes) AS refdes, type_id FROM c
UNION ALL
SELECT item, unit, concat('DA', refdes) AS refdes, type_id FROM d WHERE dtype='A'
UNION ALL
SELECT item, unit, concat('DD', refdes) AS refdes, type_id FROM d WHERE dtype='D'
UNION ALL
SELECT item, unit, concat('F',  refdes) AS refdes, type_id FROM f
UNION ALL
SELECT item, unit, concat('G',  refdes) AS refdes, type_id FROM g
UNION ALL
SELECT item, unit, concat('HL', refdes) AS refdes, type_id FROM h WHERE htype='L'
UNION ALL
SELECT item, unit, concat('L',  refdes) AS refdes, type_id FROM l
UNION ALL
SELECT item, unit, concat('R',  refdes) AS refdes, type_id FROM r
UNION ALL
SELECT item, unit, concat('S',  refdes) AS refdes, type_id FROM s
UNION ALL
SELECT item, unit, concat('T',  refdes) AS refdes, type_id FROM t
UNION ALL
SELECT item, unit, concat('U',  refdes) AS refdes, type_id FROM u
UNION ALL
SELECT item, unit, concat('VD', refdes) AS refdes, type_id FROM v WHERE vtype='D'
UNION ALL
SELECT item, unit, concat('VT', refdes) AS refdes, type_id FROM v WHERE vtype='T'
UNION ALL
SELECT item, unit, concat('XP', refdes) AS refdes, type_id FROM x WHERE xtype='P'
UNION ALL
SELECT item, unit, concat('XS', refdes) AS refdes, type_id FROM x WHERE xtype='S'
UNION ALL
SELECT item, unit, concat('Z',  refdes) AS refdes, type_id FROM z;