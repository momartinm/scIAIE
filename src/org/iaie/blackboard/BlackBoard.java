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
 *     * Neither the name of the Mario AI nor the
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

package org.iaie.blackboard;

import org.iaie.blackboard.suggestions.Suggestion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.iaie.blackboard.priority.PriorityList;

public class BlackBoard {
    
    private final Arbitrator arbitrator;
    private final Board board;
    private final List<Expert> experts;
    
    public BlackBoard(PriorityList pl) {
        this.arbitrator = new Arbitrator(pl);
        this.board = new Board();
        this.experts = new ArrayList<>();
    }
    
    public void addExpert(Expert expert) throws NoSuchMethodException, ClassNotFoundException, IOException {
        this.experts.add(expert);
    }
    
    public void run(Object data) {
        
        String symbol = "";
        
        for (int i = 0; i < this.experts.size(); i++) {
            this.board.addSuggestions(new Suggestion(i, this.experts.get(i).getAction(symbol)));
        }
        
        int suggestionChosen = this.arbitrator.chooseAction(this.board.getSuggestions());
        
        if (suggestionChosen >= 0) {
            
            Expert expert = this.experts.get(this.board.getSuggestion(suggestionChosen).getId());
            expert.executeAction(this.board.getSuggestion(suggestionChosen).getAction(), data);
        }

        this.board.removeAll();
    }
}
