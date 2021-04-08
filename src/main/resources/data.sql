Delete from user_role;
Delete from USERS ;
Delete from ROLE;

INSERT INTO USERS (id,user_name,password,active) VALUES (1,'turkcell','$2a$10$MK9vkwcMwwWaKZk.Aiysju3ca/mATwm0Fo7pFtgocPPL21vtqUmjO',1);
INSERT INTO ROLE (id,role_type) VALUES (1,'ADMIN');
INSERT INTO user_role (user_id,role_id) VALUES(1,1);