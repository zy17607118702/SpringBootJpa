INSERT INTO `tm_sys_user`
VALUES
	(
	1,
	'00000',
	NOW( ),
	'1',
	'00000',
	NOW( ),
	NULL,
	NULL,
	'0',
	NULL,
	'',
	'男',
	'admin',
	'$2a$10$V9kGn/imyO1TC8hJZWUiDe0PlUzUWIyuV4OVLksD1K5GL0Rgr7GJG' 
	);
INSERT INTO `tm_sys_role`
VALUES
	( 1, '00000', NOW(),, '1', '00000', NOW(),, '1', 'admin' );
INSERT INTO `tm_sys_resource`
VALUES
	( 1, '00000', NOW(),, '1', '00000', NOW(),, 0, 1, '用户信息', 'user', '/api/user', 'url' );
INSERT INTO `tr_sys_role_resource`
VALUES
	( 1, '00000', NOW(),, '1', '00000', NOW(),, 1, 1 );
INSERT INTO `tr_sys_user_role`
VALUES
	( 1, '00000', NOW(), '1', '00000', NOW(), 1, 1 );