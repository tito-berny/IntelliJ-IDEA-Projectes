package com.example.berny.motoruta.Utilidades;

public class Utilidades {


    //nuemero de version BBDD
    public static final int DATABASE_VERSION = 1;
    //Nombre de la base de datos
    public static final String DATABASE_NAME = "motoRuta.db";


    //-----------------------Contantes tabla USUARIO---------------------------

    //NOMBRE TABLA
    public static final String TABLA_USUARIO = "tbl_usuario";
    //NOMBRE COLUMNAS
    public static final String COLUMNA_ID_USUARIO = "id";
    public static final String COLUMNA_ALIAS = "alias";
    public static final String COLUMNA_EMAIL = "email";
    public static final String COLUMNA_PASS = "pass";
    public static final String COLUMNA_MOTO = "moto";
    public static final String COLUMNA_NIVEL = "nivel";

    //String sentencia creacion de tabla
    public static final String SQL_CREAR_TBL_USUARIO = "create table " + TABLA_USUARIO
            + "(" + COLUMNA_ID_USUARIO + " integer primary key autoincrement,"
            + COLUMNA_ALIAS + " text,"
            + COLUMNA_EMAIL + " text,"
            + COLUMNA_PASS + " text,"
            + COLUMNA_MOTO + " text,"
            + COLUMNA_NIVEL + " integer"
            + ");";

    //------------------------Constantes tabla RUTA----------------------------

    //NOMBRE TABLA
    public static final String TABLA_RUTA = "tbl_ruta";
    //NOMBRE COLUMNAS
    public static final String COLUMNA_ID_RUTA = "id";
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_FECHA = "fecha";
    public static final String COLUMNA_TIEMPO = "tiempo";
    public static final String COLUMNA_METEOROLOGIA = "meteorologia";
    public static final String COLUMNA_VALORACION = "valoracion";
    public static final String COLUMNA_PATH = "path";


    //String sentencia creacion de tabla RUTA
    public static final String SQL_CREAR_TBL_RUTA = "create table " + TABLA_RUTA
            + "(" + COLUMNA_ID_RUTA + " integer primary key autoincrement, "
            + COLUMNA_NOMBRE + " text not null,"
            + COLUMNA_FECHA + " text,"
            + COLUMNA_TIEMPO + " integer,"
            + COLUMNA_METEOROLOGIA + " integer,"
            + COLUMNA_VALORACION + " REAL,"
            + COLUMNA_PATH + " text"
            + ");";


    //-----------------------Constantes Tabla COMENTARIO---------------------------

    //NOMBRE TABLA
    public static final String TABLA_COMENTARIO = "tbl_comentario";
    //NOMBRE COLUMNAS
    public static final String COLUMNA_ID_COMENTARIO = "id";
    public static final String COLUMNA_RUTA_ID = "ruta_id";
    public static final String COLUMNA_COMENTARIO = "comentario";



    //String sentencia creacion de tabla
    public static final String SQL_CREAR_TBL_COMENTARIO = "create table " + TABLA_COMENTARIO
            + "(" + COLUMNA_ID_COMENTARIO + " integer primary key autoincrement,"
            + COLUMNA_RUTA_ID + " integer,"
            + COLUMNA_COMENTARIO + " text,"
            + "FOREIGN KEY (" + COLUMNA_RUTA_ID + ") REFERENCES " + TABLA_RUTA + " (" + COLUMNA_ID_RUTA + ")"
            + " );";

}
