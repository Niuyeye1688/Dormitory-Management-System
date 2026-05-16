Get-ChildItem -Path "E:\DormitoryManagement\src\main\webapp\WEB-INF\views" -Recurse -Filter "*.jsp" | ForEach-Object {
    $file = $_.FullName
    $content = [System.IO.File]::ReadAllText($file, [System.Text.Encoding]::UTF8)
    $original = $content
    
    # Common corruption replacements
    $content = $content -replace '管理\x{FFFD}\?', '管理员'
    $content = $content -replace '控制\x{FFFD}\?', '控制台'
    $content = $content -replace '宿舍楼管\x{FFFD}\?', '宿舍楼管理'
    $content = $content -replace '退出登\x{FFFD}\?', '退出登录'
    $content = $content -replace '用户\x{FFFD}\?', '用户名'
    $content = $content -replace '请输入密\x{FFFD}\?', '请输入密码'
    $content = $content -replace '验证\x{FFFD}\?', '验证码'
    $content = $content -replace '\x{FFFD}\?\x{FFFD}\?', '登录'
    $content = $content -replace '默认学生\x{FFFD}\?', '默认学生：'
    $content = $content -replace '原密\x{FFFD}\?', '原密码'
    $content = $content -replace '新密\x{FFFD}\?', '新密码'
    $content = $content -replace '确认新密\x{FFFD}\?', '确认新密码'
    $content = $content -replace '状\x{FFFD}\?', '状态'
    $content = $content -replace '上一\x{FFFD}\?', '上一页'
    $content = $content -replace '下一\x{FFFD}\?', '下一页'
    $content = $content -replace '此操作不可恢复\x{FFFD}\?', '此操作不可恢复。'
    $content = $content -replace '已入\x{FFFD}\?', '已入住'
    $content = $content -replace '待处理报\x{FFFD}\?', '待处理报修'
    $content = $content -replace '宿舍\x{FFFD}\?', '宿舍楼'
    $content = $content -replace '\x{FFFD}\?/option>', '男</option>'
    $content = $content -replace '\x{FFFD}\?/option>', '女</option>'
    
    if ($content -ne $original) {
        [System.IO.File]::WriteAllText($file, $content, [System.Text.UTF8Encoding]::new($false))
        Write-Host "Fixed: $file"
    }
}