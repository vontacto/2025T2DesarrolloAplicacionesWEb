DROP TABLE IF EXISTS reservacion;
DROP TABLE IF EXISTS equipo;

CREATE TABLE equipo (
  cod_equipo BIGINT PRIMARY KEY,
  nom_equipo VARCHAR(100) NOT NULL,
  tipo_equipo VARCHAR(50) NOT NULL,
  estado VARCHAR(30) NOT NULL
);

CREATE TABLE reservacion (
  num_reservacion BIGINT PRIMARY KEY AUTO_INCREMENT,
  fecha DATE NOT NULL,
  hora_inicio TIME NOT NULL,
  hora_fin TIME NOT NULL,
  usuario VARCHAR(100) NOT NULL,
  cod_equipo BIGINT NOT NULL,
  CONSTRAINT fk_equipo FOREIGN KEY (cod_equipo) REFERENCES equipo(cod_equipo)
);
