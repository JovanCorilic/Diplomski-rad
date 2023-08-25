/*TIP*/
INSERT INTO tip (id,naziv,broj_pojavljivanja) VALUES (1,'Voce',0)
INSERT INTO tip (id,naziv,broj_pojavljivanja) VALUES (2,'Racunar',0)
INSERT INTO tip (id,naziv,broj_pojavljivanja) VALUES (3,'Test',0)

/*Produkt*/
INSERT INTO produkt(id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id) VALUES (1,100,'2020-12-1','Tako nesto','Jagoda',5,5,'123',null)
INSERT INTO produkt(id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id) VALUES (2,200,'2020-10-1','Tako nestowdwdw','Procesor',4,4,'223',null)
INSERT INTO produkt(id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id) VALUES (3,300,'2020-11-1','Tako nestowdwdwdwdw','Nesto',3,3,'323',null)

/*Veza tipa i produkta*/
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (1,1)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (2,2)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (3,3)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (3,1)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (3,2)