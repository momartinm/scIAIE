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
 *     * Neither the name of the IAIE nor the names of its contributors may be 
 *       used to endorse or promote products derived from this software without 
 *       specific prior written permission.
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

package org.iaie.machine.util;

public class Util {
    public static Class[] GenerateArrayClasses(String[] data, int position, int size) {
        Class[] temp = new Class[size - position];
        
        for (int i = position; i < size; i++)
        {
            if (data[i].toUpperCase().matches("STRING"))
                temp[i-position] = String.class;
            else if (data[i].toUpperCase().matches("INT"))
                temp[i-position] = Integer.class;
            else if (data[i].toUpperCase().matches("FLOAT"))
                temp[i-position] = Float.class;
            else if (data[i].toUpperCase().matches("DOUBLE"))
                temp[i-position] = Double.class;
            else if (data[i].toUpperCase().matches("BOOLEAN"))
                temp[i-position] = Boolean.class;
            else if (data[i].toUpperCase().matches("BYTE"))
                temp[i-position] = Byte.class;
            else if (data[i].toUpperCase().matches("OBJECT"))
                temp[i-position] = Object.class; 
        }
        
        return temp;
    }
    
    public static int proccesLine(String line) {
        if (line.length() == 0) return 0;
        if (line.matches("begin_machine")) return 8;
        if (line.matches("end_machine")) return 2;
        if (line.matches("begin_state")) return 8;
        if (line.matches("end_state")) return 3;
        if (line.matches("begin_input_symbol")) return 8;
        if (line.matches("end_input_symbol")) return 4;
        if (line.matches("begin_output_symbol")) return 8;
        if (line.matches("end_output_symbol")) return 5;
        if (line.matches("begin_transition")) return 8;
        if (line.matches("end_transition")) return 6;
        if (line.charAt(0) == '#') return 0;
        
        return 1;  
    }
}
