/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.test;

import javax.ejb.Local;
import wbs.lotto.domain.Gebuehr;

/**
 *
 * @author Master
 */
@Local
public interface TestLocal {
    public Gebuehr getGebuehr();
}
