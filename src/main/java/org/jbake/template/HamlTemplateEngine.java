package org.jbake.template;

import org.apache.commons.configuration.CompositeConfiguration;
import org.jbake.app.ContentStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.Writer;
import java.util.Map;

public class HamlTemplateEngine extends AbstractJRubyTemplateEngine {

    private final static Logger LOGGER = LoggerFactory.getLogger(ModelExtractors.class);

    public HamlTemplateEngine(CompositeConfiguration config, ContentStore db, File destination, File templatesPath) {
        super(config, db, destination, templatesPath);
    }

    @Override
    public void renderDocument(Map<String, Object> model, String templateName, Writer writer) throws RenderingException {
        /*
        Object result = scriptingContainer.runScriptlet("require 'haml'\n" +
                "");
         */
        Object result = scriptingContainer.runScriptlet(
                "#!/usr/bin/env ruby\n" +
                "# The command line Haml parser.\n" +
                "\n" +
                "$LOAD_PATH.unshift File.dirname(__FILE__) + '/../lib'\n" +
                "require 'haml'\n" +
                "require 'haml/exec'\n" +
                "\n" +
                "opts = Haml::Exec::Haml.new(ARGV)\n" +
                "opts.parse!\n");
        LOGGER.info("result is " + result);
    }
}
