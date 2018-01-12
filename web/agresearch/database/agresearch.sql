-- phpMyAdmin SQL Dump
-- version 4.2.6
-- http://www.phpmyadmin.net
--
-- Servidor: 192.168.86.55
-- Tiempo de generación: 12-01-2018 a las 15:23:32
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
(9, 'Pest control application', 'Pest control', 0, 'applied amount', 'Rats: clear the vegetation around the field and set up 3 to 5 rat traps within the plots to avoid rats feeding on the crops.**Insects (daily control): application of the prepared pest control according to the occurrence and intensity of any corresponding pest infestation. For maize, each plant has to be sprayed with the preparation every second day. For cassava, the basis of the stems need to be sprayed twice or three times per week.'),
(10, 'Compost preparation', 'Soil management', 0, 'hours', 'Start a compost by filling a pit with layers of ash, dried maize stems, mature grass, legumes, manure and top soil. Water extensively and repeat several times until the pit is full. Finally, the filled pit is covered with grass or a permeable tarpaulin. The compost in the pit has to be stirred regularly so that it doesn\\''t get too hot or anaerobic - this can be controlled by a wooden stick which is placed in the middle of the compost pit and which works as thermometer.'),
(11, 'Compost application', 'Soil management', 0, 'applied amount', 'Mix the soil with compost. Each S-plot gets 10 buckets of compost (1bucket = 10kg) which has to be evenly distributed on the plot or according to the sowing scheme locally applied.'),
(12, 'Mulch preparation', 'Soil management', 0, 'hours', ''),
(13, 'Mulch application', 'Soil management', 0, 'applied amount', 'Cover the soil surface with a layer of fine-cut mature grass. The layer should be as thin as possible while covering the soil surface completely.'),
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
(24, 15, 4, NULL),
(25, 15, 8, NULL),
(26, 15, 1, NULL),
(28, 16, 2, NULL),
(30, 23, 2, NULL),
(31, 23, 4, NULL),
(32, 23, 1, NULL),
(33, 23, 8, NULL),
(34, 24, 2, NULL),
(35, 24, 4, NULL),
(36, 24, 1, NULL),
(37, 24, 8, NULL),
(40, 21, 1, NULL),
(43, 25, 2, NULL),
(44, 15, 2, NULL),
(45, 22, 2, NULL),
(46, 22, 4, NULL),
(47, 22, 1, NULL),
(48, 22, 8, NULL),
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='Fields registered in the ag. research' AUTO_INCREMENT=37 ;

--
-- Volcado de datos para la tabla `field`
--

