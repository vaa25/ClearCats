package com.vaa25.clearcats; /**
 * Created with IntelliJ IDEA.
 * User: Vlasov Alexander
 * Date: 20.08.13
 * Time: 0:59
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {
    public String count(String pathToFile) {
        String output = "";
        try {
            File f = new File(pathToFile);
            if (f.exists()) {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                InputStream is = new FileInputStream(f);
                byte[] buffer = new byte[819200];
                int read = 0;
                while ((read = is.read(buffer)) > 0) {
                    digest.update(buffer, 0, read);
                }
                byte[] md5sum = digest.digest();
                BigInteger bigInt = new BigInteger(1, md5sum);
                output = bigInt.toString(16);
                is.close();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }
}
