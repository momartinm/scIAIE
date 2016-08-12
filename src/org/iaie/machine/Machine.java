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

package org.iaie.machine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.iaie.machine.alphabet.Alphabet;
import org.iaie.machine.state.BasicState;
import org.iaie.machine.handler.Function;
import org.iaie.machine.state.State;
import org.iaie.machine.transition.Transition;
import org.iaie.machine.alphabet.FunctionAlphabet;
import org.iaie.machine.alphabet.TokenAlphabet;
import org.iaie.machine.handler.Handler;
import org.iaie.machine.transition.OutputTransition;
import org.iaie.machine.util.Util;
import java.util.ArrayList;
import java.util.List;

public class Machine extends State {
    private int attr_current_state;
    private int attr_initial_state;
    
    private Handler attr_functions;
    
    private TokenAlphabet attr_input_alphabet;
    private FunctionAlphabet attr_output_alphabet;
    
    private Transition[][] attr_transitions;

    private List<State> attr_states;
        
    public Machine(String fileName, Handler funtions) throws NoSuchMethodException, ClassNotFoundException, IOException {
        this.attr_states            = new ArrayList<>();
        this.attr_input_alphabet    = new TokenAlphabet();
        this.attr_output_alphabet   = new FunctionAlphabet();
        this.attr_functions         = funtions;
            
        String line         = null;
        BufferedReader file = null;
        int reading_state   = 0;
        int cursor          = 0;
        String[] data       = new String[32];  
            
        file = new BufferedReader(new FileReader(new File(fileName)));
                
        while ((line = file.readLine()) != null) {
            reading_state = Util.proccesLine(line);
                    
            switch (reading_state) {
                case 0:
                    // Reading a line of comments
                    break;
                case 1: 
                    // Reading a line of data 
                    data[cursor] = line; 
                    cursor++; 
                    break;
                case 2:
                    // Reading end of machine block [end_machine]
                    // 0: name
                    // 1: initial state
                    this.attr_name = data[0];
                    this.attr_initial_state = Integer.parseInt(data[1]);
                    this.attr_current_state = this.attr_initial_state;
                    cursor = 0;
                    break;
                case 3: 
                    // Reading end of state block [end_state]
                    // 0: state name
                    // 1: state position
                    // 2: type of state => 1:Basic 2:Machine
                    // 3: machine description file
                    if (data[2].matches("2"))
                    this.attr_states.add(Integer.parseInt(data[1]), new Machine(data[3], funtions));
                    else
                    this.attr_states.add(Integer.parseInt(data[1]), new BasicState(data[0]));
                    cursor = 0;
                    break;
                case 4: 
                    // Reading end of input symbol block [end_imput_symbol]
                    // 0: symbol
                    this.attr_input_alphabet.addSymbol(data[0]);
                    cursor = 0;
                    break;
                case 5: 
                    // Reading end of output symbol block [end_output symbol]
                    // 0: symbol
                    // 1: class name
                    // 2: funtion name
                    // 3+: type of the objects (String, int, boolean, double, float, byte, object)
                    this.attr_output_alphabet.addSymbol(data[0], new Function(data[1], data[2], Util.GenerateArrayClasses(data, 3, cursor)));
                    cursor = 0;
                    break;
                case 6: 
                    //Reading end of transition block [end_transition]
                    // 0: state
                    // 1: input symbol
                    // 2: next state
                    // 3: output symbol
                    // 4: machine where transition will be applicated.
                    if (this.attr_transitions == null) this.attr_transitions = new Transition[this.attr_states.size()][this.attr_input_alphabet.getNumberOfSymbols()];
                    this.attr_transitions[Integer.parseInt(data[0])][this.attr_input_alphabet.getSymbol(data[1])] = new Transition(data[1], data[3], Integer.parseInt(data[2]), (cursor < 5) ? this.attr_name:data[4]);
                    cursor = 0;
                    break;
                case 8:
                    //Reading init block [begin_*]
                break;
            }
        }

        file.close();
    }
    
    public Machine(String name, int initialState, Alphabet inputAlphabet, int states, Alphabet outputAlphabet, Handler funtions) {
        this.attr_name              = name;
        this.attr_current_state     = initialState;
        this.attr_initial_state     = initialState;
        this.attr_states            = new ArrayList<>();
        this.attr_input_alphabet    = (TokenAlphabet) inputAlphabet;
        this.attr_output_alphabet   = (FunctionAlphabet) outputAlphabet;
        this.attr_functions         = funtions;
        this.attr_transitions       = new Transition[states][this.attr_input_alphabet.getNumberOfSymbols()];
    }
    
