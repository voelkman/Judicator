/*
 * Created on 30.07.2008
 */
package de.voelkman.utils.generator.smith;

import java.util.HashMap;
import javax.enterprise.inject.Typed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Typed(IGeneratorLibrary.class)
public class GeneratorFactory implements IGeneratorLibrary {

    private HashMap<String, Generator> generators = new HashMap<String, Generator>();
    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);
    
    public GeneratorFactory() {
    }

    @Override
    public Generator getGenerator(String name) {
        if (!generators.containsKey(name)) {
            generators.put(name, new Generator(name, this));
            LOG.debug(name + " generated");
        }
        return generators.get(name);
    }

    public String getResult(String generator) {
        return getGenerator(generator).evaluate();
    }

    public static void setWorkingBase(String base) {
        Generator.setWorkingBase(base);
    }
}
