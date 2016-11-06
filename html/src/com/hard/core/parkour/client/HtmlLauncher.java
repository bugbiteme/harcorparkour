package com.hard.core.parkour.client;

import static com.hard.core.parkour.handlers.B2DVars.HTML_SIZE_MULTIPLYER;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.hard.core.parkour.Game;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {

                return new GwtApplicationConfiguration(480*HTML_SIZE_MULTIPLYER, 320*HTML_SIZE_MULTIPLYER);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new Game();
        }
}