    public void addState(State state) {
        this.attr_states.add(state);
    }
    
    public void addTransition(int state, int nextState, String inSymbol, String outSymbol, String machineName) {
        this.attr_transitions[state][this.attr_input_alphabet.getSymbol(inSymbol)] = new Transition(inSymbol, outSymbol, nextState, machineName);                 
    }
    
    public String analyzeSymbol(String symbol) {
        if (this.attr_states.get(this.attr_current_state) instanceof BasicState)
        {
            if (this.attr_input_alphabet.containsSymbol(symbol))
            {
                Transition transition = this.attr_transitions[this.attr_current_state][this.attr_input_alphabet.getSymbol(symbol)];
            
                if (transition != null)
                {
                    return transition.getOutputSymbol();
                }
                else
                {
                    System.out.println("Error: No transition for symbol (" + symbol + ") in state " + this.attr_current_state + "");
                    return "";
                }
            }
            else
            {
                System.out.println("Error: No input symbol (" + symbol + ") in the alphabet in machine " + this.attr_name);
                return "";
            }
        }
        
        return "";
        
    }
    
    @Override
    public void processSymbol(String symbol, Object data) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Object result;
        
        if (this.attr_states.get(this.attr_current_state) instanceof BasicState)
        {
            if (this.attr_input_alphabet.containsSymbol(symbol))
            {
                Transition transition = this.attr_transitions[this.attr_current_state][this.attr_input_alphabet.getSymbol(symbol)];
            
                if (transition != null)
                {
                    Function handler = this.attr_output_alphabet.getSymbol(transition.getOutputSymbol());
                
                    result = handler.getMethod().invoke(this.attr_functions, data);
                    
                    this.attr_current_state = transition.getTransitionState();
                }
                else
                {
                    System.out.println("Error: No transition for symbol (" + symbol + ") in state " + this.attr_current_state + "");
                }
            }
            else
            {
                System.out.println("Error: No input symbol (" + symbol + ") in the alphabet");
            }
        }
        else
        {
            OutputTransition opt = ((Machine) this.attr_states.get(this.attr_current_state)).processSymbol(symbol, this.attr_current_state, this.attr_name, data);    
            this.attr_current_state = opt.getMoveState();
            
        }
    }
    
    public int getCurrentState() {
        return this.attr_current_state;
    }
    
    public int getInitialState() {
        return this.attr_initial_state;
    }

    @Override
    public OutputTransition processSymbol(String symbol, int superState, String superName, Object data) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Object result;
        
        if (this.attr_states.get(this.attr_current_state) instanceof BasicState)
        {
            if (this.attr_input_alphabet.containsSymbol(symbol))
            {
                Transition transition = this.attr_transitions[this.attr_current_state][this.attr_input_alphabet.getSymbol(symbol)];
            
                if (transition != null)
                {
                    Function handler = this.attr_output_alphabet.getSymbol(transition.getOutputSymbol());
                
                    result = handler.getMethod().invoke(this.attr_functions, data);
                    
                    if (transition.getMachineName().matches(this.attr_name))
                    {
                        this.attr_current_state = transition.getTransitionState();
                        return new OutputTransition(superState, superName);
                    }
                    else
                    {
                        this.attr_current_state = this.attr_initial_state;
                        return new OutputTransition(transition.getTransitionState(), transition.getMachineName());
                    }
                }
                else
                {
                    System.out.println("Error: No transition for symbol (" + symbol + ") in state " + this.attr_current_state + "");
                    return new OutputTransition(superState, superName);
                }
            }
            else
            {
                System.out.println("Error: No input symbol (" + symbol + ") in the alphabet");
                return new OutputTransition(superState, superName);
            }
        }
        else
        {
            OutputTransition opt = ((Machine) this.attr_states.get(this.attr_current_state)).processSymbol(symbol, this.attr_current_state, this.attr_name, data);
            
            if (opt.getMachineName().matches(this.attr_name))
            {
                this.attr_current_state = opt.getMoveState();
                return new OutputTransition(superState, superName);
            }
            else
            {
                this.attr_current_state = this.attr_initial_state;
                return opt;
            }
        }
    }
}
