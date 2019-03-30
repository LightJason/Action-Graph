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
 * returns outgoing edges of a vertex of each graph.
 * The actions returns a list outgoing edges
 * of a vertex for each graph argument, the first
 * argument is the vertex, all other graphs
 *
 * {@code [OE1|OE2] = .graph/outedgessingle( Vertex, Graph1, Graph2 );}
 */
public final class COutEdgesSingle extends IApplySingle
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -540059834584856674L;
    /**
     * action name
     */
    private static final IPath NAME = namebyclass( COutEdgesSingle.class, "graph" );

    @Nonnull
    @Override
    public IPath name()
    {
        return NAME;
    }

    @Override
    protected int skipsize()
    {
        return 1;
    }

    @Override
    protected void apply( final boolean p_parallel, @Nonnull final Graph<Object, Object> p_graph,
                          @Nonnull final List<ITerm> p_window, @Nonnull final List<ITerm> p_return
    )
    {
        final List<?> l_return = new ArrayList<>( p_graph.getOutEdges( p_window.get( 0 ).raw() ) );

        p_return.add(
            CRawTerm.of(
                p_parallel
                ? Collections.synchronizedList( l_return )
                : l_return
            )
        );
    }
}