INSERT INTO `field` (`field_id`, `parent_field_id`, `user_id`, `field_date_created`, `field_name`, `field_replication_number`, `field_lat`, `field_lng`, `field_configuration`) VALUES
(23, 23, 1, '2017-02-24', 'Masasi', 1, '-10.792300', '38.894855', 'F=(2,1,1,1);G=(4,4);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,0,0);P=(2,4,0,0);P=(1,0,0,1);P=(2,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(1,4,1,1);P=(2,4,1,1);P=(2,0,1,0);P=(1,0,1,0);P=(2,0,1,1);P=(1,0,1,1);P=(2,4,1,0);P=(1,4,1,0);'),
(24, 23, 1, '2017-02-24', 'Masasi', 2, '-10.792300', '38.894855', 'F=(2,1,1,1);G=(4,4);P=(2,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(2,0,1,1);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,1,1);P=(2,4,1,1);P=(2,0,0,0);P=(1,0,0,0);P=(2,4,1,0);P=(1,4,1,0);P=(2,4,0,0);P=(1,4,0,0);P=(2,0,1,0);P=(1,0,1,0);'),
(25, 23, 1, '2017-02-24', 'Masasi', 3, '-10.792300', '38.894855', 'F=(2,1,1,1);G=(4,4);P=(2,0,1,0);P=(1,4,1,0);P=(1,0,1,1);P=(2,4,1,1);P=(1,0,1,0);P=(2,4,1,0);P=(1,4,1,1);P=(2,0,1,1);P=(2,4,0,0);P=(1,0,0,0);P=(2,0,0,1);P=(1,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(2,4,0,1);P=(1,0,0,1);'),
(34, 34, 3, '2017-04-19', 'Morogoro', 1, '-6.741821', '37.550743', 'F=(2,1,1,1);G=(4,4);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,0,0);P=(2,4,0,0);P=(1,0,0,1);P=(2,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(1,4,1,1);P=(2,4,1,1);P=(1,0,1,0);P=(2,0,1,0);P=(2,0,1,1);P=(1,0,1,1);P=(1,4,1,0);P=(2,4,1,0);'),
(35, 34, 3, '2017-04-19', 'Morogoro', 2, '-6.741821', '37.550743', 'F=(2,1,1,1);G=(4,4);P=(2,4,0,1);P=(1,0,0,1);P=(1,4,1,1);P=(2,0,1,1);P=(1,4,0,1);P=(2,0,0,1);P=(1,0,1,1);P=(2,4,1,1);P=(2,0,0,0);P=(1,0,0,0);P=(2,4,1,0);P=(1,4,1,0);P=(2,4,0,0);P=(1,4,0,0);P=(2,0,1,0);P=(1,0,1,0);'),
(36, 34, 3, '2017-04-19', 'Morogoro', 3, '-6.741821', '37.550743', 'F=(2,1,1,1);G=(4,4);P=(2,0,1,0);P=(1,4,1,0);P=(1,0,1,1);P=(2,4,1,1);P=(1,0,1,0);P=(2,4,1,0);P=(1,4,1,1);P=(2,0,1,1);P=(2,4,0,0);P=(1,0,0,0);P=(2,0,0,1);P=(1,4,0,1);P=(1,4,0,0);P=(2,0,0,0);P=(2,4,0,1);P=(1,0,0,1);');

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
(1, 'Disease', 'Don\\''t know,Bacterial,Fungal,Viral,Other ...'),
(2, 'Pest', 'Don\\''t know,Ants/Termites,Beetles,Birds,Locusts/Grasshoppers,Mites,Moths,Rats,Worms/Larvae,Other ...'),
(3, 'Discoloration', 'Brown,Dark green,Light green,Purple,Yellow,Other ...'),
(4, 'Water stress', 'Dry leaves,Folded leaves,Rippled leave margins,Rolled leaves,Other ...');

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
  `input_units` varchar(100) NOT NULL,
  `input_cost` varchar(100) NOT NULL,
  `input_treatment_material` text NOT NULL,
  `input_treatment_preparation_method` text NOT NULL,
  `input_comments` text NOT NULL,
  `input_picture` varchar(200) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- Volcado de datos para la tabla `input_log`
--

INSERT INTO `input_log` (`input_log_id`, `input_log_date`, `field_id`, `plots`, `user_id`, `crop_id`, `treatment_id`, `input_age`, `input_origin`, `input_crop_variety`, `input_quantity`, `input_units`, `input_cost`, `input_treatment_material`, `input_treatment_preparation_method`, `input_comments`, `input_picture`) VALUES
(7, '2017-11-30', 24, '0,1,2,3,4,5,6,7', 9, 0, 17, '', '', '', 1, 'kg', '', 'on farm', 'cooking 15 times, sieving and dilluting water 1:5', '', ''),
(8, '2017-11-30', 23, '5,4,8,12,0,1,13,9', 9, 0, 17, '', '', '', 1, 'kg', '', 'on farm', 'cooking 15 times, sieving and dilluting water 1:5', ' (copied)', ''),
(9, '2017-11-30', 25, '14,15,6,7,11,10,2,3', 9, 0, 17, '', '', '', 1, 'kg', '', 'on farm', 'cooking 15 times, sieving and dilluting water 1:5', ' (copied)', ''),
(10, '2017-11-30', 24, '0,1,2,3,4,5,6,7', 9, 0, 17, '', '', '', 1, 'kg', '', 'on farm', 'cooking 10 times', '', ''),
(11, '2017-11-30', 23, '5,4,8,12,0,1,13,9', 9, 0, 17, '', '', '', 1, 'kg', '', 'on farm', 'cooking 10 times', ' (copied)', ''),
(12, '2017-11-30', 25, '14,15,6,7,11,10,2,3', 9, 0, 17, '', '', '', 1, 'kg', '', 'on farm', 'cooking 10 times', ' (copied)', '');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `log`
--

