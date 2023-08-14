insert into admins(id, email, first_name, last_name, role_id, level, created_time, updated_time, last_activity_time,
                   status)
values (100, 'email1@gmail.com', 'f1', 'l1', 5, 1, '2020-06-02 11:12:000000', '2021-06-02 11:12:000000',
        '2023-08-02 11:12:000000', 'ENABLED');

insert into passwords(id, password_hash, status, updated_by, admin_id, disabled_at, created_time,
                      updated_time)
values (100, 'hash1', 'DISABLED', 200, 100, null, '2022-06-02 11:12:000001', '2022-06-02 10:10:02.0000002'),
       (200, 'hash2', 'DISABLED', 200, 100, null, '2022-06-06 11:12:00', '2022-06-05 10:10:02.0000002');