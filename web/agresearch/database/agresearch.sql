-- phpMyAdmin SQL Dump
-- version 4.2.6
-- http://www.phpmyadmin.net
--
-- Servidor: 192.168.86.55
-- Tiempo de generación: 11-08-2017 a las 18:28:34
-- Versión del servidor: 5.5.57-0+deb7u1-log
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
  `activity_measurement_units` varchar(30) NOT NULL,
  `activity_description` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

--
-- Volcado de datos para la tabla `activity`
--

INSERT INTO `activity` (`activity_id`, `activity_name`, `activity_category`, `activity_periodicity`, `activity_measurement_units`, `activity_description`) VALUES
(1, 'Ploughing', 'Field work', 0, 'hours', ''),
(4, 'Planting', 'Field work', 0, 'hours', ''),
(5, 'Harvesting', 'Field work', 0, 'hours', ''),
(6, 'Weeding', 'Field work', 0, 'hours', ''),
(7, 'Irrigation', 'Field work', 0, 'L/ha', ''),
(8, 'Pest control preparation', 'Pest control', 0, 'hours', ''),
(9, 'Pest control application', 'Pest control', 0, 'applied amount', ''),
(10, 'Compost preparation', 'Soil management', 0, 'hours', ''),
(11, 'Compost application', 'Soil management', 0, 'applied amount', ''),
(12, 'Mulch preparation', 'Soil management', 0, 'hours', ''),
(13, 'Mulch application', 'Soil management', 0, 'applied amount', ''),
(15, 'Thinning', 'Field work', 0, 'hours', ''),
(16, 'Prunning', 'Field work', 0, 'hours', ''),
(17, 'Sowing', 'Field work', 0, 'hours', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_x_crop_or_treatment`
--

CREATE TABLE IF NOT EXISTS `activity_x_crop_or_treatment` (
`activity_x_crop_or_treatment_id` int(10) unsigned NOT NULL,
  `activity_id` int(10) unsigned NOT NULL,
  `crop_id` int(10) unsigned DEFAULT NULL,
  `treatment_id` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=30 ;

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
(18, 8, NULL, 6),
(19, 4, 2, NULL),
(20, 17, 4, NULL),
(21, 17, 1, NULL),
(22, 17, 8, NULL),
(23, 17, 9, NULL),
(24, 15, 4, NULL),
(25, 15, 8, NULL),
(26, 15, 1, NULL),
(27, 15, 9, NULL),
(28, 16, 2, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `crop`
--

CREATE TABLE IF NOT EXISTS `crop` (
`crop_id` int(10) unsigned NOT NULL,
  `crop_name` varchar(20) NOT NULL,
  `crop_symbol` varchar(10) NOT NULL,
  `crop_variety_name` varchar(40) NOT NULL,
  `crop_used_for_intercropping` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Volcado de datos para la tabla `crop`
--

INSERT INTO `crop` (`crop_id`, `crop_name`, `crop_symbol`, `crop_variety_name`, `crop_used_for_intercropping`) VALUES
(1, 'Maize', 'C1', 'TMV1', 0),
(2, 'Cassava', 'C2', 'MKURANGA1', 0),
(4, 'Cowpeas', '', 'Diolole', 1),
(8, 'Pigeon peas', '', 'Local', 1),
(9, 'Sorghum', 'C3', 'Local', 0);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='Fields registered in the ag. research' AUTO_INCREMENT=38 ;

--
-- Volcado de datos para la tabla `field`
--

INSERT INTO `field` (`field_id`, `parent_field_id`, `user_id`, `field_date_created`, `field_name`, `field_replication_number`, `field_lat`, `field_lng`, `field_configuration`) VALUES
(23, 23, 1, '2017-02-24', 'Masasi', 1, '-10.792300', '38.894855', 'F=(2,1,1,1);G=(4,4);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,0,0);P=(2,4,0,0);P=(1,0,0,1);P=(2,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(1,4,1,1);P=(2,4,1,1);P=(2,0,1,0);P=(1,0,1,0);P=(2,0,1,1);P=(1,0,1,1);P=(2,4,1,0);P=(2,4,1,0);'),
(24, 23, 1, '2017-02-24', 'Masasi', 2, '-10.792300', '38.894855', 'F=(2,1,1,1);G=(4,4);P=(2,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(2,0,1,1);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,1,1);P=(2,4,1,1);P=(2,0,0,0);P=(1,0,0,0);P=(2,4,1,0);P=(1,4,1,0);P=(2,4,0,0);P=(1,4,0,0);P=(2,0,1,0);P=(1,0,1,0);'),
(25, 23, 1, '2017-02-24', 'Masasi', 3, '-10.792300', '38.894855', 'F=(2,1,1,1);G=(4,4);P=(2,0,1,0);P=(1,4,1,0);P=(1,0,1,1);P=(2,4,1,1);P=(1,0,1,0);P=(2,4,1,0);P=(1,4,1,1);P=(2,0,1,1);P=(2,4,0,0);P=(1,0,0,0);P=(2,0,0,1);P=(1,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(2,4,0,1);P=(1,0,0,1);'),
(34, 34, 3, '2017-04-19', 'Morogoro', 1, '-6.741821', '37.550743', 'F=(3,1,1,1);G=(4,4);P=(1,4,0,1);P=(9,0,0,1);P=(1,0,0,0);P=(9,4,0,0);P=(1,0,0,1);P=(9,4,0,1);P=(1,4,0,0);P=(9,0,0,0);P=(1,4,1,1);P=(9,4,1,1);P=(1,0,1,0);P=(2,0,1,0);P=(9,0,1,1);P=(1,0,1,1);P=(1,4,1,0);P=(2,4,1,0);'),
(35, 34, 3, '2017-04-19', 'Morogoro', 2, '-6.741821', '37.550743', 'F=(3,1,1,1);G=(4,4);P=(9,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(9,0,1,1);P=(1,4,0,1);P=(9,0,0,1);P=(1,0,1,1);P=(9,4,1,1);P=(9,0,0,0);P=(1,0,0,0);P=(2,4,1,0);P=(1,4,1,0);P=(9,4,0,0);P=(1,4,0,0);P=(9,0,1,0);P=(1,0,1,0);'),
(36, 34, 3, '2017-04-19', 'Morogoro', 3, '-6.741821', '37.550743', 'F=(2,1,1,1);G=(4,4);P=(9,0,1,0);P=(1,4,1,0);P=(1,0,1,1);P=(9,4,1,1);P=(1,0,1,0);P=(9,4,1,0);P=(1,4,1,1);P=(9,0,1,1);P=(9,4,0,0);P=(1,0,0,0);P=(9,0,0,1);P=(1,4,0,1);P=(1,4,0,0);P=(9,0,0,0);P=(9,4,0,1);P=(1,0,0,1);');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `input_log`
--

CREATE TABLE IF NOT EXISTS `input_log` (
`input_log_id` int(10) unsigned NOT NULL,
  `input_log_date` date NOT NULL,
  `field_id` int(10) unsigned NOT NULL,
  `plot_number` int(10) NOT NULL,
  `user_id` int(11) NOT NULL,
  `crop_id` int(11) NOT NULL,
  `treatment_id` int(11) NOT NULL,
  `input_age` int(11) NOT NULL,
  `input_origin` varchar(200) NOT NULL,
  `input_quantity` int(10) unsigned NOT NULL,
  `input_cost` float NOT NULL,
  `input_treatment_material` text NOT NULL,
  `input_treatment_preparation_method` text NOT NULL,
  `input_comments` text NOT NULL,
  `input_picture` varchar(200) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `input_log`
--

INSERT INTO `input_log` (`input_log_id`, `input_log_date`, `field_id`, `plot_number`, `user_id`, `crop_id`, `treatment_id`, `input_age`, `input_origin`, `input_quantity`, `input_cost`, `input_treatment_material`, `input_treatment_preparation_method`, `input_comments`, `input_picture`) VALUES
(6, '2017-07-03', 23, -1, 1, 0, 6, 0, '', 5, 10000, 'garlic (100 g) peppers (200 g), neem leaves (1 kg)', 'mixing, grinding, adding 10 l. water', 'added an image', 'images/DSC_0038_small.JPG');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `log`
--

CREATE TABLE IF NOT EXISTS `log` (
`log_id` int(10) unsigned NOT NULL,
  `field_id` int(10) unsigned NOT NULL,
  `plot_number` varchar(200) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `crop_id` int(10) unsigned NOT NULL,
  `sample_number` varchar(200) NOT NULL,
  `treatment_id` int(10) unsigned NOT NULL,
  `measurement_id` int(10) unsigned NOT NULL,
  `activity_id` int(10) unsigned NOT NULL,
  `log_date` date NOT NULL,
  `log_value_number` float NOT NULL,
  `log_value_units` varchar(30) NOT NULL,
  `log_value_text` text NOT NULL,
  `labour_time` int(10) unsigned NOT NULL COMMENT 'in hours',
  `cost` float NOT NULL,
  `log_comments` text NOT NULL,
  `log_picture` varchar(200) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Volcado de datos para la tabla `log`
--

INSERT INTO `log` (`log_id`, `field_id`, `plot_number`, `user_id`, `crop_id`, `sample_number`, `treatment_id`, `measurement_id`, `activity_id`, `log_date`, `log_value_number`, `log_value_units`, `log_value_text`, `labour_time`, `cost`, `log_comments`, `log_picture`) VALUES
(6, 23, '8', 1, 0, '0', 0, 0, 13, '2017-07-03', 20, 'applied amount', '', 0, 0, 'image added', 'images/DSC_0027_small.JPG'),
(7, 23, '13', 1, 0, '0', 0, 0, 13, '2017-07-03', 20, 'applied amount', '', 0, 0, '', ''),
(8, 23, '-1', 1, 0, '0', 0, 0, 8, '2017-07-03', 3, 'hours', '', 0, 0, '', ''),
(9, 34, '9', 1, 0, '2', 0, 8, 0, '2017-06-28', 200, 'sticks', '', 0, 0, 'look at the image', 'images/DSC_0038_small.JPG'),
(10, 23, '5', 1, 0, '0', 0, 0, 4, '2017-07-04', 3, 'hours', '', 0, 0, 'cowpeas', '');

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
  `measurement_periodicity` int(10) unsigned NOT NULL COMMENT 'in days',
  `measurement_has_sample_number` tinyint(1) NOT NULL DEFAULT '0',
  `measurement_common_complex` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0 = common, 1 = complex',
  `measurement_description` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=46 ;

--
-- Volcado de datos para la tabla `measurement`
--

INSERT INTO `measurement` (`measurement_id`, `measurement_name`, `measurement_category`, `measurement_subcategory`, `measurement_type`, `measurement_range_min`, `measurement_range_max`, `measurement_units`, `measurement_categories`, `measurement_periodicity`, `measurement_has_sample_number`, `measurement_common_complex`, `measurement_description`) VALUES
(8, 'Number of planted seeds/sticks', 'Crops', 'Sowing/planting - survival', 1, 0, 1000, '', '', 0, 0, 0, ''),
(9, 'Germinated/sprouted after 14 days', 'Crops', 'Sowing/planting - survival', 1, 0, 1000, '', '', 0, 0, 0, ''),
(10, 'Crop stand', 'Crops', 'Plant growth', 0, 0, 0, '', 'homogeneous,heterogeneous (between 25%-50% var),very heterogeneous (over 50% var)', 0, 0, 0, ''),
(11, 'Growth stage (Maize)', 'Crops', 'Plant growth', 0, 0, 0, '', 'VE,V1,V2,V3,V4,V5,V6,V7,V8,V9,V10,V11,V12,V13,V14,V15,V16,V17,V18,V19,V20,V21,V22,V23,R0,R1,R2,R3,R4,R5,R6', 0, 1, 1, ''),
(12, 'Plant size', 'Crops', 'Plant growth', 1, 0, 500, 'cm', '', 0, 1, 1, ''),
(13, 'Stem diameter (Cassava)', 'Crops', 'Plant growth', 1, 0, 100, 'mm', '', 0, 1, 1, ''),
(14, 'Date of branching', 'Crops', 'Plant growth', 1, 0, 0, 'date', '', 0, 0, 0, ''),
(16, 'Date of flowering', 'Crops', 'Plant growth', 1, 0, 0, 'date', '', 0, 0, 0, ''),
(17, 'Pest infestation', 'Crops', 'Plant health', 0, 0, 0, '', 'Bird,Insect,Insect larvae,Vertebrate,Worm', 0, 1, 1, ''),
(21, 'Stress symptoms', 'Crops', 'Plant health', 0, 0, 0, '', 'yes,no', 0, 1, 1, ''),
(23, '100(0) seed weight', 'Crops', 'Yield', 1, 0, 5000, 'g', '', 0, 1, 1, ''),
(24, 'Total biomass', 'Crops', 'Yield', 1, 0, 1000, 'kg', '', 0, 0, 1, ''),
(25, 'Weight', 'Crops', 'Yield', 1, 0, 10000, 'g', '', 0, 0, 1, ''),
(27, 'Nutritional value per mass (kcal)', 'Crops', 'Quality', 1, 0, 1000, 'kcal', '', 0, 1, 1, ''),
(28, 'Growth stage (Sorghum)', 'Crops', 'Plant growth', 0, 0, 0, '', '0,1,2,3,4,5,6,7,8,9', 0, 1, 1, ''),
(29, 'Disease infestation', 'Crops', 'Plant health', 0, 0, 0, '', 'Bacterial,Fungal,Viral', 0, 1, 1, ''),
(35, 'Moisture', 'Soil', 'Soil moisture', 1, 0, 100, '%', '', 0, 1, 1, ''),
(36, 'pH', 'Soil', 'Soil fertility', 1, 0, 14, 'pH', '', 0, 1, 1, ''),
(37, 'CN ratio', 'Soil', 'Soil fertility', 1, 0, 100, '%', '', 0, 1, 1, ''),
(38, 'Particle size', 'Soil', 'Soil fertility', 1, 0, 100, 'mm', '', 0, 0, 0, ''),
(39, 'Number of survived plants', 'Crops', 'Yield', 1, 0, 150, '', '', 0, 0, 0, ''),
(44, 'Growth stage (Cassava)', 'Crops', 'Plant growth', 0, 0, 0, '', '0,1,2', 0, 1, 1, ''),
(45, 'Moisture (Maize)', 'Crops', 'Quality', 1, 0, 100, '%', '', 0, 1, 1, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `measurement_x_crop_or_treatment`
--

CREATE TABLE IF NOT EXISTS `measurement_x_crop_or_treatment` (
`measurement_x_crop_or_treatment_id` int(10) unsigned NOT NULL,
  `measurement_id` int(10) unsigned NOT NULL,
  `crop_id` int(10) unsigned DEFAULT NULL,
  `treatment_id` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Volcado de datos para la tabla `measurement_x_crop_or_treatment`
--

INSERT INTO `measurement_x_crop_or_treatment` (`measurement_x_crop_or_treatment_id`, `measurement_id`, `crop_id`, `treatment_id`) VALUES
(1, 11, 1, NULL),
(2, 28, 9, NULL),
(3, 33, 2, NULL),
(4, 32, 4, NULL),
(5, 32, 1, NULL),
(6, 32, 9, NULL),
(7, 15, 2, NULL),
(8, 16, 2, NULL),
(9, 14, 2, NULL),
(10, 13, 2, NULL);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

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
-- Estructura de tabla para la tabla `treatment_color`
--

CREATE TABLE IF NOT EXISTS `treatment_color` (
`treatment_color_id` int(10) unsigned NOT NULL,
  `treatment_category` varchar(100) NOT NULL,
  `color` varchar(100) NOT NULL,
  `color_hex` varchar(100) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `treatment_color`
--

INSERT INTO `treatment_color` (`treatment_color_id`, `treatment_category`, `color`, `color_hex`) VALUES
(1, 'No treatment', 'w3-grey', '616161'),
(2, 'Soil management', 'w3-deep-orange', 'ff5722'),
(3, 'Pest control', 'w3-yellow', 'ffeb3b'),
(4, 'Soil management and pest control', 'w3-green', '4caf50');

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

INSERT INTO `user` (`user_id`, `user_name`, `user_alias`, `user_password`, `user_organization`, `user_role`) VALUES
(1, 'Eugenio Tisselli', 'eugenio', 'cubo23', 'ETHZ', 2),
(2, 'Angelika Hilbeck', 'angelika', 'ah', 'ETHZ', 2),
(3, 'Milena Wiget', 'milena', 'milena', 'ETHZ', 2);

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
-- Indices de la tabla `treatment_color`
--
ALTER TABLE `treatment_color`
 ADD PRIMARY KEY (`treatment_color_id`);

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
MODIFY `activity_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT de la tabla `activity_x_crop_or_treatment`
--
ALTER TABLE `activity_x_crop_or_treatment`
MODIFY `activity_x_crop_or_treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT de la tabla `crop`
--
ALTER TABLE `crop`
MODIFY `crop_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `field`
--
ALTER TABLE `field`
MODIFY `field_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=38;
--
-- AUTO_INCREMENT de la tabla `input_log`
--
ALTER TABLE `input_log`
MODIFY `input_log_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `log`
--
ALTER TABLE `log`
MODIFY `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `measurement`
--
ALTER TABLE `measurement`
MODIFY `measurement_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=46;
--
-- AUTO_INCREMENT de la tabla `measurement_x_crop_or_treatment`
--
ALTER TABLE `measurement_x_crop_or_treatment`
MODIFY `measurement_x_crop_or_treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `plot`
--
ALTER TABLE `plot`
MODIFY `plot_id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `treatment`
--
ALTER TABLE `treatment`
MODIFY `treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT de la tabla `treatment_color`
--
ALTER TABLE `treatment_color`
MODIFY `treatment_color_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
MODIFY `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
