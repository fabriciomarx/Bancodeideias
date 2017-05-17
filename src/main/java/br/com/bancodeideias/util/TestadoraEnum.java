/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bancodeideias.util;

public class TestadoraEnum {

    public static void main(String[] args) {
        MarcasEnum hp = MarcasEnum.HP;
        MarcasEnum samsung = MarcasEnum.SAMSUNG;
        System.out.println("Nome da Marca = " + hp.name());
        System.out.println("Nome da Marca = " + samsung.name());
    }
}
