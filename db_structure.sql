
\c postgres
DROP DATABASE IF EXISTS ayd1_prac1;
CREATE DATABASE ayd1_prac1;
\c ayd1_prac1

-- === ENUMS ===
CREATE TYPE user_role_t AS ENUM ('ADMIN','EMPLEADO','ESPECIALISTA','CLIENTE','PROVEEDOR');
CREATE TYPE twofa_method_t AS ENUM ('EMAIL','SMS');
CREATE TYPE log_type_t AS ENUM ('avance', 'diagnostico','observación','síntoma detectado', 
  'imprevisto', 'reportar daño adicional', 'solicitud de servicio adicional', 'solicitud de especialista', 
  'prueba tecnica', 'sugerencia', 'comentario', 'aprobacion');
CREATE TYPE job_status_t AS ENUM ('borrador', 'pendiente', 'autorizado', 'en espera', 'en curso',
  'necesita especialista', 'cancelado', 'completado', 'cerrado');
CREATE TYPE task_status_t AS ENUM ('pendiente','iniciado','en progreso','completado','cancelado');
CREATE TYPE invoice_status_t AS ENUM ('borrador','emitido', 'parcial', 'pagado', 'cancelado', 'reembolsado');
CREATE TYPE payment_method_t AS ENUM ('efectivo','tarjeta','transferencia','otro');
CREATE TYPE purchase_status_t AS ENUM ('borrador','ordenado','recibido','cancelado');

CREATE TABLE users (
  id serial PRIMARY KEY,
  role user_role_t NOT NULL DEFAULT 'CLIENTE',
  email varchar(255) UNIQUE,
  nit integer UNIQUE,
  name varchar(255) NOT NULL,
  address text,
  phone_number varchar(30),
  password_hash text NOT NULL,
  is_active boolean DEFAULT true,
  twofa_method twofa_method_t DEFAULT 'EMAIL',
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- 2FA y códigos temporales
CREATE TABLE mfa_codes (
  id SERIAL PRIMARY KEY,
  user_id integer NOT NULL REFERENCES users(id),
  code varchar(10) NOT NULL,
  expires_at timestamp NOT NULL,
  is_used boolean DEFAULT false,
  twofa_method twofa_method_t NOT NULL,
  created_at timestamp DEFAULT now()
);

-- === Vehículos ===
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

CREATE TABLE specialties (
  id serial PRIMARY KEY,
  name varchar(300) NOT NULL UNIQUE,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- === Tipos de servicio / mantenimiento ===
CREATE TABLE service_types (
  id serial PRIMARY KEY,
  specialty_id integer REFERENCES specialties(id),
  name varchar(200) NOT NULL,
  description text,
  price numeric(12,2) DEFAULT 0,
  estimated_time interval,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- especializaciones (qué servicios puede hacer cada empleado/especialista)
CREATE TABLE employee_specializations (
  user_id integer REFERENCES users(id),
  specialty_id integer REFERENCES specialties(id),
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now(),
  PRIMARY KEY (user_id, specialty_id)
);

-- === Inventario / repuestos ===
CREATE TABLE parts (
  id serial PRIMARY KEY,
  code varchar(100) UNIQUE,
  name varchar(255) NOT NULL,
  brand varchar(120),
  description text,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

CREATE TABLE parts_catalogs (
  id serial PRIMARY KEY,
  supplier_id integer NOT NULL REFERENCES users(id),
  part_id integer NOT NULL REFERENCES parts(id),
  price numeric(12, 2),
  stock integer,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now(),
  UNIQUE(supplier_id, part_id)
);

CREATE TABLE purchase_orders (
  id serial PRIMARY KEY,
  supplier_id integer REFERENCES users(id),
  status purchase_status_t DEFAULT 'borrador',
  description text,
  total numeric(14,2) DEFAULT 0,
  order_date timestamp DEFAULT now(),
  delivery_date date,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

CREATE TABLE purchase_order_items (
  purchase_order_id integer NOT NULL REFERENCES purchase_orders(id),
  part_id serial NOT NULL REFERENCES parts(id),
  unit_price numeric(12,2) NOT NULL,
  amount numeric(12,3) NOT NULL,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now(),
  PRIMARY KEY (purchase_order_id, part_id)
);

-- === Trabajos ===
CREATE TABLE jobs (
  id serial PRIMARY KEY,
  vehicle_id integer NOT NULL REFERENCES vehicles(id),
  description text,
  status job_status_t DEFAULT 'pendiente',
  authorized_at timestamp,
  estimated_time INTERVAL,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- un trabajo puede tener varios empleados/especialistas asignados
CREATE TABLE job_assignments (
  job_id integer NOT NULL REFERENCES jobs(id),
  user_id integer REFERENCES users(id),
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now(),
  PRIMARY KEY(job_id, user_id)
);

CREATE TABLE job_tasks (
  id serial PRIMARY KEY,
  job_id integer NOT NULL REFERENCES jobs(id),
  service_id integer REFERENCES service_types(id),
  status task_status_t DEFAULT 'pendiente',
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

CREATE TABLE job_logs (
  id serial PRIMARY KEY,
  job_id integer NOT NULL REFERENCES jobs(id),
  user_id integer REFERENCES users(id),
  ocurred_at timestamp DEFAULT now(),
  description text,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

-- Repuestos usados en un trabajo
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
CREATE TABLE invoices (
  id serial PRIMARY KEY,
  job_id integer REFERENCES jobs(id),
  client_id integer REFERENCES users(id),
  issued_by integer REFERENCES users(id),
  issued_at timestamp DEFAULT now(),
  total numeric(14,2) DEFAULT 0,
  status invoice_status_t DEFAULT 'borrador',
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

CREATE TABLE invoice_items (
  id serial PRIMARY KEY,
  invoice_id integer NOT NULL REFERENCES invoices(id),
  description text,
  unit_price numeric(12,2) DEFAULT 0,
  amount numeric(12,3) DEFAULT 1,
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

CREATE TABLE payments (
  id serial PRIMARY KEY,
  invoice_id integer REFERENCES invoices(id) ,
  amount numeric(14,2) NOT NULL,
  method payment_method_t DEFAULT 'efectivo',
  paid_at timestamp DEFAULT now(),
  created_at timestamp DEFAULT now()
);

create table session(
	token varchar(50) primary key,
	user_id integer not null references users(id),
	expired_at timestamp not null
);
-- === Datos quemados ===
-- INSERT INTO users (username, email, password_hash, role, requires_2fa, twofa_method) VALUES ('admin', 'admin@taller.mec', 'hash_admin', 'admin', true, 'email');
