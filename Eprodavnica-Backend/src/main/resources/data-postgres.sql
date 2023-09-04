/*Musterija*/
INSERT INTO korisnik(user_id,email,ime,lozinka,odobren_od_admina,potvrdjen,prezime) VALUES (1,'nesto@gmail.com','Test','tERtm+D3BqYvXbDztPObaVg4xH90ZSMBi5Z8bWJgPiYt6dpGZaEDkvMyClBGwHJWHq7gBuSvtxjq7w1vi2jwL4A=yCwn9GttRJnMYxVwCs8qFyHtl5LIXtFFuuAK+W+tvQ==',true,true,'ĆČŠĐĆ')

/*Prodavac*/
INSERT INTO korisnik(user_id,email,ime,lozinka,odobren_od_admina,potvrdjen,prezime) VALUES (2,'nesto2@gmail.com','wdwdw','tERtm+D3BqYvXbDztPObaVg4xH90ZSMBi5Z8bWJgPiYt6dpGZaEDkvMyClBGwHJWHq7gBuSvtxjq7w1vi2jwL4A=yCwn9GttRJnMYxVwCs8qFyHtl5LIXtFFuuAK+W+tvQ==',true,true,'ĆČŠeeĐĆ')

/*Uloge*/
INSERT INTO uloga(id,name) VALUES (1,'ROLE_SUPERADMIN')
INSERT INTO uloga(id,name) VALUES (2,'ROLE_ADMIN')
INSERT INTO uloga(id,name) VALUES (3,'ROLE_PRODAVAC')
INSERT INTO uloga(id,name) VALUES (4,'ROLE_MUSTERIJA')

/*Uloga konekcija*/
INSERT INTO korisnik_uloge(korisnik_list_user_id,uloge_id) VALUES (1,4)
INSERT INTO korisnik_uloge(korisnik_list_user_id,uloge_id) VALUES (2,3)

/*TIP*/
INSERT INTO tip (id,naziv,broj_pojavljivanja) VALUES (1,'Voce',0)
INSERT INTO tip (id,naziv,broj_pojavljivanja) VALUES (2,'Racunar',0)
INSERT INTO tip (id,naziv,broj_pojavljivanja) VALUES (3,'Test',0)

/*Produkt*/
INSERT INTO produkt(id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija) VALUES (1,100,'2020-12-1','Tako nesto','Jagoda',5,5,'123',2,5)
INSERT INTO produkt(id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija) VALUES (2,200,'2020-10-1','Tako nestowdwdw','Procesor',4,4,'223',2,0)
INSERT INTO produkt(id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija) VALUES (3,300,'2020-11-1','Tako nestowdwdwdwdw','Nesto',3,3,'323',2,40)

/*Veza tipa i produkta*/
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (1,1)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (2,2)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (3,3)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (3,1)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (3,2)

/*Recenzija*/
INSERT INTO recenzija(id,datum_pravljenja,komentar,ocena,musterija_user_id,produkt_id) VALUES (1,'2022-12-1','wdwdwdw wdwdw wdw',5,1,1)