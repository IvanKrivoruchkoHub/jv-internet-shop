CREATE SCHEMA `internetShop_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internetShop_db`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`item_id`));

INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('IPhone 11', '1000');
