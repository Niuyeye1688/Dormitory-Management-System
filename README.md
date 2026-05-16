# 学生宿舍管理系统

基于 Java Servlet + JSP + MySQL 的宿舍管理系统。

## 环境要求

| 依赖 | 版本 |
|------|------|
| JDK | 1.8+ |
| Maven | 3.6+ |
| MySQL | 5.7+ |

## 快速开始

### 1. 安装 MySQL 并创建数据库

确保 MySQL 服务已启动，然后执行 SQL 脚本：

```bash
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/init_data.sql
```

以上命令会：
- 创建 `dormitory_db` 数据库
- 建立表结构（管理员、学生、宿舍楼、房间、住宿分配、报修、公告）
- 插入初始测试数据

### 2. 修改数据库连接配置

编辑 `src/main/resources/db.properties`，将数据库用户名和密码改成你自己的：

```properties
url=jdbc:mysql://localhost:3306/dormitory_db?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8
username=root        # 改成你的 MySQL 用户名
password=123123      # 改成你的 MySQL 密码
```

### 3. 启动项目

```bash
cd E:\DormitoryManagement
mvn tomcat7:run
```

项目启动后访问：**http://localhost:8080**

### 默认测试账号

| 角色 | 账号 | 密码 |
|------|------|------|
| 管理员 | `admin` | `123456` |
| 学生 | `2023001` | `123456` |

## 项目打包部署（生产环境）

```bash
mvn clean package
```

生成的 WAR 包位于 `target/DormitoryManagement.war`，将其放入外部 Tomcat 的 `webapps` 目录即可。

## 终止运行

按 `Ctrl + C` 停止 `mvn tomcat7:run` 进程。
