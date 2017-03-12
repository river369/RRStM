
-- Selection Model Run Actions results
CREATE TABLE IF NOT EXISTS `stock`.`selection` (
`selection_id` bigint(20) NOT NULL COMMENT 'Calculaton Id',
`status` int(2) DEFAULT 0  COMMENT 'success 0, fail 1' ,
`creation_date` datetime  DEFAULT NOW() COMMENT 'Creation datetime',
`description` text DEFAULT NULL  COMMENT 'description',
PRIMARY KEY (`selection_id`),
KEY (`status`),
KEY (`creation_date`)
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci CHECKSUM=0 ROW_FORMAT=DYNAMIC DELAY_KEY_WRITE=0;

-- Selection Items
CREATE TABLE IF NOT EXISTS `stock`.`selection_items` (
`id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
`selection_id` bigint(20) NOT NULL COMMENT 'Calculaton Id',
`stock_id` varchar(10) NOT NULL COMMENT 'Stock Id like SH000000',
`stock_name` varchar(50)  DEFAULT NULL  COMMENT 'Stock Name like 首旅股份',
`driven_by_blocks` varchar(255)  DEFAULT NULL  COMMENT '驱动板块',
`belong_to_blocks` varchar(511)  DEFAULT NULL  COMMENT '归属板块',
`price` decimal(7,2) DEFAULT 0  COMMENT '当前价格',
`yesterday_finish_price` decimal(7,2) DEFAULT 0  COMMENT '昨日收盘价格',
`today_start_price` decimal(7,2) DEFAULT 0  COMMENT '今日开盘价格',
`volume_ratio` decimal(7,2) DEFAULT 0  COMMENT '量比',
`turn_over` decimal(7,2) DEFAULT 0  COMMENT '换手',
`creation_date` datetime  DEFAULT NOW() COMMENT 'Creation datetime',
PRIMARY KEY (`id`),
KEY (`selection_id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci CHECKSUM=0 ROW_FORMAT=DYNAMIC DELAY_KEY_WRITE=0;


-- Selected stocks by algorithm  -- Deprecated
CREATE TABLE IF NOT EXISTS `stock`.`selected_stocks` (
`id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
`stock_id` varchar(10) NOT NULL COMMENT 'Stock Id like SH000000',
`stock_name` varchar(50)  DEFAULT NULL  COMMENT 'Stock Name like 首旅股份',
`driven_by_blocks` varchar(255)  DEFAULT NULL  COMMENT '驱动板块',
`belong_to_blocks` varchar(511)  DEFAULT NULL  COMMENT '归属板块',
`price` decimal(7,2) DEFAULT 0  COMMENT '当前价格',
`yesterday_finish_price` decimal(7,2) DEFAULT 0  COMMENT '昨日收盘价格',
`today_start_price` decimal(7,2) DEFAULT 0  COMMENT '今日开盘价格',
`volume_ratio` decimal(7,2) DEFAULT 0  COMMENT '量比',
`turn_over` decimal(7,2) DEFAULT 0  COMMENT '换手',
`creation_date` datetime  DEFAULT NOW() COMMENT 'Creation datetime',
PRIMARY KEY (`id`),
KEY (`creation_date`),
KEY (`stock_id`),
KEY (`stock_name`)
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci CHECKSUM=0 ROW_FORMAT=DYNAMIC DELAY_KEY_WRITE=0;

