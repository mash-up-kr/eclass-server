# 멤버
CREATE TABLE IF NOT EXISTS `member` (
    `member_id` bigint NOT NULL AUTO_INCREMENT,
    `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# 펫
CREATE TABLE IF NOT EXISTS `pet` (
    `pet_id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `birth_date` datetime DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`pet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# 적용된 스티커
CREATE TABLE IF NOT EXISTS `attached_sticker` (
    `attached_sticker_id` bigint NOT NULL AUTO_INCREMENT,
    `sticker_id` bigint NOT NULL,
    `attached_id` bigint NOT NULL,
    `member_id` bigint NOT NULL,
    `sticker_x` double DEFAULT NULL,
    `sticker_y` double DEFAULT NULL,
    `attached_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`attached_sticker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# 뱃지
CREATE TABLE IF NOT EXISTS `badge` (
    `badge_id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`badge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# 메인 커버
CREATE TABLE IF NOT EXISTS `cover` (
    `cover_id` bigint NOT NULL AUTO_INCREMENT,
    `pet_id` bigint NOT NULL,
    `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `shape_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `shape_x` double DEFAULT NULL,
    `shape_y` double DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`cover_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# 다이어리
CREATE TABLE IF NOT EXISTS `diary` (
    `diary_id` bigint NOT NULL AUTO_INCREMENT,
    `pet_id` bigint NOT NULL,
    `badge_id` bigint NOT NULL,
    `member_id` bigint NOT NULL,
    `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`diary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# 다이어리 사진
CREATE TABLE IF NOT EXISTS `diary_picture` (
    `diary_picture_id` bigint NOT NULL AUTO_INCREMENT,
    `diary_id` bigint NOT NULL,
    `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `is_thumbnail` tinyint(1) DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`diary_picture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# 댓글
CREATE TABLE IF NOT EXISTS `reply` (
    `reply_id` bigint NOT NULL AUTO_INCREMENT,
    `diary_id` bigint NOT NULL,
    `member_id` bigint NOT NULL,
    `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`reply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# 스티커
CREATE TABLE IF NOT EXISTS `sticker` (
    `sticker_id` bigint NOT NULL AUTO_INCREMENT,
    `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`sticker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;