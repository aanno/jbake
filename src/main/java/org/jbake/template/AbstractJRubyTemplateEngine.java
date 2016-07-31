package org.jbake.template;

import org.apache.commons.configuration.CompositeConfiguration;
import org.jbake.app.ContentStore;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;

import java.io.File;

/**
 * There are several way to include embedded JRuby in Java:
 *
 * https://github.com/jruby/jruby/wiki/RedBridge
 * http://jruby.org/apidocs/org/jruby/embed/ScriptingContainer.html
 * http://jruby.org/apidocs/org/jruby/embed/package-summary.html
 * http://jruby.org/apidocs/org/jruby/javasupport/JavaEmbedUtils.html
 *
 * https://github.com/jruby/jruby/wiki/JRubyAndJavaCodeExamples
 * https://github.com/jruby/jruby/wiki/DirectJRubyEmbedding
 * https://github.com/jruby/jruby/wiki/JavaIntegration
 *
 * http://stackoverflow.com/questions/12348114/run-ruby-scripts-in-embedded-tomcat
 * http://stackoverflow.com/questions/30969834/how-do-i-use-jruby-and-java
 * http://programmers.stackexchange.com/questions/180106/how-to-execute-a-ruby-file-in-java-capable-of-calling-functions-from-the-java-p
 * http://robertpanzer.github.io/blog/2014/jrubydynamics.html
 */
public abstract class AbstractJRubyTemplateEngine extends AbstractTemplateEngine {

    protected ScriptingContainer scriptingContainer;

    public AbstractJRubyTemplateEngine(final CompositeConfiguration config, final ContentStore db, final File destination, final File templatesPath) {
        super(config, db, destination, templatesPath);

        scriptingContainer = new ScriptingContainer(LocalContextScope.CONCURRENT, LocalVariableBehavior.TRANSIENT);
    }


}
