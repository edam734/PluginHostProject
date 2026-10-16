package com.edam.pluginhost.handler;

import com.edam.pluginhost.plugin.PluginManager;
import com.edam.pluginhost.watcher.event.AppEvent;
import com.edam.pluginhost.watcher.event.CommandEvent;
import com.edam.pluginhost.watcher.event.PluginEvent;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EventHandler {


    private final PluginManager pluginManager;

    public EventHandler(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }


    public void handle(AppEvent event) throws IOException {
        switch (event.getType()) {
            case PLUGIN -> handlePluginEvent((PluginEvent) event);
            case USER_INPUT -> handleUserInput((CommandEvent) event);
        }
    }

    private void handlePluginEvent(PluginEvent event) throws IOException {
        Path jarPath = Paths.get("plugins/" + event.path().toString());

        switch (event.kind()) {
            case CREATED -> pluginManager.addPlugin(jarPath);
            case MODIFIED -> pluginManager.updatePlugin(jarPath);
            case DELETED -> pluginManager.removePlugin(jarPath);
            case OVERFLOW -> pluginManager.overflow(jarPath);
        }
    }

    private void handleUserInput(CommandEvent event) {
        // TODO
    }
}