INSERT INTO `log` (`log_id`, `field_id`, `plots`, `user_id`, `crop_id`, `sample_number`, `treatment_id`, `measurement_id`, `activity_id`, `log_date`, `log_value_number`, `log_value_units`, `log_value_text`, `log_number_of_laborers`, `log_cost`, `log_comments`, `log_picture`) VALUES
(1, 24, '1', 1, 0, '0', 0, 76, 0, '2018-01-11', 0, '', '1* # # # *2* #Fungal#Mites#Rolled leaves*3* *4*Light green# # # ', '', '', '', '');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=82 ;

--
-- Volcado de datos para la tabla `measurement`
--

INSERT INTO `measurement` (`measurement_id`, `measurement_name`, `measurement_category`, `measurement_subcategory`, `measurement_type`, `measurement_range_min`, `measurement_range_max`, `measurement_units`, `measurement_categories`, `measurement_periodicity`, `measurement_has_sample_number`, `measurement_common_complex`, `measurement_description`) VALUES
(8, 'Number of planted seeds', 'Maize', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Record how many seeds have been sown per plot.'),
(9, 'Number of germinated plants', 'Maize', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have germinated per plot.'),
(12, 'Plant size', 'Maize', 'Development', 1, 0, 500, 'cm', '', 0, 1, 1, 'Method 1: measure the height of each plant in a plot from the stem basis/soil surface to the tip of the longest leaf.**Method 2: measure the height of each plant in a plot from the stem basis/soil surface to the highest point of the arch of the uppermost leaf whose tip is pointing down.'),
(24, 'Weight of harvested tubers', 'Cassava', 'Productivity', 1, 0, 1000, 'kg', '', 0, 0, 1, 'Weigh the dry matter of all remaining plant residuals which can be collected on one plot after harvest and record the mass you get per plot and crop.'),
(39, 'Number of survived plants', 'Cassava', 'Productivity', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have survived on each plot shortly before harvesting the crops on that plot.'),
(48, 'Plant size', 'Cassava', 'Development', 1, 0, 500, 'cm', '', 0, 1, 1, 'Plant size is measured by adding the stem heigth plus the height of the longest branch.'),
(54, 'Number of sprouted plants', 'Cassava', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have sprouted per plot.'),
(55, 'Number of planted sticks', 'Cassava', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Record how many sticks have been planted per plot.'),
(56, 'Number of survived plants', 'Maize', 'Productivity', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have survived on each plot shortly before harvesting the crops on that plot.'),
(58, 'Weight of harvested kernels', 'Maize', 'Productivity', 1, 0, 1000, 'kg', '', 0, 0, 1, 'Weigh the dry matter of all remaining plant residuals which can be collected on one plot after harvest and record the mass you get per plot and crop.'),
(59, 'Number of planted seeds', 'Pulses', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Record how many seeds have been sown per plot.'),
(60, 'Number of germinated plants', 'Pulses', 'Development', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have germinated per plot.'),
(61, 'Number of survived plants', 'Pulses', 'Productivity', 1, 0, 1000, 'per plot', '', 0, 0, 0, 'Count the number of plants which have survived on each plot shortly before harvesting the crops on that plot.'),
(62, 'Weight of harvested grains', 'Pulses', 'Productivity', 1, 0, 1000, 'kg', '', 0, 0, 0, 'Weigh the dry matter of all remaining plant residuals which can be collected on one plot after harvest and record the mass you get per plot and crop.'),
(76, 'Health report', 'Maize', 'Health', 2, 0, 0, '', '', 0, 1, 1, '1. Control whether the plants show any stress symptoms. Record for each plant in one plot whether and if so, which stress symptoms occur. There are different categories of stress symptoms: (1) folded, (2) rolled, (3) dry (4) yellowish, (5) light green, (6) dark green or (7) purple leaves.**2. Check each plant for pest signs and categorize the extent of the pest infestion with the following categories: (1) no or little pest signs, (2) clear pest damage but the development/growth is not limited (apex is intact), (3) the development/growth is limited due to heavy pest damage (apex is not intact), (4) the heavy pest infestion kills/killed the plant. **Determine the pest (category) or disease (viral/bacterial) by which the plant is infested and record them for each plant in one plot. If possible specify the pest species or the disease type.'),
(77, 'Health report', 'Cassava', 'Health', 2, 0, 0, '', '', 0, 1, 1, '1. Control whether the plants show any stress symptoms. Record for each plant in one plot whether and if so, which stress symptoms occur. There are different categories of stress symptoms: (1) folded, (2) rolled, (3) dry (4) yellowish, (5) light green, (6) dark green or (7) purple leaves.**2. Check each plant for pest signs and categorize the extent of the pest infestion with the following categories: (1) no or little pest signs, (2) clear pest damage but the development/growth is not limited (apex is intact), (3) the development/growth is limited due to heavy pest damage (apex is not intact), (4) the heavy pest infestion kills/killed the plant. **Determine the pest (category) or disease (viral/bacterial) by which the plant is infested and record them for each plant in one plot. If possible specify the pest species or the disease type.'),
(78, 'Number of flowering plants', 'Cassava', 'Development', 1, 0, 1000, 'plants', '', 0, 0, 1, 'Determine the number of plants flowering or tasseling in one plot.'),
(79, 'Number of harvested tubers per plant', 'Cassava', 'Productivity', 1, 0, 100, 'tubers', '', 0, 1, 0, 'Count all the tubers which can be harvested form one plant.'),
(80, 'Number of leaves', 'Maize', 'Development', 1, 0, 200, 'leaves', '', 0, 1, 1, 'Determine the growth stage of each plant per plot by counting the number of leaves with visible collars, beginning with the lowermost, short, rounded-tip true leaf and ending with the uppermost leaf with a visible leaf collar.'),
(81, 'Number of flowering plants', 'Maize', 'Development', 1, 0, 1000, 'plants', '', 0, 0, 1, 'Determine the number of plants flowering or tasseling in one plot.');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=30 ;

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
(29, 1, 1, '2018-01-09', 'Message to self', 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=20 ;

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
(19, 'Cattle urine based', 'Pest control', NULL, NULL);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_alias`, `user_password`, `user_organization`, `user_role`) VALUES
(1, 'Eugenio Tisselli', 'eugenio', 'cubo23', 'ETHZ', 2),
(2, 'Angelika Hilbeck', 'angelika', 'ah', 'ETHZ', 2),
(3, 'Milena Wiget', 'milena', 'milena', 'ETHZ', 2),
(5, 'Janet Maro', 'janet', 'j4n3t', 'SAT', 1),
(6, 'Alex Wostry', 'alex', '4l3x', 'SAT', 1),
(7, 'Jackson', 'jackson', 'j4cks0n', 'SAT', 0),
(8, 'Chambezi', 'chambezi', 'ch4mb3z1', 'Bagamoyo Agricultural Office', 1),
(9, 'Mirjam Schleiffer', 'mirjam', 'mirjam', 'ETHZ', 2),
(10, 'Student', 'student', 'student', 'SUA', 1),
(11, 'Test', 'test', 'test', 'Any', 1),
(12, 'Gagamba Kisesa', 'gagamba', 'gaki', 'SAT', 0),
(13, 'Meddy Njusi', 'meddy', '6369', 'SAT', 1),
(14, 'Simon Njowoka', 'Simon', 'simon', 'Swissaid', 0);

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
MODIFY `field_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=37;
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
MODIFY `input_log_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT de la tabla `log`
--
ALTER TABLE `log`
MODIFY `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `measurement`
--
ALTER TABLE `measurement`
MODIFY `measurement_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=82;
--
-- AUTO_INCREMENT de la tabla `measurement_x_crop_or_treatment`
--
ALTER TABLE `measurement_x_crop_or_treatment`
MODIFY `measurement_x_crop_or_treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=65;
--
-- AUTO_INCREMENT de la tabla `notification`
--
ALTER TABLE `notification`
MODIFY `notification_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT de la tabla `plot`
--
ALTER TABLE `plot`
MODIFY `plot_id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `treatment`
--
ALTER TABLE `treatment`
MODIFY `treatment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT de la tabla `treatment_color`
--
ALTER TABLE `treatment_color`
MODIFY `treatment_color_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
MODIFY `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=15;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
