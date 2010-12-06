/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://code.google.com/p/geobatch/
 *  Copyright (C) 2007-2008-2009 GeoSolutions S.A.S.
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

package it.geosolutions.geobatch.nurc.sem.rep10.meteoam.ecmwf.wave;

import it.geosolutions.geobatch.octave.actions.OctaveActionConfiguration;
import it.geosolutions.geobatch.octave.actions.templates.FileInFileOut.FileInFileOutAction;

import java.util.Date;


public class ECMWFWaveAction extends FileInFileOutAction {

    public ECMWFWaveAction(OctaveActionConfiguration actionConfiguration) {
        super(actionConfiguration);
    }
    
    /**
     * @return a string representing the output file name of the script
     */
    @Override
    protected String buildFileName(){
        return "rep10_ECMWFWAVE-Forecast-T" + new Date().getTime()+".nc";
    }
    
    /**
     *  set the output dir of this action
     *  @note this should be relative path as 
     *  it will be a sub dir of the working dir
     */
    @Override
    protected String getOutputDir(){
        return "nc";
    }

    @Override
    protected String getFunction() {
        return "ecmwf_wave2nc";
    }
}
