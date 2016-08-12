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

package org.iaie.machine.transition;

public class Transition 
{
    private final String attr_input_symbol;
    private final int attr_state_to_move;
    private final String attr_output_symbol;
    private final String attr_machine; 
    
    public Transition(String inSymbol, String outSymbol, int state, String machine) {
        this.attr_input_symbol  = inSymbol;
        this.attr_output_symbol = outSymbol;
        this.attr_state_to_move = state;
        this.attr_machine       = machine;
    }
    
    public int getTransitionState() {
        return this.attr_state_to_move;
    }
    
    public String getOutputSymbol() {
        return this.attr_output_symbol;
    }
    
    public String getInputSymbol() {
        return this.attr_input_symbol;
    }
    
    public String getMachineName() {
        return this.attr_machine;
    }
}
