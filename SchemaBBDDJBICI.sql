/*
SQLyog Ultimate v8.8 
MySQL - 5.5.24-log : Database - jbici
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jbici` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `jbici`;

/*Table structure for table `activo` */

DROP TABLE IF EXISTS `activo`;

CREATE TABLE `activo` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `idAlquiler` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `alquiler` */

DROP TABLE IF EXISTS `alquiler`;

CREATE TABLE `alquiler` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `fechaHoraRetiro` DATETIME NOT NULL,
  `idEstacionRetiro` bigint(20) NOT NULL,
  `idBicicleta` bigint(20) NOT NULL,
  `idPerfil` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `auditoria` */

DROP TABLE IF EXISTS `auditoria`;

CREATE TABLE `auditoria` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `clase` varchar(255) NOT NULL,
  `fechaHora` datetime NOT NULL,
  `idEntidad` bigint(20) NOT NULL,
  `operacion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `bicicleta` */

DROP TABLE IF EXISTS `bicicleta`;

CREATE TABLE `bicicleta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fechaIngreso` date NOT NULL,
  `idEstado` bigint(20) NOT NULL,
  `idEstacion` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `estacion` */

DROP TABLE IF EXISTS `estacion`;

CREATE TABLE `estacion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `ubicacion` varchar(255) NOT NULL,
  `cantBicicletas` int(11) NOT NULL,
  `cantLugaresLibres` int(11) NOT NULL,
  `codPostal` varchar(50) NOT NULL,
  `idEstado` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `estado` */

DROP TABLE IF EXISTS `estado`;

CREATE TABLE `estado` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `estadobici` */

DROP TABLE IF EXISTS `estadobici`;

CREATE TABLE `estadobici` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `historial` */

DROP TABLE IF EXISTS `historial`;

CREATE TABLE `historial` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fechaHora` datetime NOT NULL,
  `idEstado` bigint(20) NOT NULL,
  `idBicicleta` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `historico` */

DROP TABLE IF EXISTS `historico`;

CREATE TABLE `historico` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idAlquiler` bigint(20) NOT NULL,
  `fechaHoraDevolucion` datetime NOT NULL,
  `idEstacionDevolucion` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `perfiles` */

DROP TABLE IF EXISTS `perfiles`;

CREATE TABLE `perfiles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `activo` tinyint(1) DEFAULT NULL,
  `idPersona` bigint(20) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `persona` */

DROP TABLE IF EXISTS `persona`;

CREATE TABLE `persona` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dni` varchar(10) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `nombres` varchar(255) NOT NULL,
  `domicilio` varchar(255) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `sexo` varchar(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
