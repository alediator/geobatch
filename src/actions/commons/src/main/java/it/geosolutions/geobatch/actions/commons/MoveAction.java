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
package it.geosolutions.geobatch.actions.commons;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.geobatch.annotations.CheckConfiguration;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;
import it.geosolutions.tools.compress.file.Extract;
import it.geosolutions.tools.io.file.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copy
 * 
 * @author Carlo Cancellieri - carlo.cancellieri@geo-solutions.it
 * 
 */
@Action(configurationClass=MoveConfiguration.class)
public class MoveAction extends BaseAction<EventObject> {

    private final static Logger LOGGER = LoggerFactory.getLogger(MoveAction.class);

    /**
     * configuration
     */
    private final MoveConfiguration conf;

    /**
     * 
     * @param configuration
     * @throws IllegalAccessException if input template file cannot be resolved
     * 
     */
    public MoveAction(MoveConfiguration configuration) throws IllegalArgumentException {
        super(configuration);
        conf = configuration;

    }
    
    @Override
	@CheckConfiguration
	public boolean checkConfiguration(){
	    LOGGER.info("Calculating if this action could be Created...");
	    return true;
	}

    /**
     * Removes TemplateModelEvents from the queue and put
     */
    public Queue<EventObject> execute(Queue<EventObject> events) throws ActionException {

        listenerForwarder.started();
        listenerForwarder.setTask("build the output absolute file name");

        // return
        final Queue<EventObject> ret = new LinkedList<EventObject>();

        listenerForwarder.setTask("Building/getting the root data structure");
        
        boolean moveMultipleFile;
        final int size=events.size();
        if (size==0){
            throw new ActionException(this, "Empty file list");
        } else if (size>1){
            moveMultipleFile=true;
        } else {
            moveMultipleFile=false;
        }
        if (conf.getDestination()==null){
            throw new IllegalArgumentException("Unable to work with a null dest dir");
        }
        if (!conf.getDestination().isAbsolute()){
            conf.setDestination(new File(this.getConfigDir(),conf.getDestination().getPath()));
            if (LOGGER.isWarnEnabled()){
                LOGGER.warn("Destination is not an absolute path. Absolutizing destination using temp dir: "+conf.getDestination());
            }
        }
        
        boolean moveToDir;
        if (!conf.getDestination().isDirectory()){
            // TODO LOG
            moveToDir=false;
            if (moveMultipleFile){
                throw new ActionException(this, "Unable to run on multiple file with an output file, use directory instead");
            }
        } else {
            moveToDir=true;
        }
        
        
        while (!events.isEmpty()) {
            listenerForwarder.setTask("Generating the output");
            
            final EventObject event=events.remove();
            if (event==null){
                // TODO LOG
                continue;
            }
            if (event instanceof FileSystemEvent){ 
                File source = ((FileSystemEvent) event).getSource();
                File dest;
                listenerForwarder.setTask("moving to destination");
                if (moveToDir){
                    dest=conf.getDestination();
                    try {
                        FileUtils.moveFileToDirectory(source, dest, true);
                    } catch (IOException e) {
                        throw new ActionException(this, e.getLocalizedMessage());
                    }
                } else if (moveMultipleFile){
                    dest=new File(conf.getDestination(),source.getPath());
                    try {
                        FileUtils.moveFile(source, dest);
                    } catch (IOException e) {
                        throw new ActionException(this, e.getLocalizedMessage());
                    }
                } else {
                    // LOG continue
                    continue;
                }

                // add the file to the return
                ret.add(new FileSystemEvent(dest, FileSystemEventType.FILE_ADDED));
            }
        }


        listenerForwarder.completed();
        return ret;
    }

}
