import os
import glob

# Dictionary of known corruptions and their fixes
replacements = {
    '管理\uFFFD\u003F': '管理员',
    '控制\uFFFD\u003F': '控制台',
    '宿舍楼管\uFFFD\u003F': '宿舍楼管理',
    '退出登\uFFFD\u003F': '退出登录',
    '用户\uFFFD\u003F': '用户名',
    '请输入密\uFFFD\u003F': '请输入密码',
    '验证\uFFFD\u003F': '验证码',
    '\uFFFD\u003F\uFFFD\u003F': '登录',
    '默认学生\uFFFD\u003F': '默认学生：',
    '原密\uFFFD\u003F': '原密码',
    '新密\uFFFD\u003F': '新密码',
    '确认新密\uFFFD\u003F': '确认新密码',
    '状\uFFFD\u003F': '状态',
    '上一\uFFFD\u003F': '上一页',
    '下一\uFFFD\u003F': '下一页',
    '此操作不可恢复\uFFFD\u003F': '此操作不可恢复。',
    '已入\uFFFD\u003F': '已入住',
    '待处理报\uFFFD\u003F': '待处理报修',
    '宿舍\uFFFD\u003F': '宿舍楼',
    '请输入用户名或学\uFFFD\u003F': '请输入用户名或学号',
    '\uFFFD\u003F/option>': '男</option>',
    '\uFFFD\u003F/option>': '女</option>',
    '页，\uFFFD\u003F': '页，共',
    '\uFFFD\u003F</span>': '条</span>',
    '%\\u003e': '%>',
    '\\u003e': '>',
}

views_dir = r"E:\DormitoryManagement\src\main\webapp\WEB-INF\views"
fixed_count = 0

for root, dirs, files in os.walk(views_dir):
    for file in files:
        if file.endswith('.jsp'):
            filepath = os.path.join(root, file)
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()
            
            original = content
            for old, new in replacements.items():
                content = content.replace(old, new)
            
            if content != original:
                with open(filepath, 'w', encoding='utf-8') as f:
                    f.write(content)
                print(f"Fixed: {filepath}")
                fixed_count += 1

print(f"\nTotal files fixed: {fixed_count}")
