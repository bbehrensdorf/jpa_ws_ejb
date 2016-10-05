/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.testdatengenerierung;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gz1
 */
@Local
public interface ZiehungTestdatenGeneratorLocal {

    public void generiereTestDatenFuerMehrereZiehungen(
            Date datumErsteZiehung,
            List<ZiehungGeneratorConfig> configList
    );
}
