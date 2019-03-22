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

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import org.lightjason.agentspeak.action.IBaseAction;
import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.error.CEnumConstantNotPresentException;
import org.lightjason.agentspeak.language.CCommon;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.fuzzy.IFuzzyValue;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * creates a graph data structure.
 * The action create a graph data structure with nodes and edges,
 * arguments are strings with the name of the graph type ( SPARSE |
 * SPARSEMULTI | DIRECTEDSPARSE | DIRECTEDSPARSEMULTI | UNDIRECTEDSPARSE |
 * UNDIRECTEDSPARSEMULTI ) on a wrong name,
 * a sparse graph is created
 *
 * {@code [G1|G2] = .graph/create( "sparse", "directedsparse" );}
 *
 * @see https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)
 */
public final class CCreate extends IBaseAction
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -8220165218772387059L;
    /**
     * action name
     */
    private static final IPath NAME = namebyclass( CCreate.class, "graph" );

    @Nonnull
    @Override
    public IPath name()
    {
        return NAME;
    }

    @Nonnegative
    @Override
    public int minimalArgumentNumber()
    {
        return 1;
    }

    @Nonnull
    @Override
    public Stream<IFuzzyValue<?>> execute( final boolean p_parallel, @Nonnull final IContext p_context,
                                           @Nonnull final List<ITerm> p_argument, @Nonnull final List<ITerm> p_return
    )
    {
        CCommon.flatten( p_argument )
               .map( ITerm::<String>raw )
               .map( i -> EGraphTypes.exist( i ) ? EGraphTypes.of( i ) : EGraphTypes.SPARSE )
               .map( EGraphTypes::get )
               .map( CRawTerm::of )
               .forEach( p_return::add );

        return Stream.of();
    }


    /**
     * graph types
     */
    private enum EGraphTypes
    {
        SPARSE,
        SPARSEMULTI,
        DIRECTEDSPARSE,
        DIRECTEDSPARSEMULTI,
        UNDIRECTEDSPARSE,
        UNDIRECTEDSPARSEMULTI;

        /**
         * graph names
         */
        private static final Set<String> TYPES = Collections.unmodifiableSet(
            Arrays.stream( EGraphTypes.values() )
                  .map( i -> i.name().toUpperCase( Locale.ROOT ) )
                  .collect( Collectors.toSet() )
        );

        /**
         * returns a new graph instance
         *
         * @return graph instance
         */
        @Nonnull
        public final Graph<?, ?> get()
        {
            switch ( this )
            {
                case SPARSE:
                    return new SparseGraph<>();

                case SPARSEMULTI:
                    return new SparseMultigraph<>();

                case DIRECTEDSPARSE:
                    return new DirectedSparseGraph<>();

                case DIRECTEDSPARSEMULTI:
                    return new DirectedSparseMultigraph<>();

                case UNDIRECTEDSPARSE:
                    return new UndirectedSparseGraph<>();

                case UNDIRECTEDSPARSEMULTI:
                    return new UndirectedSparseMultigraph<>();

                default:
                    throw new CEnumConstantNotPresentException( this.getClass(), this.toString() );
            }
        }

        /**
         * checks if a key exists
         *
         * @param p_value name value
         * @return existance flag
         */
        public static boolean exist( @Nonnull final String p_value )
        {
            return TYPES.contains( p_value.toUpperCase( Locale.ROOT ) );
        }

        /**
         * returns graph type
         *
         * @param p_value name value
         * @return graph type
         */
        @Nonnull
        public static EGraphTypes of( @Nonnull final String p_value )
        {
            return EGraphTypes.valueOf( p_value.toUpperCase( Locale.ROOT ) );
        }

    }
}
