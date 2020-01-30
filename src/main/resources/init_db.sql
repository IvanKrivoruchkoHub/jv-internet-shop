CREATE SCHEMA `internetShop_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internetShop_db`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`item_id`));

INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('IPhone 11', '1000');

CREATE TABLE `internetShop_db`.`orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`order_id`));

CREATE TABLE `internetShop_db`.`orders_items` (
                                                  `orders_items_id` INT NOT NULL AUTO_INCREMENT,
                                                  `order_id` INT NOT NULL,
                                                  `item_id` INT NOT NULL,
                                                  PRIMARY KEY (`orders_items_id`),
                                                  INDEX `orders_items_orders_fk_idx` (`order_id` ASC) VISIBLE,
                                                  INDEX `orders_items_items_fk_idx` (`item_id` ASC) VISIBLE,
                                                  CONSTRAINT `orders_items_orders_fk`
                                                      FOREIGN KEY (`order_id`)
                                                          REFERENCES `internetShop_db`.`orders` (`order_id`)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION,
                                                  CONSTRAINT `orders_items_items_fk`
                                                      FOREIGN KEY (`item_id`)
                                                          REFERENCES `internetShop_db`.`items` (`item_id`)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION);

CREATE TABLE `internetShop_db`.`users` (
                                           `user_id` INT NOT NULL AUTO_INCREMENT,
                                           `name` VARCHAR(45) NULL,
                                           `surname` VARCHAR(45) NULL,
                                           `login` VARCHAR(45) NOT NULL,
                                           `password` VARCHAR(255) NOT NULL,
                                           `token` VARCHAR(45) NULL,
                                           PRIMARY KEY (`user_id`),
                                           UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
                                           UNIQUE INDEX `token_UNIQUE` (`token` ASC) VISIBLE);

ALTER TABLE `internetShop_db`.`orders`
    ADD COLUMN `user_id` INT NOT NULL AFTER `order_id`,
    ADD INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `internetShop_db`.`orders`
    ADD CONSTRAINT `orders_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internetShop_db`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

INSERT INTO `internetShop_db`.`users` (`name`, `surname`, `login`, `password`) VALUES ('John', 'Jones', 'Jones1', '1');
INSERT INTO `internetShop_db`.`users` (`name`, `surname`, `login`, `password`) VALUES ('John', 'Smith', 'Admin1', '1');
INSERT INTO `internetShop_db`.`users` (`name`, `surname`, `login`, `password`) VALUES ('Ivan', 'Krivoruchko', 'Krivoruchko1', '1');

INSERT INTO `internetShop_db`.`orders` (`user_id`) VALUES ('1');
INSERT INTO `internetShop_db`.`orders` (`user_id`) VALUES ('1');
INSERT INTO `internetShop_db`.`orders` (`user_id`) VALUES ('3');

INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('Huawei', '400');
INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('LG', '450');
INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('Nokia', '600');
INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('HTC', '700');

INSERT INTO `internetShop_db`.`orders_items` (`order_id`, `item_id`) VALUES ('1', '13');
INSERT INTO `internetShop_db`.`orders_items` (`order_id`, `item_id`) VALUES ('1', '28');
INSERT INTO `internetShop_db`.`orders_items` (`order_id`, `item_id`) VALUES ('2', '29');
INSERT INTO `internetShop_db`.`orders_items` (`order_id`, `item_id`) VALUES ('3', '27');
INSERT INTO `internetShop_db`.`orders_items` (`order_id`, `item_id`) VALUES ('3', '30');

ALTER TABLE `internetShop_db`.`users`
    ADD COLUMN `salt` VARCHAR(255) NOT NULL AFTER `password`;

ALTER TABLE `internetShop_db`.`users`
    CHANGE COLUMN `salt` `salt` VARBINARY(255) NULL DEFAULT NULL ;

UPDATE `internetShop_db`.`users` SET `name` = 'User', `surname` = 'User', `login` = 'User1' WHERE (`user_id` = '26');
UPDATE `internetShop_db`.`users` SET `name` = 'Admin', `surname` = 'Admin' WHERE (`user_id` = '27');





