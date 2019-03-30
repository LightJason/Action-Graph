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

import com.codepoetics.protonpack.StreamUtils;
import edu.uci.ics.jung.graph.Graph;
import org.lightjason.agentspeak.action.IBaseAction;
import org.lightjason.agentspeak.language.CCommon;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.fuzzy.IFuzzyValue;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Stream;


/**
 * apply class for a single graph with multiple elements
 */
public abstract class IApplyMultiple extends IBaseAction
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -7023140668269162676L;

    @Nonnegative
    @Override
    public final int minimalArgumentNumber()
    {
        return 1;
    }

    @Nonnull
    @Override
    public final Stream<IFuzzyValue<?>> execute( final boolean p_parallel, @Nonnull final IContext p_context,
                                                 @Nonnull final List<ITerm> p_argument, @Nonnull final List<ITerm> p_return
    )
    {
        StreamUtils.windowed(
            CCommon.flatten( p_argument ).skip( 1 ),
            this.windowsize(),
            this.windowsize()
        )
                   .forEach( i -> this.apply( p_parallel, p_argument.get( 0 ).<Graph<Object, Object>>raw(), i, p_return ) );

        return Stream.of();
    }



    /**
     * window size
     *
     * @return size
     */
    protected abstract int windowsize();


    /**
     * apply call
     *
     * @param p_parallel parallel execution
     * @param p_graph graph instance
     * @param p_window window list
     * @param p_return return list
     */
    protected abstract void apply( final boolean p_parallel, @Nonnull final Graph<Object, Object> p_graph,
                                   @Nonnull final List<ITerm> p_window, @Nonnull final List<ITerm> p_return
    );

}
