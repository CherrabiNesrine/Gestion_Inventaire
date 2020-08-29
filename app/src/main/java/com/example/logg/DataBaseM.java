

package com.example.logg;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.database.sqlite.SQLiteStatement;

        import java.net.IDN;
        import java.util.Date;
        import androidx.annotation.Nullable;

        import java.text.SimpleDateFormat;
        import java.util.Date;

        import static com.example.logg.DataBaseFact.Table_name_F;

public class DataBaseM extends SQLiteOpenHelper {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static final String DataBase_name ="Magasin.sqlite";
    public static final String Table_name="prod";
    public static final String Table_name_O="OPERATEUR";
    public static final String Table_name_COM="Commande";
    public static final String Table_name_FOU="fournisseur";
    public static final String Table_name_client="client";
    public static final String Table_name_comAchat="commandeAchat";
    public static final String Table_name_comVente="commandeVente";
    public static final String Table_name_Facture="Facture";
    public static final String Table_name_factAchat="factureAchat";
    public static final String Table_name_factVente="factureVente";
    public static final String Table_name_Magasin="mag";
    public static final String Table_name_inventaire="inventaire";
    public static final String Table_name_Rapport="Rapport";
    public static final String Table_name_depenses="depense";
    public static final String Table_name_Entreprise="Entreprise";
    public static final String Table_name_Historique="historique";


//---------------------------------------------------------

    public static final String TABLE_NAME = "JserTable";

    public static final String Table_Column_ID = "id";

    public static final String Table_Column_1_Name = "nom";

    public static final String Table_Column_2_Prenom = "prenom";

    public static final String Table_Column_3_Password = "password";

    public static final String Table_Column_4_username = "username";

    public static final String KEY_IMG = "image";






















//-----------------------------------------------------------------------------------------

    public DataBaseM(@Nullable Context context) {
        super(context,DataBase_name, null, 17);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public void QueryData(){
        SQLiteDatabase db  = getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Entreprise+"(NIF varchar(13)PRIMARY KEY,Nom varchar(20),RG varchar(20),secteur varchar(50),taille integer ,statujur varchar(60),email varchar,tlf varchar,Address varchar,Site varchar,Fax varchar, image BLOG)");
        db.execSQL("CREATE TABLE IF NOT EXISTS mag (nomMAg varchar PRIMARY KEY ,typemag varchar,mesuremag varchar)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + Table_Column_ID + " INTEGER PRIMARY KEY, " + Table_Column_1_Name + " VARCHAR, " + Table_Column_2_Prenom + " VARCHAR, " + Table_Column_3_Password + " VARCHAR  ," + Table_Column_4_username + " VARCHAR ," + KEY_IMG + " blob)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name+"(ID varchar(14)PRIMARY KEY,Nom varchar(20),dateF Date ,datE Date , categorie varchar,sousCategorie varchar,matiere varchar,quantite number,prix number,typePr varchar,DateENtr Date,coin varchar,coins varchar,unit varchar,pricS number,discription text,fournisseur varchar,client varchar,idmag varchar,QNTT number,dateDel date,image BLOG, FOREIGN KEY (idmag) REFERENCES "+Table_name_Magasin+"(nomMAg) )");
        db.execSQL("CREATE TABLE iF NOT EXISTS "+Table_name_O+"( nomOp varchar(12),prenomOp varchar(12),matrFiscal varchar(13) ,entreprise varchar(20) not null,job varchar,TelOperateur varchar(14),email varchar2,address varchar2,Linkedin varchar,facebook varchar2,twitter varchar,logoImg BLOG,Primary key (nomOp,prenomOp ),Foreign key (matrFiscal) references "+Table_name_Entreprise+"(NIF),Foreign Key (entreprise) references "+Table_name_Entreprise+"(Nom))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_COM+"(idcom integer PRIMARY KEY AUTOINCREMENT,QntCom number,datCom date,idop integer,codePR varchar(13),FOREIGN KEY (codePR) REFERENCES "+Table_name+"(ID))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_client+"(idclient integer PRIMARY KEY AUTOINCREMENT,reduction number(2,2),Nom varchar(12), Prenom varchar(12),type varchar,FOREIGN KEY (Nom) references "+Table_name_O+"(nomOp) ,FOREIGN KEY (prenom) references "+Table_name_O+"(prenomOp))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_FOU+"(idFour integer PRIMARY KEY AUTOINCREMENT,Nom varchar(12),Prenom varchar(12),FOREIGN KEY (Nom) references "+Table_name_O+"(nomOp"+"),FOREIGN KEY (prenom) references "+Table_name_O+"(prenomOp)"+")");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Facture+"(idFact integer PRIMARY KEY AUTOINCREMENT ,DateFact date,Prix number,TVA number,HT number,terms varchar2,typeP String,com integer,FOREIGN KEY (com) REFERENCES "+Table_name_COM+"(idcom))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_factAchat+"(idfAchat integer PRIMARY KEY AUTOINCREMENT ,idca integer ,FOREIGN KEY (idca) REFERENCES "+Table_name_comAchat+"(idCA))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_factVente+"(idfVente integer PRIMARY KEY AUTOINCREMENT ,idcv integer ,FOREIGN KEY (idcv) REFERENCES "+Table_name_comVente+"(idCV))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_comAchat+"(idCA integer PRIMARY KEY AUTOINCREMENT , datelaivraison date ,idc integer,idfour integer ,FOREIGN KEY (idc) REFERENCES "+Table_name_COM+"(idcom),FOREIGN KEY (idfour) REFERENCES " +Table_name_FOU+"(idFour))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_comVente+"(idCV integer PRIMARY KEY AUTOINCREMENT , dateExp date ,methodeLaiv varchar,idc integer,idclient integer,FOREIGN KEY (idc) REFERENCES "+Table_name_COM+"(idcom),FOREIGN KEY (idclient) REFERENCES " +Table_name_client+"(idclient))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_inventaire+"(RefInv varchar PRIMARY KEY ,DateInv date,idMaag varchar,FOREIGN KEY (idMaag) REFERENCES "+Table_name_Magasin+"(nomMAg))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Rapport+"(idRap varchar PRIMARY KEY ,DateRap date,StatutInv varchar2,refinv varchar,FOREIGN KEY (refinv) REFERENCES "+Table_name_inventaire+"(RefInv))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_depenses+"(iddep integer PRIMARY KEY AUTOINCREMENT  ,NomDep Varchar ,DateDEP date, montant number ,nomMag varchar,fact BLOG,FOREIGN KEY (nomMag) REFERENCES "+Table_name_Magasin+"(nomMAg))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Historique+"(code varchar(14),QNTT number, date Date,op varchar , PRIMARY KEY (code,date,op),FOREIGN KEY (code) references "+Table_name+"(ID))");

    }



