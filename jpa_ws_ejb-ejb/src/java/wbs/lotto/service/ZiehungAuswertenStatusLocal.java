/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.service;

import javax.ejb.Local;

@Local
public interface ZiehungAuswertenStatusLocal {
    public int getStatus();
    public void setStatus(int status);
}
