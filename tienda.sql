-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-05-2019 a las 19:42:21
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 7.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tienda`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `e_codigo` int(11) NOT NULL,
  `e_nombre` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  `e_apellidos` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  `e_password` varchar(255) COLLATE latin1_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`e_codigo`, `e_nombre`, `e_apellidos`, `e_password`) VALUES
(111, 'Mario', 'Blue', '1234'),
(112, 'Sara', 'Brown', '33678');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `p_codigo` int(11) NOT NULL,
  `p_nombre` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  `p_descripcion` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  `p_precio` decimal(6,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`p_codigo`, `p_nombre`, `p_descripcion`, `p_precio`) VALUES
(222, 'Gigabyte B450M DS3H', 'Placa base GIGABYTE serie 400 con tecnología AMD StoreMI', '65.99'),
(223, 'Kingston A400 SSD 240GB', 'Disco duro A400 de estado sólido de Kingston', '32.95'),
(224, 'Lenovo Ideapad 530S', 'Ordenador portátil con Intel Core i5, 8GB RAM, 256GB SSD', '599.00');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`e_codigo`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`p_codigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
