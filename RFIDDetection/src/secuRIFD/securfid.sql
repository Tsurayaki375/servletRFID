-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Mar 15 Mars 2016 à 14:52
-- Version du serveur :  5.6.20-log
-- Version de PHP :  5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `securfid`
--
CREATE DATABASE IF NOT EXISTS `securfid` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `securfid`;

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

CREATE TABLE IF NOT EXISTS `employe` (
  `e_ID` varchar(50) NOT NULL,
  `e_Nom` varchar(25) NOT NULL,
  `e_Prenom` varchar(25) NOT NULL,
  `e_Poste` varchar(50) NOT NULL,
  `e_LvlSecu` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `employe`
--

INSERT INTO `employe` (`e_ID`, `e_Nom`, `e_Prenom`, `e_Poste`, `e_LvlSecu`) VALUES
('04D00BB27A4880', 'Valade', 'Eric', 'Psychologue du Travail', 3),
('04E70E7A7A4884', 'Massabiau', 'Bernard', 'Concierge', 1),
('0BE19516', 'Obassa', 'Andrew', 'Consultant', 2),
('9B449616', 'Sutre', 'Frederic', 'Chef de la securité', 4);

-- --------------------------------------------------------

--
-- Structure de la table `historique`
--

CREATE TABLE IF NOT EXISTS `historique` (
  `h_ID` varchar(50) NOT NULL,
  `h_Date` varchar(30) NOT NULL,
  `h_Nom` varchar(25) NOT NULL,
  `h_Prenom` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `historique`
--

INSERT INTO `historique` (`h_ID`, `h_Date`, `h_Nom`, `h_Prenom`) VALUES
('0BE19516', '2016/03/15 15:50:24', 'Obassa', 'Andrew'),
('0BE19516', '2016/03/15 15:50:43', 'Obassa', 'Andrew');

-- --------------------------------------------------------

--
-- Structure de la table `porte`
--

CREATE TABLE IF NOT EXISTS `porte` (
  `p_Terminal` varchar(50) NOT NULL,
  `p_LvlSecu` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `porte`
--

INSERT INTO `porte` (`p_Terminal`, `p_LvlSecu`) VALUES
('ACS ACR122 0', 3);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `employe`
--
ALTER TABLE `employe`
 ADD PRIMARY KEY (`e_ID`);

--
-- Index pour la table `historique`
--
ALTER TABLE `historique`
 ADD PRIMARY KEY (`h_ID`,`h_Date`);

--
-- Index pour la table `porte`
--
ALTER TABLE `porte`
 ADD PRIMARY KEY (`p_Terminal`);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `historique`
--
ALTER TABLE `historique`
ADD CONSTRAINT `historique_ibfk_1` FOREIGN KEY (`h_ID`) REFERENCES `employe` (`e_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
