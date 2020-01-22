SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `news`;

CREATE DATABASE `news`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `news`;

/* Удаление объектов БД */


DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `news`;


/* Структура для таблицы `news`:  */

CREATE TABLE `news` (
  `id_news` BIGINT NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(100) COLLATE utf8_general_ci NOT NULL COMMENT 'Краткое название новости',
  `text_news` TEXT COLLATE utf8_general_ci NOT NULL COMMENT 'Описание новости',
  `date_news` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `path_image` VARCHAR(200) COLLATE utf8_general_ci DEFAULT '',
  PRIMARY KEY USING BTREE (`id_news`),
  UNIQUE KEY `id_news` USING BTREE (`id_news`)
) ENGINE=InnoDB
AUTO_INCREMENT=37 ROW_FORMAT=DYNAMIC CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='Новости'
;

/* Структура для таблицы `users`:  */

CREATE TABLE `users` (
  `id_user` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор пользователя',
  `login` VARCHAR(80) COLLATE utf8_general_ci NOT NULL COMMENT 'Логин пользователя (email)',
  `first_name` VARCHAR(80) COLLATE utf8_general_ci NOT NULL COMMENT 'Имя пользователя',
  `last_name` VARCHAR(80) COLLATE utf8_general_ci NOT NULL COMMENT 'Фамилия пользователя',
  `patronomic` VARCHAR(80) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Отчество пользователя',
  `password` VARCHAR(80) COLLATE utf8_general_ci NOT NULL COMMENT 'Пароль пользователя',
  `active` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Признак подтверждения учетной записи',
  `token` VARCHAR(100) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Идентификатор для подтверждения аккаунта',
  `registration_date` DATETIME NOT NULL COMMENT 'Дата и время регистрации',
  PRIMARY KEY USING BTREE (`id_user`),
  UNIQUE KEY `id_user` USING BTREE (`id_user`),
  UNIQUE KEY `login` USING BTREE (`login`),
  UNIQUE KEY `token` USING BTREE (`token`)
) ENGINE=InnoDB
AUTO_INCREMENT=38 ROW_FORMAT=DYNAMIC CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'
COMMENT='Таблица пользователей системы'
;

/* Data for the `news` table  (LIMIT 0,500) */

INSERT INTO `news` (`id_news`, `label`, `text_news`, `date_news`, `path_image`) VALUES
  (1,'Полиция Гоа разогнала концерт Бориса Гребенщикова, туристы крайне недовольны.','Отпуск в конце января –<b> начале февраля</b> для оптимиста – это хороший шанс погреться на теплых берегах за очень доступные деньги. Турпром подобрал для вас наиболее «вкусные» предложения от туроператоров на тёплые туры на ближайшие заезды. Напомним, цена указана за одного человека, а сами цены кликабельны: ✈ Шарджа (ОАЭ) – всего за 15’973 р/чел с 21 января!','2020-01-21 00:06:31','Greb.jpg'),
  (2,'\r\nОткрытое небо сулит Санкт-Петербургу хаос, а туристам – дефицит мест в отелях и музеях','Режим «открытого неба» в Санкт-Петербурге вкупе с активным продвижением электронных виз может привести как к успешному развитию туризма в городе на Неве, так и к сложностям и хаосу. Всё будет зависеть от того, насколько туристические власти Северной столицы будут внимательны и готовы работать в изменившихся','2020-01-20 23:00:56','Piter.jpg'),
  (3,'Составлены самые дешевые места в мире для жизни в 2020 году','Туристические направления Юго-Восточной Азии оказались лидерами рейтинга International Living, возглавив перечень самых дешевых для жизни стран. Речь идет о Вьетнаме и Камбодже. Хотя рейтинг оценивает места для жизни с точки зрения проведения пенсионного возраста, в него входят многие актуальные для туристов пункты – такие как стоимость продуктов и общепита','2020-01-20 23:02:33','Asia.jpg'),
  (4,'Бастующие пенсионеры заблокировали для туристов парижский Лувр','«Леонардо бастует!», - таким заявлением встречал туристов знаменитый парижский музей Лувр. Сотрудники музея, где на данный момент проходит специальная выставка Леонардо да Винчи, посвященная 500-летию со дня смерти художника, присоединились к национальной забастовке «в честь» пенсионных реформ, которые длятся с начала декабря. ','2020-01-20 23:45:57','Pens.jpg'),
  (5,'Российский турист арестован в Индии за перевозку наркотиков','38-летний российский турист был пойман с более чем 1 кг чараса (Чарас на хинди это необработанная смола с соцветий конопли – наркотик, как и гашиш) в районе Куллу штата Химачал-Прадеш рано утром в понедельник, сообщила местная полиция. Индийские СМИ сообщают, что российский турист по имени Вячеслав был арестован после изъятия 1.263 кг контрабанды наркотика','2020-01-20 23:46:41','Drugs.jpg'),
  (6,'Постояльцы мини-отеля сварились в кипятке','Пять человек, среди них один ребенок, погибли в результате аварии в мини-отеле «Карамель» в Перми. В отеле, находящемся в «цокольном этаже», а точнее говоря подвальном помещении, прорвало трубу горячего водоснабжения, в результате его затопило горячей водой. По информации местного МЧС, пятеро погибших были обнаружены во время работ по откачке воды. Еще одна пострадавшая в тяжелом состоянии','2020-01-20 23:47:35','Zhest.jpg'),
  (7,'Иностранный туризм отказался от Ирана, авиакомпании сняли рейсы','Сбитый Ираном украинский Боинг оказал критическое влияние на туристический сектор, большая часть авиаперевозчиков сняла рейсы и скорее всего не вернет их до высокого сезона. Такой пессимистичный прогноз представила ассоциация авиатранспортных и туристических агентств Ирана. В частности, единственный крупный европейский перевозчик, летающий в Иран','2020-01-20 23:48:22','Iran.jpg');
COMMIT;

/* Data for the `users` table  (LIMIT 0,500) */

INSERT INTO `users` (`id_user`, `login`, `first_name`, `last_name`, `patronomic`, `password`, `active`, `token`, `registration_date`) VALUES
  (31,'senko.leonid@gmail.com','Leonid','Senko',NULL,'$2a$12$7KYtt4HBIvUUKcOuOt7x/.ZLSQ.3JLH5zcazl2Rrx.o/ASMv36G/6',1,'$2a$12$XcuqNIt57tuHDne9larXRu067zwJ05p7hHo361M0On7y2yRuXKhJe','2019-01-29 09:04:44'),
  (32,'sdc','sdc','sdcsdc',NULL,'werfwef',0,NULL,'2019-02-15 00:00:00'),
  (37,'senkoleonid@yandex.ru','L','S',NULL,'$2a$12$LCl61jilvsxMWdIOsylQ/.4EK0XsuVoqnBhgm2RIeY4qTpjDXbCr.',0,'$2a$12$ZlgVl3WsQ17q8hU5YUlh7OU0XC2e7MLurWbd58Lo5Covq/lUIKLOS','2020-01-20 22:32:06');
COMMIT;