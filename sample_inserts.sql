
-- ==========================
-- USERS
-- ==========================

-- No se puede iniciar sesión, ya que los correos son ficticios
-- Reemplazar alguno por un correo válido para poder iniciar sesión
INSERT INTO users (role, email, nit, name, address, phone_number, password_hash) VALUES
    -- hashed password: 1234
    ('EMPLEADO', 'empleado1@taller.com', 10001, 'Juan Pérez', 'Zona 1, Ciudad', '5551-0001', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('EMPLEADO', 'empleado2@taller.com', 10002, 'María López', 'Zona 2, Ciudad', '5551-0002', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('EMPLEADO', 'empleado3@taller.com', 10003, 'Carlos Gómez', 'Zona 3, Ciudad', '5551-0003', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('EMPLEADO', 'empleado4@taller.com', 10004, 'Ana Rodríguez', 'Zona 4, Ciudad', '5551-0004', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('EMPLEADO', 'empleado5@taller.com', 10005, 'Pedro Pérez', 'Zona 5, Ciudad', '5551-0005', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),

    ('ESPECIALISTA', 'especialista1@taller.com', 20001, 'Luis Fernández', 'Zona 5, Ciudad', '5552-0001', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('ESPECIALISTA', 'especialista2@taller.com', 20002, 'Sofía Morales', 'Zona 6, Ciudad', '5552-0002', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('ESPECIALISTA', 'especialista3@taller.com', 20003, 'Lorenzo López', 'Zona 7, Ciudad', '555-0003', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),

    ('CLIENTE', 'cliente1@mail.com', 30001, 'Pedro Castillo', 'Zona 7, Ciudad', '5553-0001', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente2@mail.com', 30002, 'Laura Méndez', 'Zona 7, Ciudad', '5553-0002', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente3@mail.com', 30003, 'José Martínez', 'Zona 8, Ciudad', '5553-0003', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente4@mail.com', 30004, 'Gabriela Herrera', 'Zona 9, Ciudad', '5553-0004', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente5@mail.com', 30005, 'Andrés Ramírez', 'Zona 10, Ciudad', '5553-0005', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente6@mail.com', 30006, 'Mónica Díaz', 'Zona 11, Ciudad', '5553-0006', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente7@mail.com', 30007, 'Fernando Ruiz', 'Zona 12, Ciudad', '5553-0007', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente8@mail.com', 30008, 'Claudia Ortega', 'Zona 13, Ciudad', '5553-0008', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente9@mail.com', 30009, 'Ricardo Morales', 'Zona 14, Ciudad', '5553-0009', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente10@mail.com', 30010, 'Daniela Flores', 'Zona 15, Ciudad', '5553-0010', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente11@mail.com', 30011, 'Cliente Ramírez', 'Zona 1', '5553-0011', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('CLIENTE', 'cliente12@mail.com', 30012, 'Cliente González', 'Zona 2', '5553-0012', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),

    ('PROVEEDOR', 'proveedor1@mail.com', 4001, 'Proveedor Martínez', 'Zona Industrial 1', '5554-0001', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('PROVEEDOR', 'proveedor2@mail.com', 4002, 'Proveedor Gonzales', 'Zona Industrial 2', '5554-0002', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),
    ('PROVEEDOR', 'proveedor3@mail.com', 4003, 'Proveedor Gutierrez', 'Zona Industrial 3', '5554-0003', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG'),

    ('ADMIN', 'admin1@taller.com', 5001, 'Admin', 'Zona administrativa 1', '5555-0001', '$2a$10$mOHQ8HDg0LGBIfdpvN8np.k4Kq0QxDw7vJV9.wwK7pvaLQs9I3zbG');



-- ==========================
-- VEHICLES
-- ==========================

INSERT INTO vehicles (owner_id, plate, vin, model, year, brand, color) VALUES
    (9, 'P005EEE', 'VIN005', 'Accord', 2020, 'Honda', 'Plata'),
    (10, 'P006FFF', 'VIN006', 'Yaris', 2015, 'Toyota', 'Gris'),
    (10, 'P003CCC', 'VIN003', 'Mazda 3', 2019, 'Mazda', 'Negro'),
    (11, 'P007GGG', 'VIN007', 'Altima', 2018, 'Nissan', 'Negro'),
    (12, 'P008HHH', 'VIN008', 'CX-5', 2021, 'Mazda', 'Rojo'),
    (12, 'P004DDD', 'VIN004', 'Sentra', 2016, 'Nissan', 'Azul'),
    (13, 'P009III', 'VIN009', 'Hilux', 2017, 'Toyota', 'Blanco'),
    (13, 'P123ABC', 'VIN021', 'Corolla', 2018, 'Toyota', 'Blanco'),
    (14, 'P010JJJ', 'VIN010', 'CR-V', 2019, 'Honda', 'Verde'),
    (15, 'P011KKK', 'VIN011', 'Kicks', 2020, 'Nissan', 'Azul'),
    (15, 'P456DEF', 'VIN023', 'Civic', 2020, 'Honda', 'Negro'),
    (16, 'P012LLL', 'VIN012', 'Mazda 6', 2016, 'Mazda', 'Gris'),
    (17, 'P013MMM', 'VIN013', 'Civic', 2015, 'Honda', 'Negro'),
    (17, 'P014NNN', 'VIN014', 'Rav4', 2021, 'Toyota', 'Rojo'),
    (18, 'P015OOO', 'VIN015', 'X-Trail', 2022, 'Nissan', 'Blanco'),
    (18, 'P789GHI', 'VIN123', 'Model 3', 2021, 'Tesla', 'Rojo'),
    (19, 'P002BBB', 'VIN002', 'Corolla', 2017, 'Toyota', 'Blanco'),
    (20, 'P001AAA', 'VIN001', 'Civic', 2018, 'Honda', 'Rojo');



-- ==========================
-- SPECIALTIES & SERVICES
-- ==========================

INSERT INTO specialties (name) VALUES
    ('Transmisión'),
    ('Frenos'),
    ('Motor'),
    ('Dirección'),
    ('Suspensión'),
    ('Electrónica'),
    ('Montaje'),
    ('Pintura');

INSERT INTO employee_specializations (user_id, specialty_id) VALUES
    -- Especialista 1 en Transmisión y motor
    (6, 1),
    (6, 3),
    -- Especialista 2 en Frenos y suspensión
    (7, 2),
    (7, 5),
    -- Especialista 3 en Electrónica
    (8, 6),

    -- Empleados
    (1, 4), -- Empleado 1: Dirección
    (2, 7), -- Empleado 2: Montaje
    (3, 6), -- Empleado 3: Montaje
    (4, 8); -- Empleado 4: Pintura

INSERT INTO service_types (specialty_id, name, description, price, estimated_time) VALUES
    (1, 'Revisión de transmisión', 'Inspección y ajuste de transmisión', 500.00, 2 ),
    (1, 'Cambio de aceite de transmisión', 'Reemplazo de fluido de transmisión', 800.00, 3),
    (2, 'Revisión de frenos', 'Inspección de pastillas y discos', 300.00, 1),
    (2, 'Cambio de pastillas de freno', 'Sustitución de pastillas de freno', 600.00, 2),
    (3, 'Diagnóstico de motor', 'Revisión general de motor con escáner', 400.00, 1),
    (3, 'Sustitución de piezas de motor', 'Cambio de piezas dañadas en el motor', 1500.00, 5),
    (4, 'Revisión de dirección', 'Inspección de sistema de dirección', 350.00, 1),
    (4, 'Alineación de dirección', 'Alineación completa de ruedas', 700.00, 2),
    (5, 'Revisión de suspensión', 'Chequeo de amortiguadores y resortes', 300.00, 1),
    (5, 'Cambio de amortiguadores', 'Reemplazo de amortiguadores', 1200.00, 4),
    (6, 'Diagnóstico electrónico', 'Chequeo de sensores y computadora del vehículo', 450.00, 1),
    (6, 'Reparación de cableado', 'Reemplazo y reparación de conexiones eléctricas', 900.00, 3),
    (7, 'Montaje de llantas', 'Instalación de nuevas llantas', 200.00, 1),
    (7, 'Balanceo de llantas', 'Balanceo de ruedas para estabilidad', 250.00, 1),
    (8, 'Pintura general', 'Pintura completa del vehículo', 4000.00, 72),
    (8, 'Retoque de pintura', 'Reparación de rayones y pequeños daños', 800.00, 6);


-- ==========================
-- PARTS
-- ==========================
INSERT INTO parts (code, name, brand, description) VALUES
    ('P-001', 'Filtro de aceite', 'Bosch', 'Filtro de aceite para motor 1.6L'),
    ('P-002', 'Filtro de aire', 'Mann', 'Filtro de aire estándar'),
    ('P-003', 'Filtro de combustible', 'ACDelco', 'Filtro de gasolina inyección'),
    ('P-004', 'Bujía de encendido', 'NGK', 'Bujía estándar cobre'),
    ('P-005', 'Pastillas de freno delanteras', 'Brembo', 'Juego de pastillas delanteras'),
    ('P-006', 'Disco de freno', 'Bosch', 'Disco ventilado 280mm'),
    ('P-007', 'Amortiguador delantero', 'Monroe', 'Amortiguador hidráulico delantero'),
    ('P-008', 'Amortiguador trasero', 'KYB', 'Amortiguador de gas trasero'),
    ('P-009', 'Correa de distribución', 'Gates', 'Correa dentada reforzada'),
    ('P-010', 'Bomba de agua', 'Dayco', 'Bomba de agua estándar'),
    ('P-011', 'Radiador', 'Denso', 'Radiador aluminio 2 filas'),
    ('P-012', 'Alternador', 'Valeo', 'Alternador 90A'),
    ('P-013', 'Batería 12V 60Ah', 'LTH', 'Batería libre de mantenimiento'),
    ('P-014', 'Sensor de oxígeno', 'Bosch', 'Sensor lambda 4 cables'),
    ('P-015', 'Bobina de encendido', 'Delphi', 'Bobina electrónica compacta'),
    ('P-016', 'Kit de embrague', 'Sachs', 'Disco, prensa y balero'),
    ('P-017', 'Espejo retrovisor izquierdo', 'Magneti Marelli', 'Espejo manual'),
    ('P-018', 'Lámpara halógena H4', 'Philips', 'Bombillo 12V 60/55W'),
    ('P-019', 'Aceite sintético 5W30', 'Mobil', 'Aceite motor sintético 1L'),
    ('P-020', 'Balero rueda delantera', 'SKF', 'Rodamiento de rueda con sello');


-- ==========================
-- CATALOGS
-- ==========================

-- Proveedor 1
INSERT INTO parts_catalogs (supplier_id, part_id, price, stock) VALUES
    (21, 1, 65.50, 30),
    (21, 2, 80.00, 25),
    (21, 3, 120.00, 15),
    (21, 5, 340.00, 10),
    (21, 7, 450.00, 12),
    (21, 9, 500.00, 8),
    (21, 13, 750.00, 5),
    (21, 19, 95.00, 50);

-- Proveedor 2
INSERT INTO parts_catalogs (supplier_id, part_id, price, stock) VALUES
    (22, 4, 35.00, 100),
    (22, 6, 280.00, 20),
    (22, 8, 460.00, 18),
    (22, 10, 600.00, 9),
    (22, 11, 850.00, 6),
    (22, 14, 420.00, 14),
    (22, 15, 520.00, 7),
    (22, 20, 250.00, 10);

-- Proveedor 3
INSERT INTO parts_catalogs (supplier_id, part_id, price, stock) VALUES
    (23, 12, 980.00, 4),
    (23, 16, 1150.00, 3),
    (23, 17, 220.00, 6),
    (23, 18, 45.00, 40),
    (23, 2, 75.00, 30),
    (23, 5, 330.00, 12),
    (23, 7, 460.00, 9),
    (23, 19, 100.00, 45);

-- Catálogo propio del taller (supplier_id NULL)
INSERT INTO parts_catalogs (supplier_id, part_id, price, stock) VALUES
    (NULL, 1, 70.00, 20),
    (NULL, 4, 40.00, 50),
    (NULL, 6, 300.00, 10),
    (NULL, 8, 480.00, 8),
    (NULL, 9, 510.00, 6),
    (NULL, 11, 890.00, 4),
    (NULL, 13, 770.00, 6),
    (NULL, 14, 430.00, 10),
    (NULL, 18, 50.00, 25),
    (NULL, 20, 260.00, 12);


-- ===========================
-- Órdenes de compra
-- ===========================
INSERT INTO purchase_orders (supplier_id, status, description, total, delivery_date)
VALUES
    (21, 'RECIBIDO', 'Pedido inicial de lubricantes y frenos', 0, '2025-09-10'),
    (22, 'RECIBIDO', 'Pedido de filtros y suspensión', 0, '2025-09-12'),
    (23, 'RECHAZADO', 'Pedido de batería y sistema eléctrico', 0, '2025-09-15'),
    (21, 'ENVIADO', 'Pedido de repuestos menores', 0, '2025-09-18'),
    (22, 'ORDENADO', 'Pedido grande de amortiguadores y rótulas', 0, '2025-09-20');

-- ===========================
-- Items de las órdenes
-- ===========================
-- Orden 1 (ID=1) - Proveedor 1
INSERT INTO purchase_order_items (purchase_order_id, part_id, unit_price, amount)
VALUES
    (1, 1, 35.00, 20),   -- Aceite 10W-40
    (1, 2, 120.00, 10),  -- Pastillas de freno
    (1, 10, 75.00, 15);  -- Discos de freno ventilados

-- Orden 2 (ID=2) - Proveedor 2
INSERT INTO purchase_order_items (purchase_order_id, part_id, unit_price, amount)
VALUES
    (2, 3, 50.00, 25),   -- Filtro de aire
    (2, 4, 60.00, 20),   -- Filtro de aceite
    (2, 15, 250.00, 8);  -- Amortiguadores delanteros

-- Orden 3 (ID=3) - Proveedor 3
INSERT INTO purchase_order_items (purchase_order_id, part_id, unit_price, amount)
VALUES
    (3, 5, 950.00, 5),   -- Batería 12V 75Ah
    (3, 16, 180.00, 12), -- Alternador
    (3, 8, 45.00, 30);   -- Bujías iridium

-- Orden 4 (ID=4) - Proveedor 1
INSERT INTO purchase_order_items (purchase_order_id, part_id, unit_price, amount)
VALUES
    (4, 6, 220.00, 6),   -- Kit de embrague
    (4, 7, 80.00, 12),   -- Correa de distribución
    (4, 11, 55.00, 18);  -- Zapatas de freno

-- Orden 5 (ID=5) - Proveedor 2
INSERT INTO purchase_order_items (purchase_order_id, part_id, unit_price, amount)
VALUES
    (5, 15, 250.00, 20), -- Amortiguadores delanteros
    (5, 18, 95.00, 25),  -- Rótulas de dirección
    (5, 19, 130.00, 15); -- Terminales de dirección

-- ===========================
-- Actualizar totales de las órdenes
-- ===========================
UPDATE purchase_orders
SET total = (SELECT SUM(unit_price * amount) FROM purchase_order_items WHERE purchase_order_id = purchase_orders.id)
WHERE id IN (1,2,3,4,5);




-- =====================================
-- 5 TRABAJOS (jobs)
-- =====================================
INSERT INTO jobs (vehicle_id, description, status, authorized_at, estimated_time)
VALUES
    (1, 'Cambio de aceite, filtro y revisión general de frenos', 'PENDIENTE', now(), 2),
    (2, 'Diagnóstico electrónico por encendido intermitente', 'AUTORIZADO', now(), 1),
    (3, 'Revisión de suspensión y reemplazo de amortiguadores', 'COMPLETADO', now(), 4),
    (4, 'Sustitución de bomba de agua y correa de distribución', 'EN_CURSO', now(), 5),
    (5, 'Alineación, balanceo y reemplazo de pastillas de freno', 'NECESITA_ESPECIALISTA', now(), 3);

-- =====================================
-- ASIGNACIONES (job_assignments)
-- =====================================
-- Job 1 asigno empleados 1 y 2
INSERT INTO job_assignments (job_id, user_id) VALUES (1, 1);
INSERT INTO job_assignments (job_id, user_id) VALUES (1, 2);

-- Job 2 asigno empleado 3 (y especialista 5)
INSERT INTO job_assignments (job_id, user_id) VALUES (2, 3);
INSERT INTO job_assignments (job_id, user_id) VALUES (2, 5);

-- Job 3 asigno especialista 6 y empleado 4
INSERT INTO job_assignments (job_id, user_id) VALUES (3, 6);
INSERT INTO job_assignments (job_id, user_id) VALUES (3, 4);

-- Job 4 asigno empleado 7
INSERT INTO job_assignments (job_id, user_id) VALUES (4, 7);

-- Job 5 asigno empleado 8 y empleado 1
INSERT INTO job_assignments (job_id, user_id) VALUES (5, 8);
INSERT INTO job_assignments (job_id, user_id) VALUES (5, 1);

-- =====================================
-- TAREAS (job_tasks) — 2 por trabajo
-- =====================================
-- Job 1: cambio de aceite (service_id 13: Aceite sintético) + revisión frenos (service_id 3)
INSERT INTO job_tasks (job_id, service_id, status) VALUES (1, 13, 'PENDIENTE');
INSERT INTO job_tasks (job_id, service_id, status) VALUES (1, 3, 'PENDIENTE');

-- Job 2: diagnóstico (service_id 6: Diagnóstico electrónico) + prueba técnica (service_id 5)
INSERT INTO job_tasks (job_id, service_id, status) VALUES (2, 6, 'PENDIENTE');
INSERT INTO job_tasks (job_id, service_id, status) VALUES (2, 5, 'PENDIENTE');

-- Job 3: revisión suspensión (service_id 9) + cambio amortiguadores (service_id 10)
INSERT INTO job_tasks (job_id, service_id, status) VALUES (3, 9, 'PENDIENTE');
INSERT INTO job_tasks (job_id, service_id, status) VALUES (3, 10, 'PENDIENTE');

-- Job 4: bomba de agua (service_id 2?) y correa (service_id 1?) — ajusta ids si es necesario
INSERT INTO job_tasks (job_id, service_id, status) VALUES (4, 2, 'PENDIENTE');
INSERT INTO job_tasks (job_id, service_id, status) VALUES (4, 9, 'PENDIENTE');

-- Job 5: alineación (service_id 14) + pastillas freno (service_id 4)
INSERT INTO job_tasks (job_id, service_id, status) VALUES (5, 14, 'PENDIENTE');
INSERT INTO job_tasks (job_id, service_id, status) VALUES (5, 4, 'PENDIENTE');

-- =====================================
-- LOGS (job_logs) — 2 por trabajo
-- =====================================
-- Job 1 logs
INSERT INTO job_logs (job_id, user_id, log_type, occurred_at, description)
VALUES
    (1, 1, 'AVANCE', now(), 'Inicio de trabajo: revisión inicial y confirmación de cotización.'),
    (1, 2, 'REPORTAR_DAÑO_ADICIONAL', now(), 'Se detectó desgaste en pastillas delanteras; propuesta de reemplazo.');

-- Job 2 logs
INSERT INTO job_logs (job_id, user_id, log_type, occurred_at, description)
VALUES
    (2, 3, 'COMENTARIO', now(), 'Cliente reporta encendidos intermitentes al arrancar.'),
    (2, 5, 'PRUEBA_TECNICA', now(), 'Especialista realizó escaneo OBD, registró error P0301.');

-- Job 3 logs
INSERT INTO job_logs (job_id, user_id, log_type, occurred_at, description)
VALUES
    (3, 6, 'PRUEBA_TECNICA', now(), 'Medición de amortiguadores: valores fuera de tolerancia.'),
    (3, 4, 'APROBACION', now(), 'Cliente autorizado para reemplazo de amortiguadores.');

-- Job 4 logs
INSERT INTO job_logs (job_id, user_id, log_type, occurred_at, description)
VALUES
    (4, 7, 'SINTOMA_DETECTADO', now(), 'Detección de fuga en bomba de agua.'),
    (4, 7, 'SOLICITUD_SERVICIO_ADICIONAL', now(), 'Solicitada cotización para kit de bomba y correa.');

-- Job 5 logs
INSERT INTO job_logs (job_id, user_id, log_type, occurred_at, description)
VALUES
    (5, 8, 'IMPREVISTO', now(), 'Inspección visual: pastillas con 60% uso restante.'),
    (5, 1, 'COMENTARIO', now(), 'Cliente solicita alineación urgente antes de entrega.');

-- =====================================
-- PARTES USADAS EN TRABAJOS (job_parts) — 2 por trabajo
-- =====================================
-- Job 1 uses: filtro de aceite (part_id 1), pastillas delanteras (part_id 5)
INSERT INTO job_parts (job_id, part_id, unit_price, amount)
VALUES
    (1, 1, 70.00, 1),
    (1, 5, 340.00, 1);

-- Job 2 uses: sensor oxígeno (14), bujía (4)
INSERT INTO job_parts (job_id, part_id, unit_price, amount)
VALUES
    (2, 14, 430.00, 1),
    (2, 4, 35.00, 4);

-- Job 3 uses: amortiguador delantero (7), amortiguador trasero (8)
INSERT INTO job_parts (job_id, part_id, unit_price, amount)
VALUES
    (3, 7, 450.00, 2),
    (3, 8, 480.00, 2);

-- Job 4 uses: bomba de agua (10), correa distribución (9)
INSERT INTO job_parts (job_id, part_id, unit_price, amount)
VALUES
    (4, 10, 600.00, 1),
    (4, 9, 500.00, 1);

-- Job 5 uses: pastillas de freno (5), kit de montaje/balanceo (18 as lamp used?) -> use part 6 disk as example
INSERT INTO job_parts (job_id, part_id, unit_price, amount)
VALUES
    (5, 5, 340.00, 2),
    (5, 6, 300.00, 4);



-- ============================
-- INSERT: FACTURAS (invoices)
-- ============================
INSERT INTO invoices (job_id, client_id, issued_by, status)
VALUES
    (1, 7, 1, 'EMITIDO'),   -- Factura 1 para job 1 (cliente 7)
    (2, 7, 1, 'EMITIDO'),   -- Factura 2 para job 2 (cliente 7)
    (3, 8, 1, 'EMITIDO'),   -- Factura 3 para job 3 (cliente 8)
    (4, 8, 2, 'EMITIDO'),   -- Factura 4 para job 4 (emitida por user 2)
    (5, 9, 1, 'EMITIDO');   -- Factura 5 para job 5 (cliente 9)

-- ============================
-- INSERT: ITEMS DE FACTURA (invoice_items)
-- ============================
-- Factura 1 (id = 1) - job 1: mano de obra + filtro de aceite + pastillas
INSERT INTO invoice_items (invoice_id, description, unit_price, amount)
VALUES
    (1, 'Mano de obra - cambio de aceite', 50.00, 1),
    (1, 'Filtro de aceite (P-001)', 70.00, 1),
    (1, 'Pastillas de freno delanteras (P-005)', 340.00, 1);

-- Factura 2 (id = 2) - job 2: diagnóstico + sensor + bujías
INSERT INTO invoice_items (invoice_id, description, unit_price, amount)
VALUES
    (2, 'Diagnóstico electrónico', 400.00, 1),
    (2, 'Sensor de oxígeno (P-014)', 430.00, 1),
    (2, 'Bujías (pack) (P-004)', 35.00, 4);

-- Factura 3 (id = 3) - job 3: amortiguadores y mano de obra
INSERT INTO invoice_items (invoice_id, description, unit_price, amount)
VALUES
    (3, 'Amortiguador delantero (P-007)', 450.00, 2),
    (3, 'Amortiguador trasero (P-008)', 480.00, 2),
    (3, 'Mano de obra - reemplazo amortiguadores', 200.00, 1);

-- Factura 4 (id = 4) - job 4: bomba de agua y correa + mano de obra
INSERT INTO invoice_items (invoice_id, description, unit_price, amount)
VALUES
    (4, 'Bomba de agua (P-010)', 600.00, 1),
    (4, 'Correa de distribución (P-009)', 500.00, 1),
    (4, 'Mano de obra - reemplazo bomba y correa', 150.00, 1);

-- Factura 5 (id = 5) - job 5: pastillas y discos + alineación
INSERT INTO invoice_items (invoice_id, description, unit_price, amount)
VALUES
    (5, 'Pastillas de freno delanteras (P-005)', 340.00, 2),
    (5, 'Disco de freno (P-006)', 300.00, 4),
    (5, 'Servicio - Alineación', 200.00, 1);

-- ============================
-- ACTUALIZAR TOTALES DE LAS FACTURAS
-- ============================
UPDATE invoices
SET total = (
    SELECT COALESCE(SUM(unit_price * amount),0) FROM invoice_items WHERE invoice_items.invoice_id = invoices.id
)
WHERE id IN (1,2,3,4,5);

-- ============================
-- INSERT: PAGOS (payments)
-- Factura 1 -> Pago completo (EFECTIVO)
-- Factura 2 -> Pago parcial (TARJETA)
-- Factura 3 -> Sin pago (aún)
-- Factura 4 -> Pago completo (TRANSFERENCIA)
-- Factura 5 -> Pago parcial (EFECTIVO)
-- ============================

-- Pago completo factura 1
INSERT INTO payments (invoice_id, amount, method)
VALUES (1, (SELECT total FROM invoices WHERE id = 1), 'EFECTIVO');

-- Pago parcial factura 2 (300)
INSERT INTO payments (invoice_id, amount, method)
VALUES (2, 300.00, 'TARJETA');

-- Factura 3 -> sin pagos por ahora

-- Pago completo factura 4
INSERT INTO payments (invoice_id, amount, method)
VALUES (4, (SELECT total FROM invoices WHERE id = 4), 'TRANSFERENCIA');

-- Pago parcial factura 5 (500)
INSERT INTO payments (invoice_id, amount, method)
VALUES (5, 500.00, 'EFECTIVO');

-- ============================
-- ACTUALIZAR ESTADO DE LAS FACTURAS SEGÚN PAGOS (PARCIAL / PAGADO / EMITIDO)
-- ============================
UPDATE invoices
SET status = CASE
                 WHEN COALESCE((SELECT SUM(amount) FROM payments p WHERE p.invoice_id = invoices.id), 0) >= invoices.total
                     THEN 'PAGADO'
                 WHEN COALESCE((SELECT SUM(amount) FROM payments p WHERE p.invoice_id = invoices.id), 0) > 0
                     THEN 'PARCIAL'
                 ELSE 'EMITIDO'
    END,
    updated_at = now()
WHERE id IN (1,2,3,4,5);




