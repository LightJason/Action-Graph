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
import java.util.List;


/**
 * returns multiple vertex degrees of a single graph.
 * The action returns for each vertex the degree of the
 * graph reference within the first argument
 *
 * {@code [D1|D2] = .graph/degreemultiple( Graph, Vertex1, Vertex2 );}
 */
public final class CDegreeMultiple extends IApplyMultiple
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 4311346775363807891L;
    /**
     * action name
     */
    private static final IPath NAME = namebyclass( CDegreeMultiple.class, "graph" );

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
                (double) p_graph.degree( p_window.get( 0 ).raw() )
            )
        );
    }
}
