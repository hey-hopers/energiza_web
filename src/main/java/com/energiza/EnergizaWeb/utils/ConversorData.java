/*******************************************************************************
* ENTRA21 - TURMA 2021 - EQUIPE INFINITY
* DEFINIÇÃO: COVERTER DATA
* AUTOR: FERNANDO D B DA CUNHA
********************************************************************************
*/
package com.energiza.EnergizaWeb.utils;

import java.util.Calendar;
public class ConversorData {
    public static java.sql.Date conv(Calendar d){
       java.sql.Date data = new java.sql.Date(d.getTime().getTime());
       return data;
    }
}
