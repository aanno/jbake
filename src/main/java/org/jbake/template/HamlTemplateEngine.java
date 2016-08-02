package org.jbake.template;

import org.apache.commons.configuration.CompositeConfiguration;
import org.jbake.app.ContentStore;
import org.jruby.RubyObject;
import org.jruby.util.NormalizedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Reference:
 * http://haml.info/docs/yardoc/file.REFERENCE.html
 *
 * Subtemplates/Includes/Inlines/Partials:
 * http://stackoverflow.com/questions/4421842/structuring-of-layout-template-in-haml
 *
 * Context/Local Variables:
 * http://stackoverflow.com/questions/16025453/how-can-i-pass-instance-variables-to-a-haml-template-on-the-command-line
 */
public class HamlTemplateEngine extends AbstractJRubyTemplateEngine {

    private final static Logger LOGGER = LoggerFactory.getLogger(ModelExtractors.class);

    public HamlTemplateEngine(CompositeConfiguration config, ContentStore db, File destination, File templatesPath) {
        super(config, db, destination, templatesPath);
    }

    @Override
    public void renderDocument(Map<String, Object> model, String templateName, Writer writer) throws RenderingException {
        Map<String, String> options = new HashMap<String, String>();
        options.put("encoding", "utf-8");
        File templateFile = new File(templatesPath, templateName);

        Object engine = scriptingContainer.runScriptlet(
                "require 'haml'\n" +
                "require 'haml/engine'\n" +
                "Haml::Engine"
        );
        RubyObject haml = null;
        try {
            haml = scriptingContainer.runRubyMethod(RubyObject.class, engine, "new",
                    readFile(templateFile), options);
        } catch (IOException e) {
            throw new RenderingException(e.toString(), e);
        }
        String result = scriptingContainer.runRubyMethod(String.class, haml, "render", null, model);
        LOGGER.info("result is " + result);
    }

    // see http://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file
    // for java 7 improvements
    private String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
}
