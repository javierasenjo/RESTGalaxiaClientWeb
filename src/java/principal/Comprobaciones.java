/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import Pojo.Galaxia;

/**
 *
 * @author javie
 */
public class Comprobaciones {

    boolean tokenValidado = false;
    boolean galaxiaExiste = false;

    public boolean comprobarToken(String token) {
        if (token.toString().equals("")) {
            tokenValidado = false;
        } else {
            tokenValidado = true;
        }
        return tokenValidado;
    }

    public boolean comprobarGalaxia(Galaxia galaxia) {
        if (galaxia.getNombre().equals("")) {
            galaxiaExiste = false;
        } else {
            galaxiaExiste = true;
        }
        return galaxiaExiste;
    }
}
