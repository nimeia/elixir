ALTER TABLE `app`.`user`
    ADD COLUMN `permission` varchar(255) NULL AFTER `valid_time`;