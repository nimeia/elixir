insert into user(username, display_name, phone, email, password, create_by, create_time, locked, lock_time,
                 short_lock_time, login_fail_times, valid_time, enabled)
    value (
           'admin', '管理员', '13800138000',
           'admin@admin.com', '{bcrypt}$2a$10$WEwjqsPmRJ5o4mDGs27SueYeIGu190ZfYRhZJs5U11Zy8gt3x61mS',
           'system init', curdate(), false, null,
           null, 0, current_timestamp(), true
    )