    public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);

    }
    public void Delete(String TableName ,String clause ,String[] args){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TableName,clause,args);
    }
    public void Update( String TableName ,ContentValues values,String clause,String[] args){
        SQLiteDatabase db = getWritableDatabase();
        db.update(TableName,values,clause,args);
    }
    //-----------------------------------------------------------------------------------------------
    public String getName() throws SQLException {
        String name = "";
        Cursor cursor = this.getReadableDatabase().query(
                TABLE_NAME, new String[]{Table_Column_1_Name},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return name;
    }

    public String getUser() throws SQLException {
        String user = "";
        Cursor cursor = this.getReadableDatabase().query(
                TABLE_NAME, new String[]{Table_Column_4_username},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                user = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return user;
    }

    public String getSurname() throws SQLException {
        String prenom = "";
        Cursor cursor = this.getReadableDatabase().query(
                TABLE_NAME, new String[]{Table_Column_2_Prenom},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                prenom = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return prenom;
    }

    public int getId() throws SQLException {
        int id = 0;
        Cursor cursor = this.getReadableDatabase().query(
                TABLE_NAME, new String[]{Table_Column_ID},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return id;
    }

    public boolean updateProfile(String id, String nom, String prenom, String password, String username, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table_Column_ID, id);
        values.put(Table_Column_1_Name, nom);
        values.put(Table_Column_2_Prenom, prenom);
        values.put(Table_Column_3_Password, password);
        values.put(Table_Column_4_username, username);
        values.put(KEY_IMG, image);
        db.update(TABLE_NAME, values, Table_Column_ID + "=?", new String[]{id});
        db.close();
        return true;
    }

    public boolean updateProfile1(String id, String nom, String prenom, String password, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table_Column_ID, id);
        values.put(Table_Column_1_Name, nom);
        values.put(Table_Column_2_Prenom, prenom);
        values.put(Table_Column_3_Password, password);
        values.put(Table_Column_4_username, username);
        db.update(TABLE_NAME, values, Table_Column_ID + "=?", new String[]{id});
        db.close();
        return true;
    }
    SQLiteDatabase sqLiteDatabaseObj;
    DataBaseM DataBaseM;

    public User getUserByID(String UsernameHolder) {

        Cursor cursor;

        sqLiteDatabaseObj = DataBaseM.getWritableDatabase();

        cursor = sqLiteDatabaseObj.query(DataBaseM.TABLE_NAME, null, " " + DataBaseM.Table_Column_4_username + "=?", new String[]{UsernameHolder}, null, null, null);
        String TempPassword = "", id = "", Usr = "", Nom = "", Prenom = "",  password = "";
        while (cursor.moveToNext()) {


            if (cursor.isFirst()) {

                cursor.moveToFirst();




                Usr = cursor.getString(cursor.getColumnIndex(Table_Column_4_username));

                id = cursor.getString(cursor.getColumnIndex(Table_Column_ID));

                Nom = cursor.getString(cursor.getColumnIndex(Table_Column_1_Name));

                Prenom = cursor.getString(cursor.getColumnIndex(Table_Column_2_Prenom));


                password = cursor.getString(cursor.getColumnIndex(Table_Column_3_Password));


                cursor.close();


            }


        }
        User usr = new User(id, Nom, Prenom, password, Usr);
        return usr;
    }
    //-----------------------------------------------------------------------------------------------
    public void InsertDataProduit(String id, String Nom, Date dateF,Date datE, String Categorie, String sousCategorie, String matiere, int quantite, double prix,String typePr,Date ENT,String coin,String coins,String unit,Double prics,String discription,String fournisseur,String client,String idmag,int qnttMin,Date dateDel,byte[] image){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO prod VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,id);
        statement.bindString(2,Nom);
        statement.bindString(3,sdf.format(dateF));
        statement.bindString(4,sdf.format(datE));
        statement.bindString(5,Categorie);
        statement.bindString(6,sousCategorie);
        statement.bindString(7,matiere);
        statement.bindLong(8,quantite);
        statement.bindDouble(9,prix);
        statement.bindString(10,typePr);
        statement.bindString(11,sdf.format(ENT));
        statement.bindString(12,coin);
        statement.bindString(13,coins);
        statement.bindString(14,unit);
        statement.bindDouble(15,prics);
        statement.bindString(16,discription);
        statement.bindString(17,idmag);
        statement.bindString(18,fournisseur);
        statement.bindString(19,client);
        statement.bindLong(20,qnttMin);
        statement.bindString(21,sdf.format(dateDel));
        statement.bindBlob(22,image);

        statement.executeInsert();


    }
    public void InsertDataOperateur( String NomOP, String prenomOP,String matfisc,String entreprise,String job,String telOpera,String email,String address,String linkedin,String facebook,String twitter, byte[] logo){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_O+" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,NomOP);
        statement.bindString(2,prenomOP);
        statement.bindString(3,matfisc);
        statement.bindString(4,entreprise);
        statement.bindString(5,job);
        statement.bindString(6,telOpera);
        statement.bindString(7,email);
        statement.bindString(8,address);
        statement.bindString(9,linkedin);
        statement.bindString(10,facebook);
        statement.bindString(11,twitter);
        statement.bindBlob(12,logo);

        statement.executeInsert();


    }
    public void InsertDatacommande( Long qntcom,Date datCom){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_COM+" VALUES ( NULL, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1,qntcom);
        statement.bindString(2,sdf.format(datCom));
        statement.executeInsert();
    }
    public void InsertDatacommandeAchat(Date datelaivraison  ,long idc, long idfour ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_comAchat+" VALUES ( NULL,?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,sdf.format(datelaivraison));
        statement.bindLong(2,idc);
        statement.bindLong(3,idfour);
        statement.executeInsert();
    }
    public void InsertDatacommandeVente(Date dateExp,String methodeLaiv ,long idc,long idclient ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_comVente+" VALUES ( NULL, ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,sdf.format(dateExp));
        statement.bindString(2,methodeLaiv);
        statement.bindLong(3,idc);
        statement.bindLong(4,idclient);
        statement.executeInsert();
    }public void InsertDataFacture(Date DateFact ,double Prix,double TVA ,double HT , String terms ,String typeP,long com ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_Facture+" VALUES ( NULL, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,sdf.format(DateFact));
        statement.bindDouble(2,Prix);
        statement.bindDouble(3,TVA);
        statement.bindDouble(4,HT);
        statement.bindString(5,terms);
        statement.bindString(6,typeP);
        statement.bindLong(7,com);


        statement.executeInsert();
    }
    public void InsertDataFactureAchat(long idca ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_factAchat+" VALUES ( NULL, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1,idca);
        statement.executeInsert();
    }
    public void InsertDataFactureVente(long idcv ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_factVente+" VALUES ( NULL, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1,idcv);
        statement.executeInsert();
    }
    public void InsertDataFounisseur(String Nom,String Prenom ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_FOU+" VALUES ( NULL, ?,?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,Nom);
        statement.bindString(2,Prenom);

        statement.executeInsert();
    }
    public void InsertDataClient(Double reduction ,String Nom ,String Prenom,String type){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_client+" VALUES ( Null,?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,reduction);
        statement.bindString(2,Nom);
        statement.bindString(3,Prenom);
        statement.bindString(4,type);
        statement.executeInsert();
    }
    public void InsertDataMag(String nomMAg,String typemag ,String mesuremag ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO mag VALUES ( ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,nomMAg);
        statement.bindString(2,typemag);
        statement.bindString(3,mesuremag);
        statement.executeInsert();
    }
    public void InsertDataINV( String RefInv ,Date DateInv ,String StatutInv ,String idMaag){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_inventaire+" VALUES (  ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,RefInv);
        statement.bindString(2,sdf.format(DateInv));
        statement.bindString(3,StatutInv);
        statement.bindString(4, idMaag);
        statement.executeInsert();
    }public void InsertDataRapport(String idRap,Date DateRap ,String refinv){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_Rapport+" VALUES ( ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,idRap);
        statement.bindString(2,sdf.format(DateRap));
        statement.bindString(3,refinv);
        statement.executeInsert();
    }public void InsertDataDepenses( String NomDep ,Date DateDEP,double montant ,String idmag ,byte[]fact){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_depenses+" VALUES ( NULL, ?, ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,NomDep);
        statement.bindString(2,sdf.format(DateDEP));
        statement.bindDouble(3,montant);
        statement.bindString(4, idmag);
        statement.bindBlob(5, fact);
        statement.executeInsert();
    }
    public void InsertDataEntreprise(String NIF,String Nom ,String RG ,String secteur,String taille, String statusjur,String email,String tlf,String address,String site, String Fax,byte[] image  ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_Entreprise+" VALUES (  ?, ?, ?, ?,  ?, ?, ?, ?,  ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,NIF);
        statement.bindString(2,Nom);
        statement.bindString(3,RG);
        statement.bindString(4,secteur);
        statement.bindString(5,taille);
        statement.bindString(6,statusjur);
        statement.bindString(7,email);
        statement.bindString(8,tlf);
        statement.bindString(9,address);
        statement.bindString(10,site);
        statement.bindString(11,Fax);
        statement.bindBlob(12, image);
        statement.executeInsert();
    }
    public void InsertDataHistorique(String code,int QNTT ,Date date ,String operation  ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_Entreprise+" VALUES (  ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,code);
        statement.bindLong(2,QNTT);
        statement.bindString(3,sdf.format(date));
        statement.bindString(4, operation);
        statement.executeInsert();
    }




    public Cursor getData(String sql){
        SQLiteDatabase db= getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS "+Table_name_Magasin);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Magasin+"(nomMAg varchar PRIMARY KEY ,typemag varchar,mesuremag varchar)");
*/
        db.execSQL("DROP TABLE IF EXISTS "+Table_name_O);
        db.execSQL("CREATE TABLE iF NOT EXISTS "+Table_name_O+"(nomOp varchar(12),prenomOp varchar(12),matrFiscal varchar(13) ,entreprise varchar(20) not null,job varchar,TelOperateur varchar(14),email varchar2,address varchar2,Linkedin varchar,facebook varchar2,twitter varchar,logoImg BLOG,Primary key (nomOp,prenomOp ),Foreign key (matrFiscal) references "+Table_name_Entreprise+"(NIF),Foreign Key (entreprise) references "+Table_name_Entreprise+"(Nom))");
        db.execSQL("DROP TABLE IF EXISTS "+Table_name_FOU);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_FOU+"(idFour integer PRIMARY KEY AUTOINCREMENT,Nom varchar(12),Prenom varchar(12),FOREIGN KEY (Nom) references "+Table_name_O+"(nomOp"+"),FOREIGN KEY (prenom) references "+Table_name_O+"(prenomOp)"+")");
        db.execSQL("DROP TABLE IF EXISTS "+Table_name_client);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_client+"(idclient integer PRIMARY KEY AUTOINCREMENT,reduction number(2,2),Nom varchar(12), Prenom varchar(12),type varchar,FOREIGN KEY (Nom) references "+Table_name_O+"(nomOp) ,FOREIGN KEY (prenom) references "+Table_name_O+"(prenomOp))");


        db.execSQL("DROP TABLE IF EXISTS "+Table_name_Entreprise);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Entreprise+"(NIF varchar(13)PRIMARY KEY,Nom varchar(20),RG varchar(20),secteur varchar(50),taille integer ,statujur varchar(60),email varchar,tlf varchar,Address varchar,Site varchar,Fax varchar, image BLOG)");

    }
}

