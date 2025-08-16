INSERT INTO equipo(cod_equipo, nom_equipo, tipo_equipo, estado) VALUES
(1001, 'Laptop Dell 5400', 'LAPTOP', 'DISPONIBLE'),
(1002, 'Proyector Epson X05', 'PROYECTOR', 'DISPONIBLE'),
(1003, 'Tablet Samsung A7', 'TABLET', 'DISPONIBLE');

INSERT INTO reservacion(fecha, hora_inicio, hora_fin, usuario, cod_equipo) VALUES
(CURDATE(), '08:00:00', '10:00:00', 'ana', 1001),
(CURDATE(), '11:00:00', '12:30:00', 'luis', 1002);
