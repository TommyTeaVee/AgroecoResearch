-- phpMyAdmin SQL Dump
-- version 4.2.6
-- http://www.phpmyadmin.net
--
-- Servidor: 192.168.86.55
-- Tiempo de generación: 25-04-2017 a las 16:50:30
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=16 ;

--
-- Volcado de datos para la tabla `activity`
--

INSERT INTO `activity` (`activity_id`, `activity_name`, `activity_category`, `activity_periodicity`, `activity_measurement_units`) VALUES
(1, 'Ploughing', 'Field work', 0, 'hours'),
(4, 'Planting', 'Field work', 0, 'hours'),
(5, 'Harvesting', 'Field work', 0, 'hours'),
(6, 'Weeding', 'Field work', 0, 'hours'),
(7, 'Irrigation', 'Field work', 0, 'L/ha'),
(8, 'Pest control preparation', 'Pest control', 0, 'hours'),
(9, 'Pest control application', 'Pest control', 0, 'applied amount'),
(10, 'Compost preparation', 'Soil management', 0, 'hours'),
(11, 'Compost application', 'Soil management', 0, 'applied amount'),
(12, 'Mulch preparation', 'Soil management', 0, 'hours'),
(13, 'Mulch application', 'Soil management', 0, 'applied amount');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_x_crop_or_treatment`
--

CREATE TABLE IF NOT EXISTS `activity_x_crop_or_treatment` (
`activity_x_crop_or_treatment_id` int(10) unsigned NOT NULL,
  `activity_id` int(10) unsigned NOT NULL,
  `crop_id` int(10) unsigned DEFAULT NULL,
  `treatment_id` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

--
-- Volcado de datos para la tabla `activity_x_crop_or_treatment`
--

INSERT INTO `activity_x_crop_or_treatment` (`activity_x_crop_or_treatment_id`, `activity_id`, `crop_id`, `treatment_id`) VALUES
(8, 1, 1, NULL),
(9, 1, NULL, 5),
(10, 14, NULL, 5),
(11, 14, NULL, 10),
(12, 11, NULL, 5),
(14, 10, NULL, 5),
(15, 13, NULL, 10),
(16, 12, NULL, 10),
(17, 9, NULL, 6),
(18, 8, NULL, 6);

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

--
-- Volcado de datos para la tabla `crop`
--

INSERT INTO `crop` (`crop_id`, `crop_name`, `crop_variety_name`, `crop_used_for_intercropping`) VALUES
(1, 'Maize', 'Staha', 0),
(2, 'Cassava', 'Kiroba', 0),
(4, 'Cowpeas', 'Local', 1),
(8, 'Pigeon peas', 'Local', 1),
(9, 'Sorghum', 'Local', 0);

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

--
-- Volcado de datos para la tabla `field`
--

INSERT INTO `field` (`field_id`, `parent_field_id`, `user_id`, `field_date_created`, `field_name`, `field_replication_number`, `field_lat`, `field_lng`, `field_configuration`) VALUES
(23, 23, 1, '2017-02-24', 'Masasi', 1, '-10.710850660670198', '38.82671356201172', 'F=(2,1,1,1);G=(4,4);P=(2,0,0,0);P=(2,4,0,0);P=(2,0,1,0);P=(2,4,1,0);P=(1,4,0,0);P=(1,0,0,0);P=(1,4,1,0);P=(1,0,1,0);P=(2,0,0,1);P=(2,4,0,1);P=(2,0,1,1);P=(2,4,1,1);P=(1,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(1,0,1,1);'),
(24, 23, 1, '2017-02-24', 'Masasi', 2, '-10.710850660670198', '38.82671356201172', 'F=(2,1,1,1);G=(4,4);P=(2,0,0,0);P=(2,4,0,0);P=(2,0,1,0);P=(2,4,1,0);P=(1,4,0,0);P=(1,0,0,0);P=(1,4,1,0);P=(1,0,1,0);P=(2,0,0,1);P=(2,4,0,1);P=(2,0,1,1);P=(2,4,1,1);P=(1,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(1,0,1,1);'),
(25, 23, 1, '2017-02-24', 'Masasi', 3, '-10.710850660670198', '38.82671356201172', 'F=(2,1,1,1);G=(4,4);P=(2,0,0,0);P=(2,4,0,0);P=(2,0,1,0);P=(2,4,1,0);P=(1,4,0,0);P=(1,0,0,0);P=(1,4,1,0);P=(1,0,1,0);P=(2,0,0,1);P=(2,4,0,1);P=(2,0,1,1);P=(2,4,1,1);P=(1,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(1,0,1,1);'),
(31, 31, 1, '2017-02-24', 'Morogoro', 1, '-6.778438046488761', '37.59143829345703', 'F=(2,1,1,1);G=(4,4);P=(2,0,0,0);P=(2,4,0,0);P=(2,0,1,0);P=(2,4,1,0);P=(1,4,0,0);P=(1,0,0,0);P=(1,4,1,0);P=(1,0,1,0);P=(2,0,0,1);P=(2,4,0,1);P=(2,0,1,1);P=(2,4,1,1);P=(1,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(1,0,1,1);'),
(32, 31, 1, '2017-02-24', 'Morogoro', 2, '-6.778438046488761', '37.59143829345703', 'F=(2,1,1,1);G=(4,4);P=(2,0,0,0);P=(2,4,0,0);P=(2,0,1,0);P=(2,4,1,0);P=(1,4,0,0);P=(1,0,0,0);P=(1,4,1,0);P=(1,0,1,0);P=(2,0,0,1);P=(2,4,0,1);P=(2,0,1,1);P=(2,4,1,1);P=(1,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(1,0,1,1);'),
(33, 31, 1, '2017-02-24', 'Morogoro', 3, '-6.778438046488761', '37.59143829345703', 'F=(2,1,1,1);G=(4,4);P=(2,0,0,0);P=(2,4,0,0);P=(2,0,1,0);P=(2,4,1,0);P=(1,4,0,0);P=(1,0,0,0);P=(1,4,1,0);P=(1,0,1,0);P=(2,0,0,1);P=(2,4,0,1);P=(2,0,1,1);P=(2,4,1,1);P=(1,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(1,0,1,1);');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `input_log`
--

CREATE TABLE IF NOT EXISTS `input_log` (
`input_log_id` int(10) unsigned NOT NULL,
  `input_log_date` date NOT NULL,
  `field_id` int(10) unsigned NOT NULL,
  `plot_number` int(10) unsigned NOT NULL,
  `user_id` int(11) NOT NULL,
  `crop_id` int(11) NOT NULL,
  `treatment_id` int(11) NOT NULL,
  `input_age` int(11) NOT NULL,
  `input_origin` varchar(200) NOT NULL,
  `input_quantity` int(10) unsigned NOT NULL,
  `input_cost` float NOT NULL,
  `input_treatment_material` text NOT NULL,
  `input_treatment_preparation_method` text NOT NULL,
  `input_comments` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

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
  `sample_number` int(10) unsigned NOT NULL,
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=28 ;

--
-- Volcado de datos para la tabla `measurement`
--

INSERT INTO `measurement` (`measurement_id`, `measurement_name`, `measurement_category`, `measurement_subcategory`, `measurement_type`, `measurement_range_min`, `measurement_range_max`, `measurement_units`, `measurement_categories`, `measurement_periodicity`) VALUES
(8, 'Number of planted seeds/sticks', 'Crops', 'Sowing/planting - survival', 1, 0, 1000, 'units', '', 0),
(9, 'Germinated/sprouted after 14 days', 'Crops', 'Sowing/planting - survival', 1, 0, 1000, 'units', '', 0),
(10, 'Crop stand after 3-4 weeks', 'Crops', 'Sowing/planting - survival', 0, 0, 0, '', 'a,b,c,d', 0),
(11, 'Growth stage', 'Crops', 'Plant growth', 0, 0, 0, '', 'a,b,c,d', 0),
(12, 'Plant size', 'Crops', 'Plant growth', 1, 0, 500, 'cm', '', 0),
(13, 'Stem girth', 'Crops', 'Plant growth', 1, 0, 100, 'cm', '', 0),
(14, 'Date of branching', 'Crops', 'Plant growth', 1, 0, 0, 'date', '', 0),
(15, 'Maximum branch size', 'Crops', 'Plant growth', 1, 0, 300, 'cm', '', 0),
(16, 'Date of flowering', 'Crops', 'Plant growth', 1, 0, 0, 'date', '', 0),
(17, 'Pest category', 'Crops', 'Plant health', 0, 0, 0, '', 'a,b,c,d', 0),
(18, 'Total damaged leaf area', 'Crops', 'Plant health', 1, 0, 100, 'cm2', '', 0),
(19, 'By N/P shortage affected (plant part)', 'Crops', 'Plant health', 0, 0, 0, '', 'a,b,c,d', 0),
(20, 'Discoloration', 'Crops', 'Plant health', 0, 0, 0, '', 'a,b,c,d', 0),
(21, 'Water stress', 'Crops', 'Plant health', 0, 0, 0, '', 'yes,no', 0),
(22, 'Weight of kernel/grain', 'Crops', 'Yield', 1, 0, 100, 'g', '', 0),
(23, '100(0) seed weight', 'Crops', 'Yield', 1, 0, 5000, 'g', '', 0),
(24, 'Total biomass', 'Crops', 'Yield', 1, 0, 1000, 'kg', '', 0),
(25, 'Weight', 'Crops', 'Productivity and quality', 1, 0, 10000, 'g', '', 0),
(26, 'Dry weight', 'Crops', 'Productivity and quality', 1, 0, 10000, 'g', '', 0),
(27, 'Nutritional value', 'Crops', 'Productivity and quality', 1, 0, 1000, 'kcal', '', 0);

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

--
-- Volcado de datos para la tabla `treatment`
--

INSERT INTO `treatment` (`treatment_id`, `treatment_name`, `treatment_category`, `primary_crop_id`, `intercropping_crop_id`) VALUES
(5, 'Composting', 'Soil management', NULL, NULL),
(6, 'Pest control', 'Pest control', NULL, NULL),
(10, 'Mulching', 'Soil management', NULL, NULL),
(12, 'Intercropping', 'Intercropping', 1, 4);

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
-- Volcado de datos para la tabla `user`
--

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
-- Indices de la tabla `input_log`
--
ALTER TABLE `input_log`
 ADD PRIMARY KEY (`input_log_id`);

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
MODIFY `activity_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT de la tabla `activity_x_crop_or_treatment`
--
ALTER TABLE `activity_x_crop_or_treatment`
MODIFY `activity_x_crop_or_treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
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
-- AUTO_INCREMENT de la tabla `input_log`
--
ALTER TABLE `input_log`
MODIFY `input_log_id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `log`
--
ALTER TABLE `log`
MODIFY `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `measurement`
--
ALTER TABLE `measurement`
MODIFY `measurement_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=28;
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
