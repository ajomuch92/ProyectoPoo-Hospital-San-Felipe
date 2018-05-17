-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-12-2015 a las 18:55:20
-- Versión del servidor: 5.6.21
-- Versión de PHP: 5.5.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `db_hospital`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_area`
--

CREATE TABLE IF NOT EXISTS `tbl_area` (
`codigo_area` int(11) NOT NULL,
  `nombre_area` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_area`
--

INSERT INTO `tbl_area` (`codigo_area`, `nombre_area`) VALUES
(1, 'Estadística'),
(2, 'Enfermería'),
(3, 'Medicina'),
(4, 'Administración'),
(5, 'Administración medicinas'),
(6, 'Empleado común');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_citas`
--

CREATE TABLE IF NOT EXISTS `tbl_citas` (
`codigo_cita` int(11) NOT NULL,
  `hora` varchar(49) NOT NULL,
  `fecha` date NOT NULL,
  `numero_expediente` int(11) NOT NULL,
  `numero_empleado` int(11) NOT NULL,
  `estado` varchar(25) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_citas`
--

INSERT INTO `tbl_citas` (`codigo_cita`, `hora`, `fecha`, `numero_expediente`, `numero_empleado`, `estado`) VALUES
(1, '3pm', '2015-12-14', 1, 2, 'FINALIZADO'),
(2, '7am', '2015-12-15', 2, 6, 'FINALIZADO'),
(3, '8am', '2015-12-15', 4, 2, 'FINALIZADO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_diagnostico`
--

CREATE TABLE IF NOT EXISTS `tbl_diagnostico` (
`id_diagnostico` int(11) NOT NULL,
  `nombre_diagnostico` varchar(45) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_diagnostico`
--

INSERT INTO `tbl_diagnostico` (`id_diagnostico`, `nombre_diagnostico`) VALUES
(1, 'Chikungunya'),
(2, 'Dengue'),
(3, 'Malaria'),
(4, 'Gripe'),
(5, 'Fiebre tifoidea');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_empleados`
--

CREATE TABLE IF NOT EXISTS `tbl_empleados` (
`numero_empleado` int(11) NOT NULL,
  `numero_identidad` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `fecha` date NOT NULL,
  `telefono_fijo` varchar(45) DEFAULT NULL,
  `telefono_celular` varchar(45) DEFAULT NULL,
  `horario` varchar(45) DEFAULT NULL,
  `codigo_area` int(11) NOT NULL,
  `contraseña` varchar(45) NOT NULL,
  `edad` int(11) NOT NULL,
  `genero` varchar(45) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_empleados`
--

INSERT INTO `tbl_empleados` (`numero_empleado`, `numero_identidad`, `nombre`, `apellido`, `direccion`, `fecha`, `telefono_fijo`, `telefono_celular`, `horario`, `codigo_area`, `contraseña`, `edad`, `genero`) VALUES
(1, '0313-1992-00019', 'Aarón Josué', 'Montes', 'Col. Escoto, Comayagua', '1992-01-03', '2771-1481', '3324-3739', '8am-5pm', 4, 'bfab982a401d43383a7a12fb722d9a25f9cd0fce', 23, 'Masculino'),
(2, '1201-1986-00014', 'Leticia', 'Ordoñez', 'Bo. Lourdes', '1986-08-07', '2774-5470', '3387-9741', '8am-2pm', 3, '586c15c6fd8448116ae5a0f2e995d41e36f153b8', 29, 'Femenino'),
(3, '1204-1987-00125', 'Hernan', 'Granados', 'Col. Huerta', '1987-06-18', '2231-1479', '3321-1479', '8am-4pm', 1, '2c1214083a775f7cf6cb44b98ab41c29cdf7d419', 28, 'Masculino'),
(4, '1407-1989-00143', 'Katya', 'Mejía', 'Col. Las Acacias', '1989-05-17', '2241-4789', '9874-5120', '3pm-1am', 5, 'da1b356f06f869260e7f95c649b770cd45633eea', 25, 'Femenino'),
(5, '0801-1990-17403', 'Carmen', 'Salgado', 'Col. Hato', '1990-04-12', '2274-1463', '3478-1023', '7am-3pm', 2, 'fe9f9a42df78f942d2bace10e12a4dc78eaefb6b', 25, 'Femenino'),
(6, '0501-1989-00018', 'Gabriel', 'Castañeda', 'Col. Miramontes', '2015-12-16', '2241-7974', '9923-1987', '3pm-12am', 3, 'ed8a7983510f356aad28310677f49eb16abae69a', 27, 'Masculino'),
(7, '1801-1987-00147', 'Victoria', 'Huerta', 'Col. Miravalle', '2014-05-15', '2774-4123', '8741-3245', '9am-1pm', 2, 'bddb6bd774995e97d67b2f4cdbdf577a47d714a6', 28, 'Femenino'),
(8, '0301-1987-00145', 'Juan', 'Zepeda', 'Col. Americana', '2003-12-11', '2231-7489', '3300-1412', '12m-8pm', 2, 'f8a41f1968fc4be757b6ab931af718e25d129a81', 28, 'Masculino');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_enfermeros`
--

CREATE TABLE IF NOT EXISTS `tbl_enfermeros` (
  `numero_empleado` int(11) NOT NULL,
  `numero_colegio_enfermeria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_enfermeros`
--

INSERT INTO `tbl_enfermeros` (`numero_empleado`, `numero_colegio_enfermeria`) VALUES
(5, 7132),
(7, 7413),
(8, 7410);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_especialidad`
--

CREATE TABLE IF NOT EXISTS `tbl_especialidad` (
`codigo_especialidad` int(11) NOT NULL,
  `nombre_especialidad` varchar(45) NOT NULL,
  `descripcion` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_especialidad`
--

INSERT INTO `tbl_especialidad` (`codigo_especialidad`, `nombre_especialidad`, `descripcion`) VALUES
(1, 'Odontología', 'Aquí se sacan dientes'),
(2, 'Preclícnica', 'Área antes de ir al médico'),
(3, 'Pediatría', 'Atención a los niños'),
(4, 'Otorrinolaringología', 'Atención de nariz, boca y oído'),
(5, 'Medicina general', 'Atención de cualquier cosa'),
(6, 'Oftalmología', 'Ojos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_examenes`
--

CREATE TABLE IF NOT EXISTS `tbl_examenes` (
`codigo_examen` int(11) NOT NULL,
  `nombre_examen` varchar(45) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_examenes`
--

INSERT INTO `tbl_examenes` (`codigo_examen`, `nombre_examen`) VALUES
(2, 'Hemograma'),
(3, 'Ticción_KOH'),
(4, 'Orina'),
(5, 'Heces'),
(6, 'Radiografia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_historial`
--

CREATE TABLE IF NOT EXISTS `tbl_historial` (
`codigo_historial` int(11) NOT NULL,
  `codigo_cita` int(11) NOT NULL,
  `codigo_examen` int(11) DEFAULT NULL,
  `codigo_medicamento` int(11) DEFAULT NULL,
  `id_diagnostico` int(11) DEFAULT NULL,
  `fecha` date NOT NULL,
  `observacion` varchar(2000) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_historial`
--

INSERT INTO `tbl_historial` (`codigo_historial`, `codigo_cita`, `codigo_examen`, `codigo_medicamento`, `id_diagnostico`, `fecha`, `observacion`) VALUES
(1, 2, 2, 1, 2, '2015-12-15', 'Tomar abundante líquido'),
(2, 3, 2, 1, 2, '2015-12-15', 'Visite al médico la próxima semana');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_medicamentos`
--

CREATE TABLE IF NOT EXISTS `tbl_medicamentos` (
`codigo_medicamento` int(11) NOT NULL,
  `nombre_medicamento` varchar(45) NOT NULL,
  `codigo_presentacion` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_medicamentos`
--

INSERT INTO `tbl_medicamentos` (`codigo_medicamento`, `nombre_medicamento`, `codigo_presentacion`) VALUES
(1, 'Panadol', 1),
(2, 'Tosan', 5),
(3, 'Éter', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_medico`
--

CREATE TABLE IF NOT EXISTS `tbl_medico` (
  `numero_empleado` int(11) NOT NULL,
  `numero_colegio_medico` int(11) NOT NULL,
  `numero_consultorio` int(11) NOT NULL,
  `codigo_especialidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_medico`
--

INSERT INTO `tbl_medico` (`numero_empleado`, `numero_colegio_medico`, `numero_consultorio`, `codigo_especialidad`) VALUES
(2, 741, 741, 5),
(6, 7, 1362, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_pacientes`
--

CREATE TABLE IF NOT EXISTS `tbl_pacientes` (
`numero_expediente` int(11) NOT NULL,
  `numero_identidad` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `genero` varchar(25) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `telefono_fijo` varchar(45) NOT NULL,
  `telefono_celular` varchar(45) NOT NULL,
  `nombre_padre` varchar(200) NOT NULL,
  `nombre_madre` varchar(200) NOT NULL,
  `codigo_tipo_sangre` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_pacientes`
--

INSERT INTO `tbl_pacientes` (`numero_expediente`, `numero_identidad`, `nombre`, `apellido`, `genero`, `fecha_nacimiento`, `direccion`, `telefono_fijo`, `telefono_celular`, `nombre_padre`, `nombre_madre`, `codigo_tipo_sangre`) VALUES
(1, '0801-1998-00019', 'Carmen', 'Martínez', 'Femenino', '1998-09-17', 'Col. Lomas del Guijarro', '2278-9821', '3374-4560', 'Alberto Martínez', 'Karla Alonzo', 2),
(2, '0801-1998-00143', 'Gabriela', 'Mendoza', 'Femenino', '1998-12-17', 'Col. Bella Flor', '2236-9801', '9874-1403', 'Gerardo Mendoza', 'Suyapa Garcia', 2),
(3, '0801-1999-00147', 'Yolanda', 'Obando', 'Femenino', '2002-12-12', 'Col. Vista Hermosa', '2231-1400', '9870-0010', 'Pedro Obando', 'Reina Zelaya', 4),
(4, '0710-2000-00124', 'Hugo', 'Montecillos', 'Masculino', '2000-06-15', 'Col. Miravalle', '2235-1478', '9874-4123', 'Hugo Montecillos', 'Erminia Valladares', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_preclinica`
--

CREATE TABLE IF NOT EXISTS `tbl_preclinica` (
  `codigo_cita` int(11) NOT NULL,
  `temperatura` float NOT NULL,
  `peso` float NOT NULL,
  `altura` float NOT NULL,
  `presion` varchar(45) NOT NULL,
  `numero_empleado_enfermero` int(11) DEFAULT NULL,
  `estado` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_preclinica`
--

INSERT INTO `tbl_preclinica` (`codigo_cita`, `temperatura`, `peso`, `altura`, `presion`, `numero_empleado_enfermero`, `estado`) VALUES
(1, 33.5, 162, 1.78, '80-90', 5, 'FINALIZADO'),
(2, 34.5, 154, 1.65, '70-110', 7, 'FINALIZADO'),
(3, 37.3, 162, 1.86, '80-110', 8, 'FINALIZADO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_presentacion`
--

CREATE TABLE IF NOT EXISTS `tbl_presentacion` (
`codigo_presentacion` int(11) NOT NULL,
  `presentacion` varchar(45) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_presentacion`
--

INSERT INTO `tbl_presentacion` (`codigo_presentacion`, `presentacion`) VALUES
(1, 'Pastilla'),
(2, 'Cápsulas'),
(4, 'pastilla'),
(5, 'Jarabe'),
(6, 'pastilla'),
(7, 'pastilla'),
(8, 'Líquido'),
(9, 'Ungüento');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_tipos_sangre`
--

CREATE TABLE IF NOT EXISTS `tbl_tipos_sangre` (
`codigo_tipo_sangre` int(11) NOT NULL,
  `tipo_sangre` varchar(45) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_tipos_sangre`
--

INSERT INTO `tbl_tipos_sangre` (`codigo_tipo_sangre`, `tipo_sangre`) VALUES
(2, 'O+'),
(3, 'B-'),
(4, 'A+'),
(5, 'O-'),
(6, 'AB+');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tbl_area`
--
ALTER TABLE `tbl_area`
 ADD PRIMARY KEY (`codigo_area`);

--
-- Indices de la tabla `tbl_citas`
--
ALTER TABLE `tbl_citas`
 ADD PRIMARY KEY (`codigo_cita`), ADD KEY `fk_tbl_citas_tbl_pacientes1_idx` (`numero_expediente`), ADD KEY `fk_tbl_citas_tbl_medico1_idx` (`numero_empleado`);

--
-- Indices de la tabla `tbl_diagnostico`
--
ALTER TABLE `tbl_diagnostico`
 ADD PRIMARY KEY (`id_diagnostico`);

--
-- Indices de la tabla `tbl_empleados`
--
ALTER TABLE `tbl_empleados`
 ADD PRIMARY KEY (`numero_empleado`), ADD KEY `fk_tbl_empleados_tbl_area_idx` (`codigo_area`);

--
-- Indices de la tabla `tbl_enfermeros`
--
ALTER TABLE `tbl_enfermeros`
 ADD PRIMARY KEY (`numero_empleado`), ADD KEY `fk_tbl_enfermeros_tbl_empleados1_idx` (`numero_empleado`);

--
-- Indices de la tabla `tbl_especialidad`
--
ALTER TABLE `tbl_especialidad`
 ADD PRIMARY KEY (`codigo_especialidad`);

--
-- Indices de la tabla `tbl_examenes`
--
ALTER TABLE `tbl_examenes`
 ADD PRIMARY KEY (`codigo_examen`);

--
-- Indices de la tabla `tbl_historial`
--
ALTER TABLE `tbl_historial`
 ADD PRIMARY KEY (`codigo_historial`), ADD UNIQUE KEY `codigo_cita_UNIQUE` (`codigo_cita`), ADD KEY `fk_tbl_atenciones_tbl_examenes1_idx` (`codigo_examen`), ADD KEY `fk_tbl_atenciones_tbl_medicamentos1_idx` (`codigo_medicamento`), ADD KEY `fk_tbl_historial_tbl_diagnostico1_idx` (`id_diagnostico`), ADD KEY `fk_tbl_historial_tbl_precilinica1_idx` (`codigo_cita`);

--
-- Indices de la tabla `tbl_medicamentos`
--
ALTER TABLE `tbl_medicamentos`
 ADD PRIMARY KEY (`codigo_medicamento`), ADD KEY `fk_tbl_medicamentos_tbl_presentacion1_idx` (`codigo_presentacion`);

--
-- Indices de la tabla `tbl_medico`
--
ALTER TABLE `tbl_medico`
 ADD PRIMARY KEY (`numero_empleado`), ADD KEY `fk_tbl_medico_tbl_especialidad1_idx` (`codigo_especialidad`), ADD KEY `fk_tbl_medico_tbl_empleados1_idx` (`numero_empleado`);

--
-- Indices de la tabla `tbl_pacientes`
--
ALTER TABLE `tbl_pacientes`
 ADD PRIMARY KEY (`numero_expediente`), ADD KEY `fk_tbl_pacientes_tbl_tipos_sangre1_idx` (`codigo_tipo_sangre`);

--
-- Indices de la tabla `tbl_preclinica`
--
ALTER TABLE `tbl_preclinica`
 ADD UNIQUE KEY `codigo_cita_UNIQUE` (`codigo_cita`), ADD KEY `fk_tbl_precilinica_tbl_citas1_idx` (`codigo_cita`), ADD KEY `fk_tbl_precilinica_tbl_enfermeros1_idx` (`numero_empleado_enfermero`);

--
-- Indices de la tabla `tbl_presentacion`
--
ALTER TABLE `tbl_presentacion`
 ADD PRIMARY KEY (`codigo_presentacion`);

--
-- Indices de la tabla `tbl_tipos_sangre`
--
ALTER TABLE `tbl_tipos_sangre`
 ADD PRIMARY KEY (`codigo_tipo_sangre`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tbl_area`
--
ALTER TABLE `tbl_area`
MODIFY `codigo_area` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `tbl_citas`
--
ALTER TABLE `tbl_citas`
MODIFY `codigo_cita` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `tbl_diagnostico`
--
ALTER TABLE `tbl_diagnostico`
MODIFY `id_diagnostico` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `tbl_empleados`
--
ALTER TABLE `tbl_empleados`
MODIFY `numero_empleado` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `tbl_especialidad`
--
ALTER TABLE `tbl_especialidad`
MODIFY `codigo_especialidad` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `tbl_examenes`
--
ALTER TABLE `tbl_examenes`
MODIFY `codigo_examen` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `tbl_historial`
--
ALTER TABLE `tbl_historial`
MODIFY `codigo_historial` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `tbl_medicamentos`
--
ALTER TABLE `tbl_medicamentos`
MODIFY `codigo_medicamento` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `tbl_pacientes`
--
ALTER TABLE `tbl_pacientes`
MODIFY `numero_expediente` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `tbl_presentacion`
--
ALTER TABLE `tbl_presentacion`
MODIFY `codigo_presentacion` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `tbl_tipos_sangre`
--
ALTER TABLE `tbl_tipos_sangre`
MODIFY `codigo_tipo_sangre` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tbl_citas`
--
ALTER TABLE `tbl_citas`
ADD CONSTRAINT `fk_tbl_citas_tbl_medico1` FOREIGN KEY (`numero_empleado`) REFERENCES `tbl_medico` (`numero_empleado`) ON DELETE CASCADE ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_tbl_citas_tbl_pacientes1` FOREIGN KEY (`numero_expediente`) REFERENCES `tbl_pacientes` (`numero_expediente`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tbl_empleados`
--
ALTER TABLE `tbl_empleados`
ADD CONSTRAINT `fk_tbl_empleados_tbl_area` FOREIGN KEY (`codigo_area`) REFERENCES `tbl_area` (`codigo_area`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tbl_enfermeros`
--
ALTER TABLE `tbl_enfermeros`
ADD CONSTRAINT `fk_tbl_enfermeros_tbl_empleados1` FOREIGN KEY (`numero_empleado`) REFERENCES `tbl_empleados` (`numero_empleado`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tbl_historial`
--
ALTER TABLE `tbl_historial`
ADD CONSTRAINT `fk_tbl_atenciones_tbl_examenes1` FOREIGN KEY (`codigo_examen`) REFERENCES `tbl_examenes` (`codigo_examen`) ON DELETE SET NULL ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_tbl_atenciones_tbl_medicamentos1` FOREIGN KEY (`codigo_medicamento`) REFERENCES `tbl_medicamentos` (`codigo_medicamento`) ON DELETE SET NULL ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_tbl_historial_tbl_diagnostico1` FOREIGN KEY (`id_diagnostico`) REFERENCES `tbl_diagnostico` (`id_diagnostico`) ON DELETE SET NULL ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_tbl_historial_tbl_precilinica1` FOREIGN KEY (`codigo_cita`) REFERENCES `tbl_preclinica` (`codigo_cita`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tbl_medicamentos`
--
ALTER TABLE `tbl_medicamentos`
ADD CONSTRAINT `fk_tbl_medicamentos_tbl_presentacion1` FOREIGN KEY (`codigo_presentacion`) REFERENCES `tbl_presentacion` (`codigo_presentacion`) ON DELETE SET NULL ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tbl_medico`
--
ALTER TABLE `tbl_medico`
ADD CONSTRAINT `fk_tbl_medico_tbl_empleados1` FOREIGN KEY (`numero_empleado`) REFERENCES `tbl_empleados` (`numero_empleado`) ON DELETE CASCADE ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_tbl_medico_tbl_especialidad1` FOREIGN KEY (`codigo_especialidad`) REFERENCES `tbl_especialidad` (`codigo_especialidad`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tbl_pacientes`
--
ALTER TABLE `tbl_pacientes`
ADD CONSTRAINT `fk_tbl_pacientes_tbl_tipos_sangre1` FOREIGN KEY (`codigo_tipo_sangre`) REFERENCES `tbl_tipos_sangre` (`codigo_tipo_sangre`) ON DELETE SET NULL ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tbl_preclinica`
--
ALTER TABLE `tbl_preclinica`
ADD CONSTRAINT `fk_tbl_precilinica_tbl_citas1` FOREIGN KEY (`codigo_cita`) REFERENCES `tbl_citas` (`codigo_cita`) ON DELETE CASCADE ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_tbl_precilinica_tbl_enfermeros1` FOREIGN KEY (`numero_empleado_enfermero`) REFERENCES `tbl_enfermeros` (`numero_empleado`) ON DELETE CASCADE ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
