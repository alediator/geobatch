/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2007-2012 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.geosolutions.geobatch.flow.event.consumer;

public enum EventConsumerStatus {
    /**
     * Idle EventConsumerStatus CODE
     */
    IDLE,

    /**
     * Waiting EventConsumerStatus CODE
     */
    WAITING,

    /**
     * Processing EventConsumerStatus CODE
     */
    EXECUTING,

    /**
     * Finished OK EventConsumerStatus CODE
     */
    COMPLETED,

    /**
     * Finished OK EventConsumerStatus CODE
     */
    FAILED,
    /*
     * added by Carlo on 02 02 2011
     * these status are inherited from the EventConsumer
     * interface methods
     * isPaused()
     * isCanceled()
     */
    /**
     * isCanceled()
     */
    CANCELED,
    /**
     * isPaused()
     */
    PAUSED;

}
