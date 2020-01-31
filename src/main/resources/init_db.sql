CREATE SCHEMA `internetShop_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internetShop_db`.`users` (
                                            `user_id` INT NOT NULL AUTO_INCREMENT,
                                            `name` VARCHAR(45) NULL,
                                            `surname` VARCHAR(45) NULL,
                                            `login` VARCHAR(45) NOT NULL,
                                            `password` VARCHAR(255) NOT NULL,
                                            `salt` VARBINARY(255) NULL,
                                            PRIMARY KEY (`user_id`),
                                            UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
                                            UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE);
CREATE TABLE `internetShop_db`.`roles` (
                                            `role_id` INT NOT NULL AUTO_INCREMENT,
                                            `role_name` VARCHAR(45) NOT NULL,
                                            PRIMARY KEY (`role_id`),
                                            UNIQUE INDEX `role_id_UNIQUE` (`role_id` ASC) VISIBLE,
                                            UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE);

CREATE TABLE `internetShop_db`.`users_roles` (
                                                  `users_roles_id` INT NOT NULL AUTO_INCREMENT,
                                                  `user_id` INT NOT NULL,
                                                  `role_id` INT NOT NULL,
                                                  PRIMARY KEY (`users_roles_id`),
                                                  UNIQUE INDEX `users_roles_id_UNIQUE` (`users_roles_id` ASC) VISIBLE,
                                                  INDEX `users_roles_roles_fk_idx` (`role_id` ASC) VISIBLE,
                                                  INDEX `users_roles_users_fk_idx` (`user_id` ASC) VISIBLE,
                                                  CONSTRAINT `users_roles_roles_fk`
                                                      FOREIGN KEY (`role_id`)
                                                          REFERENCES `internetShop_db`.`roles` (`role_id`)
                                                          ON DELETE CASCADE
                                                          ON UPDATE RESTRICT,
                                                  CONSTRAINT `users_roles_users_fk`
                                                      FOREIGN KEY (`user_id`)
                                                          REFERENCES `internetShop_db`.`users` (`user_id`)
                                                          ON DELETE CASCADE
                                                          ON UPDATE RESTRICT);

CREATE TABLE `internetShop_db`.`orders` (
                                             `order_id` INT NOT NULL AUTO_INCREMENT,
                                             `user_id` INT NOT NULL,
                                             PRIMARY KEY (`order_id`),
                                             UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC) VISIBLE,
                                             INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE,
                                             CONSTRAINT `orders_users_fk`
                                                 FOREIGN KEY (`user_id`)
                                                     REFERENCES `internetShop_db`.`users` (`user_id`)
                                                     ON DELETE CASCADE
                                                     ON UPDATE RESTRICT);
CREATE TABLE `internetShop_db`.`items` (
                                            `item_id` INT NOT NULL AUTO_INCREMENT,
                                            `name` VARCHAR(45) NOT NULL,
                                            `price` DECIMAL(6,2) NOT NULL,
                                            UNIQUE INDEX `item_id_UNIQUE` (`item_id` ASC) VISIBLE,
                                            PRIMARY KEY (`item_id`));

CREATE TABLE `internetShop_db`.`orders_items` (
                                                   `orders_items_id` INT NOT NULL AUTO_INCREMENT,
                                                   `order_id` INT NOT NULL,
                                                   `item_id` INT NOT NULL,
                                                   PRIMARY KEY (`orders_items_id`),
                                                   UNIQUE INDEX `orders_items_id_UNIQUE` (`orders_items_id` ASC) VISIBLE,
                                                   INDEX `orders_items_items_fk_idx` (`item_id` ASC) VISIBLE,
                                                   INDEX `orders_items_orders_fk_idx` (`order_id` ASC) VISIBLE,
                                                   CONSTRAINT `orders_items_items_fk`
                                                       FOREIGN KEY (`item_id`)
                                                           REFERENCES `internetShop_db`.`items` (`item_id`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION,
                                                   CONSTRAINT `orders_items_orders_fk`
                                                       FOREIGN KEY (`order_id`)
                                                           REFERENCES `internetShop_db`.`orders` (`order_id`)
                                                           ON DELETE CASCADE
                                                           ON UPDATE NO ACTION);

CREATE TABLE `internetShop_db`.`buckets` (
                                              `bucket_id` INT NOT NULL AUTO_INCREMENT,
                                              `user_id` INT NOT NULL,
                                              PRIMARY KEY (`bucket_id`),
                                              UNIQUE INDEX `bucket_id_UNIQUE` (`bucket_id` ASC) VISIBLE,
                                              INDEX `buckets_users_fk_idx` (`user_id` ASC) VISIBLE,
                                              CONSTRAINT `buckets_users_fk`
                                                  FOREIGN KEY (`user_id`)
                                                      REFERENCES `internetShop_db`.`users` (`user_id`)
                                                      ON DELETE CASCADE
                                                      ON UPDATE RESTRICT);

CREATE TABLE `internetShop_db`.`buckets_items` (
                                                    `buckets_items_id` INT NOT NULL AUTO_INCREMENT,
                                                    `bucket_id` INT NOT NULL,
                                                    `item_id` INT NOT NULL,
                                                    PRIMARY KEY (`buckets_items_id`),
                                                    UNIQUE INDEX `buckets_items_id_UNIQUE` (`buckets_items_id` ASC) VISIBLE,
                                                    INDEX `buckets_items_buckets_fk_idx` (`bucket_id` ASC) VISIBLE,
                                                    INDEX `buckets_items_items_fk_idx` (`item_id` ASC) VISIBLE,
                                                    CONSTRAINT `buckets_items_buckets_fk`
                                                        FOREIGN KEY (`bucket_id`)
                                                            REFERENCES `internetShop_db`.`buckets` (`bucket_id`)
                                                            ON DELETE CASCADE
                                                            ON UPDATE NO ACTION,
                                                    CONSTRAINT `buckets_items_items_fk`
                                                        FOREIGN KEY (`item_id`)
                                                            REFERENCES `internetShop_db`.`items` (`item_id`)
                                                            ON DELETE RESTRICT
                                                            ON UPDATE RESTRICT);

INSERT INTO `internetShop_db`.`roles` (`role_name`) VALUES ('ADMIN');
INSERT INTO `internetShop_db`.`roles` (`role_name`) VALUES ('USER');

INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('IPhone', '1000');
INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('Huawei', '400');
INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('LG', '450');
INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('Nokia', '600');
INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('HTC', '700');
INSERT INTO `internetShop_db`.`items` (`name`, `price`) VALUES ('Lenovo', '400');







