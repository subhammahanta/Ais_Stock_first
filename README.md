=======Add These SQL Scripts First====


drop table if exists `device_renewal_request`;
create table `device_renewal_request`(
  `id`  bigint auto_increment,
  `req_code`varchar(255) default null,
  `created_at` datetime default null,
  `created_by` bigint default null,
  
  primary key (`id`)
);

drop table if exists `renewal_device`;
CREATE TABLE `renewal_device` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `device_id` int NOT NULL,
  `imei_no` varchar(50) DEFAULT NULL,
  `iccid_no` varchar(50) DEFAULT NULL,
  `old_expiry_date` date DEFAULT NULL,
  `new_expiry_date` date DEFAULT NULL,
  `request_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `request_id` (`request_id`)
   );
