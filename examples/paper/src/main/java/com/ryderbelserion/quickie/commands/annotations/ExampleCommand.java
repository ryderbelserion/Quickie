package com.ryderbelserion.quickie.commands.annotations;

import com.ryderbelserion.quckie.annotations.Command;
import com.ryderbelserion.quickie.QuickiePlugin;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;

@Command("annotations")
public class ExampleCommand {

    private final QuickiePlugin plugin = JavaPlugin.getPlugin(QuickiePlugin.class);

    private final ComponentLogger logger = this.plugin.getComponentLogger();

    @Command("help")
    public void help() {
        this.logger.warn("<red>This is the help command!");
    }

    @Command("test")
    public void test() {
        this.logger.warn("<red>This is the test command!");
    }

    @Command("beans")
    public class InnerClass {

        private final QuickiePlugin plugin = JavaPlugin.getPlugin(QuickiePlugin.class);

        private final ComponentLogger logger = this.plugin.getComponentLogger();

        @Command("nested")
        public void nested() {
            this.logger.warn("<red>This is the nested command!");
        }

        @Command("next")
        public void next() {
            this.logger.warn("<red>This is the next command!");
        }

        @Command("easy")
        public class NestedClass {

            private final QuickiePlugin plugin = JavaPlugin.getPlugin(QuickiePlugin.class);

            private final ComponentLogger logger = this.plugin.getComponentLogger();

            @Command("small")
            public void easy() {
                this.logger.warn("<red>This is the easy command!");
            }

            @Command("supernested")
            public class SuperNestedClass {

                private final QuickiePlugin plugin = JavaPlugin.getPlugin(QuickiePlugin.class);

                private final ComponentLogger logger = this.plugin.getComponentLogger();

                @Command("superdupernested")
                public void woohooo() {
                    this.logger.warn("<red>This is the woohooo command!");
                }
            }
        }
    }
}