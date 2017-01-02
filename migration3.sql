-- MySQL Script generated by MySQL Workbench
-- 09/21/16 12:21:02
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema forum
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `forum` ;

-- -----------------------------------------------------
-- Schema forum
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `forum` DEFAULT CHARACTER SET utf8 ;
USE `forum` ;

-- -----------------------------------------------------
-- Table `forum`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `forum`.`user` ;

CREATE TABLE IF NOT EXISTS `forum`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `role` VARCHAR(45) NULL,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `forum`.`topic`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `forum`.`topic` ;

CREATE TABLE IF NOT EXISTS `forum`.`topic` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `picpath` VARCHAR(100) NULL,
  `created` DATETIME NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_topic_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_topic_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `forum`.`user` (`id`)
    ON DELETE CASCADE 
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `forum`.`message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `forum`.`message` ;

CREATE TABLE IF NOT EXISTS `forum`.`message` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `header` VARCHAR(100) NULL,
  `content` TEXT NULL,
  `created` DATETIME NULL,
  `topic_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `topic_id`, `user_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_message_topic1_idx` (`topic_id` ASC),
  INDEX `fk_message_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_message_topic1`
    FOREIGN KEY (`topic_id`)
    REFERENCES `forum`.`topic` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `forum`.`user` (`id`)
    ON DELETE CASCADE 
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `forum`.`attache`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `forum`.`attache` ;

CREATE TABLE IF NOT EXISTS `forum`.`attache` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `filepath` VARCHAR(100) NULL,
  `message_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`, `message_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_atache_message1_idx` (`message_id` ASC),
  CONSTRAINT `fk_atache_message1`
    FOREIGN KEY (`message_id`)
    REFERENCES `forum`.`message` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;