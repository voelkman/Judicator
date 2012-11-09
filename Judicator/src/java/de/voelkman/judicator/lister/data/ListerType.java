/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.lister.data;

import java.io.Serializable;

/**
 *
 * @author g8712
 */
public enum ListerType implements Serializable{
    ReadOnly, Editable , Detached, Generated
}
