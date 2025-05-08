package com.ryderbelserion.quickie.commands.annotations;

import com.ryderbelserion.quckie.annotations.Command;

@Command("annotations")
public class ExampleCommand {

    @Command("help")
    public void help() {

    }

    @Command("test")
    public void test() {

    }

    @Command("beans")
    public class InnerClass {

        @Command("nested")
        public void nested() {

        }

        @Command("next")
        public void next() {

        }

        @Command("easy")
        public class NestedClass {

            @Command("small")
            public void easy() {

            }

            @Command("supernested")
            public class SuperNestedClass {

                @Command("superdupernested")
                public void woohooo() {

                }
            }
        }
    }
}