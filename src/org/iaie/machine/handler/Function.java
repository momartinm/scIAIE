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

package org.iaie.machine.handler;

import java.lang.reflect.Method;

public class Function {
    private final String attr_className;
    private final String attr_functionName;
    private final Method attr_method;
    private Class[] attr_parameters;
    
    public Function(String className, String functionName, Class[] parameters) throws NoSuchMethodException, ClassNotFoundException {
        this.attr_className     = className;
        this.attr_functionName  = functionName;
        this.attr_method        = Class.forName(className).getMethod(functionName, parameters);
    }
    
    public String getClassName() {
        return this.attr_className;
    }
    
    public String getFunctionName() {
        return this.attr_functionName;
    }
    
    public Method getMethod() {
        return this.attr_method;
    }
    
    public Class getParameter(int position) {
        return this.attr_parameters[position];
    }
    
    public int getNumberOfParameters() {
        return this.attr_parameters.length;
    }
}
