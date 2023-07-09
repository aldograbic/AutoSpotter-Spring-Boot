INSERT INTO vehicle_type VALUES(1,"Automobil");
INSERT INTO vehicle_type VALUES(2,"Motor");
INSERT INTO vehicle_type VALUES(3,"Kamion");

INSERT INTO states VALUES(1,"Novo");
INSERT INTO states VALUES(2,"Rabljeno");
INSERT INTO states VALUES(3,"Oštećeno");
INSERT INTO states VALUES(4,"Neispravno");
INSERT INTO states VALUES(5,"Za dijelove");

INSERT INTO body_type VALUES (1, "Hatchback");
INSERT INTO body_type VALUES (2, "Kabriolet");
INSERT INTO body_type VALUES (3, "Karavan");
INSERT INTO body_type VALUES (4, "Kupe");
INSERT INTO body_type VALUES (5, "Limuzina");
INSERT INTO body_type VALUES (6, "Monovolumen");
INSERT INTO body_type VALUES (7, "SUV");

INSERT INTO engine_type VALUES (1, "Benzin");
INSERT INTO engine_type VALUES (2, "Dizel");
INSERT INTO engine_type VALUES (3, "Električni");
INSERT INTO engine_type VALUES (4, "Hibrid");
INSERT INTO engine_type VALUES (5, "Plin");
INSERT INTO engine_type VALUES (6, "Plug-in hibrid");
INSERT INTO engine_type VALUES (7, "Vodik");

INSERT INTO transmission VALUES (1, "Ručni");
INSERT INTO transmission VALUES (2, "Automatski");
INSERT INTO transmission VALUES (3, "Poluautomatski");

INSERT INTO drive_train VALUES (1, "Prednji");
INSERT INTO drive_train VALUES (2, "Zadnji");
INSERT INTO drive_train VALUES (3, "Na sva četri");

INSERT INTO manufacturers VALUES(1, "BMW", 1);
INSERT INTO manufacturers VALUES(2, "Audi", 1);
INSERT INTO manufacturers VALUES(3, "Honda", 2);
INSERT INTO manufacturers VALUES(4, "MAN", 3);

INSERT INTO models VALUES (1, "3", 1);
INSERT INTO models VALUES (2, "A4", 2);
INSERT INTO models VALUES (3, "G200", 3);
INSERT INTO models VALUES (4, "TGL", 4);

INSERT INTO countries VALUES (1, "Hrvatska");

INSERT INTO counties VALUES (1, "Zagrebačka", "ZŽ", 1);

INSERT INTO cities VALUES (1, "Velika Gorica", 1);

INSERT INTO user VALUES(1, "agrabic", "$2a$12$N86VahTqVHFFchpiMvJ0g...4PuWtcL9oSq/dN/0ZXd9DlHo6BRPa", "Aldo", "Grabić", null, null, "Nikole Tesle 33", "+385 91 26222 17", "aldo.grabic99@gmail.com", 1);