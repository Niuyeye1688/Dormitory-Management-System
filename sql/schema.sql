-- 创建数据库
CREATE DATABASE IF NOT EXISTS dormitory_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE dormitory_db;

-- 1. 管理员表
CREATE TABLE IF NOT EXISTS admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '登录账号',
    password VARCHAR(100) NOT NULL COMMENT '登录密码 (MD5加密)',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    last_login_time DATETIME COMMENT '最后登录时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- 2. 学生表
CREATE TABLE IF NOT EXISTS student (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_no VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    password VARCHAR(100) NOT NULL COMMENT '登录密码 (MD5加密)',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender TINYINT NOT NULL COMMENT '性别: 0-女, 1-男',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    college VARCHAR(100) COMMENT '学院',
    major VARCHAR(100) COMMENT '专业',
    class_name VARCHAR(50) COMMENT '班级',
    enrollment_year INT COMMENT '入学年份',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-离校, 1-在校',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 3. 宿舍楼表
CREATE TABLE IF NOT EXISTS building (
    id INT PRIMARY KEY AUTO_INCREMENT,
    building_no VARCHAR(20) NOT NULL UNIQUE COMMENT '楼号',
    building_name VARCHAR(50) COMMENT '楼名称',
    floors INT NOT NULL COMMENT '楼层数',
    total_rooms INT NOT NULL COMMENT '总房间数',
    room_capacity INT DEFAULT 4 COMMENT '每间标准入住人数',
    building_type TINYINT DEFAULT 0 COMMENT '楼类型: 0-男寝, 1-女寝',
    manager_name VARCHAR(50) COMMENT '宿管姓名',
    manager_phone VARCHAR(20) COMMENT '宿管电话',
    address VARCHAR(200) COMMENT '地址描述',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-停用, 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宿舍楼表';

-- 4. 宿舍房间表
CREATE TABLE IF NOT EXISTS dormitory (
    id INT PRIMARY KEY AUTO_INCREMENT,
    building_id INT NOT NULL COMMENT '所属楼ID',
    room_no VARCHAR(20) NOT NULL COMMENT '房间号',
    floor INT NOT NULL COMMENT '所在楼层',
    capacity INT DEFAULT 4 COMMENT '床位数',
    current_count INT DEFAULT 0 COMMENT '当前入住人数',
    room_type TINYINT DEFAULT 0 COMMENT '房型: 0-四人间, 1-六人间, 2-双人间',
    leader_student_id INT COMMENT '宿舍长学生ID',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-维修中, 1-可用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_building_room (building_id, room_no),
    FOREIGN KEY (building_id) REFERENCES building(id) ON DELETE CASCADE,
    FOREIGN KEY (leader_student_id) REFERENCES student(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宿舍房间表';

-- 5. 住宿分配表
CREATE TABLE IF NOT EXISTS allocation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL COMMENT '学生ID',
    dormitory_id INT NOT NULL COMMENT '宿舍ID',
    bed_no INT COMMENT '床位号',
    check_in_date DATE NOT NULL COMMENT '入住日期',
    check_out_date DATE COMMENT '退宿日期',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-已退宿, 1-在住',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (dormitory_id) REFERENCES dormitory(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='住宿分配表';

-- 6. 报修工单表
CREATE TABLE IF NOT EXISTS repair (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL COMMENT '报修学生ID',
    dormitory_id INT NOT NULL COMMENT '所在宿舍ID',
    repair_type TINYINT NOT NULL COMMENT '报修类型: 0-水电, 1-门窗, 2-家具, 3-网络, 4-其他',
    title VARCHAR(100) NOT NULL COMMENT '报修标题',
    description TEXT COMMENT '问题描述',
    image_urls TEXT COMMENT '图片URL列表 (JSON数组)',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待处理, 1-处理中, 2-已完成, 3-已驳回',
    handler_name VARCHAR(50) COMMENT '处理人',
    handle_result TEXT COMMENT '处理结果',
    handle_time DATETIME COMMENT '处理时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (dormitory_id) REFERENCES dormitory(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报修工单表';

-- 7. 公告表
CREATE TABLE IF NOT EXISTS announcement (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    publisher_id INT COMMENT '发布者管理员ID',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶: 0-否, 1-是',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-草稿, 1-已发布',
    publish_time DATETIME COMMENT '发布时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (publisher_id) REFERENCES admin(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告表';
