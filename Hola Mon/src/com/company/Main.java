package com.company;

public class Main {


    public static jefe subir_a_jefe(Empleado emp, double inc){
        double sou;
        sou = emp.getSou() + inc;

        jefe j1 = new jefe(emp.getDni(),emp.getNom(),emp.getCognom(),emp.getSeccio(),sou,inc);

        return j1;

    }

    public static void main(String[] args) {
	// write your code her

            jefe j1 = new jefe();

            Empleado empleado1 = new Empleado ("Berny", 3000);
            Empleado empleado2 = new Empleado ("empleado2", 2000);
            Empleado empleado3 = new Empleado ("empleado3", 1000);

            System.out.println("Empleat "+ empleado1.getNom() + " te un sou de "+ empleado1.getSou());

            empleado1.subir_sueldo(10);


            j1 = subir_a_jefe(empleado1, 2000);

            System.out.println("Empleat "+ empleado1.getNom() + " despres de augmentar en un 10% el seu sou queda en: "+ empleado1.getSou());
            System.out.println("Empleat "+ j1.getNom() + " despres de ser el puto jefe: "+ j1.getSou() + " €");


    }





    }


