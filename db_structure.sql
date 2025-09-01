DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
  id serial PRIMARY KEY,
  role varchar(20) NOT NULL DEFAULT 'CLIENTE',
  email varchar(255) UNIQUE,
  nit integer UNIQUE,
  name varchar(255) NOT NULL,
  address text,
  phone_number varchar(30),
  password_hash text NOT NULL,
  is_active boolean DEFAULT true,
  twofa_method varchar(20) DEFAULT 'EMAIL',
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- 2FA y códigos temporales
DROP TABLE IF EXISTS mfa_codes CASCADE;
CREATE TABLE mfa_codes (
  id SERIAL PRIMARY KEY,
  user_id integer NOT NULL REFERENCES users(id),
  code varchar(10) NOT NULL,
  expires_at timestamp NOT NULL,
  is_used boolean DEFAULT false,
  twofa_method varchar(20) NOT NULL,
  created_at timestamp DEFAULT now()
);

-- === Vehículos ===
DROP TABLE IF EXISTS vehicles CASCADE;
CREATE TABLE vehicles (
  id serial PRIMARY KEY,
  owner_id integer NOT NULL REFERENCES users(id),
  plate varchar(30) UNIQUE,
  vin varchar(50) UNIQUE,
  model varchar(100),
  year smallint,
  brand varchar(100),
  color varchar(50),
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

DROP TABLE IF EXISTS specialties CASCADE;
CREATE TABLE specialties (
  id serial PRIMARY KEY,
  name varchar(300) NOT NULL UNIQUE,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- === Tipos de servicio / mantenimiento ===
DROP TABLE IF EXISTS service_types CASCADE;
CREATE TABLE service_types (
  id serial PRIMARY KEY,
  specialty_id integer REFERENCES specialties(id),
  name varchar(200) NOT NULL,
  description text,
  price numeric(12,2) DEFAULT 0,
  estimated_time numeric(12,2),  -- cambiado de INTERVAL a numeric
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- especializaciones (qué servicios puede hacer cada empleado/especialista)
DROP TABLE IF EXISTS employee_specializations CASCADE;
CREATE TABLE employee_specializations (
  user_id integer REFERENCES users(id),
  specialty_id integer REFERENCES specialties(id),
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now(),
  PRIMARY KEY (user_id, specialty_id)
);

-- === Inventario / repuestos ===
DROP TABLE IF EXISTS parts CASCADE;
CREATE TABLE parts (
  id serial PRIMARY KEY,
  code varchar(100) UNIQUE,
  name varchar(255) NOT NULL,
  brand varchar(120),
  description text,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

DROP TABLE IF EXISTS parts_catalogs CASCADE;
CREATE TABLE parts_catalogs (
  id serial PRIMARY KEY,
  supplier_id integer REFERENCES users(id),
  part_id integer NOT NULL REFERENCES parts(id),
  price numeric(12, 2),
  stock integer,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now(),
  UNIQUE(supplier_id, part_id)
);

DROP TABLE IF EXISTS purchase_orders CASCADE;
CREATE TABLE purchase_orders (
  id serial PRIMARY KEY,
  supplier_id integer REFERENCES users(id),
  status varchar(20) DEFAULT 'ORDENADO',
  description text,
  total numeric(14,2) DEFAULT 0,
  order_date timestamp DEFAULT now(),
  delivery_date date,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

DROP TABLE IF EXISTS purchase_order_items CASCADE;
CREATE TABLE purchase_order_items (
  purchase_order_id integer NOT NULL REFERENCES purchase_orders(id),
  part_id serial NOT NULL REFERENCES parts(id),
  unit_price numeric(12,2) NOT NULL,
  amount integer NOT NULL,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now(),
  PRIMARY KEY (purchase_order_id, part_id)
);

-- === Trabajos ===
DROP TABLE IF EXISTS jobs CASCADE;
CREATE TABLE jobs (
  id serial PRIMARY KEY,
  vehicle_id integer NOT NULL REFERENCES vehicles(id),
  description text,
  status varchar(50) DEFAULT 'PENDIENTE',
  authorized_at timestamp,
  estimated_time numeric(12,2),  -- cambiado de INTERVAL a numeric
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- un trabajo puede tener varios empleados/especialistas asignados
DROP TABLE IF EXISTS job_assignments CASCADE;
CREATE TABLE job_assignments (
  job_id integer NOT NULL REFERENCES jobs(id),
  user_id integer REFERENCES users(id),
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now(),
  PRIMARY KEY(job_id, user_id)
);

DROP TABLE IF EXISTS job_tasks CASCADE;
CREATE TABLE job_tasks (
  id serial PRIMARY KEY,
  job_id integer NOT NULL REFERENCES jobs(id),
  service_id integer REFERENCES service_types(id),
  status varchar(20) DEFAULT 'PENDIENTE',
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

DROP TABLE IF EXISTS job_logs CASCADE;
CREATE TABLE job_logs (
  id serial PRIMARY KEY,
  job_id integer NOT NULL REFERENCES jobs(id),
  user_id integer REFERENCES users(id),
  log_type varchar(50) DEFAULT 'OBSERVACION',
  occurred_at timestamp DEFAULT now(),
  description text,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- Repuestos usados en un trabajo
DROP TABLE IF EXISTS job_parts CASCADE;
CREATE TABLE job_parts (
  id serial PRIMARY KEY,
  job_id integer NOT NULL REFERENCES jobs(id),
  part_id integer NOT NULL REFERENCES parts(id),
  unit_price numeric(12,2) NOT NULL,
  amount integer NOT NULL,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- === Facturación y pagos ===
DROP TABLE IF EXISTS invoices CASCADE;
CREATE TABLE invoices (
  id serial PRIMARY KEY,
  job_id integer REFERENCES jobs(id),
  client_id integer REFERENCES users(id),
  issued_by integer REFERENCES users(id),
  issued_at timestamp DEFAULT now(),
  total numeric(14,2) DEFAULT 0,
  status varchar(20) DEFAULT 'EMITIDO',
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

DROP TABLE IF EXISTS invoice_items CASCADE;
CREATE TABLE invoice_items (
  id serial PRIMARY KEY,
  invoice_id integer NOT NULL REFERENCES invoices(id),
  description text,
  unit_price numeric(12,2) DEFAULT 0,
  amount numeric(12,3) DEFAULT 1,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

DROP TABLE IF EXISTS payments CASCADE;
CREATE TABLE payments (
  id serial PRIMARY KEY,
  invoice_id integer REFERENCES invoices(id),
  amount numeric(14,2) NOT NULL,
  method varchar(20) DEFAULT 'EFECTIVO',
  paid_at timestamp DEFAULT now(),
  created_at timestamp DEFAULT now()
);

DROP TABLE IF EXISTS session CASCADE;
CREATE TABLE session(
  token varchar(50) primary key,
  user_id integer not null references users(id),
  expired_at timestamp not null
);
