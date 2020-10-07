

package com.example.logg;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.database.sqlite.SQLiteStatement;

        import java.net.IDN;
        import java.util.ArrayList;
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
    public static final String Table_name_takenproduct="takenproduct";
    public static final String Table_commandes_prod= "student_subject";

    public static final String Table_name_Magasin="mag";
    public static final String Table_name_Sold="sold";
    public static final String Table_name_Purchase="purchase";
    public static final String Table_name_inventaire="inventaire";
    public static final String Table_name_Rapport="Rapport";
    public static final String Table_name_depenses="depense";
    public static final String Table_name_Entreprise="Entreprise";
    public static final String Table_name_Historique="historique";
    public static final String Table_name_Transfert="transfert";


//---------------------------------------------------------

    public static final String TABLE_NAME = "JserTable";

    public static final String Table_Column_ID = "id";

    public static final String Table_Column_1_Name = "nom";

    public static final String Table_Column_2_Prenom = "prenom";

    public static final String Table_Column_3_Password = "password";

    public static final String Table_Column_4_username = "username";

    public static final String KEY_IMG = "image";

    public static final String STUDENT_ID_FK = "student_id";
    public static final String SUBJECT_ID_FK = "subject_id";
    public static final String STUDENT_SUB_CONSTRAINT = "student_sub_unique";





















