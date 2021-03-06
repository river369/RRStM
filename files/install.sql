
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

--DFCF Real data for selected items.
CREATE TABLE IF NOT EXISTS `stock`.`selection_items_realtime_history` (
`id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
`stock_id` varchar(10) NOT NULL COMMENT 'Stock Id like SH000000',
`price` decimal(7,2) DEFAULT 0  COMMENT '当前价格',
`yesterday_finish_price` decimal(7,2) DEFAULT 0  COMMENT '昨日收盘价格',
`today_start_price` decimal(7,2) DEFAULT 0  COMMENT '今日开盘价格',
`volume_ratio` decimal(7,2) DEFAULT 0  COMMENT '量比',
`turn_over` decimal(7,2) DEFAULT 0  COMMENT '换手',
`creation_date` datetime  DEFAULT NOW() COMMENT 'Creation datetime',
PRIMARY KEY (`id`),
KEY (`stock_id`),
KEY (`creation_date`)
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci CHECKSUM=0 ROW_FORMAT=DYNAMIC DELAY_KEY_WRITE=0;

-- Temperature
CREATE TABLE IF NOT EXISTS `stock`.`temperature` (
`id` bigint(20) NOT NULL AUTO_INCREMENT  COMMENT 'temperature id',
`D11` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 11 percent' ,
`D10` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 10 percent' ,
`D9` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 9 percent' ,
`D8` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 8 percent' ,
`D7` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 7 percent' ,
`D6` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 6 percent' ,
`D5` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 5 percent' ,
`D4` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 4 percent' ,
`D3` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 3 percent' ,
`D2` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 2 percent' ,
`D1` int(10) DEFAULT 0  COMMENT 'count in this range, Decrease less than 1 percent' ,
`I1` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 1 percent' ,
`I2` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 2 percent' ,
`I3` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 3 percent' ,
`I4` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 4 percent' ,
`I5` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 5 percent' ,
`I6` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 6 percent' ,
`I7` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 7 percent' ,
`I8` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 8 percent' ,
`I9` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 9 percent' ,
`I10` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 10 percent' ,
`I11` int(10) DEFAULT 0  COMMENT 'count in this range, increase less than 11 percent' ,
`creation_date` datetime  DEFAULT NOW() COMMENT 'Creation datetime',
PRIMARY KEY (`id`),
KEY (`creation_date`)
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci CHECKSUM=0 ROW_FORMAT=DYNAMIC DELAY_KEY_WRITE=0;

