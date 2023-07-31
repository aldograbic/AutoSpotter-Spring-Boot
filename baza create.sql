CREATE TABLE `countries` (
  `id` int NOT NULL,
  `country_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `counties` (
  `id` int NOT NULL,
  `county_name` varchar(255) DEFAULT NULL,
  `country_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`),
  CONSTRAINT `counties_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cities` (
  `id` int NOT NULL,
  `city_name` varchar(255) DEFAULT NULL,
  `county_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `county_id` (`county_id`),
  CONSTRAINT `cities_ibfk_1` FOREIGN KEY (`county_id`) REFERENCES `counties` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `contact_messages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `message` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `states` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `vehicle_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `body_type` (
  `id` int NOT NULL,
  `body_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `engine_type` (
  `id` int NOT NULL,
  `engine_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transmission` (
  `id` int NOT NULL,
  `transmission_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `drive_train` (
  `id` int NOT NULL,
  `drive_train_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `manufacturers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `manufacturer_name` varchar(50) NOT NULL,
  `vehicle_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vehicle_type_manufacturers` (`vehicle_type_id`),
  CONSTRAINT `fk_vehicle_type_manufacturers` FOREIGN KEY (`vehicle_type_id`) REFERENCES `vehicle_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `models` (
  `id` int NOT NULL,
  `model_name` varchar(255) DEFAULT NULL,
  `manufacturer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `manufacturer_id` (`manufacturer_id`),
  CONSTRAINT `models_ibfk_1` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `vehicle_safety_features` (
  `id` int NOT NULL AUTO_INCREMENT,
  `abs` tinyint(1) DEFAULT NULL,
  `esp` tinyint(1) DEFAULT NULL,
  `central_locking` tinyint(1) DEFAULT NULL,
  `traction_control` tinyint(1) DEFAULT NULL,
  `front_side_airbag` tinyint(1) DEFAULT NULL,
  `rear_side_airbag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `vehicle_extras` (
  `id` int NOT NULL AUTO_INCREMENT,
  `adaptive_cruise_control` tinyint(1) DEFAULT NULL,
  `air_suspension` tinyint(1) DEFAULT NULL,
  `alarm_system` tinyint(1) DEFAULT NULL,
  `ambient_lighting` tinyint(1) DEFAULT NULL,
  `android_auto` tinyint(1) DEFAULT NULL,
  `apple_carplay` tinyint(1) DEFAULT NULL,
  `arm_rest` tinyint(1) DEFAULT NULL,
  `automatic_air_conditioning` tinyint(1) DEFAULT NULL,
  `automatic_2_zone_climatisations` tinyint(1) DEFAULT NULL,
  `automatic_3_zones_climatisation` tinyint(1) DEFAULT NULL,
  `automatic_4_zones_climatisation` tinyint(1) DEFAULT NULL,
  `bi_xenon_headlights` tinyint(1) DEFAULT NULL,
  `bluetooth` tinyint(1) DEFAULT NULL,
  `blind_spot_assist` tinyint(1) DEFAULT NULL,
  `cd_player` tinyint(1) DEFAULT NULL,
  `cruise_control` tinyint(1) DEFAULT NULL,
  `dab_radio` tinyint(1) DEFAULT NULL,
  `distance_warning_system` tinyint(1) DEFAULT NULL,
  `electric_seat_adjustment` tinyint(1) DEFAULT NULL,
  `electric_side_mirror` tinyint(1) DEFAULT NULL,
  `electric_windows` tinyint(1) DEFAULT NULL,
  `emergency_call_system` tinyint(1) DEFAULT NULL,
  `emergency_tyre_repair_kit` tinyint(1) DEFAULT NULL,
  `fog_lamp` tinyint(1) DEFAULT NULL,
  `hands_free_kit` tinyint(1) DEFAULT NULL,
  `head_up_display` tinyint(1) DEFAULT NULL,
  `headlight_washer_system` tinyint(1) DEFAULT NULL,
  `heated_rear_seats` tinyint(1) DEFAULT NULL,
  `heated_seats` tinyint(1) DEFAULT NULL,
  `heated_steering_wheel` tinyint(1) DEFAULT NULL,
  `hill_start_assist` tinyint(1) DEFAULT NULL,
  `induction_charging_for_smartphones` tinyint(1) DEFAULT NULL,
  `isofix` tinyint(1) DEFAULT NULL,
  `keyless_central_locking` tinyint(1) DEFAULT NULL,
  `lane_change_assist` tinyint(1) DEFAULT NULL,
  `laser_headlights` tinyint(1) DEFAULT NULL,
  `leather_steering_wheel` tinyint(1) DEFAULT NULL,
  `led_headlights` tinyint(1) DEFAULT NULL,
  `lumbar_support` tinyint(1) DEFAULT NULL,
  `manual_climatisation` tinyint(1) DEFAULT NULL,
  `massage_seats` tinyint(1) DEFAULT NULL,
  `multifunction_steering_wheel` tinyint(1) DEFAULT NULL,
  `navigation_system` tinyint(1) DEFAULT NULL,
  `on_board_computer` tinyint(1) DEFAULT NULL,
  `paddle_shifters` tinyint(1) DEFAULT NULL,
  `panoramic_roof` tinyint(1) DEFAULT NULL,
  `parking_sensors` tinyint(1) DEFAULT NULL,
  `power_assisted_steering` tinyint(1) DEFAULT NULL,
  `rain_sensor` tinyint(1) DEFAULT NULL,
  `roof_rack` tinyint(1) DEFAULT NULL,
  `spare_tyre` tinyint(1) DEFAULT NULL,
  `sport_seats` tinyint(1) DEFAULT NULL,
  `start_stop_system` tinyint(1) DEFAULT NULL,
  `sunroof` tinyint(1) DEFAULT NULL,
  `traffic_sign_recognition` tinyint(1) DEFAULT NULL,
  `tyre_pressure_monitoring` tinyint(1) DEFAULT NULL,
  `usb_port` tinyint(1) DEFAULT NULL,
  `voice_control` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `vehicle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `manufacturer` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `body_type` varchar(255) NULL,
  `color` varchar(255) NOT NULL,
  `registered` date NOT NULL,
  `mileage` int DEFAULT NULL,
  `state` varchar(255) NOT NULL,
  `year` int NOT NULL,
  `number_of_wheels` int NOT NULL,
  `maximum_allowable_weight` double NULL,
  `engine_type` varchar(255) NOT NULL,
  `engine_displacement` varchar(255) NOT NULL,
  `engine_power` int NOT NULL,
  `fuel_consumption` varchar(255) NOT NULL,
  `transmission` varchar(255) NOT NULL,
  `drive_train` varchar(255) NOT NULL,
  `battery_capacity` double NULL,
  `charging_time` double NULL,
  `vehicle_range` double NULL,
  `city_id` int NOT NULL,
  `vehicle_type_id` int NOT NULL,
  `vehicle_safety_features_id` int NOT NULL,
  `vehicle_extras_id` int NOT NULL,
  
  PRIMARY KEY (`id`),
  KEY `fk_vehicle_type` (`vehicle_type_id`),
  KEY `fk_city_name` (`city_id`),
  KEY `fk_vehicle_safety_features` (`vehicle_safety_features_id`),
  KEY `fk_vehicle_extras` (`vehicle_extras_id`),
  CONSTRAINT `fk_vehicle_type` FOREIGN KEY (`vehicle_type_id`) REFERENCES `vehicle_type` (`id`),
  CONSTRAINT `fk_city_name` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`),
  CONSTRAINT `fk_vehicle_safety_features` FOREIGN KEY (`vehicle_safety_features_id`) REFERENCES `vehicle_safety_features` (`id`),
  CONSTRAINT `fk_vehicle_extras` FOREIGN KEY (`vehicle_extras_id`) REFERENCES `vehicle_extras` (`id`)
  
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vehicle_id` int DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vehicle_id` (`vehicle_id`),
  CONSTRAINT `images_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `company_oib` varchar(255) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `city_id` int NOT NULL,
  `role_id` int DEFAULT NULL,
  `email_verified` tinyint(1) NOT NULL DEFAULT '0',
  `confirmation_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `city_id` (`city_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `listing` (
  `id` int NOT NULL AUTO_INCREMENT,
  `listing_description` text NOT NULL,
  `listing_price` double NOT NULL,
  `vehicle_id` int NOT NULL,
  `user_id` int NOT NULL,
  `status` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `vehicle_id` (`vehicle_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `listing_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`),
  CONSTRAINT `listing_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_likes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `listing_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `listing_id` (`listing_id`),
  CONSTRAINT `user_likes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_likes_ibfk_2` FOREIGN KEY (`listing_id`) REFERENCES `listing` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;