//-----------------------------------------------------------------------------------------

    public DataBaseM(@Nullable Context context) {
        super(context,DataBase_name, null, 31);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_name_factVente + "(idfv integer PRIMARY KEY AUTOINCREMENT, commandevente varchar, typefv varchar,totalfv number,termsfv varchar,DateCreafv varchar,FOREIGN KEY (commandevente) REFERENCES "+Table_name_comVente+ "(idCV))");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_name_factAchat + "(idfa integer PRIMARY KEY AUTOINCREMENT, commandeachat varchar, typefa varchar,totalfa number,termsfa varchar,DateCreafa varchar,FOREIGN KEY (commandeachat) REFERENCES "+Table_name_comAchat+ "(idCA))");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_name_takenproduct + "(IDh integer  PRIMARY KEY,IDT varchar,quantityT number, unitT varchar ,IDd integer)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_name_depenses + "(IDD integer PRIMARY KEY AUTOINCREMENT, nomD varchar, magD varchar , amountD number, arD varchar , DateCreaD varchar , detailsD varchar,FOREIGN KEY (magD) REFERENCES "+Table_name_Magasin+ "(nomMAg))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_comVente+"(idCV integer PRIMARY KEY AUTOINCREMENT , DescriptionV varchar ,costumer varchar,notesV varchar ,termsV varchar ,DateCreaV varchar,DateExp varchar)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_comAchat+"(idCA integer PRIMARY KEY AUTOINCREMENT , DescriptionA varchar ,vendor varchar ,notesA varchar ,termsA varchar ,DateCreaA varchar,DateLiv varchar)");

    }
    public void QueryData(){
        SQLiteDatabase db  = getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Entreprise+"(NIF varchar(13)PRIMARY KEY,Nom varchar(20),RG varchar(20),secteur varchar(50),taille integer ,statujur varchar(60),email varchar,tlf varchar,Address varchar,Site varchar,Fax varchar, image BLOG)");
        db.execSQL("CREATE TABLE IF NOT EXISTS mag (nomMAg varchar PRIMARY KEY ,typemag varchar,mesuremag varchar)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + Table_Column_ID + " INTEGER PRIMARY KEY, " + Table_Column_1_Name + " VARCHAR, " + Table_Column_2_Prenom + " VARCHAR, " + Table_Column_3_Password + " VARCHAR  ," + Table_Column_4_username + " VARCHAR ," + KEY_IMG + " blob)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name+"(ID varchar(14)PRIMARY KEY,Nom varchar(20) unique,dateF Date ,datE Date , categorie varchar,sousCategorie varchar,matiere varchar,quantite number,prix number,typePr varchar,DateENtr Date,coin varchar,coins varchar,unit varchar,pricS number,discription text,fournisseur varchar,client varchar,idmag varchar,QNTT number,dateDel date,image BLOG, FOREIGN KEY (idmag) REFERENCES "+Table_name_Magasin+"(nomMAg) )");
        db.execSQL("CREATE TABLE iF NOT EXISTS "+Table_name_O+"( nomOp varchar(12),prenomOp varchar(12),matrFiscal varchar(13) ,entreprise varchar(20) not null,job varchar,TelOperateur varchar(14),email varchar2,address varchar2,Linkedin varchar,facebook varchar2,twitter varchar,logoImg BLOG,Primary key (nomOp,prenomOp ),Foreign key (matrFiscal) references "+Table_name_Entreprise+"(NIF),Foreign Key (entreprise) references "+Table_name_Entreprise+"(Nom))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_COM+"(idcom integer PRIMARY KEY AUTOINCREMENT,QntCom number,datCom date,idop integer,codePR varchar(13),FOREIGN KEY (codePR) REFERENCES "+Table_name+"(ID))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_client+"(idclient integer PRIMARY KEY AUTOINCREMENT,reduction number(2,2),Nom varchar(12), Prenom varchar(12),type varchar,FOREIGN KEY (Nom) references "+Table_name_O+"(nomOp) ,FOREIGN KEY (prenom) references "+Table_name_O+"(prenomOp))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_FOU+"(idFour integer PRIMARY KEY AUTOINCREMENT,Nom varchar(12),Prenom varchar(12),FOREIGN KEY (Nom) references "+Table_name_O+"(nomOp"+"),FOREIGN KEY (prenom) references "+Table_name_O+"(prenomOp)"+")");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Facture+"(idFact integer PRIMARY KEY AUTOINCREMENT ,DateFact date,Prix number,TVA number,HT number,terms varchar2,typeP String,com integer,FOREIGN KEY (com) REFERENCES "+Table_name_COM+"(idcom))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_factAchat+"(idfAchat integer PRIMARY KEY AUTOINCREMENT ,idca integer ,FOREIGN KEY (idca) REFERENCES "+Table_name_comAchat+"(idCA))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_factVente+"(idfVente integer PRIMARY KEY AUTOINCREMENT ,idcv integer ,FOREIGN KEY (idcv) REFERENCES "+Table_name_comVente+"(idCV))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_comAchat+"(idCA integer PRIMARY KEY AUTOINCREMENT , datelaivraison date ,idc integer,idfour integer ,FOREIGN KEY (idc) REFERENCES "+Table_name_COM+"(idcom),FOREIGN KEY (idfour) REFERENCES " +Table_name_FOU+"(idFour))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_comVente+"(idCV integer PRIMARY KEY AUTOINCREMENT , dateExp date ,methodeLaiv varchar,idc integer,idclient integer,FOREIGN KEY (idc) REFERENCES "+Table_name_COM+"(idcom),FOREIGN KEY (idclient) REFERENCES " +Table_name_client+"(idclient))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_inventaire+"(RefInv varchar PRIMARY KEY ,DateInv date,tYPiNV varchar2,status Varchar2,duration varchar)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Rapport+"(idRap varchar PRIMARY KEY ,DateRap date,StatutInv varchar2,refinv varchar,FOREIGN KEY (refinv) REFERENCES "+Table_name_inventaire+"(RefInv))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Historique+"(code varchar(14),QNTT number, date Date,op varchar , PRIMARY KEY (code,date,op),FOREIGN KEY (code) references "+Table_name+"(ID))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Transfert+"(Id integer PRIMARY KEY AUTOINCREMENT,code varchar(14),QNTT number, date Date,wareh1 varchar ,wareh2 varchar,etatP varchar)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Sold+"(Id varchar(14),DateE date,qntt number,prix number,PRIMARY KEY (Id,DateE),Foreign key (Id) references "+Table_name+"(ID))");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Purchase+"(Id varchar(14),DateE date,qntt number,prix number,PRIMARY KEY (Id,DateE),Foreign key (Id) references "+Table_name+"(ID))");

    }

    //add a new expense
    public void addTakenproduct(String IDT,double quantityT,String unitT,int IDd) {
        SQLiteDatabase Database = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("IDT",IDT);
        values.put("quantityT",quantityT);
        values.put("unitT",unitT);
        values.put("IDd",IDd);

        Database.insert(Table_name_takenproduct, null , values);
        Database.close();
    }
    //get all expenses
    /*public ArrayList<TakenProductData> getTakenProduct() {
        ArrayList<TakenProductData> arrayList = new ArrayList<>();
        String select_query= "SELECT *FROM " + Table_name_takenproduct;
        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);
        if (cursor.moveToFirst()) {
            do {
                TakenProductData P = new TakenProductData();
                P.setID(cursor.getString(0));
                P.setCode(cursor.getString(1));
                P.setQuantity(cursor.getInt(2));
                P.setUnit(cursor.getString(3));
                P.setIdd(cursor.getInt(4));


                arrayList.add(P);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }*/
    //delete one expense
    public void deleteTakenProduct(String IDh) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(Table_name_takenproduct, "IDh=" + IDh, null);
        sqLiteDatabase.close();
    }
    //update one expense
    public void updateTakenProduct(String IDT,double quantityT,String unitT,int IDd,String IDh) {
        SQLiteDatabase Database = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("IDT",IDT);
        values.put("quantityT",quantityT);
        values.put("unitT",unitT);
        values.put("IDd",IDd);
        Database.update(Table_name_takenproduct,values, "IDh=" + IDh, null);
        Database.close();
    }

    //------------------------------------------------expenses-----------------------------------------------------------
    //add a new expense
    public void addDepense(String nomD,String magD,Double amountD,String arD,String DateCreaD,String detailsD) {
        SQLiteDatabase Database = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("nomD",nomD);
        values.put("magD",magD);
        values.put("amountD",amountD);
        values.put("arD",arD);
        values.put("DateCreaD",DateCreaD);
        values.put("detailsD",detailsD);

        Database.insert(Table_name_depenses, null , values);
        Database.close();
    }
    //get all expenses
    public ArrayList<DepenseData> getDepense() {
        ArrayList<DepenseData> arrayList = new ArrayList<>();
        String select_query= "SELECT *FROM " + Table_name_depenses;
        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);
        if (cursor.moveToFirst()) {
            do {
                DepenseData de = new DepenseData();
                de.setID(cursor.getString(0));
                de.setNOM(cursor.getString(1));
                de.setMAGASIN(cursor.getString(2));
                de.setTOTAL(cursor.getDouble(3));
                de.setAR(cursor.getString(4));
                de.setDateCrea(cursor.getString(5));
                de.setDESCRIPTION(cursor.getString(6));
                arrayList.add(de);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }
    //delete one expense
    public void deleteExp(String ID) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(Table_name_depenses, "ID=" + ID, null);
        sqLiteDatabase.close();
    }
    //update one expense
    public void updateDepense(String nomD,String magD,Double amountD,String arD,String DateCreaD,String detailsD,String IDD) {
        SQLiteDatabase Database = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("nomD",nomD);
        values.put("magD",magD);
        values.put("amountD",amountD);
        values.put("arD",arD);
        values.put("DateCreaD",DateCreaD);
        values.put("detailsD",detailsD);
        Database.update(Table_name_depenses,values, "IDD=" + IDD, null);
        Database.close();
    }
    //------------------------------------------orders----------------------------------------------
    //add purchase order
    public void addCommandeAchat( String DescriptionA,String vendor,String notesA,String termsA,String DateCreaA,String DateLiv) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DescriptionA", DescriptionA);
        values.put("vendor", vendor);
        values.put("notesA", notesA);
        values.put("termsA", termsA);
        values.put("DateCreaA", DateCreaA);
        values.put("DateLiv", DateLiv);
        sqLiteDatabase.insert(Table_name_comAchat, null , values);
        sqLiteDatabase.close();
    }


    //get purchase orders
 /*   public ArrayList<CommandeAchatData> getCommandeAchat() {
        ArrayList<CommandeAchatData> arrayList = new ArrayList<>();
        String select_query= "SELECT *FROM " + Table_name_comAchat;
        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);
        if (cursor.moveToFirst()) {
            do {
                CommandeAchatData dataAchat = new CommandeAchatData();
                dataAchat.setID(cursor.getString(0));
                dataAchat.setShipmentpreference(cursor.getString(1));
                dataAchat.setVendor(cursor.getString(2));
                dataAchat.setNotes(cursor.getString(3));
                dataAchat.setTerms(cursor.getString(4));
                dataAchat.setDateCrea(cursor.getString(5));
                dataAchat.setDateLiv(cursor.getString(6));

                arrayList.add(dataAchat);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }
    //delete a purchase order
    public void deleteCommandeAchat(String idCA) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //deleting row
        sqLiteDatabase.delete(Table_name_comAchat, "idCA=" + idCA, null);
        sqLiteDatabase.close();
    }
    //update a purchase order
    public void updateCommandeAchat( String DescriptionA,String vendor,String notesA,String termsA,String DateLiv,String idCA) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("DescriptionA", DescriptionA);
        values.put("vendor", vendor);
        values.put("notesA", notesA);
        values.put("termsA", termsA);
        values.put("DateLiv", DateLiv);
        //updating row
        sqLiteDatabase.update(Table_name_comAchat, values, "idCA=" + idCA, null);
        sqLiteDatabase.close();
    }
    //--------------------------------------------sale orders------------------------------------
    //add sale order
    public void addCommandeVente(String DescriptionV,String costumer,String notesV,String termsV,String DateCreaV,String DateExp) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DescriptionV", DescriptionV);
        values.put("costumer", costumer);
        values.put("notesV", notesV);
        values.put("termsV", termsV);
        values.put("DateCreaV", DateCreaV);
        values.put("DateExp", DateExp);
        sqLiteDatabase.insert(Table_name_comVente, null , values);
        sqLiteDatabase.close();
    }
    //get sale orders
  /*  public ArrayList<CommandeVenteData> getCommandeVente() {
        ArrayList<CommandeVenteData> arrayList = new ArrayList<>();
        String select_query= "SELECT *FROM " + Table_name_comVente;
        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);
        if (cursor.moveToFirst()) {
            do {
                CommandeVenteData dataAchat = new CommandeVenteData();
                dataAchat.setID(cursor.getString(0));
                dataAchat.setMethodLiv(cursor.getString(1));
                dataAchat.setCustomer(cursor.getString(2));
                dataAchat.setCustomerNotes(cursor.getString(3));
                dataAchat.setTerms(cursor.getString(4));
                dataAchat.setDateCrea(cursor.getString(5));
                dataAchat.setDateLiv(cursor.getString(6));

                arrayList.add(dataAchat);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }
    //delete a sale order
    public void deleteCommandeVente(String idCV) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //deleting row
        sqLiteDatabase.delete(Table_name_comVente, "idCV=" + idCV, null);
        sqLiteDatabase.close();
    }
    //update a sale order
    public void updateCommandeVente( String DescriptionV,String costumer,String notesV,String termsV,String DateExp,String idCV) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("DescriptionV", DescriptionV);
        values.put("costumer", costumer);
        values.put("notesV", notesV);
        values.put("termsV", termsV);
        values.put("DateExp", DateExp);
        //updating row
        sqLiteDatabase.update(Table_name_comVente, values, "idCV=" + idCV, null);
        sqLiteDatabase.close();
    }


    //--------------------------------------------sale bills------------------------------------

    //add the new note
    public void addFactureVente(String commandevente, String typefv,double totalfv,String termsfv,String DateCreafv) {
        SQLiteDatabase sqLiteDatabase = this .getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("commandevente", commandevente);
        values.put("typefv", typefv);
        values.put("totalfv", totalfv);
        values.put("termsfv", termsfv);
        values.put("DateCreafv", DateCreafv);


        //inserting new row
        sqLiteDatabase.insert(Table_name_factVente, null , values);
        //close database connection
        sqLiteDatabase.close();
    }

    //get the all notes
    public ArrayList<FactureVenteData> getFactureVente() {
        ArrayList<FactureVenteData> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + Table_name_factVente;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FactureVenteData datavente = new FactureVenteData();
                datavente.setID(cursor.getString(0));
                datavente.setCommandeVente(cursor.getString(1));
                datavente.setTypePayement(cursor.getString(2));
                datavente.setTotal(cursor.getDouble(3));
                datavente.setTerms(cursor.getString(4));
                datavente.setDateCrea(cursor.getString(5));

                arrayList.add(datavente);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    //delete the note
    public void deleteFactureVente(String idfv) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //deleting row
        sqLiteDatabase.delete(Table_name_factVente, "idfv=" + idfv, null);
        sqLiteDatabase.close();
    }

    //update the note
    public void updateFactureVente(String commandevente, String typefv,double totalfv,String termsfv,String idfv) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("commandevente", commandevente);
        values.put("typefv", typefv);
        values.put("totalfv", totalfv);
        values.put("termsfv", termsfv);
        //updating row
        sqLiteDatabase.update(Table_name_factVente, values, "idfv=" + idfv, null);
        sqLiteDatabase.close();
    }

    //-----------------------------------------------------------------------------------------------

    //add the new note
    public void addFactureAchat(String commandeachat, String typefa,double totalfa,String termsfa,String DateCreafa) {
        SQLiteDatabase sqLiteDatabase = this .getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("commandeachat", commandeachat);
        values.put("typefa", typefa);
        values.put("totalfa", totalfa);
        values.put("Termsfa", termsfa);
        values.put("DateCreafa", DateCreafa);


        //inserting new row
        sqLiteDatabase.insert(Table_name_factAchat, null , values);
        //close database connection
        sqLiteDatabase.close();
    }

    //get the all notes
    public ArrayList<FactureAchatData> getFactureAchat() {
        ArrayList<FactureAchatData> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT *FROM " + Table_name_factAchat;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FactureAchatData dataAchat = new FactureAchatData();
                dataAchat.setID(cursor.getString(0));
                dataAchat.setCommandeAchat(cursor.getString(1));
                dataAchat.setTypePayement(cursor.getString(2));
                dataAchat.setTotal(cursor.getDouble(3));
                dataAchat.setTerms(cursor.getString(4));
                dataAchat.setDateCrea(cursor.getString(5));

                arrayList.add(dataAchat);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    //delete the note
    public void deleteFactureAchat(String idfa) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //deleting row
        sqLiteDatabase.delete(Table_name_factAchat, "idfa=" + idfa, null);
        sqLiteDatabase.close();
    }

    //update the note
    public void updateFactureAchat(String commandeachat, String typefa,double totalfa,String termsfa,String idfa) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("commandeachat", commandeachat);
        values.put("typefa", typefa);
        values.put("totalfa", totalfa);
        values.put("termsfa", termsfa);
        //updating row
        sqLiteDatabase.update(Table_name_factAchat, values, "idfa=" + idfa, null);
        sqLiteDatabase.close();
    }

*/
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
    public void InsertDataINV( String RefInv ,Date DateInv ,String type ,String status,String duration){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_inventaire+" VALUES (  ?, ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,RefInv);
        statement.bindString(2,sdf.format(DateInv));
        statement.bindString(3,type);
        statement.bindString(4, status);
        statement.bindString(5, duration);
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
    }  public void InsertDataTranfert(String code,long QNTT ,Date date ,String wareh1,String wareh2,String etatP ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_Transfert+" VALUES ( NULL, ?, ?, ?, ?, ?,?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,code);
        statement.bindLong(2,QNTT);
        statement.bindString(3,sdf.format(date));
        statement.bindString(4, wareh1);
        statement.bindString(5, wareh2);
        statement.bindString(6, etatP);
        statement.executeInsert();
    }
    public void InsertDataSold(String code,Date date,long QNTT ,double prix ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_Sold+" VALUES (  ?, ?,?,?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,code);
        statement.bindString(2,sdf.format(date));
        statement.bindLong(3,QNTT);
        statement.bindDouble(4,prix);

        statement.executeInsert();
    }
    public void InsertDataPurshase(String code,Date date,long QNTT,double Prix  ){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_Purchase+" VALUES (  ?, ?,?,?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,code);
        statement.bindString(2,sdf.format(date));
        statement.bindLong(3,QNTT);
        statement.bindDouble(4,Prix);

        statement.executeInsert();
    }




    public Cursor getData(String sql){
        SQLiteDatabase db= getReadableDatabase();
        return db.rawQuery(sql,null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_name_Transfert);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Transfert+"(Id integer PRIMARY KEY AUTOINCREMENT,code varchar(14),QNTT number, date Date,wareh1 varchar ,wareh2 varchar,etatP varchar)");
        db.execSQL("DROP TABLE IF EXISTS "+Table_name);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name+"(ID varchar(14)PRIMARY KEY,Nom varchar(20) unique,dateF Date ,datE Date , categorie varchar,sousCategorie varchar,matiere varchar,quantite number,prix number,typePr varchar,DateENtr Date,coin varchar,coins varchar,unit varchar,pricS number,discription text,fournisseur varchar,client varchar,idmag varchar,QNTT number,dateDel date,image BLOG, FOREIGN KEY (idmag) REFERENCES "+Table_name_Magasin+"(nomMAg) )");

        db.execSQL("DROP TABLE IF EXISTS "+Table_name_Purchase);

        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Purchase+"(Id varchar(14),DateE date,qntt number,prix number,PRIMARY KEY (Id,DateE),Foreign key (Id) references "+Table_name+"(ID))");
        db.execSQL("DROP TABLE IF EXISTS "+Table_name_Sold);

        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name_Sold+"(Id varchar(14),DateE date,qntt number,prix number,PRIMARY KEY (Id,DateE),Foreign key (Id) references "+Table_name+"(ID))");

    }

    public ArrayList<Magasin> getMag() {


        ArrayList<Magasin> arrayList = new ArrayList<>();
        String select_query= "SELECT *FROM " + Table_name_Magasin;
        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);
        if (cursor.moveToFirst()) {
            do {
                Magasin maagasin = new Magasin();
                maagasin.setNomMag(cursor.getString(0));
                maagasin.setTypMag(cursor.getString(1));
                maagasin.setUnitMag(cursor.getString(2));
                arrayList.add(maagasin);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

}

