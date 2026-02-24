-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           8.0.30 - MySQL Community Server - GPL
-- SE du serveur:                Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Listage de la structure de la base pour gestionbibliotheque
CREATE DATABASE IF NOT EXISTS `gestionbibliotheque` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gestionbibliotheque`;

-- Listage de la structure de table gestionbibliotheque. emprunt
CREATE TABLE IF NOT EXISTS `emprunt` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dateEmprunt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `dateRetour` date NOT NULL,
  `idEtudiant` int NOT NULL,
  `idLivre` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh1bqbeeyfrx5op0xv3un2dxpg` (`idEtudiant`),
  KEY `FK94u31oa0f1vp04pqqpcymstxh` (`idLivre`),
  CONSTRAINT `FK94u31oa0f1vp04pqqpcymstxh` FOREIGN KEY (`idLivre`) REFERENCES `livre` (`id`),
  CONSTRAINT `FKh1bqbeeyfrx5op0xv3un2dxpg` FOREIGN KEY (`idEtudiant`) REFERENCES `etudiant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table gestionbibliotheque.emprunt : ~3 rows (environ)
INSERT INTO `emprunt` (`id`, `dateEmprunt`, `dateRetour`, `idEtudiant`, `idLivre`) VALUES
	(8, '2026-02-23 00:00:00', '2026-03-08', 1, 1),
	(9, '2026-02-23 00:00:00', '2026-03-08', 4, 2),
	(10, '2026-02-24 00:00:00', '2026-03-09', 3, 3);

-- Listage de la structure de table gestionbibliotheque. etudiant
CREATE TABLE IF NOT EXISTS `etudiant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `matricule` varchar(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hqipexqvpuw5mhdeefxuquhoy` (`matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table gestionbibliotheque.etudiant : ~3 rows (environ)
INSERT INTO `etudiant` (`id`, `email`, `matricule`, `nom`, `prenom`) VALUES
	(1, 'oumysagnaisikm@groupeisi.com', 'AZERT', 'SAGNA', 'Salma'),
	(3, 'moistaphadiop57@gmail.com', 'ZERTY', 'DIOP', 'Moustapha'),
	(4, 'osagna2@gmail.com', 'XCV', 'SAGNA', 'Oumar');

-- Listage de la structure de table gestionbibliotheque. livre
CREATE TABLE IF NOT EXISTS `livre` (
  `id` int NOT NULL AUTO_INCREMENT,
  `auteur` varchar(255) NOT NULL,
  `isbn` varchar(255) NOT NULL,
  `titre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Listage des données de la table gestionbibliotheque.livre : ~3 rows (environ)
INSERT INTO `livre` (`id`, `auteur`, `isbn`, `titre`) VALUES
	(1, 'Oumy Salimata SAGNA', 'TREZA', 'La belle au bois dormant'),
	(2, 'Even prod', 'YTREZ', 'Coeur Brisé'),
	(3, 'Even Prod', 'UYTRE', 'Xalissoo'),
	(4, 'une autrice', 'IUYTR', 'Rebelle');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
