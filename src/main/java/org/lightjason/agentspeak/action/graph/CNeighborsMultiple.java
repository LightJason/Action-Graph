/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason                                                #
 * # Copyright (c) 2015-19, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.agentspeak.action.graph;

import edu.uci.ics.jung.graph.Graph;
import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ITerm;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * returns the neighbors of each vertex of single graph instance.
 * The actions returns a list of neighbors of each vertex for
 * a single graph argument, the first argument is the graph,
 * all other vertices
 *
 * {@code [N1|N2] = .graph/neighborsmultiple( Graph, Vertex1, Vertex2 );}
 */
public final class CNeighborsMultiple extends IApplyMultiple
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -8428326199496471618L;
    /**
     * action name
     */
    private static final IPath NAME = namebyclass( CNeighborsMultiple.class, "graph" );

    @Nonnull
    @Override
    public IPath name()
    {
        return NAME;
    }

    @Override
    protected int windowsize()
    {
        return 1;
    }

    @Override
    protected void apply( final boolean p_parallel, @Nonnull final Graph<Object, Object> p_graph,
                          @Nonnull final List<ITerm> p_window, @Nonnull final List<ITerm> p_return
    )
    {
        final List<?> l_return = new ArrayList<>( p_graph.getNeighbors( p_window.get( 0 ).raw() ) );

        p_return.add(
            CRawTerm.of(
                p_parallel
                ? Collections.synchronizedList( l_return )
                : l_return
            )
        );
    }
}
