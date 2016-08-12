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

package org.iaie.machine.alphabet;

import java.util.HashMap;

public class TokenAlphabet extends Alphabet {
    private HashMap<String, Integer> attr_symbols;
    
    public TokenAlphabet() {
        this.attr_symbols = new HashMap();
    }
    
    @Override
    public void addSymbol(String symbol, Object value) {
        this.attr_symbols.put(symbol, (Integer) value);
    }
    
    public void addSymbol(String symbol) {
        this.attr_symbols.put(symbol, this.attr_symbols.size());
    }
    
    @Override
    public boolean containsSymbol(String symbol) {
        return this.attr_symbols.containsKey(symbol);
    }
    
    @Override
    public Integer getSymbol(String symbol) {
        return this.attr_symbols.get(symbol);
    }
    
    @Override
    public int getNumberOfSymbols() {
        return this.attr_symbols.size();
    }
}
