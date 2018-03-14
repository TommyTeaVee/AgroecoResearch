-- phpMyAdmin SQL Dump
-- version 4.2.6
-- http://www.phpmyadmin.net
--
-- Servidor: 192.168.86.55
-- Tiempo de generación: 14-03-2018 a las 19:29:49
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=26 ;

--
-- Volcado de datos para la tabla `activity`
--

INSERT INTO `activity` (`activity_id`, `activity_name`, `activity_category`, `activity_periodicity`, `activity_measurement_units`, `activity_description`) VALUES
(1, 'Ploughing', 'Field work', 0, 'hours', 'A week before sowing or planting, plots need to be ploughed after they have been watered intensively to loose the soil and make it workable. Afterwards, weekly/monthly ploughing is needed to break up the crust on the soil surface and thus to support the water nutrient uptake as well as the root/plant development.'),
(5, 'Harvesting', 'Field work', 0, 'hours', ''),
(6, 'Weeding', 'Field work', 0, 'hours', 'Control whether the weed population is interfering with the development of the main crop.'),
(7, 'Irrigation', 'Field work', 0, 'L/plot', 'The crops are spot irrigated according to their needs or a given calendar. The amount of water (1can = 10L) used for one plot and the date/time of watering has to be recorded. To avoid bias, the signs of water needs and the amount of water used for irrigation are defined for each crop in advance. After applying compost, plots have to be irrigated intensively, so that the nutrients are washed into the soil and compost and soil components are better mixed.'),
(8, 'Pest control preparation', 'Pest control', 0, 'hours', 'Recipe 1 (maize): 1kg of Neem leaves, 200g hot chillies and 100g garlic are finely pound in a big mortar. The mixed material is then dissolved in 10L of fresh water and soaked for one night (12h). The next day the solution is sieved and is ready for spraying.**Recipe 2 (maize): Mucuna beans are collected and cooked for one hour several times, exhanging the water with each cooking. The first three volumes of cooking water are then sieved and used for spraying the crops/plants.**Recipe 3 (maize): Ash from fireplaces is collected, sieved and mixed with sun-dried and powdered Pilipili.**Recipe 4 (cassava): At least 1kg of Neem leaves is collected and finely pound in a big mortar. The pounded leaves are then dissolved in 10L of fresh water and soaked for one night (12h). The next day the solution is sieved and is ready for spraying.'),
(9, 'Pest control application', 'Pest control', 0, 'litres', 'Rats: clear the vegetation around the field and set up 3 to 5 rat traps within the plots to avoid rats feeding on the crops.**Insects (daily control): application of the prepared pest control according to the occurrence and intensity of any corresponding pest infestation. For maize, each plant has to be sprayed with the preparation every second day. For cassava, the basis of the stems need to be sprayed twice or three times per week.'),
(10, 'Compost preparation', 'Soil management', 0, 'hours', 'Start a compost by filling a pit with layers of ash, dried maize stems, mature grass, legumes, manure and top soil. Water extensively and repeat several times until the pit is full. Finally, the filled pit is covered with grass or a permeable tarpaulin. The compost in the pit has to be stirred regularly so that it doesn\\''t get too hot or anaerobic - this can be controlled by a wooden stick which is placed in the middle of the compost pit and which works as thermometer.'),
(11, 'Compost application', 'Soil management', 0, 'kg', 'Mix the soil with compost. Each S-plot gets 10 buckets of compost (1bucket = 10kg) which has to be evenly distributed on the plot or according to the sowing scheme locally applied.'),
(12, 'Mulch preparation', 'Soil management', 0, 'hours', ''),
(13, 'Mulch application', 'Soil management', 0, 'kg', 'Cover the soil surface with a layer of fine-cut mature grass. The layer should be as thin as possible while covering the soil surface completely.'),
(15, 'Thinning', 'Field work', 0, 'hours', 'Only one plant should remain per spot. Remove (in case transplanting will follow) or cut off the weaker seedlings (at the stem basis in case gap-filling will be done subsequently) to support the development of the stronger seedlings.'),
(16, 'Pruning', 'Field work', 0, 'hours', ''),
(17, 'Sowing', 'Field work', 0, 'hours', 'Maize: sow the maize seeds according to the sowing scheme in a depth of approx. 5cm and cover the holes with a little soil. Two seeds are sown per spot. The sowing scheme can easily be laid out with the help of ropes with corresponding marking. In the end, there should be five rows of maize on each plot with 19 maize plants in one row, or 13 maize plants in one row on intercopped plots.**Cowpeas (with maize): Sow the cowpeas according to the sowing scheme in a depth of approx. 5cm between the maize and cover the holes with a little soil. Two seeds are sown per spot. There should be two rows of cowpeas between the maize rows and one cowpea between each maize in a row.**Cowpeas (with cassava): Sow the cowpeas according to the sowing scheme in a depth of approx. 5cm between the cassava sticks and cover the holes with a little soil. There should be three rows of cowpeas between the cassava rows and the margins, and three cowpeas between each cassava stick in a row.'),
(19, 'Top dressing application', 'Soil management', 0, 'hours', 'Application of 0,25 L of manure tea as top dressing on each plant.'),
(20, 'Top dressing preparation', 'Soil management', 0, 'hours', 'A water permeable bag has to be filled with approx. 20kg of fresh manure. The bag is then hanged into a 160L bucket which is filled with around 80L of water. With a tarpaulin cover, the manure bags will remain for 21 days in the water and moved from time to time until the manure tea is ready for top dressing. The manure tea can then be used for up to 4 months as top dressing if properly stored.'),
(21, 'Seed soaking', 'Field work', 0, 'hours', 'To speed up and support the germination of the maize seeds during the dry season, the seeds can be soaked in water the day before sowing.'),
(22, 'Gap filling', 'Field work', 0, 'hours', 'Follow the given sowing scheme and sowing recommendations and sow new seeds in the gaps between the germinated seedlings where the previous sowed seeds have not germinated. Sow again two seeds per spot.'),
(23, 'Transplanting', 'Field work', 0, 'hours', 'If the weather and soil conditions, as well as the condition of the seedlings, allow transplanting, take the removed and remaining healthy seedlings after thinning and plant them in the gaps between the germinated seedlings, where the previously sowed seeds have not germinated.'),
(24, 'Seed bed preparation', 'Field work', 0, 'hours', 'Rake the ploughed plots to get a fine seed bed texture.  In the case of cassava, dig holes according to the planting scheme (1 by 1 meter), whereby the planting scheme can easily be measured/laid out with the help of two 1m sticks. The removed soil is then loosened and mixed with compost, and then put back into the hole.'),
(25, 'Planting', 'Field work', 0, 'hours', '20cm cassava sticks are planted in a lopsided position (to promote a vertical and not horizontal root development) according to a 1x1m planting scheme (1m distance to each other and the margins), whereby 1/3 of the planted sticks should be above the ground and 2/3 below, and the leaf scars have to look upwards.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_x_crop_or_treatment`
--

CREATE TABLE IF NOT EXISTS `activity_x_crop_or_treatment` (
`activity_x_crop_or_treatment_id` int(10) unsigned NOT NULL,
  `activity_id` int(10) unsigned NOT NULL,
  `crop_id` int(10) unsigned DEFAULT NULL,
  `treatment_id` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=61 ;

--
-- Volcado de datos para la tabla `activity_x_crop_or_treatment`
--

INSERT INTO `activity_x_crop_or_treatment` (`activity_x_crop_or_treatment_id`, `activity_id`, `crop_id`, `treatment_id`) VALUES
(10, 14, NULL, 5),
(11, 14, NULL, 10),
(12, 11, NULL, 5),
(14, 10, NULL, 5),
(15, 13, NULL, 10),
(16, 12, NULL, 10),
(19, 4, 2, NULL),
(20, 17, 4, NULL),
(21, 17, 1, NULL),
(22, 17, 8, NULL),
(28, 16, 2, NULL),
(40, 21, 1, NULL),
(43, 25, 2, NULL),
(53, 9, NULL, 18),
(54, 9, NULL, 19),
(55, 9, NULL, 16),
(56, 8, NULL, 18),
(57, 8, NULL, 19),
(58, 8, NULL, 16),
(59, 19, NULL, 14),
(60, 20, NULL, 14);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Volcado de datos para la tabla `crop`
--

INSERT INTO `crop` (`crop_id`, `crop_name`, `crop_symbol`, `crop_variety_name`, `crop_used_for_intercropping`) VALUES
(1, 'Maize', 'C2', 'All', 0),
(2, 'Cassava', 'C1', 'All', 0),
(4, 'Cowpeas', 'L', 'All', 1),
(8, 'Pigeon peas', 'L', 'All', 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='Fields registered in the ag. research' AUTO_INCREMENT=40 ;

--
-- Volcado de datos para la tabla `field`
--

INSERT INTO `field` (`field_id`, `parent_field_id`, `user_id`, `field_date_created`, `field_name`, `field_replication_number`, `field_lat`, `field_lng`, `field_configuration`) VALUES
(23, 23, 1, '2017-02-24', 'Masasi', 1, '-10.792300', '38.894855', 'F=(2,1,1,1);G=(4,4);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,0,0);P=(2,4,0,0);P=(1,0,0,1);P=(2,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(1,4,1,1);P=(2,4,1,1);P=(2,0,1,0);P=(1,0,1,0);P=(2,0,1,1);P=(1,0,1,1);P=(2,4,1,0);P=(1,4,1,0);'),
(24, 23, 1, '2017-02-24', 'Masasi', 2, '-10.792300', '38.894855', 'F=(2,1,1,1);G=(4,4);P=(2,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(2,0,1,1);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,1,1);P=(2,4,1,1);P=(2,0,0,0);P=(1,0,0,0);P=(2,4,1,0);P=(1,4,1,0);P=(2,4,0,0);P=(1,4,0,0);P=(2,0,1,0);P=(1,0,1,0);'),
(25, 23, 1, '2017-02-24', 'Masasi', 3, '-10.792300', '38.894855', 'F=(2,1,1,1);G=(4,4);P=(2,0,1,0);P=(1,4,1,0);P=(1,0,1,1);P=(2,4,1,1);P=(1,0,1,0);P=(2,4,1,0);P=(1,4,1,1);P=(2,0,1,1);P=(2,4,0,0);P=(1,0,0,0);P=(2,0,0,1);P=(1,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(2,4,0,1);P=(1,0,0,1);'),
(34, 34, 3, '2017-04-19', 'Morogoro', 1, '-6.741821', '37.550743', 'F=(2,1,1,1);G=(4,4);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,0,0);P=(2,4,0,0);P=(1,0,0,1);P=(2,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(1,4,1,1);P=(2,4,1,1);P=(1,0,1,0);P=(2,0,1,0);P=(2,0,1,1);P=(1,0,1,1);P=(1,4,1,0);P=(2,4,1,0);'),
(35, 34, 3, '2017-04-19', 'Morogoro', 2, '-6.741821', '37.550743', 'F=(2,1,1,1);G=(4,4);P=(2,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(2,0,1,1);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,1,1);P=(2,4,1,1);P=(2,0,0,0);P=(1,0,0,0);P=(2,4,1,0);P=(1,4,1,0);P=(2,4,0,0);P=(1,4,0,0);P=(2,0,1,0);P=(1,0,1,0);'),
(36, 34, 3, '2017-04-19', 'Morogoro', 3, '-6.741821', '37.550743', 'F=(2,1,1,1);G=(4,4);P=(2,0,1,0);P=(1,4,1,0);P=(1,0,1,1);P=(2,4,1,1);P=(1,0,1,0);P=(2,4,1,0);P=(1,4,1,1);P=(2,0,1,1);P=(2,4,0,0);P=(1,0,0,0);P=(2,0,0,1);P=(1,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(2,4,0,1);P=(1,0,0,1);'),
(37, 37, 1, '2018-02-01', 'Chambezi', 1, '-6.553823333333334', '38.91755', 'F=(2,1,1,1);G=(4,4);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,0,0);P=(2,4,0,0);P=(1,0,0,1);P=(2,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(1,4,1,1);P=(2,4,1,1);P=(2,0,1,0);P=(1,0,1,0);P=(2,0,1,1);P=(1,0,1,1);P=(2,4,1,0);P=(1,4,1,0);'),
(38, 37, 1, '2018-02-01', 'Chambezi', 2, '-6.553823333333334', '38.91755', 'F=(2,1,1,1);G=(4,4);P=(2,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(2,0,1,1);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,1,1);P=(2,4,1,1);P=(2,0,0,0);P=(1,0,0,0);P=(2,4,1,0);P=(1,4,1,0);P=(2,4,0,0);P=(1,4,0,0);P=(2,0,1,0);P=(1,0,1,0);'),
(39, 37, 1, '2018-02-01', 'Chambezi', 3, '-6.553823333333334', '38.91755', 'F=(2,1,1,1);G=(4,4);P=(2,0,1,0);P=(1,4,1,0);P=(1,0,1,1);P=(2,4,1,1);P=(1,0,1,0);P=(2,4,1,0);P=(1,4,1,1);P=(2,0,1,1);P=(2,4,0,0);P=(1,0,0,0);P=(2,0,0,1);P=(1,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(2,4,0,1);P=(1,0,0,1);');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `field_history`
--

CREATE TABLE IF NOT EXISTS `field_history` (
`field_history_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `valid_until` date NOT NULL,
  `field_configuration` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `health_report_item`
--

CREATE TABLE IF NOT EXISTS `health_report_item` (
`health_report_item_id` int(10) unsigned NOT NULL,
  `item` varchar(100) NOT NULL,
  `item_categories` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `health_report_item`
--

INSERT INTO `health_report_item` (`health_report_item_id`, `item`, `item_categories`) VALUES
(1, 'Disease', 'Don\\''t know,Brown streak,Lethal necrosis,Mosaic,Other ...'),
(2, 'Pest', 'Don\\''t know,Ants/Termites,Beetles,Birds,Fungi,Locusts/Grasshoppers,Mites,Moths,Rats,Worms/Larvae,Other ...'),
(3, 'Discoloration', 'Brown,Dark green,Light green,Purple,Yellow,Other ...'),
(4, 'Water stress', 'Dry leaves,Rolled leaves,Other ...');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `input_log`
--

CREATE TABLE IF NOT EXISTS `input_log` (
`input_log_id` int(10) unsigned NOT NULL,
  `input_log_date` date NOT NULL,
  `field_id` int(10) unsigned NOT NULL,
  `plots` varchar(200) NOT NULL,
  `user_id` int(11) NOT NULL,
  `crop_id` int(11) NOT NULL,
  `treatment_id` int(11) NOT NULL,
  `input_age` varchar(50) NOT NULL,
  `input_origin` varchar(200) NOT NULL,
  `input_crop_variety` varchar(100) NOT NULL,
  `input_quantity` int(10) unsigned NOT NULL,
  `input_units` text NOT NULL,
  `input_cost` varchar(100) NOT NULL,
  `input_treatment_material` text NOT NULL,
  `input_treatment_preparation_method` text NOT NULL,
  `input_comments` text NOT NULL,
  `input_picture` varchar(200) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=94 ;

--
-- Volcado de datos para la tabla `input_log`
--

INSERT INTO `input_log` (`input_log_id`, `input_log_date`, `field_id`, `plots`, `user_id`, `crop_id`, `treatment_id`, `input_age`, `input_origin`, `input_crop_variety`, `input_quantity`, `input_units`, `input_cost`, `input_treatment_material`, `input_treatment_preparation_method`, `input_comments`, `input_picture`) VALUES
(70, '2018-02-02', 23, '10,11,14,15,8,9,12,13', 14, 0, 5, '', '', '', 0, '', '200000', 'water*10.0*kg*dry grasses*20.0*cm*fresh grass*10.0*cm*fresh manure*50.0*cm*soil*10.0*cm', 'pit method', 'none', ''),
(71, '2018-02-02', 24, '14,15,10,11,2,3,6,7', 14, 0, 5, '', '', '', 0, '', '20', 'water*10.0*kg*dry grasses*20.0*cm*fresh grass*10.0*cm*fresh manure*50.0*cm*soil*10.0*cm', 'pit method', 'none (copied)', ''),
(72, '2018-02-02', 23, '\r\n8,9,10,11,12,13,14,15', 17, 0, 5, '', '', '', 0, '', '200000.00', 'water*10.0*kg*dried \r\nglasses*20.0*cm*fresh glasses*10.0*cm*fresh manure*5.0*cm*dried manure*5.0*\r\ncm*soil*5.0*cm', 'pit', '', ''),
(73, '2018-03-02', 23, '1,3,5,7,9,10,12,14', 17, 2, 0, '14', 'chambezi', 'm\r\nkuranga1', 100, 'kg', '40000.00', '', '', '', ''),
(74, '2018-03-02', 25, '6,3,0,4,7,2\r\n,5,1', 17, 0, 5, '', '', '', 0, '', '200000.00', 'water*10.0*kg*dried glasses*20.0*cm*f\r\nresh glasses*10.0*cm*fresh manure*5.0*cm*dried manure*5.0*cm*soil*5.0*cm', 'pi\r\nt', ' (copied)', ''),
(75, '2018-03-02', 23, '1,3,5,7,9,10,12,14', 16, 2, 0, '14', 'Chambezi', 'Mkuranga 1', 100, 'kg', '40000.00', '', '', '', ''),
(76, '2018-03-02', 24, '5,12,0,8,7,14,3,10', 16, 2, 0, '14', 'Chambezi', 'Mkuranga\r\n 1', 100, 'kg', '40000.00', '', '', ' (copied)', ''),
(77, '2018-03-02', 25, '10,8,14,13,3,0,7,5', 16, 2, 0, '14', '\r\nChambezi', 'Mkuranga 1', 100, 'kg', '40000.00', '', '', ' (copied)', ''),
(79, '2018-03-06', 34, '0', 19, 1, 0, '99', 'ASA', 'TMV1', 7, '7kg', '6000.00', '', '', '', ''),
(80, '2018-03-06', 35, '4', 19, 1, 0, '99', 'ASA', 'TMV1', 7, '7kg', '6000.00', '', '', ' (copied)', ''),
(81, '2018-03-06', 36, '11', 19, 1, 0, '99', 'ASA', 'TMV1', 7, '7kg', '6000.00', '', '', ' (copied)', ''),
(82, '2018-03-06', 34, '0,1,4,5,8,9,12,13', 19, 0, 16, '', '', '', 0, '', '10000.00', 'Neem*1.0*kg*Hot paper*0.5*kg*Aloe*2.0*kg*Garlic*0.5*kg', 'Mixing', 'This mixture is very efficiant', ''),
(83, '2018-03-06', 35, '4,5,1,0,2,7,3,6', 19, 0, 16, '', '', '', 0, '', '10000.00', 'Neem*1.0*kg*Hot paper*0.5*kg*Aloe*2.0*kg*Garlic*0.5*kg', 'Mixing', 'This mixture is very efficiant (copied)', ''),
(84, '2018-03-06', 36, '11,10,15,14,6,3,7,2', 19, 0, 16, '', '', '', 0, '', '10000.00', 'Neem*1.0*kg*Hot paper*0.5*kg*Aloe*2.0*kg*Garlic*0.5*kg', 'Mixing', 'This mixture is very efficiant (copied)', ''),
(85, '2018-03-03', 23, '0,4,8,13', 16, 0, 20, '', '', '', 0, '', '2000.00', 'cow milk*1.0*L*water*4.0*L', 'mixing', '', ''),
(86, '2018-03-03', 24, '4,1,2,6', 16, 0, 20, '', '', '', 0, '', '2000.00', 'cow milk*1.0*L*water*4.0*L', 'mixing', ' (copied)', ''),
(87, '2018-03-03', 25, '11,15,6,2', 16, 0, 20, '', '', '', 0, '', '2000.00', 'cow milk*1.0*L*water*4.0*L', 'mixing', ' (copied)', ''),
(88, '2018-03-07', 34, '0,2,4,6,8,10,13,14', 19, 1, 0, '1', 'ASA', 'STUKA', 458, '2 kg', '6000.00', '', '', 'This maize variety is better in our site depending on the type of climate condition of Vianzi', ''),
(89, '2018-03-07', 35, '4,9,1,13,2,15,6,11', 19, 1, 0, '1', 'ASA', 'STUKA', 458, '2 kg', '6000.00', '', '', 'This maize variety is better in our site depending on the type of climate condition of Vianzi (copied)', ''),
(90, '2018-03-07', 36, '11,9,15,12,6,4,2,1', 19, 1, 0, '1', 'ASA', 'STUKA', 458, '2 kg', '6000.00', '', '', 'This maize variety is better in our site depending on the type of climate condition of Vianzi (copied)', ''),
(91, '2018-03-07', 34, '8,9,10,11,12,13,14,15', 19, 0, 5, '', '', '', 0, '', '200000.00', 'Maasai Manure*3000.0*kg*Remains of Foods*1000.0*kg', 'Pile mixing', 'The compost has decompose well and was realy ready to be used in our plots ', ''),
(92, '2018-03-07', 35, '2,7,15,14,3,6,11,10', 19, 0, 5, '', '', '', 0, '', '200000.00', 'Maasai Manure*3000.0*kg*Remains of Foods*1000.0*kg', 'Pile mixing', 'The compost has decompose well and was realy ready to be used in our plots  (copied)', ''),
(93, '2018-03-07', 36, '6,3,4,0,7,2,1,5', 19, 0, 5, '', '', '', 0, '', '200000.00', 'Maasai Manure*3000.0*kg*Remains of Foods*1000.0*kg', 'Pile mixing', 'The compost has decompose well and was realy ready to be used in our plots  (copied)', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `log`
--

CREATE TABLE IF NOT EXISTS `log` (
`log_id` int(10) unsigned NOT NULL,
  `field_id` int(10) unsigned NOT NULL,
  `plots` varchar(200) NOT NULL,
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
  `log_number_of_laborers` varchar(100) NOT NULL,
  `log_cost` varchar(100) NOT NULL,
  `log_comments` text NOT NULL,
  `log_picture` varchar(200) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=261 ;

--
-- Volcado de datos para la tabla `log`
--

INSERT INTO `log` (`log_id`, `field_id`, `plots`, `user_id`, `crop_id`, `sample_number`, `treatment_id`, `measurement_id`, `activity_id`, `log_date`, `log_value_number`, `log_value_units`, `log_value_text`, `log_number_of_laborers`, `log_cost`, `log_comments`, `log_picture`) VALUES
(76, 23, '1,3,5,7,9,10,12,14', 14, 0, '0', 0, 0, 24, '2018-02-18', 8, 'hours', '', '2', '150000', 'we planted the cassava cuttings into pits', ''),
(77, 23, '1,3,5\r\n,7,9,10,12,14', 14, 0, '0', 0, 0, 25, '2018-02-21', 6, 'hours', '', '4', '200000.00', 'none', ''),
(78, 24, '5,12,0\r\n,8,7,14,3,10', 14, 0, '0', 0, 0, 25, '2018-02-21', 6, 'hours', '', '4', '200000.00', 'none (copied)', ''),
(79, 23, '7', 14, 0, '0', 0, 55, 0, '2018-02-22', 10, 'per plot', '', '', '', 'none', ''),
(80, 23, '1', 14, 0, '0', 0, 55, 0, '2018-02-22', 10, 'per plot', '', '', '', '', ''),
(81, 24, '14,10,3,7', 14, 0, '0', 0, 0, 11, '2018-02-19', 100, 'litres', '', '2', '50000', ' (copied)', ''),
(82, 25, '0,4,5,1', 14, 0, '0', 0, 0, 11, '2018-03-02', 100, 'litres', '', '2', '50000.00', ' (copied)', ''),
(83, 23, '0', 14, 0, '0', 0, 12, 0, '2018-03-02', 0, 'cm', '1*20*2*10*3*12*4*15\r\n*5*17*6*20', '', '', 'none', ''),
(84, 23, '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15', 17, 0, '0', 0, 0, 1, '2018-02-15', 14, 'hours', '', '\r\n4', '200000.00', '', ''),
(86, 25, '11,10,9,8,15,14,12,13,6,3,0,4,7,2\r\n,5,1', 17, 0, '0', 0, 0, 1, '2018-02-15', 14, 'hours', '', '4', '200000.00', ' (copied)', ''),
(87, 23, '0,1,2,3,4\r\n,5,6,7,8,9,10,11,12,13,14,15', 17, 0, '0', 0, 0, 24, '2018-02-18', 8, 'hours', '', '2', '150000.00', '', ''),
(88, 24, '4,5,9,12,1,0,13,8,2,7,14,15,3,6,10,11', 17, 0, '0', 0, 0, 24, '2018-02-18', 8, 'hour\r\ns', '', '2', '150000.00', ' (copied)', ''),
(89, 25, '11,10,9,8,15,14,12,13,6,3,0,4,7,2,5,1', 17, 0, '0', 0, 0, 24, '2018-02-18', 8, 'hours', '', '2', '150000.00', ' (copied)', ''),
(91, 24, '14,10', 17, 0, '0', 0, 0, 11, '2018-02-19', 200, 'kg', '', '2\r\n', '50000.00', ' (copied)', ''),
(92, 25, '0,5', 17, 0, '0', 0, 0, 11, '2018-02-19', 200, 'kg', '', '2', '50000.00', ' (cop\r\nied)', ''),
(96, 23, '7', 17, 0, '0', 0, 55, 0, '2018-03-02', 10, 'per plot', '', '', '', '', ''),
(97, 23, '1', 17, 0, '0', 0, 55, 0, '2018-03-02', 10, 'pe\r\nr plot', '', '', '', '', ''),
(98, 23, '3', 17, 0, '0', 0, 55, 0, '2018-03-02', 10, 'per plot', '', '', '', '', ''),
(99, 23, '5', 17, 0, '0', 0, 55, 0, '2018-03-02', 10, 'per plot', '', '', '', '', ''),
(101, 23, '9', 17, 0, '0', 0, 55, 0, '2018-03-02', 10, 'per plot', '', '', '', '', ''),
(102, 23, '10', 17, 0, '0', 0, 55, 0, '2018-03-02', 10, 'per plot', '', '', '', '', ''),
(103, 23, '12', 17, 0, '0', 0, 55, 0, '2018-03-02', 10, '\r\nper plot', '', '', '', '', ''),
(104, 23, '14', 17, 0, '0', 0, 55, 0, '2018-03-02', 10, 'per plot', '', '', '', '', ''),
(105, 24, '1', 17, 0, '0', 0, 12, 0, '2018-03-02', 0, 'cm', '1*20*2*10*3*12*4*15*5*17*6*20', '', '', '', ''),
(106, 23, '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15', 16, 0, '0', 0, 0, 1, '2018-02-15', 2, 'days', '', '4', '200000.00', '', ''),
(107, 24, '4,5,9,12,1,0,13,8,2,7,14,15,3,6,10,11', 16, 0, '0', 0, 0, 1, '2018-02-15', 2, 'days', '', '4', '200000.00', ' (copied)', ''),
(108, 25, '11,10,9,8,15,14,12,13,6,3,0,4,7,2,5,1\r\n', 16, 0, '0', 0, 0, 1, '2018-02-15', 2, 'days', '', '4', '200000.00', ' (copied)', ''),
(109, 23, '1,3,5,7,9,12,14\r\n,10', 16, 0, '0', 0, 0, 24, '2018-02-18', 8, 'hours', '', '2', '150000', 'the 30 cm depth with 30 cm wide pit were filled with 2 kg composit', ''),
(111, 25, '10,8,14,13,3,7,5,0', 16, 0, '0', 0, 0, 24, '2018-02-01', 8, 'hours', '', '2', '150000', 'the 30 cm depth with 30 cm wide pit were filled with 2 kg composit (copied)', ''),
(112, 23, '10,14,12,9', 16, 0, '0', 0, 0, 11, '2018-02-19', 100, 'litre(L)', '\r\n', '2', '50000', '', ''),
(113, 24, '14,10', 16, 0, '0', 0, 0, 11, '2018-02-19', 100, 'litre(L)', '', '2', '50000.00', ' (co\r\npied)', ''),
(114, 25, '0,5', 16, 0, '0', 0, 0, 11, '2018-02-19', 100, 'litre(L)', '', '2', '50000.00', ' (copied)', ''),
(115, 24, '1', 16, 0, '0', 0, 12, 0, '2018-03-02', 0, 'cm', '1*20*2*10*3*12*4*15*5*17*6*20', '', '', '', ''),
(158, 24, '5,12,0,8,7,14,3,10', 16, 0, '0', 0, 0, 5, '2017-11-12', 6, 'hours', '', '2', '50000.00', ' (copied)', ''),
(159, 25, '10,8,14,13,3,0,7,5', 16, 0, '0', 0, 0, 5, '2017-11-12', 6, 'hours', '', '2', '50000.00', ' (copied)', ''),
(160, 23, '1,3,5,6,7,9,10,12,14', 16, 0, '0', 0, 0, 5, '2018-02-12', 6, 'hours', '', '2', '50000.00', '', ''),
(161, 23, '0,2,4,6,8,11,13,15', 16, 0, '0', 0, 0, 7, '2018-02-26', 40, 'L/plot', '', '2', '40000.00', '', ''),
(162, 24, '4,9,1,13,2,15,6,11', 16, 0, '0', 0, 0, 7, '2018-02-26', 40, 'L/plot', '', '2', '40000.00', ' (copied)', ''),
(163, 25, '11,9,15,12,6,4,2,1', 16, 0, '0', 0, 0, 7, '2018-02-26', 40, 'L/plot', '', '2', '40000.00', ' (copied)', ''),
(164, 23, '0,4,8,13', 16, 0, '0', 0, 0, 8, '2018-03-03', 1, 'hours', '', '1', '5000.00', '', ''),
(165, 24, '4,1,2,6', 16, 0, '0', 0, 0, 8, '2018-03-03', 1, 'hours', '', '1', '5000.00', ' (copied)', ''),
(166, 25, '11,15,6,2', 16, 0, '0', 0, 0, 8, '2018-03-03', 1, 'hours', '', '1', '5000.00', ' (copied)', ''),
(167, 23, '1,5,9,12', 16, 0, '0', 0, 0, 9, '2018-03-03', 5, 'litres', '', '1', '10000.00', '', ''),
(168, 24, '5,0,7,3', 16, 0, '0', 0, 0, 9, '2018-03-03', 5, 'litres', '', '1', '10000.00', ' (copied)', ''),
(169, 25, '10,14,3,7', 16, 0, '0', 0, 0, 9, '2018-03-03', 5, 'litres', '', '1', '10000.00', ' (copied)', ''),
(170, 23, '12', 16, 0, '0', 0, 39, 0, '2018-03-07', 3, 'per plot', '', '', '', '', ''),
(171, 23, '10', 16, 0, '0', 0, 39, 0, '2018-03-07', 5, 'per plot', '', '', '', '', ''),
(172, 23, '3', 16, 0, '0', 0, 39, 0, '2018-03-07', 4, 'per plot', '', '', '', '', ''),
(173, 23, '12', 16, 0, '0', 0, 79, 0, '2018-03-07', 0, 'tubers', '1*9*2*11*3*3', '', '', '', ''),
(174, 23, '10', 16, 0, '0', 0, 79, 0, '2018-03-07', 0, 'tubers', '1*5*2*6*3*9*4*10*5*11', '', '', '', ''),
(175, 23, '3', 16, 0, '0', 0, 79, 0, '2018-03-07', 0, 'tubers', '1*12*2*10*3*11*4*3', '', '', '', ''),
(176, 23, '12', 16, 0, '0', 0, 24, 0, '2018-03-07', 12.5, 'kg per plot', '', '', '', '', ''),
(177, 23, '10', 16, 0, '0', 0, 24, 0, '2018-03-07', 20, 'kg per plot', '', '', '', '', ''),
(178, 23, '3', 16, 0, '0', 0, 24, 0, '2018-03-07', 15, 'kg per plot', '', '', '', '', ''),
(179, 34, '0,3,4,7,8,11,12,15', 19, 0, '0', 0, 0, 1, '2018-03-06', 10, 'hours', '', '8', '0.00', '', ''),
(180, 35, '4,12,1,8,2,14,3,10', 19, 0, '0', 0, 0, 1, '2018-03-06', 10, 'hours', '', '8', '0.00', ' (copied)', ''),
(181, 36, '11,8,15,13,6,0,7,5', 19, 0, '0', 0, 0, 1, '2018-03-06', 10, 'hours', '', '8', '0.00', ' (copied)', ''),
(182, 34, '5', 19, 0, '0', 0, 78, 0, '2018-03-06', 9, 'plants', '', '', '', '', ''),
(183, 34, '7', 19, 0, '0', 0, 78, 0, '2018-03-06', 6, 'plants', '', '', '', '', ''),
(184, 34, '3', 19, 0, '0', 0, 78, 0, '2018-03-06', 6, 'plants', '', '', '', '', ''),
(185, 34, '1', 19, 0, '0', 0, 78, 0, '2018-03-06', 9, 'plants', '', '', '', '', ''),
(186, 34, '11', 19, 0, '0', 0, 78, 0, '2018-03-06', 5, 'plants', '', '', '', '', ''),
(187, 34, '1', 19, 0, '0', 0, 48, 0, '2018-03-06', 0, 'cm', '1*23*2*58*3*36', '', '', '', ''),
(188, 34, '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15', 19, 0, '0', 0, 0, 24, '2018-03-07', 10, '9 Hours', '', '4', '200000.00', 'The task was completed successful and the plots looks wonderful', ''),
(189, 35, '4,5,9,12,1,0,13,8,2,7,15,14,3,6,11,10', 19, 0, '0', 0, 0, 24, '2018-03-07', 10, '9 Hours', '', '4', '200000.00', 'The task was completed successful and the plots looks wonderful (copied)', ''),
(190, 36, '11,10,9,8,15,14,12,13,6,3,4,0,7,2,1,5', 19, 0, '0', 0, 0, 24, '2018-03-07', 10, '9 Hours', '', '4', '200000.00', 'The task was completed successful and the plots looks wonderful (copied)', ''),
(191, 34, '0', 19, 0, '0', 0, 8, 0, '2018-03-07', 95, 'per plot', '', '', '', 'The plots last time produce more yield due to soil fertility treatment', ''),
(192, 34, '0', 19, 0, '0', 0, 58, 0, '2018-03-07', 6.5, 'kg per plot', '', '', '', 'Good yield', ''),
(193, 23, '8', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*47*2*65*3*37*4*60*5*64*6*68*7*56*8*58*9*54*10*58*11*82*12*67*13*64*14*47*15*77*16*57*17*70*18*71*19*75*20*50*21*79*22*35*23*61*24*54*25*55*26*49*27*59*28*64*29*79*30*32*31*75*32*63*33*70*34*58*35*55*36*63*37*67*38*74*39*60*40*57*41*73*42*58*43*56*44*83*45*87*46*71*47*63*48*38*49*58*50*64*51*60*52*65*53*70*54*67*55*70*56*65*57*74*58*61*59*69*60*66*61*73*62*52*63*54', '', '', '', ''),
(194, 23, '13', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*59*2*61*3*57*4*54*5*44*6*70*7*40*8*56*9*48*10*36*11*32*12*37*13*40*14*31*15*49*16*51*17*56*18*66*19*51*20*60*21*39*22*54*23*50*24*54*25*61*26*40*27*64*28*59*29*56*30*64*31*72*32*44*33*54*34*55*35*57*36*67*37*51*38*57*39*66*40*57*41*37*42*72*43*71*44*66*45*46*46*50*47*70*48*69*49*66*50*50*51*59*52*53*53*54*54*57*55*60*56*70*57*65*58*63*59*56*60*66*61*62*62*66*63*37*64*60*65*66*66*61*67*69*68*31*69*39*70*27*71*68*72*50*73*39*74*29*75*56*76*68*77*51*78*46*79*38*80*37*81*44*82*50*83*66*84*63*85*52*86*58*87*40*88*45*89*52', '', '', '', ''),
(195, 23, '15', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*35*2*44*3*49*4*43*5*30*6*40*7*50*8*36*9*37*10*48*11*45*12*43*13*41*14*55*15*60*16*51*17*52*18*48*19*69*20*67*21*50*22*64*23*56*24*59*25*59*26*59*27*52*28*74*29*68*30*74*31*67*32*44*33*58*34*65*35*53*36*42*37*47*38*53*39*51*40*36*41*48*42*57*43*50*44*47*45*48*46*27*47*46*48*58*49*41*50*43*51*49*52*60*53*47*54*54*55*44*56*55*57*66*58*47*59*45*60*49*61*64*62*46*63*38', '', '', '', ''),
(196, 23, '11', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, '', '1*30*2*46*3*31*4*49*5*34*6*41*7*55*8*50*9*48*10*53*11*51*12*64*13*37*14*47*15*48*16*50*17*60*18*50*19*57*20*50*21*58*22*61*23*49*24*58*25*31*26*50*27*29*28*32*29*52*30*38*31*35*32*34*33*38*34*40*35*42*36*55*37*52*38*55*39*51*40*52*41*73*42*56*43*54*44*54*45*39*46*42*47*37*48*45*49*38*50*38*51*43*52*55*53*44*54*59*55*58*56*52*57*35*58*54*59*29*60*22*61*34*62*38*63*40*64*41*65*50*66*53*67*47*68*55*69*29*70*45*71*53*72*46*73*44*74*41*75*39*76*60*77*46*78*40*79*36', '', '', '', ''),
(197, 23, '4', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*39*2*40*3*45*4*37*5*48*6*30*7*40*8*33*9*48*10*39*11*20*12*31*13*29*14*48*15*43*16*26*17*40*18*26*19*26*20*27*21*43*22*51*23*36*24*27*25*40*26*27*27*34*28*31*29*41*30*48*31*35*32*45*33*43*34*52*35*67*36*32*37*20*38*40*39*38*40*28*41*41*42*42*43*35*44*33*45*52*46*28*47*26*48*33*49*36*50*50*51*35*52*38*53*56*54*37*55*39*56*51*57*24*58*37*59*57*60*19*61*33*62*26*63*25*64*32*65*32*66*38*67*46*68*20*69*34*70*23*71*26*72*26*73*40*74*44*75*34*76*29*77*48*78*35*79*47*80*47*81*30*82*37*83*41*84*50*85*52', '', '', '', ''),
(198, 23, '6', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*46*2*40*3*31*4*33*5*31*6*37*7*29*8*33*9*32*10*27*11*23*12*49*13*26*14*38*15*45*16*34*17*42*18*41*19*48*20*40*21*34*22*40*23*32*24*32*25*37*26*25*27*37*28*49*29*40*30*34*31*42*32*34*33*35*34*29*35*57*36*37*37*43*38*41*39*36*40*26*41*40*42*24*43*37*44*39*45*35*46*40*47*50*48*50*49*31*50*32*51*58*52*35*53*52*54*44*55*40*56*33*57*45*58*44*59*38*60*49*61*42', '', '', '', ''),
(199, 23, '2', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*27*2*26*3*40*4*38*5*39*6*45*7*51*8*58*9*55*10*42*11*19*12*43*13*43*14*44*15*40*16*42*17*37*18*47*19*33*20*21*21*36*22*49*23*45*24*50*25*30*26*27*27*43*28*54*29*52*30*49*31*48*32*40*33*32*34*33*35*49*36*45*37*45*38*50*39*45*40*41*41*46*42*37*43*27*44*25*45*27*46*38*47*30*48*34*49*31*50*40*51*34*52*44*53*24*54*38*55*40*56*35*57*34*58*23*59*38*60*44*61*22*62*14*63*22*64*17*65*32*66*31*67*29*68*28*69*28*70*22*71*38*72*41*73*38*74*22*75*34*76*28*77*40', '', '', '', ''),
(200, 24, '13', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*41*2*28*3*30*4*34*5*28*6*46*7*32*8*59*9*40*10*50*11*15*12*30*13*40*14*20*15*17*16*30*17*27*18*28*19*20*20*23*21*51*22*36*23*37*24*42*25*42*26*32*27*43*28*33*29*14*30*30*31*43*32*38*33*46*34*44*35*54*36*44*37*24*38*23*39*45*40*40*41*48*42*40*43*50*44*16*45*56*46*53*47*46*48*40*49*37*50*11*51*25*52*24*53*30*54*20*55*23*56*39*57*29*58*45*59*50*60*43*61*38*62*49*63*47', '', '', '', ''),
(201, 24, '9', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*37*2*40*3*40*4*44*5*41*6*36*7*37*8*52*9*42*10*37*11*37*12*26*13*46*14*39*15*42*16*43*17*37*18*46*19*44*20*41*21*45*22*43*23*61*24*32*25*51*26*52*27*47*28*41*29*51*30*33*31*36*32*38*33*40*34*45*35*21*36*25*37*31*38*25*39*36*40*31*41*43*42*35*43*45*44*37*45*25*46*43*47*33*48*50*49*23*50*54*51*55*52*45*53*54*54*48*55*36*56*55*57*55*58*51*59*49*60*42*61*40*62*38*63*46*64*31*65*41*66*32*67*45*68*32*69*28*70*43*71*30*72*33*73*27*74*37*75*40*76*24*77*38*78*10*79*20*80*20*81*27*82*45*83*36*84*38*85*41*86*42*87*45*88*53*89*60*90*45*91*48*92*46*93*26', '', '', '', ''),
(202, 24, '15', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*38*2*45*3*44*4*65*5*51*6*45*7*27*8*65*9*59*10*41*11*33*12*32*13*50*14*51*15*41*16*47*17*57*18*31*19*59*20*41*21*51*22*55*23*31*24*53*25*64*26*45*27*55*28*49*29*42*30*50*31*50*32*63*33*33*34*43*35*41*36*38*37*34*38*19*39*45*40*34*41*54*42*57*43*53*44*38*45*45*46*46*47*44*48*48*49*59*50*30*51*41*52*50*53*45*54*67*55*54*56*22*57*53*58*44*59*37*60*65*61*30*62*58*63*38*64*29*65*43*66*31*67*43*68*49*69*45*70*51*71*51*72*41*73*48*74*42*75*27*76*28*77*48*78*52*79*58*80*50*81*37', '', '', '', ''),
(203, 24, '11', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*67*2*30*3*43*4*58*5*51*6*51*7*55*8*46*9*47*10*47*11*62*12*53*13*55*14*44*15*30*16*49*17*46*18*46*19*46*20*46*21*45*22*68*23*56*24*52*25*57*26*46*27*53*28*54*29*52*30*45*31*46*32*58*33*53*34*60*35*48*36*33*37*62*38*58*39*57*40*61*41*63*42*56*43*28*44*37*45*43*46*56*47*45*48*60*49*68*50*58*51*61*52*66*53*60*54*55*55*65*56*56*57*49', '', '', '', ''),
(204, 24, '6', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*37*2*53*3*59*4*42*5*41*6*35*7*27*8*42*9*49*10*42*11*35*12*39*13*28*14*22*15*42*16*34*17*48*18*39*19*38*20*41*21*49*22*42*23*52*24*39*25*52*26*51*27*57*28*42*29*55*30*55*31*28*32*48*33*39*34*28*35*61*36*43*37*42*38*40*39*46*40*39*41*35*42*52*43*44*44*43*45*20*46*48*47*32*48*42*49*39*50*43*51*54*52*48*53*39*54*26*55*41*56*25*57*46*58*45*59*62*60*35*61*42*62*45*63*55*64*28*65*48*66*44*67*43*68*50*69*36*70*35', '', '', '', ''),
(205, 24, '2', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*30*2*24*3*49*4*65*5*60*6*44*7*40*8*29*9*46*10*56*11*47*12*46*13*48*14*56*15*50*16*61*17*50*18*26*19*45*20*59*21*62*22*60*23*48*24*27*25*30*26*37*27*45*28*50*29*62*30*53*31*58*32*57*33*47*34*43*35*65*36*33*37*47*38*52*39*57*40*70*41*60*42*61*43*48*44*52*45*34*46*37*47*59*48*53*49*20*50*38*51*59*52*44*53*45*54*58*55*51*56*55*57*33*58*55*59*53*60*38', '', '', '', ''),
(206, 24, '1', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*44*2*41*3*25*4*44*5*44*6*52*7*45*8*59*9*49*10*50*11*54*12*65*13*59*14*42*15*45*16*41*17*41*18*37*19*37*20*48*21*50*22*48*23*48*24*51*25*27*26*54*27*27*28*37*29*40*30*64*31*52*32*63*33*51*34*52*35*49*36*53*37*40*38*44*39*30*40*35*41*51*42*45*43*48*44*46*45*50*46*60*47*49*48*54*49*64*50*60*51*48*52*63*53*64*54*55*55*55*56*40*57*41*58*44*59*20*60*53*61*67*62*53*63*39*64*50*65*43*66*49*67*41*68*51*69*53*70*49*71*54*72*40*73*40*74*44*75*27*76*48*77*43*78*39*79*27*80*46*81*40*82*42*83*47*84*36*85*52*86*55*87*51*88*61*89*66*90*39*91*37', '', '', '', ''),
(207, 24, '4', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*43*2*39*3*35*4*19*5*27*6*26*7*38*8*34*9*38*10*22*11*24*12*30*13*31*14*17*15*21*16*33*17*16*18*35*19*55*20*41*21*45*22*53*23*25*24*58*25*58*26*53*27*47*28*62*29*54*30*44*31*28*32*49*33*35*34*30*35*30*36*45*37*55*38*28*39*37*40*24*41*30*42*50*43*33*44*59*45*43*46*50*47*55*48*40*49*55*50*33*51*48*52*48*53*40*54*23*55*45*56*59*57*45*58*25*59*59*60*48*61*56*62*41*63*39*64*46*65*30*66*37', '', '', '', ''),
(208, 25, '12', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*28*2*47*3*41*4*37*5*38*6*17*7*34*8*34*9*50*10*33*11*35*12*23*13*21*14*37*15*45*16*48*17*45*18*46*19*56*20*31*21*46*22*54*23*44*24*25*25*38*26*51*27*33*28*53*29*43*30*19*31*36*32*41*33*41*34*15*35*25*36*24*37*43*38*42*39*54*40*43*41*55*42*48*43*44*44*42*45*32*46*42*47*40*48*42*49*48*50*41*51*47*52*39*53*50*54*37*55*52*56*48*57*49*58*40', '', '', '', ''),
(209, 25, '9', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*47*2*39*3*33*4*19*5*41*6*44*7*41*8*32*9*46*10*50*11*45*12*49*13*57*14*26*15*46*16*24*17*40*18*31*19*35*20*35*21*41*22*24*23*37*24*36*25*51*26*36*27*57*28*50*29*26*30*45*31*48*32*34*33*37*34*57*35*38*36*37*37*35*38*31*39*39*40*44*41*41*42*34*43*38*44*45*45*38*46*38*47*39*48*50*49*38*50*38*51*28*52*40*53*54*54*58*55*52*56*42*57*46*58*35*59*43*60*27*61*40*62*27*63*38*64*40*65*45*66*37*67*19*68*48*69*44*70*38*71*32*72*38*73*45*74*32*75*33', '', '', '', ''),
(210, 25, '15', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*30*2*35*3*24*4*35*5*30*6*35*7*27*8*35*9*35*10*34*11*32*12*38*13*28*14*24*15*37*16*37*17*41*18*49*19*14*20*31*21*34*22*35*23*18*24*29*25*22*26*31*27*15*28*37*29*31*30*41*31*26*32*30*33*44*34*45*35*32*36*31*37*30*38*35*39*69*40*34*41*33*42*37*43*38*44*39*45*47*46*48*47*44*48*34*49*38*50*48*51*38*52*45*53*35*54*37*55*42*56*45*57*40*58*29*59*29*60*40*61*38*62*43*63*46*64*44*65*29*66*42*67*50*68*46*69*43*70*40*71*42*72*52*73*44*74*31*75*49', '', '', '', ''),
(211, 25, '11', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*30*2*31*3*35*4*20*5*28*6*37*7*28*8*28*9*19*10*23*11*26*12*35*13*32*14*35*15*48*16*38*17*25*18*17*19*22*20*31*21*32*22*22*23*31*24*31*25*17*26*20*27*27*28*46*29*45*30*28*31*35*32*31*33*22*34*28*35*31*36*24*37*25*38*37*39*40*40*24*41*46*42*53*43*34*44*32*45*29*46*29', '', '', '', ''),
(212, 25, '6', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*58*2*46*3*45*4*54*5*62*6*57*7*50*8*44*9*63*10*53*11*51*12*53*13*58*14*50*15*67*16*54*17*42*18*47*19*62*20*46*21*48*22*70*23*64*24*62*25*50*26*63*27*56*28*57*29*61*30*50*31*52*32*47*33*57*34*72*35*48*36*39*37*54*38*57*39*60*40*63*41*69*42*45*43*37*44*56*45*51*46*57*47*46*48*65*49*47*50*53*51*30*52*42*53*45*54*53*55*51*56*56*57*55*58*60*59*56*60*58*61*60*62*55*63*55', '', '', '', ''),
(213, 25, '2', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*50*2*60*3*44*4*46*5*31*6*60*7*36*8*39*9*34*10*51*11*40*12*49*13*43*14*42*15*53*16*41*17*54*18*58*19*58*20*56*21*39*22*55*23*66*24*60*25*70*26*60*27*57*28*60*29*36*30*45*31*56*32*50*33*33*34*54*35*46*36*56*37*62*38*47*39*45*40*51*41*44*42*45*43*51*44*54*45*25*46*60*47*59*48*66*49*50*50*61*51*60*52*48*53*53*54*28*55*58*56*61*57*62*58*55*59*55*60*54*61*65*62*56*63*65*64*55*65*38*66*36*67*50*68*56*69*41*70*47*71*41*72*49*73*52*74*25*75*57*76*56*77*52*78*50*79*45*80*60*81*48*82*54*83*53*84*66*85*52*86*51*87*66*88*53*89*58*90*62*91*40', '', '', '', ''),
(214, 25, '1', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*24*2*35*3*24*4*39*5*16*6*46*7*45*8*42*9*43*10*51*11*44*12*55*13*43*14*46*15*42*16*30*17*39*18*54*19*36*20*50*21*35*22*43*23*26*24*22*25*36*26*36*27*52*28*61*29*34*30*32*31*45*32*39*33*34*34*34*35*50*36*50*37*55*38*51*39*46*40*46*41*34*42*29*43*39*44*42*45*35*46*33*47*29*48*36*49*20*50*42*51*39*52*47*53*41*54*52*55*41*56*33*57*46*58*47', '', '', '', ''),
(215, 25, '4', 9, 0, '0', 0, 12, 0, '2018-03-14', 0, 'cm', '1*42*2*24*3*43*4*54*5*44*6*45*7*60*8*69*9*53*10*56*11*56*12*55*13*47*14*39*15*52*16*48*17*62*18*44*19*64*20*65*21*61*22*59*23*65*24*74*25*63*26*54*27*61*28*55*29*66*30*62*31*70*32*36*33*49*34*52*35*75*36*36*37*46*38*79*39*69*40*56*41*82*42*63*43*50*44*58*45*60*46*53*47*72*48*52*49*60*50*58*51*42*52*46*53*72*54*58*55*59*56*62*57*62*58*66*59*71*60*73*61*52*62*57*63*54*64*44*65*66*66*27*67*57*68*51*69*62*70*67*71*68*72*61*73*57*74*59*75*60*76*60*77*56*78*49*79*60*80*61*81*60*82*63*83*65', '', '', '', ''),
(216, 23, '0', 9, 0, '', 0, 12, 0, '2018-03-14', 0, 'cm', '1*36*2*40*3*37*4*39*5*26*6*46*7*38*8*27*9*40*10*35*11*32*12*28*13*18*14*24*15*29*16*16*17*43*18*43*19*33*20*33*21*24*22*38*23*28*24*33*25*19*26*49*27*35*28*28*29*32*30*47*31*48*32*40*33*45*34*45*35*47*36*17*37*20*38*40*39*24*40*23*41*46*42*17*43*53*44*20*45*40*46*55*47*33*48*20*49*32*50*40*51*26*52*30*53*33*54*47*55*38*56*37*57*25*58*40*59*24*60*41', '', '', '', ''),
(217, 23, '12', 9, 0, '0', 0, 54, 0, '2018-03-08', 8, 'per plot', '', '', '', '', ''),
(218, 23, '9', 9, 0, '0', 0, 54, 0, '2018-03-08', 9, 'per plot', '', '', '', '', ''),
(219, 23, '10', 9, 0, '0', 0, 54, 0, '2018-03-08', 9, 'per plot', '', '', '', '', ''),
(220, 23, '14', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(221, 23, '7', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(222, 23, '3', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(223, 23, '1', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(224, 23, '5', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(225, 24, '14', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(226, 24, '12', 9, 0, '0', 0, 54, 0, '2018-03-08', 9, 'per plot', '', '', '', '', ''),
(227, 24, '8', 9, 0, '0', 0, 54, 0, '2018-03-08', 9, 'per plot', '', '', '', '', ''),
(228, 24, '5', 9, 0, '0', 0, 54, 0, '2018-03-08', 9, 'per plot', '', '', '', '', ''),
(229, 24, '0', 9, 0, '0', 0, 54, 0, '2018-03-08', 8, 'per plot', '', '', '', '', ''),
(230, 24, '3', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(231, 24, '7', 9, 0, '0', 0, 54, 0, '2018-03-08', 8, 'per plot', '', '', '', '', ''),
(232, 24, '10', 9, 0, '0', 0, 54, 0, '2018-03-08', 8, 'per plot', '', '', '', '', ''),
(233, 25, '7', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(234, 25, '3', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(235, 25, '10', 9, 0, '0', 0, 54, 0, '2018-03-08', 9, 'per plot', '', '', '', '', ''),
(236, 25, '14', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(237, 25, '13', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(238, 25, '5', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(239, 25, '0', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(240, 25, '8', 9, 0, '0', 0, 54, 0, '2018-03-08', 10, 'per plot', '', '', '', '', ''),
(241, 23, '12', 9, 0, '0', 0, 77, 0, '2018-03-08', 0, '', '1* # #Ants/Termites# ', '', '', '', ''),
(242, 23, '9', 9, 0, '0', 0, 77, 0, '2018-03-08', 0, '', '1* # #Ants/Termites# *2* # #Ants/Termites# ', '', '', '', ''),
(243, 24, '12', 9, 0, '0', 0, 77, 0, '2018-03-08', 0, '', '1* # #Ants/Termites# ', '', '', '', ''),
(244, 24, '8', 9, 0, '0', 0, 77, 0, '2018-03-08', 0, '', '1* # #Ants/Termites# ', '', '', '', ''),
(245, 24, '5', 9, 0, '0', 0, 77, 0, '2018-03-08', 0, '', '1* # #Ants/Termites# *2* # #Ants/Termites# ', '', '', '', ''),
(246, 24, '0', 9, 0, '0', 0, 77, 0, '2018-03-08', 0, '', '1* # #Ants/Termites# *2* # #Ants/Termites# ', '', '', '', ''),
(247, 24, '7', 9, 0, '0', 0, 77, 0, '2018-03-08', 0, '', '1* # #Ants/Termites# ', '', '', '', ''),
(248, 24, '10', 9, 0, '0', 0, 77, 0, '2018-03-08', 0, '', '1* # #Ants/Termites# *2* # #Ants/Termites# ', '', '', '', ''),
(249, 25, '10', 9, 0, '0', 0, 77, 0, '2018-03-08', 0, '', '1* # #Ants/Termites# *2* # #Ants/Termites# ', '', '', '', ''),
(250, 23, '10', 9, 0, '0', 0, 77, 0, '2018-03-14', 0, '', '1* # #Ants/Termites# ', '', '', '', ''),
(251, 23, '12', 9, 0, '0', 0, 55, 0, '2018-03-08', 1, 'per plot', '', '', '', '', ''),
(252, 23, '9', 9, 0, '0', 0, 55, 0, '2018-03-08', 1, 'per plot', '', '', '', '', ''),
(253, 23, '10', 9, 0, '0', 0, 55, 0, '2018-03-08', 1, 'per plot', '', '', '', '', ''),
(254, 24, '12', 9, 0, '0', 0, 55, 0, '2018-03-08', 1, 'per plot', '', '', '', '', ''),
(255, 24, '8', 9, 0, '0', 0, 55, 0, '2018-03-08', 1, 'per plot', '', '', '', '', ''),
(256, 24, '5', 9, 0, '0', 0, 55, 0, '2018-03-08', 1, 'per plot', '', '', '', '', ''),
(257, 24, '0', 9, 0, '0', 0, 55, 0, '2018-03-08', 2, 'per plot', '', '', '', '', ''),
(258, 24, '7', 9, 0, '0', 0, 55, 0, '2018-03-08', 2, 'per plot', '', '', '', '', ''),
(259, 24, '10', 9, 0, '0', 0, 55, 0, '2018-03-08', 2, 'per plot', '', '', '', '', ''),
(260, 25, '10', 9, 0, '0', 0, 55, 0, '2018-03-08', 1, 'per plot', '', '', '', '', '');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=83 ;

--
-- Volcado de datos para la tabla `measurement`
--

INSERT INTO `measurement` (`measurement_id`, `measurement_name`, `measurement_category`, `measurement_subcategory`, `measurement_type`, `measurement_range_min`, `measurement_range_max`, `measurement_units`, `measurement_categories`, `measurement_periodicity`, `measurement_has_sample_number`, `measurement_common_complex`, `measurement_description`) VALUES
(8, 'Number of planted seeds', 'Maize', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Record how many seeds have been sown per plot.'),
(9, 'Number of germinated plants', 'Maize', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have germinated per plot.'),
(12, 'Plant size', 'Maize', 'Development', 1, 0, 500, 'cm', '', 0, 1, 1, 'Interval: Every two weeks.**Measure the height of each plant in a plot from the stem basis/soil surface to the tip of the longest leaf.'),
(24, 'Weight of harvested tubers', 'Cassava', 'Productivity', 1, 0, 1000, 'kg per plot', '', 0, 0, 1, 'Weigh all tubers that can be harvested from one plot and record the mass you get per plot.'),
(39, 'Number of survived plants', 'Cassava', 'Productivity', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have survived on each plot shortly before harvesting the crops on that plot.'),
(48, 'Plant size', 'Cassava', 'Development', 1, 0, 500, 'cm', '', 0, 1, 1, 'Interval: Every three weeks.**Measure the height of each plant in a plot from the stem basis/soil surface to the tip of the longest branch.'),
(54, 'Number of sprouted plants', 'Cassava', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have sprouted per plot.'),
(55, 'Number of planted sticks', 'Cassava', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Record how many sticks have been planted per plot.'),
(56, 'Number of survived plants', 'Maize', 'Productivity', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have survived on each plot shortly before harvesting the crops on that plot.'),
(58, 'Weight of harvested kernels', 'Maize', 'Productivity', 1, 0, 1000, 'kg per plot', '', 0, 0, 1, 'Weigh all kernels that can be harvested from one plot and record the mass you get per plot.'),
(59, 'Number of planted seeds', 'Pulses', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Record how many seeds have been sown per plot.'),
(60, 'Number of germinated plants', 'Pulses', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have germinated per plot.'),
(61, 'Number of survived plants', 'Pulses', 'Productivity', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have survived on each plot shortly before harvesting the crops on that plot.'),
(62, 'Weight of harvested grains', 'Pulses', 'Productivity', 1, 0, 1000, 'kg per plot', '', 0, 0, 0, 'Weigh all grains that can be harvested from one plot and record the mass you get per plot.'),
(76, 'Health report', 'Maize', 'Health', 2, 0, 0, '', '', 0, 1, 1, 'Interval: Check daily.**Report on each plant in a plot that shows any stress, disease or pest symptoms. Define the symptoms and the disease/pests as accurately as possible.'),
(77, 'Health report', 'Cassava', 'Health', 2, 0, 0, '', '', 0, 1, 1, 'Interval: Check daily.**Report on each plant in a plot that shows any stress, disease or pest symptoms. Define the symptoms and the disease/pests as accurately as possible.'),
(78, 'Number of flowering plants', 'Cassava', 'Development', 1, 0, 1000, 'plants', '', 0, 0, 1, 'Determine the number of plants flowering or tasseling in one plot.'),
(79, 'Number of harvested tubers per plant', 'Cassava', 'Productivity', 1, 0, 100, 'tubers', '', 0, 1, 0, 'Count all the tubers which can be harvested form one plant.'),
(80, 'Number of leaves', 'Maize', 'Development', 1, 0, 200, 'leaves', '', 0, 1, 1, 'Interval: Every two weeks.'),
(81, 'Number of flowering plants', 'Maize', 'Development', 1, 0, 1000, 'plants', '', 0, 0, 1, 'Determine the number of plants flowering or tasseling in one plot.'),
(82, 'Soil moisture', 'Soil', 'Soil moisture', 1, 0, 100, 'percentage', '', 0, 1, 1, 'Interval: every two weeks**Measure soil moisture preferably before an irrigation/rain event or when water stress symptoms start to occur. Use soil moisture sensor and measure the soil moisture at ten randomly distributed spots in a plot and record each value.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `measurement_x_crop_or_treatment`
--

CREATE TABLE IF NOT EXISTS `measurement_x_crop_or_treatment` (
`measurement_x_crop_or_treatment_id` int(10) unsigned NOT NULL,
  `measurement_id` int(10) unsigned NOT NULL,
  `crop_id` int(10) unsigned DEFAULT NULL,
  `treatment_id` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=65 ;

--
-- Volcado de datos para la tabla `measurement_x_crop_or_treatment`
--

INSERT INTO `measurement_x_crop_or_treatment` (`measurement_x_crop_or_treatment_id`, `measurement_id`, `crop_id`, `treatment_id`) VALUES
(1, 11, 1, NULL),
(3, 33, 2, NULL),
(4, 32, 4, NULL),
(5, 32, 1, NULL),
(7, 15, 2, NULL),
(8, 16, 2, NULL),
(9, 14, 2, NULL),
(10, 13, 2, NULL),
(11, 47, 2, NULL),
(12, 44, 2, NULL),
(13, 48, 2, NULL),
(14, 29, 2, NULL),
(15, 17, 2, NULL),
(16, 21, 2, NULL),
(17, 27, 2, NULL),
(18, 55, 2, NULL),
(19, 54, 2, NULL),
(20, 39, 2, NULL),
(21, 24, 2, NULL),
(22, 57, 2, NULL),
(23, 10, 1, NULL),
(24, 12, 1, NULL),
(25, 49, 1, NULL),
(26, 50, 1, NULL),
(27, 51, 1, NULL),
(28, 52, 1, NULL),
(29, 45, 1, NULL),
(30, 53, 1, NULL),
(31, 9, 1, NULL),
(32, 8, 1, NULL),
(33, 23, 1, NULL),
(34, 56, 1, NULL),
(35, 58, 1, NULL),
(36, 25, 1, NULL),
(37, 60, 4, NULL),
(38, 60, 8, NULL),
(39, 59, 4, NULL),
(40, 59, 8, NULL),
(41, 61, 4, NULL),
(42, 61, 8, NULL),
(43, 62, 4, NULL),
(44, 62, 8, NULL),
(45, 63, 4, NULL),
(46, 63, 8, NULL),
(47, 69, 2, NULL),
(48, 70, 2, NULL),
(49, 68, 2, NULL),
(50, 66, 1, NULL),
(51, 67, 1, NULL),
(52, 71, 2, NULL),
(53, 72, 1, NULL),
(54, 73, 4, NULL),
(55, 73, 8, NULL),
(56, 75, 4, NULL),
(57, 75, 8, NULL),
(58, 74, 1, NULL),
(59, 76, 1, NULL),
(60, 77, 2, NULL),
(61, 78, 2, NULL),
(62, 79, 2, NULL),
(63, 81, 1, NULL),
(64, 80, 1, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
`notification_id` int(10) unsigned NOT NULL,
  `sender_id` int(10) unsigned NOT NULL,
  `receiver_id` int(10) NOT NULL,
  `notification_date` date NOT NULL,
  `notification_text` text NOT NULL,
  `notification_sent` tinyint(3) unsigned NOT NULL DEFAULT '0'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=32 ;

--
-- Volcado de datos para la tabla `notification`
--

INSERT INTO `notification` (`notification_id`, `sender_id`, `receiver_id`, `notification_date`, `notification_text`, `notification_sent`) VALUES
(16, 3, 1, '2017-09-18', 'First message for Eugenio', 1),
(17, 3, 1, '2017-09-18', 'Second message for Eugenio', 1),
(18, 3, 1, '2017-09-18', 'Third message for Eugenio', 1),
(19, 3, 1, '2017-09-18', 'Sending a very long message to Eugenio. Sending a very long message to Eugenio. Sending a very long message to Eugenio. Sending a very long message to Eugenio. Sending a very long message to Eugenio. Sending a very long message to Eugenio. Sending a very long message to Eugenio. Sending a very long message to Eugenio. ', 1),
(20, 3, 1, '2017-09-18', 'Testing ''again''', 1),
(21, 2, 1, '2017-09-18', 'hello this is a test', 1),
(22, 2, 1, '2017-09-25', 'Please report any pest-related problems as soon as possible.', 1),
(24, 2, 1, '2017-09-27', 'Hello, welcome to the AgroEco Research app!', 1),
(25, 2, 1, '2017-10-01', 'Hello this is a test', 1),
(26, 2, 1, '2017-10-01', 'Please make sure that you download catalogs again -- changes have been made.', 1),
(28, 13, 7, '2017-10-03', 'Please remember to test the application. Thank you.', 1),
(29, 1, 1, '2018-01-09', 'Message to self', 1),
(30, 1, 1, '2018-01-17', 'Hello, this is a new test. Don''t panic if things look strange.\r\n\r\nHere is another sentence.', 1),
(31, 2, 1, '2018-01-30', 'Please remember to measure plant size in Masasi plots.', 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=22 ;

--
-- Volcado de datos para la tabla `treatment`
--

INSERT INTO `treatment` (`treatment_id`, `treatment_name`, `treatment_category`, `primary_crop_id`, `intercropping_crop_id`) VALUES
(5, 'Compost', 'Soil management', NULL, NULL),
(10, 'Mulching', 'Soil management', NULL, NULL),
(12, 'Intercropping', 'Intercropping', 1, 4),
(14, 'Top dressing', 'Soil management', NULL, NULL),
(16, 'Neem based', 'Pest control', NULL, NULL),
(18, 'Ash based', 'Pest control', NULL, NULL),
(19, 'Cattle urine based', 'Pest control', NULL, NULL),
(20, 'Milk based', 'Pest control', NULL, NULL),
(21, 'Papaya leaf based', 'Pest control', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `treatment_color`
--

CREATE TABLE IF NOT EXISTS `treatment_color` (
`treatment_color_id` int(10) unsigned NOT NULL,
  `treatment_category` varchar(100) NOT NULL,
  `color` varchar(100) NOT NULL,
  `color_hex` varchar(100) NOT NULL,
  `color_code_app` varchar(2) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `treatment_color`
--

INSERT INTO `treatment_color` (`treatment_color_id`, `treatment_category`, `color`, `color_hex`, `color_code_app`) VALUES
(1, 'No treatment', 'w3-light-grey', 'f1f1f1', '4'),
(2, 'Soil management', 'w3-yellow', 'ffeb3b', '1'),
(3, 'Pest control', 'w3-blue', '2196f3', '6'),
(4, 'Soil management and pest control', 'w3-green', '4caf50', '7');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=22 ;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_alias`, `user_password`, `user_organization`, `user_role`) VALUES
(1, 'Eugenio Tisselli', 'eugenio', 'cubo23', 'ETHZ', 2),
(2, 'Angelika Hilbeck', 'angelika', 'ah', 'ETHZ', 2),
(3, 'Milena Wiget', 'milena', 'milena', 'ETHZ', 2),
(5, 'Janet Maro', 'janet', 'j4n3t', 'SAT', 1),
(6, 'Alex Wostry', 'alex', '4l3x', 'SAT', 1),
(7, 'Jackson Pallangyo', 'jackson', 'j4cks0n', 'SAT', 1),
(8, 'Chambezi', 'chambezi', 'ch4mb3z1', 'Bagamoyo Agricultural Office', 1),
(9, 'Mirjam Schleiffer', 'mirjam', 'mirjam', 'ETHZ', 2),
(10, 'Student', 'student', 'student', 'SUA', 1),
(11, 'Test', 'test', 'test', 'Any', 1),
(12, 'Gagamba Kisesa', 'gagamba', 'gaki', 'SAT', 0),
(13, 'Meddy Njusi', 'meddy', '6369', 'SAT', 1),
(14, 'Simon Njowoka', 'Simon', 'simon', 'Swissaid', 1),
(15, 'Hamza Suleyman', 'hamza', 'h4mz4', 'Bagamoyo Agricultural Office', 1),
(16, 'Gladness Brush', 'Gladness', 'melissa', 'Swissaid', 1),
(17, 'Fadhili Stambuli', 'Fadhili', '2605', 'Swissaid', 1),
(18, 'Rainard Mjunguli', 'Rainard', 'rainard', 'Swissaid', 1),
(19, 'Frank Marwa', 'frank', 'fr4nk', 'SAT', 1),
(20, 'Ayoub Ndee', 'ayoub', 'ayoub', 'Bagamoyo Agriculture Office', 1),
(21, 'Collector', 'collector', 'c0ll3ct0r', 'Test', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `weather_data`
--

CREATE TABLE IF NOT EXISTS `weather_data` (
`weather_data_id` int(10) unsigned NOT NULL,
  `field_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `filename` varchar(100) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

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
-- Indices de la tabla `field_history`
--
ALTER TABLE `field_history`
 ADD PRIMARY KEY (`field_history_id`);

--
-- Indices de la tabla `health_report_item`
--
ALTER TABLE `health_report_item`
 ADD PRIMARY KEY (`health_report_item_id`);

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
-- Indices de la tabla `notification`
--
ALTER TABLE `notification`
 ADD PRIMARY KEY (`notification_id`);

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
-- Indices de la tabla `weather_data`
--
ALTER TABLE `weather_data`
 ADD PRIMARY KEY (`weather_data_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `activity`
--
ALTER TABLE `activity`
MODIFY `activity_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT de la tabla `activity_x_crop_or_treatment`
--
ALTER TABLE `activity_x_crop_or_treatment`
MODIFY `activity_x_crop_or_treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=61;
--
-- AUTO_INCREMENT de la tabla `crop`
--
ALTER TABLE `crop`
MODIFY `crop_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT de la tabla `field`
--
ALTER TABLE `field`
MODIFY `field_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=40;
--
-- AUTO_INCREMENT de la tabla `field_history`
--
ALTER TABLE `field_history`
MODIFY `field_history_id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `health_report_item`
--
ALTER TABLE `health_report_item`
MODIFY `health_report_item_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `input_log`
--
ALTER TABLE `input_log`
MODIFY `input_log_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=94;
--
-- AUTO_INCREMENT de la tabla `log`
--
ALTER TABLE `log`
MODIFY `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=261;
--
-- AUTO_INCREMENT de la tabla `measurement`
--
ALTER TABLE `measurement`
MODIFY `measurement_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=83;
--
-- AUTO_INCREMENT de la tabla `measurement_x_crop_or_treatment`
--
ALTER TABLE `measurement_x_crop_or_treatment`
MODIFY `measurement_x_crop_or_treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=65;
--
-- AUTO_INCREMENT de la tabla `notification`
--
ALTER TABLE `notification`
MODIFY `notification_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT de la tabla `plot`
--
ALTER TABLE `plot`
MODIFY `plot_id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `treatment`
--
ALTER TABLE `treatment`
MODIFY `treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT de la tabla `treatment_color`
--
ALTER TABLE `treatment_color`
MODIFY `treatment_color_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
MODIFY `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT de la tabla `weather_data`
--
ALTER TABLE `weather_data`
MODIFY `weather_data_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
