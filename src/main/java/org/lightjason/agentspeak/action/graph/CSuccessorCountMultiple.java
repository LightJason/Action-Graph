/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason AgentSpeak(L++)                                #
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
import java.util.List;


/**
 * returns the number of successors of each vertex of a single graph instance.
 * The action returns for the graph argument the number
 * of successors of the given vertices
 *
 * {@code [C1|C2] = .graph/successorcountmultiple( Graph, Vertex1, Vertex2 );}
 */
public final class CSuccessorCountMultiple extends IApplyMultiple
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -3354752194496824469L;
    /**
     * action name
     */
    private static final IPath NAME = namebyclass( CSuccessorCountMultiple.class, "graph" );

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
        p_return.add(
            CRawTerm.of(
                (double) p_graph.getSuccessorCount( p_window.get( 0 ).raw() )
            )
        );
    }
}
