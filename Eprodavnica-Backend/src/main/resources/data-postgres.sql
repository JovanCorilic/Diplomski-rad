/*Musterija*/
INSERT INTO korisnik(user_id,email,ime,lozinka,odobren_od_admina,potvrdjen,prezime) VALUES (0,'nesto@gmail.com','Test','tERtm+D3BqYvXbDztPObaVg4xH90ZSMBi5Z8bWJgPiYt6dpGZaEDkvMyClBGwHJWHq7gBuSvtxjq7w1vi2jwL4A=yCwn9GttRJnMYxVwCs8qFyHtl5LIXtFFuuAK+W+tvQ==',true,true,'ĆČŠĐĆ')

/*Prodavac*/
INSERT INTO korisnik(user_id,email,ime,lozinka,odobren_od_admina,potvrdjen,prezime) VALUES (1,'nesto2@gmail.com','wdwdw','tERtm+D3BqYvXbDztPObaVg4xH90ZSMBi5Z8bWJgPiYt6dpGZaEDkvMyClBGwHJWHq7gBuSvtxjq7w1vi2jwL4A=yCwn9GttRJnMYxVwCs8qFyHtl5LIXtFFuuAK+W+tvQ==',true,true,'ĆČŠeeĐĆ')

/*Uloge*/
INSERT INTO uloga(id,name) VALUES (1,'ROLE_SUPERADMIN')
INSERT INTO uloga(id,name) VALUES (2,'ROLE_ADMIN')
INSERT INTO uloga(id,name) VALUES (3,'ROLE_PRODAVAC')
INSERT INTO uloga(id,name) VALUES (4,'ROLE_MUSTERIJA')

/*Uloga konekcija*/
INSERT INTO korisnik_uloge(korisnik_list_user_id,uloge_id) VALUES (0,4)
INSERT INTO korisnik_uloge(korisnik_list_user_id,uloge_id) VALUES (1,3)

/*TIP*/
INSERT INTO tip (id,naziv) VALUES (0,'Voce')
INSERT INTO tip (id,naziv) VALUES (1,'Racunar')
INSERT INTO tip (id,naziv) VALUES (2,'Test')

/*Produkt*/
INSERT INTO produkt(id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato) VALUES (0,100,'2020-12-1','Tako nesto','Jagoda',5.0,5,'123',1,5,true,0)
INSERT INTO produkt(id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato) VALUES (1,200,'2020-10-1','Tako nestowdwdw','Procesor',-1.0,-1,'223',1,0,true,0)
INSERT INTO produkt(id,cena,datum_pravljenja,deskripcija,naziv,ocena,ocena_pun_broj,serijski_broj,prodavac_user_id,akcija,odobren_od_admina,broj_prodato) VALUES (2,300,'2020-11-1','Tako nestowdwdwdwdw','Nesto',-1.0,-1,'323',1,40,true,0)

/*Veza tipa i produkta*/
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (0,0)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (1,1)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (2,2)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (2,0)
INSERT INTO produkt_lista_tipova(lista_produkata_id,lista_tipova_id) VALUES (2,1)

/*Recenzija*/
INSERT INTO recenzija(id,datum_pravljenja,komentar,ocena,musterija_user_id,produkt_id) VALUES (5000,'2022-12-1','wdwdwdw wdwdw wdw',5,0,0)

/*Racun*/
INSERT INTO racun(id,broj_racuna,datum_kreiranja,konacna_cena,musterija_user_id ,korpa) values (0,'qwe','2021-12-1',5000.0,0,false)
INSERT INTO artikal(id,broj,produkt_id,racun_id,akcija,cena,naziv_produkta,ukupna_cena) VALUES (5000,2,0,0,10,50,'Test',1000)

/*Wishlist*/
INSERT INTO korisnik_wishlist(wishlist_user_id,wishlist_id) VALUES (0,0)

/*Istorija kupljenih*/
INSERT INTO korisnik_istorija_kupljenih_produkata(istorija_kupaca_user_id,istorija_kupljenih_produkata_id) VALUES (0,0)
