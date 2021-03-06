package clara.rules;

import clojure.lang.IFn;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

/**
 * Clara rule loader. Produces a {@link WorkingMemory WorkingMemory} given one or more Clojure
 * namespaces containing rules.
 */
public class RuleLoader {

    /**
     * The clojure require function.
     */
    static final IFn require = RT.var("clojure.core", "require");

    /**
     * Function to make a new Clara session.
     */
    static final IFn makeSession;

    static {

        require.invoke(Symbol.intern("clara.rules.java"));

        makeSession = RT.var("clara.rules.java", "mk-java-session");
    }

    /**
     * Returns a new working memory with rules loaded from the given namespaces.
     *
     * @param namespaces namespaces from which to load rules
     * @return an empty working memory with rules from the given namespaces.
     */
    public static WorkingMemory loadRules(String... namespaces) {

        // Ensure requested namespaces are loaded.
        for (String namespace: namespaces)
            require.invoke(Symbol.intern(namespace));

        // Create a new working memory.
        return (WorkingMemory) makeSession.invoke(namespaces);
    }
}
