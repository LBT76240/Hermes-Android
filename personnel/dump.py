for j in range(6):
    
    if(j==0):
        lettre = 'A'
    if(j==1):
        lettre = 'E'
    if(j==2):
        lettre = 'I'
    if(j==3):
        lettre = 'O'
    if(j==4):
        lettre = 'U'
    if(j==5):
        lettre = 'Y'
    
    
    adressein = 'D:\\Android\\MyFirstApplication\\personnelle\\'+lettre+'.txt'
    adresseout = 'D:\\Android\\MyFirstApplication\\personnelle\\'+lettre+'_data.txt'
    
    print(adressein)
    
        
    
    fichier_in = open (adressein,'r')
    fichier_out = open (adresseout,'w')
    
    
    for L in fichier_in:
        q=L.split('<')
        n = len(q)
        
        
        
        adressephoto = 0
        nomout = 0
        jobout = 0
        salleout = 0
        
        for i in range (n):
            string = q[i]
            if "ldapPhoto" in string :
                photo = q[i+1]
                photo = photo.split('"')
                adressephoto = photo[1]
                adressephoto = "http://trombi.tem-tsp.eu" + adressephoto
                
                
                
                
            if "ldapNom" in string :
                nom = string.split('>')
                nomout = nom[1]
                
                
            if "ldapInfo" in string :
                job = q[i+2]
                job = job.split('>')
                jobout = job[1]
                
            
            if "ldapTitre\">Bureau" in string :
                salle = q[i+1]
                salle = salle.split('>')
                salleout = salle[1]
                
                if salleout != " -" and adressephoto != 0 and nomout != 0 and jobout != 0 and salleout != 0:
                    
                
                    fichier_out.write(nomout)
                    fichier_out.write("\n")
                
                    fichier_out.write(jobout)
                    fichier_out.write("\n")
                
                    fichier_out.write(salleout)
                    fichier_out.write("\n")
                    
                    fichier_out.write(adressephoto)
                    fichier_out.write("\n")
                
    
    fichier_out.close()
    fichier_in.close()
    

adresseout = 'D:\\Android\\MyFirstApplication\\personnelle\\data_raw.txt'
fichier_out = open (adresseout,'w')
fichier_out.close()


for j in range(6):
    
    if(j==0):
        lettre = 'A'
    if(j==1):
        lettre = 'E'
    if(j==2):
        lettre = 'I'
    if(j==3):
        lettre = 'O'
    if(j==4):
        lettre = 'U'
    if(j==5):
        lettre = 'Y'
    
    
    adressein = 'D:\\Android\\MyFirstApplication\\personnelle\\'+lettre+'_data.txt'
    
    
    print(adressein)
    
    fichier_in = open (adressein,'r')
    
    
    i = 0
    nom = 0
    job = 0
    salle = 0
    photo = 0
    for L in fichier_in:
        if(i==0) :
            i=i+1
            nom = L
            
        elif(i==1) :
            i=i+1
            job = L
            
        elif(i==2) :
            i=i+1
            salle = L
        
        elif(i==3) :
            i=0
            photo = L
            
            bool = True
            
            fichier_chek = open(adresseout,'r+')
            
            
           
            
            for K in fichier_chek :
                
                if (nom==K):
                    bool = False
            
            
            
            
            
            if(bool and nom!=0 and job !=0 and salle !=0 and photo !=0) :
                fichier_chek.write(nom)
                fichier_chek.write(job)
                fichier_chek.write(salle)
                fichier_chek.write(photo)
                
                
            fichier_chek.close()
                
        
            nom = 0
            job = 0
            salle = 0
            photo = 0
    
    
    
    fichier_in.close()




import urllib.request

adresseout = 'D:\\Android\\MyFirstApplication\\personnelle\\data_string.txt'
fichier_out = open (adresseout,'w')
adressein = 'D:\\Android\\MyFirstApplication\\personnelle\\data_raw.txt'
fichier_in = open (adressein,'r')
print(adressein)
n=0
i=0
for L in fichier_in :
    out = L.split("\n")
    if(i<3) :
        i=i+1
        fichier_out.write("<item >\"")
        fichier_out.write(out[0])
        fichier_out.write("\"</item>\n")
    else :
        i=0
        n=n+1
        nstring=str(n)
        fichier_out.write("<item >\"")
        fichier_out.write('p')
        fichier_out.write(nstring)
        fichier_out.write("\"</item>\n\n")
        
        
        
        imageout = 'D:\\Android\\MyFirstApplication\\personnelle\\photo\\'+'p' +nstring+'.jpg'
        
        print(imageout)
        
        urllib.request.urlretrieve (out[0], imageout)
        


fichier_in.close()
fichier_out.close()

print(adresseout)





