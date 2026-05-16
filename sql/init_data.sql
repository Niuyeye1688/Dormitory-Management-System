USE dormitory_db;

-- 初始化管理员
-- 密码: 123456 (初始使用MD5存储，用户修改密码后将自动升级为SHA256+盐格式)
INSERT INTO admin (username, password, real_name, phone) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', '13800000000');

-- 初始化学生数据
INSERT INTO student (student_no, password, name, gender, phone, email, college, major, class_name, enrollment_year) VALUES
('2023001', 'e10adc3949ba59abbe56e057f20f883e', '张三', 1, '13811111111', 'zhangsan@example.com', '计算机学院', '软件工程', '软工2301', 2023),
('2023002', 'e10adc3949ba59abbe56e057f20f883e', '李四', 1, '13822222222', 'lisi@example.com', '计算机学院', '软件工程', '软工2301', 2023),
('2023003', 'e10adc3949ba59abbe56e057f20f883e', '王五', 1, '13833333333', 'wangwu@example.com', '计算机学院', '网络工程', '网工2301', 2023),
('2023004', 'e10adc3949ba59abbe56e057f20f883e', '赵六', 0, '13844444444', 'zhaoliu@example.com', '电子工程学院', '电子信息工程', '电信2301', 2023),
('2023005', 'e10adc3949ba59abbe56e057f20f883e', '孙七', 0, '13855555555', 'sunqi@example.com', '电子工程学院', '电子信息工程', '电信2301', 2023);

-- 初始化宿舍楼
INSERT INTO building (building_no, building_name, floors, total_rooms, room_capacity, building_type, manager_name, manager_phone, address) VALUES
('1号楼', '男生宿舍1号楼', 6, 120, 4, 0, '王大爷', '13900000001', '校园东区'),
('2号楼', '男生宿舍2号楼', 6, 120, 4, 0, '李大爷', '13900000002', '校园东区'),
('3号楼', '女生宿舍1号楼', 6, 120, 4, 1, '张阿姨', '13900000003', '校园西区'),
('4号楼', '女生宿舍2号楼', 6, 120, 4, 1, '刘阿姨', '13900000004', '校园西区');

-- 初始化宿舍房间 (仅示例：1号楼每层2间)
INSERT INTO dormitory (building_id, room_no, floor, capacity, current_count, room_type, status) VALUES
(1, '101', 1, 4, 0, 0, 1),
(1, '102', 1, 4, 0, 0, 1),
(1, '201', 2, 4, 0, 0, 1),
(1, '202', 2, 4, 0, 0, 1),
(1, '301', 3, 4, 0, 0, 1),
(1, '302', 3, 4, 0, 0, 1),
(2, '101', 1, 4, 0, 0, 1),
(2, '102', 1, 4, 0, 0, 1),
(3, '101', 1, 4, 0, 0, 1),
(3, '102', 1, 4, 0, 0, 1),
(4, '101', 1, 4, 0, 0, 1),
(4, '102', 1, 4, 0, 0, 1);

-- 初始化公告
INSERT INTO announcement (title, content, publisher_id, is_top, status, publish_time) VALUES
('欢迎使用学生宿舍管理系统', '系统已正式上线，请各位同学及时完善个人信息。如有问题请联系宿管。', 1, 1, 1, NOW()),
('关于宿舍安全用电的通知', '为了保障宿舍安全，请同学们注意用电安全，禁止使用大功率电器。', 1, 0, 1, NOW()),
('宿舍卫生检查通知', '本周五下午将进行宿舍卫生检查，请各宿舍做好准备。', 1, 0, 1, NOW());
