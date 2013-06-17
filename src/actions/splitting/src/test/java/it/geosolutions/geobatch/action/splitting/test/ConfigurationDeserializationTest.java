package it.geosolutions.geobatch.action.splitting.test;


import static org.junit.Assert.assertTrue;
import it.geosolutions.geobatch.action.splitting.SplittingConfiguration;
import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;
import it.geosolutions.geobatch.configuration.flow.file.FileBasedFlowConfiguration;
import it.geosolutions.geobatch.registry.AliasRegistry;
import it.geosolutions.geobatch.xstream.Alias;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.thoughtworks.xstream.XStream;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"test-context.xml"})

public class ConfigurationDeserializationTest {

	@Autowired
	private AliasRegistry aliasRegistry;

	@Configuration
	static class ContextConfiguration {

	}

	@Test
	public void testSplittingConfigurationDeserialization() throws Exception{
		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);
		File configFile = new File("src/test/resources/splittingflow.xml");
		FileBasedFlowConfiguration configuration = (FileBasedFlowConfiguration)xstream.fromXML(configFile);
		boolean configurationDeserialized = false;
		for(ActionConfiguration actionConfiguration : configuration.getEventConsumerConfiguration().getActions()){
			if(actionConfiguration != null && actionConfiguration instanceof SplittingConfiguration){
				configurationDeserialized = true;
				break;
			}
		}
		assertTrue(configurationDeserialized);
		
	}

}
