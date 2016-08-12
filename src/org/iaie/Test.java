/************************************************************************
 * Planning and Learning Group PLG,
 * Department of Computer Science,
 * Carlos III de Madrid University, Madrid, Spain
 * http://plg.inf.uc3m.es
 * 
 * Copyright 2015, Moises Martinez
 *
 * (Questions/bug reports now to be sent to Moisés Martínez)
 *
 * This file is part of IAIE.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the IAIE nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with IAIE. If not, see <http://www.gnu.org/licenses/>.
 * 
 ************************************************************************/

package org.iaie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.iaie.Test.loadFromFile;

public class Test {
    
    public static void loadFromFile(char[][] m, String fileName) throws FileNotFoundException, IOException {
        
        BufferedReader buffer = new BufferedReader(new FileReader (new File (fileName)));
 
        String line;
        char[] chars = null;
        int i = 0;
        
        while((line = buffer.readLine())!=null) {
            chars = line.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                m[i][j] = chars[j];
            }
            i++;
        }  
    }

    public static float compare(char[][] m1, char[][] m2) {
        
        int value = 0;
        
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length; j++) {
                if (m1[i][j] == m2[i][j])
                    value++;
            }
        }

        return value;
    }


    
    public static void main(String[] args) {
        
        if (args.length == 4) {
            try {
                String file1 = args[0];
                String file2 = args[1];
                
                int x = Integer.parseInt(args[2]);
                int y = Integer.parseInt(args[3]);
                
                char[][] m1 = new char[x][y];
                char[][] m2 = new char[x][y];
                
                Test.loadFromFile(m1, file1);
                Test.loadFromFile(m2, file2);
                System.out.println(file2 + ": " + (Test.compare(m1, m2)/(x*y))*100);
            } catch (IOException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        System.exit(0);
    }
}