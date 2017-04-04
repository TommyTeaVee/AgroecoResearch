-- phpMyAdmin SQL Dump
-- version 4.2.6
-- http://www.phpmyadmin.net
--
-- Servidor: 192.168.86.55
-- Tiempo de generación: 04-04-2017 a las 14:43:00
-- Versión del servidor: 5.5.54-0+deb7u2-log
-- Versión de PHP: 5.3.29-1~dotdeb.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `agresearch`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity`
--

CREATE TABLE IF NOT EXISTS `activity` (
`activity_id` int(10) unsigned NOT NULL,
  `activity_name` varchar(100) NOT NULL,
  `activity_category` varchar(30) NOT NULL,
  `activity_periodicity` int(10) unsigned NOT NULL COMMENT 'in days',
  `activity_measurement_units` varchar(30) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_x_crop_or_treatment`
--

CREATE TABLE IF NOT EXISTS `activity_x_crop_or_treatment` (
`activity_x_crop_or_treatment_id` int(10) unsigned NOT NULL,
  `activity_id` int(10) unsigned NOT NULL,
  `crop_id` int(10) unsigned DEFAULT NULL,
  `treatment_id` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `crop`
--

CREATE TABLE IF NOT EXISTS `crop` (
`crop_id` int(10) unsigned NOT NULL,
  `crop_name` varchar(20) NOT NULL,
  `crop_variety_name` varchar(40) NOT NULL,
  `crop_used_for_intercropping` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `field`
--

CREATE TABLE IF NOT EXISTS `field` (
`field_id` int(10) unsigned NOT NULL,
  `parent_field_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `field_date_created` date NOT NULL,
  `field_name` varchar(30) NOT NULL,
  `field_replication_number` int(10) unsigned NOT NULL,
  `field_lat` varchar(30) NOT NULL,
  `field_lng` varchar(30) NOT NULL,
  `field_configuration` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='Fields registered in the ag. research' AUTO_INCREMENT=35 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `log`
--

CREATE TABLE IF NOT EXISTS `log` (
`log_id` int(10) unsigned NOT NULL,
  `field_id` int(10) unsigned NOT NULL,
  `plot_number` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `crop_id` int(10) unsigned NOT NULL,
  `treatment_id` int(10) unsigned NOT NULL,
  `measurement_id` int(10) unsigned NOT NULL,
  `activity_id` int(10) unsigned NOT NULL,
  `log_date` date NOT NULL,
  `log_value_number` float NOT NULL,
  `log_value_text` text NOT NULL,
  `labour_time` int(10) unsigned NOT NULL COMMENT 'in hours',
  `cost` float NOT NULL,
  `log_comments` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `measurement`
--

CREATE TABLE IF NOT EXISTS `measurement` (
`measurement_id` int(10) unsigned NOT NULL,
  `measurement_name` varchar(100) NOT NULL,
  `measurement_category` varchar(30) NOT NULL,
  `measurement_subcategory` varchar(40) NOT NULL,
  `measurement_type` int(10) unsigned NOT NULL,
  `measurement_range_min` float NOT NULL,
  `measurement_range_max` float NOT NULL,
  `measurement_units` varchar(30) NOT NULL,
  `measurement_categories` text NOT NULL COMMENT 'List of items for qualitative measurements',
  `measurement_periodicity` int(10) unsigned NOT NULL COMMENT 'in days'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `measurement_x_crop_or_treatment`
--

CREATE TABLE IF NOT EXISTS `measurement_x_crop_or_treatment` (
`measurement_x_crop_or_treatment_id` int(10) unsigned NOT NULL,
  `measurement_id` int(10) unsigned NOT NULL,
  `crop_id` int(10) unsigned DEFAULT NULL,
  `treatment_id` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plot`
--

CREATE TABLE IF NOT EXISTS `plot` (
`plot_id` int(10) unsigned NOT NULL,
  `field_id` int(11) unsigned NOT NULL,
  `polt_row` int(11) unsigned NOT NULL,
  `plot_column` int(11) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `treatment`
--

CREATE TABLE IF NOT EXISTS `treatment` (
`treatment_id` int(10) unsigned NOT NULL,
  `treatment_name` varchar(40) NOT NULL,
  `treatment_category` varchar(30) NOT NULL,
  `primary_crop_id` int(11) unsigned DEFAULT NULL,
  `intercropping_crop_id` int(11) unsigned DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
`user_id` int(10) unsigned NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `user_alias` varchar(10) NOT NULL,
  `user_password` varchar(30) NOT NULL,
  `user_organization` varchar(40) NOT NULL,
  `user_role` int(10) unsigned NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `activity`
--
ALTER TABLE `activity`
 ADD PRIMARY KEY (`activity_id`);

--
-- Indices de la tabla `activity_x_crop_or_treatment`
--
ALTER TABLE `activity_x_crop_or_treatment`
 ADD PRIMARY KEY (`activity_x_crop_or_treatment_id`);

--
-- Indices de la tabla `crop`
--
ALTER TABLE `crop`
 ADD PRIMARY KEY (`crop_id`);

--
-- Indices de la tabla `field`
--
ALTER TABLE `field`
 ADD PRIMARY KEY (`field_id`);

--
-- Indices de la tabla `log`
--
ALTER TABLE `log`
 ADD PRIMARY KEY (`log_id`);

--
-- Indices de la tabla `measurement`
--
ALTER TABLE `measurement`
 ADD PRIMARY KEY (`measurement_id`);

--
-- Indices de la tabla `measurement_x_crop_or_treatment`
--
ALTER TABLE `measurement_x_crop_or_treatment`
 ADD PRIMARY KEY (`measurement_x_crop_or_treatment_id`);

--
-- Indices de la tabla `plot`
--
ALTER TABLE `plot`
 ADD PRIMARY KEY (`plot_id`);

--
-- Indices de la tabla `treatment`
--
ALTER TABLE `treatment`
 ADD PRIMARY KEY (`treatment_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `activity`
--
ALTER TABLE `activity`
MODIFY `activity_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `activity_x_crop_or_treatment`
--
ALTER TABLE `activity_x_crop_or_treatment`
MODIFY `activity_x_crop_or_treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `crop`
--
ALTER TABLE `crop`
MODIFY `crop_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `field`
--
ALTER TABLE `field`
MODIFY `field_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT de la tabla `log`
--
ALTER TABLE `log`
MODIFY `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `measurement`
--
ALTER TABLE `measurement`
MODIFY `measurement_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `measurement_x_crop_or_treatment`
--
ALTER TABLE `measurement_x_crop_or_treatment`
MODIFY `measurement_x_crop_or_treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT de la tabla `plot`
--
ALTER TABLE `plot`
MODIFY `plot_id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `treatment`
--
ALTER TABLE `treatment`
MODIFY `treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
MODIFY `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
